package com.gl.exdos.api.dto.common;

public abstract class Message extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3230043444976381950L;
	
	protected MessageHeader header;
	
	protected MessageBody body;

	public MessageHeader getHeader() {
		return header;
	}

	public void setHeader(MessageHeader header) {
		this.header = header;
	}

	public MessageBody getBody() {
		return body;
	}

	public void setBody(MessageBody body) {
		this.body = body;
	}
}
