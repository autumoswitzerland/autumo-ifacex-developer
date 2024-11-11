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
package ch.autumo.ifacex.reader;

import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.batch.BatchData;
import ch.autumo.ifacex.batch.BatchProcessor;
import ch.autumo.ifacex.generic.Generic;

/**
 * Reader interface.
 * 
 * Source readers need to inherit from this class.
 * You must also implement the methods from {@link Generic} interface.
 */
public interface Reader extends Generic {
	
	/**
	 * Initialize the reader with a new entity being processed.
	 * 
	 * Initialize configuration for the entity and do other
	 * entity-related preparations.
	 * 
	 * This method is only called when a new entity is being processed.
	 * 
	 * @param readerName reader name
	 * @param config IPC
	 * @param entity source entity
	 * @throws IfaceXException
	 */
	public abstract void initializeEntity(String readerName, IPC config, SourceEntity entity) throws IfaceXException;
	
	/**
	 * Read the whole entity (e.g. a table) and process every {@link BatchData} created
	 * with {@link BatchProcessor#processBatchData(BatchData, SourceEntity, boolean)}.
	 * 
	 * In order that value-altering configuration takes place, you need to call
	 * {@link BatchData#modifyValueBeforeAdding(String, String)} for every single data 
	 * value you add to a batch!
	 * 
	 * This method is called once for every source entity, hence all data must be read
	 * in a loop and all data batches created to process further with the batch processor.
	 * 
	 * Source entities are processed in the order of configuration.
	 * 
	 * @param readerName reader name
	 * @param batchProcessor batch processor that must process every single batch created by this reader
	 * @param config IPC
	 * @param entity source entity
	 * @param hasMoreEntities true, when more entities are following, otherwise false
	 * @throws IfaceXException 
	 */
	public abstract void read(
			String readerName, 
			BatchProcessor batchProcessor, 
			IPC config, 
			SourceEntity entity, 
			boolean hasMoreEntities) throws IfaceXException;
	
}
