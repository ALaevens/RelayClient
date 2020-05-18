package com.gearnotes2000.relayclient.model;

public class Relay {
    private int id;
    private String label;

    public Relay(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public Relay(int id) {
        this(id, "Unlabeled Relay");
    }

    public int getId() {return id;}
    public String getLabel() {return label;}
}
