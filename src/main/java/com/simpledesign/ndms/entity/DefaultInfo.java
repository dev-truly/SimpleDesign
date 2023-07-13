package com.simpledesign.ndms.entity;

import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
public class DefaultInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
}
