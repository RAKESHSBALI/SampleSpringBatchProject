package com.bali.apps.springbatch.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter @Setter
public class Nifty50 {

    @Id
    @GeneratedValue
    private Long id;

    private String companyName;
    private String industry;
    private String symbol;
    private String isinCode;
}
