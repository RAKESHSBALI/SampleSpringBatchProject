package com.bali.apps.springbatch.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class NiftyDTO {

    private String companyName;
    @NonNull
    private String industry;
    private String symbol;
    private String series;
    private String isinCode;
}
