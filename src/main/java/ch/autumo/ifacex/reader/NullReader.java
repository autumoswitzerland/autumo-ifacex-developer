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
import ch.autumo.ifacex.Processor;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.batch.BatchData;
import ch.autumo.ifacex.batch.BatchProcessor;

/**
* Reads NADA & processes only the first entity with an empty batch of data.
*/
public class NullReader implements Reader {

	private boolean first = true;
	
	@Override
	public void initialize(String readerName, IPC config, Processor processor) throws IfaceXException {
	}

	@Override
	public void initializeEntity(String readerName, IPC config, SourceEntity entity) throws IfaceXException {
	}

	@Override
	public void read(String readerName, BatchProcessor batchProcessor, IPC config, SourceEntity entity, 
			boolean hasMoreEntities) 
			throws IfaceXException  {
		
		if (first)
			batchProcessor.processBatchData(new BatchData(config), entity, false);
		
		first = false;
	}

	@Override
	public void close(String readerName) throws IfaceXException {
	}
	
}
