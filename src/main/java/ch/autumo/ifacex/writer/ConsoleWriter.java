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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.Processor;
import ch.autumo.ifacex.SourceEntity;
import ch.autumo.ifacex.batch.BatchData;

/**
 * Console writer.
 */
public class ConsoleWriter implements Writer {

	private final static Logger LOG = LoggerFactory.getLogger(ConsoleWriter.class.getName());

	private String delim;
	private String fields[];
	
	@Override
	public void initialize(String writerName, IPC config, Processor processor) throws IfaceXException {
		
		delim = config.getCSVDelimiterChar();
	}
	
	@Override
	public void initializeEntity(String writerName, IPC config, SourceEntity entity) throws IfaceXException {
		
		fields = config.getWriterConfig(writerName).getFields(entity);
		
		if (fields == null || fields.length == 0) {
			
			LOG.info("WRITER '"+writerName+"' - No output field names specified, taking source field names for header.");
			fields = entity.getSourceFields();
		}
	}	

	@Override
	public void writeHeader(String writerName, IPC config, SourceEntity entity) throws IfaceXException {

		// write header
		System.out.println("[Entity: " + entity.getEntity() + "]");
		
		if (fields != null && fields.length > 0) {
			String header = "";
			int len = fields.length;
			for (int i = 0; i < len; i++) {
				if (i+1 == len)
					header += fields[i];
				else
					header += fields[i] + delim;
			}
			System.out.println(header);
		}
	}
	
	@Override
	public void writeBatchData(String writerName, IPC config, BatchData batch, SourceEntity entity) throws IfaceXException {
		
		// Log to console
		System.out.println(batch.toString());
	}

	@Override
	public void close(String writerName) throws IfaceXException {
	}

}
