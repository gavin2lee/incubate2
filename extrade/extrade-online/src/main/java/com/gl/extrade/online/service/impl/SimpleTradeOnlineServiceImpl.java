package com.gl.extrade.online.service.impl;

import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gl.extrade.online.config.SysConfig;
import com.gl.extrade.online.service.SimpleTradeOnlineService;
import com.gl.extrade.spec.dto.Response;
import com.gl.extrade.spec.dto.TradeRequest;
import com.gl.extrade.spec.dto.TradeResponse;
import com.gl.extrade.spec.exception.BizException;

public class SimpleTradeOnlineServiceImpl implements SimpleTradeOnlineService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleTradeOnlineServiceImpl.class);

    public TradeResponse process(TradeRequest tradeRequest) throws BizException {
        if(LOG.isInfoEnabled()){
            LOG.info("process - {}", tradeRequest);
        }
        
        TradeResponse resp = internalProcess(tradeRequest);
        
        if(LOG.isInfoEnabled()){
            LOG.info("processed return - {}", resp);
        }
        
        return resp;
    }
    
    private TradeResponse internalProcess(TradeRequest tradeRequest){
        TradeResponse resp = new TradeResponse();
        resp.setRetCode(Response.RetCode.OK);
        String retMsg = "server-"+SysConfig.SERVER_ID+"-"+(new Random().nextInt());
        resp.setRetMsg(retMsg);
        
        return resp;
    }

}
