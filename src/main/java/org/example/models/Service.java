package org.example.models;

import java.util.HashMap;
import java.util.Map;

public class Service {
    private final Map<String, Branch> branches = new HashMap<>();
    private String name;

    public Service(String name) {
        this.name = name;
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
}
