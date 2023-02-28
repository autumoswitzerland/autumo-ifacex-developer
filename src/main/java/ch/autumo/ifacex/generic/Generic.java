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
package ch.autumo.ifacex.generic;

import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.Processor;


/**
 * Generic reader/writer interface.
 */
public interface Generic {

	/**
	 * Overall initialization of this reader/writer.
	 * Initialize configuration for the reader/writer interface.
	 * Open here reusable connections or streams too.
	 * 
	 * This method is called once for every new reader/writer.
	 * 
	 * @param rwName reader or writer name
	 * @param config IPC
	 * @param processor processor
	 * @throws IfaceXException
	 */
	public abstract void initialize(String rwName, IPC config, Processor processor) throws IfaceXException;

	/**
	 * Close the reader/writer. If necessary, close all streams
	 * and connections that have been opened for this reader or 
	 * writer in the {@link #initialize(String, IPC, Processor)}
	 * method.
	 * 
	 * This method is called once for every reader/writer.
	 * 
	 * On the reader-side, close is only called after all data has
	 * been read from the source.
	 * 
	 * On the writer-side, close is only called after all data has
	 * been written an there is no data batch left in the batch
	 * processor in parallel batch mode.
	 * 
	 * Close is only called after all entities have been processed,
	 * so watch out, if you need top close resources (e.g., files)
	 * per entity!
	 * 
	 * @param rwName reader or writer name
	 * @throws IfaceXException
	 */
	public void close(String rwName) throws IfaceXException;
	
}
