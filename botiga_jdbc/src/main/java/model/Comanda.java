package model;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class Comanda {
    private int id;
    private int clientId;
    private Timestamp data;
    private BigDecimal total;
    private List<LiniaComanda> linies = new ArrayList<>();

    public Comanda() {
    }

    public Comanda(int clientId) {
        this.clientId = clientId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int c) {
        this.clientId = c;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp d) {
        this.data = d;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal t) {
        this.total = t;
    }

    public List<LiniaComanda> getLinies() {
        return linies;
    }

    public void addLinia(LiniaComanda l) {
        linies.add(l);
    }

    public String toString() {
        return "Comanda{id=" + id + ", clientId=" + clientId + ", data=" + data + ", total=" + total + ", linies="
                + linies + "}";
    }
}
