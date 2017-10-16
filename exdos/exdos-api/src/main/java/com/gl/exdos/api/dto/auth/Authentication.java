package com.gl.exdos.api.dto.auth;

import java.util.ArrayList;
import java.util.List;

import com.gl.exdos.api.dto.common.BaseDto;

public class Authentication extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7037549105532180038L;
	
	private AuthenticationToken authToken;
	
	private boolean authenticated;
	
	
	private List<Authority> authorities = new ArrayList<Authority>();


	public AuthenticationToken getAuthToken() {
		return authToken;
	}


	public void setAuthToken(AuthenticationToken authToken) {
		this.authToken = authToken;
	}


	public boolean isAuthenticated() {
		return authenticated;
	}


	public void setAuthenticated(boolean authenticated) {
		this.authenticated = authenticated;
	}


	public List<Authority> getAuthorities() {
		return authorities;
	}


	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
	
}
