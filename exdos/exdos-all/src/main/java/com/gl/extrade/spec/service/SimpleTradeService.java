package com.gl.extrade.spec.service;

import com.gl.extrade.spec.dto.TradeRequest;
import com.gl.extrade.spec.dto.TradeResponse;
import com.gl.extrade.spec.exception.BizException;

public interface SimpleTradeService {
    TradeResponse process(TradeRequest tradeRequest) throws BizException;
}
