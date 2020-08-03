package sg.edu.rp.webservices.c302_p11_firebasefirestore;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;

public class Inventory {
    private String id;
    private double cost;
    private String name;
    private ArrayList<String> options;

    public Inventory(String id, double cost, String name, ArrayList<String> options) {
        this.id = id;
        this.cost = cost;
        this.name = name;
        this.options = options;
    }

    public Inventory(double cost, String name, ArrayList<String> options) {
        this.cost = cost;
        this.name = name;
        this.options = options;
    }

    @Exclude
    public String getId() {
        return id;
    }

    public double getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getOptions() {
        return options;
    }


}
