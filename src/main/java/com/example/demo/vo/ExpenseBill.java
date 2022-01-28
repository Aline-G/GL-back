package com.example.demo.vo;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    private String name;
    private String description;
    @OneToMany
    @JoinTable(
            name = "line_in_expense_bill",
            joinColumns = @JoinColumn(name = "expense_id"),
            inverseJoinColumns = @JoinColumn(name = "line_id"))
    private List<LineBill> listLineBill;
    // TODO liste d'avances
    private String date;
    private float amount;
    private BillStates state;
    //TODO User


}
