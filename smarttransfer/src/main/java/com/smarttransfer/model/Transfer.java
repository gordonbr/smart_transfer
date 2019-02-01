package com.smarttransfer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by jonathasalves on 27/01/2019.
 */
@Entity
public class Transfer {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_source", referencedColumnName = "id")
    private Account accountSource;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "account_target", referencedColumnName = "id")
    private Account accountTarget;

    private double value;
    private Date timestamp;

    public Transfer() {}

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public Account getAccountSource() { return accountSource; }

    public void setAccountSource(Account accountSource) { this.accountSource = accountSource; }

    public Account getAccountTarget() { return accountTarget; }

    public void setAccountTarget(Account accountTarget) { this.accountTarget = accountTarget; }

    public double getValue() { return value; }

    public void setValue(double value) { this.value = value; }

    public Date getTimestamp() { return timestamp; }

    public void setTimestamp(Date timestamp) { this.timestamp = timestamp; }
}
