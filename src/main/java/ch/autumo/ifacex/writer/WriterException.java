/**
 * Copyright 2020 autumo GmbH, Michael Gasche.
 * All Rights Reserved.
 * 
 * NOTICE: All information contained herein is, and remains
 * the property of autumo GmbH The intellectual and technical
 * concepts contained herein are proprietary to autumo GmbH
 * and are protected by trade secret or copyright law.
 * 
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from autumo GmbH.
 * 
 */
package ch.autumo.ifacex.writer;

import ch.autumo.ifacex.IfaceXException;

/**
 * Writer exception.
 * 
 * Use this exception when you have to throw an exception
 * in your writer.
 */
public class WriterException extends IfaceXException {

	private static final long serialVersionUID = 7626324635647708647L;

	private int code;
	private String rawMessage;

	public WriterException(String message) {
		super(message);
	}

	public WriterException(String message, Throwable cause) {
		super(message, cause);
	}

	public WriterException(String message, int code) {
		super(message);
		this.code = code;
	}

	public WriterException(String message, String rawMessage) {
		super(message);
		this.rawMessage = rawMessage;
	}

	public int getCode() {
		return code;
	}

	public String getRawMessage() {
		return rawMessage;
	}

	public void setRawMessage(String rawMessage) {
		this.rawMessage = rawMessage;
	}
	
}
