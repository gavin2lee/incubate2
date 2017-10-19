package com.gl.exdos.api.service;

import com.gl.exdos.api.dto.common.Message;
import com.gl.exdos.api.exception.CommonRpcException;

public interface AuthenticationService {
	Message authenticate(Message token) throws CommonRpcException;
}
