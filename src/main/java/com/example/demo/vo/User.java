package com.example.demo.vo;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;
    private String name;
    private String firstname;
    private String mail;
    // private Object status;
    // @ManyToOne
    // @JoinColumn(name = "work_team_id")
    // private Team workTeam;

}

