package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Airplane {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String model;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="aircompany_id")
    private Aircompany aircompany;

}
