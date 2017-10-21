package com.gl.extrade.common.redis;

public class User {
	private Long oid;
	private String uname;
	private String psword;

	public User() {
		super();
	}
	
	

	public User(Long oid, String uname, String psword) {
		super();
		this.oid = oid;
		this.uname = uname;
		this.psword = psword;
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
	
	

	public Long getOid() {
		return oid;
	}



	public void setOid(Long oid) {
		this.oid = oid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((oid == null) ? 0 : oid.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (oid == null) {
			if (other.oid != null)
				return false;
		} else if (!oid.equals(other.oid))
			return false;
		return true;
	}



	@Override
	public String toString() {
		return "User [oid=" + oid + ", uname=" + uname + ", psword=" + psword + "]";
	}

}
