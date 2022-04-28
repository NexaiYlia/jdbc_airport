package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Aircompany {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String name;
    @OneToMany(mappedBy = "airplane")
    private List<Airplane> airplaneList;

}
