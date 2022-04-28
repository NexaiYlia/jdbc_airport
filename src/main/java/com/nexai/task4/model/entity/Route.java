package com.nexai.task4.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Route {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private String cityFrom;
    @Column
    private String cityTo;
    @Column
    private int price;

}
