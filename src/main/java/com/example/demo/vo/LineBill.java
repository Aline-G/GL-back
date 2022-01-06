package com.example.demo.vo;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Float tvaPercent;
    private Float tva;
    private LocalDate date;
    //TODO private Mission mission; à faire lors de l'implémentation de mission
    private String country;
    //TODO
    private String justificatif;

}
