package com.bali.apps.springbatch.processor;


import com.bali.apps.springbatch.entity.Nifty;
import com.bali.apps.springbatch.model.NiftyDTO;
import org.springframework.batch.item.ItemProcessor;

public class NiftyItemProcessor implements ItemProcessor<NiftyDTO, Nifty> {

    @Override
    public Nifty process(final NiftyDTO niftyDTO) throws Exception {
        return Nifty.builder()
                .companyName(niftyDTO.getCompanyName())
                .industry(niftyDTO.getIndustry())
                .symbol(niftyDTO.getSymbol())
                .isinCode(niftyDTO.getIsinCode())
                .build();
    }

}
