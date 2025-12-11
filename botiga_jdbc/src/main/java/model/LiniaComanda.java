package model;

import java.math.BigDecimal;

public class LiniaComanda {
    private int producteId;
    private int quantitat;
    private BigDecimal preuUnitari;

    public LiniaComanda() {
    }

    public LiniaComanda(int producteId, int quantitat, BigDecimal preuUnitari) {
        this.producteId = producteId;
        this.quantitat = quantitat;
        this.preuUnitari = preuUnitari;
    }

    public int getProducteId() {
        return producteId;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public BigDecimal getPreuUnitari() {
        return preuUnitari;
    }

    public String toString() {
        return "LiniaComanda{producteId=" + producteId + ", quantitat=" + quantitat + ", preuUnitari=" + preuUnitari
                + "}";
    }
}
