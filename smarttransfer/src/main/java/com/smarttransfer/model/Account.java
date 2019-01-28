package com.smarttransfer.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Version;
import java.io.Serializable;

/**
 * Created by jonathasalves on 26/01/2019.
 */
@Entity
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    @Version
    private long versionID;

    private double balance;

    public Account(){}

    public Account(double balance) {
        this.balance = balance;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public long getVersionID() { return versionID; }

    public void setVersionID(long versionID) { this.versionID = versionID; }

    @Override
    public String toString(){
        return id + " " + balance;
    }
}
