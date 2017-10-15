package com.gl.extrade.frontend.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gl.extrade.common.BizConstants;
import com.gl.extrade.frontend.service.SimpleTradeFrontendService;
import com.gl.extrade.spec.dto.TradeRequest;
import com.gl.extrade.spec.dto.TradeResponse;
import com.gl.extrade.spec.exception.BizException;
import com.gl.extrade.spec.service.SimpleTradeService;

public class SimpleTradeFrontendServiceImpl implements SimpleTradeFrontendService {
    
    private static final Logger LOG = LoggerFactory.getLogger(SimpleTradeFrontendServiceImpl.class);
    
    private SimpleTradeService simpleTradeService;

    public String trade(String requestBody) {
        TradeRequest tradeRequest = new TradeRequest();
        tradeRequest.setCreatedTimeMillis(System.currentTimeMillis());
        tradeRequest.setLifeCycle(BizConstants.TradeLifeCycle.LC_NEW);
        tradeRequest.setProductCode("100010");
        tradeRequest.setTradeId("90000111112220");
        
        try {
            TradeResponse tradeResponse = simpleTradeService.process(tradeRequest);
            return tradeResponse.toString();
        } catch (BizException e) {
            LOG.error("", e);
        }
        return null;
    }

    public void setSimpleTradeService(SimpleTradeService simpleTradeService) {
        this.simpleTradeService = simpleTradeService;
    }
    
}
