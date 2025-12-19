package dao;

import util.Connexio;
import model.*;
import java.sql.*;
import java.math.BigDecimal;
import java.util.List;

public class ComandaTransaccioDAO {
    private final ProducteDAO producteDAO = new ProducteDAO();

    public int crearComandaSimple(int clientId, List<LiniaComanda> linies, boolean forzarErrorNotificacio)
            throws SQLException {
        String insCom = "INSERT INTO comandes (client_id, total) VALUES (?, 0)";
        String insLin = "INSERT INTO linies_comanda (comanda_id,producte_id,quantitat,preu_unitari) VALUES (?,?,?,?)";
        String updTot = "UPDATE comandes SET total=? WHERE id=?";
        try (Connection conn = Connexio.getConnection()) {
            conn.setAutoCommit(false);
            try {
                int comandaId;
                try (PreparedStatement ps = conn.prepareStatement(insCom, Statement.RETURN_GENERATED_KEYS)) {
                    ps.setInt(1, clientId);
                    ps.executeUpdate();
                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        if (rs.next()) {
                            comandaId = rs.getInt(1);
                        } else {
                            throw new SQLException("Failed to get generated key");
                        }
                    }
                }
                BigDecimal total = BigDecimal.ZERO;
                for (LiniaComanda l : linies)
                    producteDAO.decrementaEstoc(conn, l.getProducteId(), l.getQuantitat());
                try (PreparedStatement ps = conn.prepareStatement(insLin)) {
                    for (LiniaComanda l : linies) {
                        ps.setInt(1, comandaId);
                        ps.setInt(2, l.getProducteId());
                        ps.setInt(3, l.getQuantitat());
                        ps.setBigDecimal(4, l.getPreuUnitari());
                        ps.addBatch();
                        total = total.add(l.getPreuUnitari().multiply(new BigDecimal(l.getQuantitat())));
                    }
                    ps.executeBatch();
                }
                Savepoint sp = conn.setSavepoint("DespresLinies");
                try {
                    if (forzarErrorNotificacio)
                        throw new SQLException("Error notificació forçat");
                    try (PreparedStatement pst = conn.prepareStatement(updTot)) {
                        pst.setBigDecimal(1, total);
                        pst.setInt(2, comandaId);
                        pst.executeUpdate();
                    }
                    conn.commit();
                    return comandaId;
                } catch (SQLException ex) {
                    conn.rollback(sp);
                    try (PreparedStatement pst = conn.prepareStatement(updTot)) {
                        pst.setBigDecimal(1, total);
                        pst.setInt(2, comandaId);
                        pst.executeUpdate();
                    }
                    conn.commit();
                    return comandaId;
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
