package com.gl.exdos.api.dto.auth;

import com.gl.exdos.api.dto.common.BaseDto;

public class AuthenticationToken extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8023978569697915778L;
	
	private String username;
	private String token;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}

}
