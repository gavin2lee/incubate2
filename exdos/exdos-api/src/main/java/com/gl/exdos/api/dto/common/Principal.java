package com.gl.exdos.api.dto.common;

public class Principal extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7284210680343886625L;

	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
