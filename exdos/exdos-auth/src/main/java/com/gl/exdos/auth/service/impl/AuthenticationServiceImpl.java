package com.gl.exdos.auth.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gl.exdos.api.dto.common.Message;
import com.gl.exdos.api.exception.CommonRpcException;
import com.gl.exdos.api.service.AuthenticationService;

public class AuthenticationServiceImpl implements AuthenticationService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	public Message authenticate(Message msg) throws CommonRpcException {
		logger.info("recv:{}", msg);
		
		String body = msg.getBody().getContent();
		return null;
	}

}
