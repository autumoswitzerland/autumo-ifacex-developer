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

import ch.autumo.ifacex.ExclusionFilter;
import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.Processor;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.batch.BatchData;
import ch.autumo.ifacex.batch.BatchProcessor;

/**
 * EXAMPLE reader.
 */
public class ExampleReader implements Reader {
	
	private int batchSize = 0;
	private int dummyCount = 0;

	private String fields[] = null;
	
	private ExclusionFilter exFilter = null;

	
	@Override
	public void initialize(String readerName, IPC config, Processor processor) throws IfaceXException {
		
		// TODO initialize the writer, e.g. read general config value, etc.
		//config.getValue(key);
		
		// TODO for all other config values, you should use only writer configuration
		//config.getWriterConfig().getXYZ();
		
		batchSize = config.getGeneralBatchSize();		
	}

	@Override
	public void initializeEntity(String readerName, IPC config, SourceEntity entity) throws IfaceXException {
		
		// TODO prepare input for specific entity, e.g. the input file
		//entity.getEntity()    // get entity name
		
		int i = 0;
		final int size = config.getReaderConfig().getHeaderSize();
		String header = null;
		
		while (i < size && (header = getNextRecord()) != null) {
			
			// we assume field names are in last header !
			if (i == size - 1) {
				fields = config.getCSVSepValues(header, true);
			}
			i++;
		}
		
		exFilter = config.getReaderConfig().getExclusionFilter(entity);
	}

	@Override
	public void read(String readerName, BatchProcessor batchProcessor, IPC config, SourceEntity entity, 
			boolean hasMoreEntities) 
			throws IfaceXException {
		
		// TODO: Read data to fill a batch and process batch by calling 'processBatchData',
		// then read further data to create a new batch until all data is consumed
		// from the source entity.
		
		String record = null;
		
		// at start we have more data
		boolean hasMoreData = true;

		// process batches as long we have data
		while (hasMoreData) {
			
			// create a new batch
			final BatchData batch = new BatchData(config);
			
			// fill a whole batch or partial at EOF
			BATCH: for (int i = 0; i < batchSize; i++) {

				// read data
				record = getNextRecord(); // read records from somewhere

				// check record
				if (record != null) {
					
					// we still have data to process
					String values[] = config.getCSVSepValues(record);
					
					// MUST: make modifications based on configuration, must be called in every reader !
					for (int j = 0; j < values.length; j++) {
						values[i] = batch.modifyValueBeforeAdding(values[i], fields[i]);
					}
					
					// SHOULD: Call configured exclusion filter, if you want to filter data based on
					// exclusion filters
					if (exFilter != null && exFilter.addRecord(fields, values))
						// MUST: Must be called always! With or without exclusion filters!
						batch.addRecordValues(values);
					
				} else {
					// no more data, stop reading data!
					hasMoreData = false;
					break BATCH;
				}
			}
			// process batch
			batchProcessor.processBatchData(batch, entity, hasMoreData || hasMoreEntities);
		}
	}

	private String getNextRecord() {
		
		String result = null;
		
		// get data from somewhere ...
		if (dummyCount == 0)
			result = "id;company;email;domain";
		else if (dummyCount < 256 && dummyCount > 0)
			result = "1;autumo GmbH;@info@autumo.ch;autumo.ch";
		dummyCount++;
		
		return result;
	}

	@Override
	public void close(String readerName) throws IfaceXException {
		
		// TODO e.g. delete files or close open connections to databases, etc.
	}
	
}
