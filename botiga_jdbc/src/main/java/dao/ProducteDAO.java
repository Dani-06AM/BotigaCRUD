package dao;

import util.Connexio;
import model.Producte;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class ProducteDAO {
    public List<Producte> llistar() throws SQLException {
        String sql = "SELECT id,nom,preu,estoc FROM productes ORDER BY id";
        List<Producte> l = new ArrayList<>();
        try (Connection conn = Connexio.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                l.add(new Producte(rs.getInt("id"), rs.getString("nom"), rs.getBigDecimal("preu"), rs.getInt("estoc")));
            }
        }
        return l;
    }

    public Producte buscarPerId(int id) throws SQLException {
        String sql = "SELECT id,nom,preu,estoc FROM productes WHERE id=?";
        try (Connection conn = Connexio.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return new Producte(rs.getInt("id"), rs.getString("nom"), rs.getBigDecimal("preu"),
                            rs.getInt("estoc"));
            }
        }
        return null;
    }

    public void decrementaEstoc(Connection conn, int producteId, int quantitat) throws SQLException {
        String sel = "SELECT estoc FROM productes WHERE id=? FOR UPDATE";
        try (PreparedStatement ps = conn.prepareStatement(sel)) {
            ps.setInt(1, producteId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int e = rs.getInt("estoc");
                    if (e < quantitat)
                        throw new SQLException("Estoc insuficient");
                } else
                    throw new SQLException("Producte no trobat");
            }
        }
        String upd = "UPDATE productes SET estoc=estoc-? WHERE id=?";
        try (PreparedStatement ps2 = conn.prepareStatement(upd)) {
            ps2.setInt(1, quantitat);
            ps2.setInt(2, producteId);
            ps2.executeUpdate();
        }
    }
}
