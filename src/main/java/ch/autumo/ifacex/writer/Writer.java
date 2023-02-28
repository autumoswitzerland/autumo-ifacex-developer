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

import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.WriterMapping;
import ch.autumo.ifacex.batch.BatchData;
import ch.autumo.ifacex.generic.Generic;

/**
 * Writer interface.
 * 
 * Destination writers must implement this interface.
 * You must also implement the methods from {@link Generic} interface.
 */
public interface Writer extends Generic {
	
	/**
	 * Initialize the writer with a new entity being processed.
	 * 
	 * Initialize configuration for the entity and do other
	 * entity-related preparations. On the writer's side, this
	 * usually means reading an output mapping. See also
	 * {@link {@link WriterMapping}. 
	 * 
	 * This method is only called when a new entity is being processed.
	 *  
	 * @param writerName writer name
	 * @param config IPC
	 * @param entity source entity
	 * @throws IfaceXException 
	 */
	public void initializeEntity(String writerName, IPC config, SourceEntity entity) throws IfaceXException;

	/**
	 * Write a header for a certain source entity if necessary.
	 * 
	 * This method is only called when a new entity is being processed.
	 * 
	 * @param writerName writer name
	 * @param config IPC
	 * @param entity source entity
	 * @throws IfaceXException 
	 */
	public void writeHeader(String writerName, IPC config, SourceEntity entity) throws IfaceXException;
	
	/**
	 * Write a batch.
	 * 
	 * This method is called as long there are data batches available 
	 * from the source/reader.
	 * 
	 * Source entities are processed in the order of configuration,
	 * so batches of a given entity are processed one after the other 
	 * and not mixed!
	 * 
	 * Though, in parallel batch mode it is not guaranteed that the
	 * batches are written in the order they have been read!
	 * 
	 * Note: It is not allowed to operate a mail writer in parallel 
	 * batch mode! When a mail-writer is used, then the whole IPC will
	 * be forced to run in serial batch mode. 
	 * 
	 * @param writerName writer name
	 * @param config IPC
	 * @param batch batch data
	 * @param entity source entity
	 * @throws IfaceXException
	 */
	public void writeBatchData(String writerName, IPC config, BatchData batch, SourceEntity entity) throws IfaceXException;
	
}
