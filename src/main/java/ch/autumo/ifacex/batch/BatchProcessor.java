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
package ch.autumo.ifacex.batch;

import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.writer.WriterException;


/**
 * Batch processor; 1 Interface Processor (IP) = 1 BatchProcessor.
 * 
 * Processing batches for all IP writers serially or in parallel.
 */
public interface BatchProcessor {

	/**
	 * Check if batch processor is in parallel mode.
	 * 
	 * @return true, if in parallel mode, otherwise false
	 */
	boolean isInParallelMode();

	/**
	 * You must tell this batch processor that no data can be fetched
	 * from the reader; no data is coming at all! The batch processor
	 * then can properly end its work when it is in parallel mode!
	 */
	void noDataIsComing();
	
	/**
	 * Process a batch of records for a given source entity to all 
	 * output writers and destination interfaces. Source entities 
	 * are processed in the order of configuration, so batches of a 
	 * given entity are processed one after the other and not mixed!
	 * batches of a specific entity are subsequent and not mixed!
	 * 
	 * You must specify if more data is following after a batch or
	 * if it is the last one ever for all entities! No new entities
	 * are following either in this case!
	 * 
	 * @param batch batch data
	 * @param entity source entity
	 * @param boolean moreData true, if more batches are following, false if it is the last batch to process
	 * @throws WriterException
	 */
	void processBatchData(BatchData batch, SourceEntity entity, boolean moreData) throws IfaceXException;
	
}
