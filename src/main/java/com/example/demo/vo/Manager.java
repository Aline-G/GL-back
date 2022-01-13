package com.example.demo.vo;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Manager extends User {

    @OneToMany(mappedBy = "manager") private List<ExpenseBill> billsToValidate = new java.util.ArrayList<>();

    public void validateBill(ExpenseBill bill) {
        if (billsToValidate.contains(bill)) {
            // TODO other stuff
            bill.setState(BillStates.VALIDATED);
            billsToValidate.remove(bill);
        } else {
            // TODO error ?
        }
    }
}

