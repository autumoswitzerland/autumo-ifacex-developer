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
import ch.autumo.ifacex.Processor;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.batch.BatchData;


/**
 * EXAMPLE writer.
 */
public class ExampleWriter implements Writer {
	
	@Override
	public void initialize(String writerName, IPC config, Processor processor) throws IfaceXException {
		
		// TODO initialize the writer, e.g. read general config value, etc.
		String v = config.getValue("any_keye");
		if (v.equals("any_value"))
			System.out.println("You got any value! :)");
		
		// TODO for all other config values, you should use only reader configuration
		//config.getReaderConfig().getXYZ();
	}

	@Override
	public void initializeEntity(String writerName, IPC config, SourceEntity entity) throws IfaceXException {

		// TODO prepare output for specific entity, e.g. the output file
		//entity.getEntity()    // get entity name
	}

	@Override
	public void writeHeader(String writerName, IPC config, SourceEntity entity) throws IfaceXException {
		
		// TODO Write a header if necessary
	}

	@Override
	public void writeBatchData(String writerName, IPC config, BatchData batch, SourceEntity entity) throws IfaceXException {
		
		// Example batch processing - no mapping is done here, it is 1 to 1.
		while (batch.hasNext()) {

			final String record[] = batch.next();
			int len = record.length;
			
			String result = "";
			for (int i = 0; i < len; i++) {
				if (i+1 == len)
					result += record[i];
				else
					result += record[i] + config.getCSVDelimiterChar();
			}
			
			// TODO: Write the batch to the destination
			System.out.println(result);
		}
	}
	
	@Override
	public void close(String writerName) throws IfaceXException {
		
		// TODO Close the writer!
	}

}
