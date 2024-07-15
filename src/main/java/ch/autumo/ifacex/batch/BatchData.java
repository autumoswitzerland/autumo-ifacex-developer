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
package ch.autumo.ifacex.batch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ch.autumo.commons.utils.system.OSUtils;
import ch.autumo.ifacex.Configuration;
import ch.autumo.ifacex.Constants;
import ch.autumo.ifacex.IPC;


/**
 * Batch data. The order of the source values are 
 * preserved within the batch!
 * 
 * Batches are created by a reader and added to a
 * batch processor.
 */
public class BatchData implements Iterator<String[]> {

	private List<String[]> data = new ArrayList<String[]>();
	private Iterator<String[]> iterator = null;
	
	private boolean firstIterCall = true;
	
	private String batch = null;
	
	private boolean first = false;
	private boolean last = false;
	
	private boolean replace = false;
	private String oldD; 
	private String newD; 
	private boolean cleanNullValues = true;

	private boolean mapCountry;
	private List<String> countryFieldNames; 
	private String defaultIso;
	
	private String csvDelim = ";";
	
	// has this batch been processed?
	private volatile boolean processed = false;
	

	/**
	 * Private copy constructor.
	 */
	private BatchData() {
	}

	/**
	 * Create a new batch of data.
	 * 
	 * @param config config
	 */
	public BatchData(IPC config) {
		
		replace = ((Configuration) config).replaceChar();
		oldD = ((Configuration) config).getOldChar();
		newD = ((Configuration) config).getNewChar();
		
		cleanNullValues = ((Configuration) config).cleanNullValues();
		
		mapCountry = ((Configuration) config).map2ISOCodes();
		countryFieldNames = ((Configuration) config).getSourceCountryFieldsList();
		defaultIso = ((Configuration) config).getMapDefaultIso();
		if (defaultIso == null)
			defaultIso = Constants.DEFAULT_ISO;
		
		defaultIso = defaultIso.toUpperCase();
		
		csvDelim = config.getCSVDelimiterChar();
	}

	/**
	 * Call this method for EVERY value BEFORE it is added to this to
	 * a record array and added to this batch and before adding this
	 * batch to the batch processor on the reader-side only!
	 * 
	 * Helper method, that doesn't modify this batch, only the given
	 * value that is possibly modified and returned.
	 * 
	 * @param val batch value.
	 * @param fieldName source (reader) field name
	 * @return
	 */
	public String modifyValueBeforeAdding(String val, String fieldName) {
		
		if (val == null)
			return "";

		val = val.trim();
		
		// 'null' string values are just futile, always, fight me!
		if (cleanNullValues && val.equalsIgnoreCase("null")) 
			return "";
		
		// replace char ?
		if (replace && val.indexOf(oldD) != -1) {
			val = val.replaceAll(oldD, newD);
		}
		
		if (mapCountry && countryFieldNames.contains(fieldName)) {
			
			if (Configuration.containsGeodata(val))
				val = Configuration.getGeodata(val).getISO().toUpperCase();
			else
				val = defaultIso;
		}
		
		return val;
	}
	
	/**
	 * Add method.
	 * 
	 * @param record record
	 */
	public void addRecordValues(List<String> record) {
		this.addRecordValues(record.toArray(new String[record.size()]));
	}
	
	/**
	 * Main add method (all add calls end here).
	 * 
	 * @param record record
	 */
	public void addRecordValues(String record[]) {
		data.add(record);
	}
	
	@Override
	public boolean hasNext() {
		if (firstIterCall) {
			iterator = data.iterator();
			firstIterCall = false;
		}
		return iterator.hasNext();
	}

	@Override
	public String[] next() {
		return iterator.next();
	}
	
	
	/**
	 * Amount of records in the batch data.
	 * 
	 * @return size of batch
	 */
	public int size() {
		return data.size();
	}
	
	/**
	 * Has this batch been processed?
	 * 
	 * Don't bother, the batch processor is
	 * using this method.
	 * 
	 * @return true if processed
	 */
	protected boolean isProcessed() {
		return processed;
	}
	
	/**
	 * First batch being processed
	 * for all entities?
	 * 
	 * @return true if so
	 */
	public boolean isFirst() {
		return first;
	}
	
	/**
	 * Last batch being processed
	 * for all entities?
	 * 
	 * @return true if so
	 */
	public boolean isLast() {
		return last;
	}

	/**
	 * Don't bother, the batch processor is
	 * marking this batch as the first batch!
	 */
	void markFirst() {
		this.first = true;
	}
	
	/**
	 * Don't bother, the batch processor is
	 * marking this batch as the last batch!
	 */
	void markLast() {
		this.last = true;
	}
	
	/**
	 * Mark this batch as processed.
	 * 
	 * Don't bother, the batch processor is
	 * marking this batch as processed!
	 * 
	 * @param processed processed flag
	 */
	void markProcessed() {
		this.processed = true;
	}
	
	/**
	 * Copy this batch.
	 * 
	 * Don't bother, the batch processor is
	 * copying batches only. Note that not all
	 * instance values are copied!
	 * 
	 * Don't used it.
	 * 
	 * Only the following values are copied:
	 * - Value delimiter for string representation
	 * - String representation
	 * - All records of the batch
	 * 
	 * @return copied batch
	 */
	BatchData copy() {
		
		final BatchData newData = new BatchData();
		
		newData.csvDelim = this.csvDelim;
		
		// we don't need to copy infos for modifyng, 
		// only the first batch created in the reader
		// needs this data.
		
		newData.batch = this.batch;
		
		int len = data.size();
		for (int i = 0; i < len; i++) {
			newData.data.add(this.data.get(i));
		}
		
		return newData;
	}
	
	/**
	 * Batch representation. Records are shown
	 * as CSV records.
	 * 
	 * @return batch representation
	 */
	public final String toString() {
		
		if (batch == null) {
			batch = "";
			int datalen = data.size();
			for (int l = 0; l < datalen; l++) {
				final String record[] = data.get(l);
				int len = record.length;
				for (int i = 0; i < len; i++) {
					if (i+1 == len)
						batch += record[i];
					else
						batch += ( record[i] + csvDelim );
				}
				batch += OSUtils.LINE_SEPARATOR;				
			}
		}
		
		if (batch.length() != 0)
			return batch.substring(0, batch.length()-1);

		else return "";
	}

	
}
