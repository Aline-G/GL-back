package com.expensebills.back.vo;

import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LineBill {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private Float amount;
    private LineBillSates state;
    private LineBillCategory category;
    private Float amountWithoutTaxes;
    private Float tva;
    private LocalDate date;
    private String description;
    private int km;
    private int fiscalHorsepower;
    private String registrationNumber;
    private String conveyance;
    private String restoPlace;
    private String hebergementPlace;
    private String vehicle;
    private String paymentMethod;
    private String guestsName;
    //TODO justificatif
    //private String supportingDocuments;

    @ManyToOne
    @JoinTable(
            name = "line_for_mission",
            joinColumns = @JoinColumn(name = "line_id"),
            inverseJoinColumns = @JoinColumn(name = "mission_id"))
    private Mission mission;
    private String country;
    private int idExpenseBill;


}
