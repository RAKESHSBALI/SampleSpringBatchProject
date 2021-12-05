package com.bali.apps.springbatch.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter @Builder
public class Nifty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String companyName;
    private String industry;

    @Column(nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String isinCode;
}
