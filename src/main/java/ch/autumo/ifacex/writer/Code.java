/**
 * Copyright 2023 autumo GmbH, Michael Gasche.
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

import ch.autumo.ifacex.IPC;


/**
 * Code interface used for providing own implementations executing
 * any code with an the code runner (writer).
 */
public interface Code {

	/**
	 * Initialize.
	 * 
	 * @param rwName the code runner's name (writer name)
	 * @param config IPC configuration
	 * @throws Exception
	 */
	public void initialize(String rwName, IPC config) throws Exception;

	/**
	 * Execute.
	 * 
	 * @param rwName the code runner's name (writer name)
	 * @throws Exception
	 */
	public void execute(String rwName) throws Exception;

	/**
	 * Finish. E.g. close streams and connections.
	 * 
	 * @param rwName the code runner's name (writer name)
	 * @throws Exception
	 */
	public void finish(String rwName) throws Exception;
	
}
