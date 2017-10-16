package com.gl.exdos.api.dto.common;

import java.util.Date;

public class MessageBody extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6018540182793454937L;

	private String bizSeq;

	private Date time;

	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getBizSeq() {
		return bizSeq;
	}

	public void setBizSeq(String bizSeq) {
		this.bizSeq = bizSeq;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
