package org.example.booker;

import org.example.models.Branch;

public interface Booker {
    double book(Branch branch, String vehicleType, int start, int end);
}
