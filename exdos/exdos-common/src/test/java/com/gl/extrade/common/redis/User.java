package com.gl.extrade.common.redis;

public class User {
	private String uname;
	private String psword;

	public User() {
		super();
	}

	public User(String uname, String psword) {
		super();
		this.uname = uname;
		this.psword = psword;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname;
	}

	public String getPsword() {
		return psword;
	}

	public void setPsword(String psword) {
		this.psword = psword;
	}

	@Override
	public String toString() {
		return "User [uname=" + uname + ", psword=" + psword + "]";
	}

}
