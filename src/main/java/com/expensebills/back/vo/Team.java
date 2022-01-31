package com.expensebills.back.vo;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "leader_id")
    private Manager leader;
}

