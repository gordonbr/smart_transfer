package com.smarttransfer.model;

/**
 * Created by jonathasalves on 27/01/2019.
 */
public class TransferModel {

    private long idSource;
    private long idTarget;
    private double value;

    public long getIdSource() {
        return idSource;
    }

    public void setIdSource(long idSource) {
        this.idSource = idSource;
    }

    public long getIdTarget() {
        return idTarget;
    }

    public void setIdTarget(long idTarget) {
        this.idTarget = idTarget;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
