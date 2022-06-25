package org.example.models;

import org.example.booker.Booker;

import java.util.HashMap;
import java.util.Map;

public class Service {
    private final Map<String, Branch> branches = new HashMap<>();
    private String name;

    private final Booker booker;

    public Service(String name, Booker booker) {
        this.name = name;
        this.booker = booker;
    }

    public boolean hasBranch(String branch) {
        return branches.containsKey(branch);
    }

    public void addBranch(Branch newBranch) {
        this.branches.put(newBranch.getName(), newBranch);
    }

    public Branch getBranch(String branchName) {
        return branches.get(branchName);
    }

    public Booker getBooker() {
        return this.booker;
    }
}
