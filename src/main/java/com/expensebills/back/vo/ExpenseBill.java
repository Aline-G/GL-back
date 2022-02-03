package com.expensebills.back.vo;

import lombok.*;

import javax.persistence.*;
import java.util.List;


@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExpenseBill {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    @OneToMany
    @JoinTable(
            name = "line_in_expense_bill",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    private List<LineBill> listLineBill;
    @OneToMany
    @JoinTable(
            name = "advance_in_expense_bill",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "advance_id"))
    private List<Advance> listAdvance;
    private String date;
    private float amount;
    private BillStates state;

    /*@ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;*/

    private int  userId;

    //TODO User


}
