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
package ch.autumo.ifacex;

import java.util.Arrays;
import java.util.List;


/**
 * Source entity to process.
 */
public final class SourceEntity implements Comparable<String> {
	
	/**
	 * Used for 'file_in' reader.
	 */
	public static final String FILES_SOURCE_FIELDS[] = new String[] {"filepath"};
	
	private IPC config = null;
	
	private boolean first = false;
	private boolean last = false;
	
	private String entity = null;
	private String sourceFields[] = null;

	/**
	 * Create a source entity.
	 * Entities are created by the Interface Processor.
	 * 
	 * @param config IPC
	 * @param entity entity name
	 * @param sourceFields all source fields (ordered)
	 */
	public SourceEntity(IPC config, String entity, String sourceFields[]) {
		super();
		this.config = config;
		this.entity = entity;
		this.sourceFields = sourceFields;
	}

	/**
	 * Get IPC.
	 * 
	 * @return IPC
	 */
	public IPC getConfiguration() {
		return config;
	}

	/**
	 * Get entity name.
	 * 
	 * @return entity name
	 */
	public String getEntity() {
		return entity;
	}
	
	/**
	 * Get all source fields.
	 * 
	 * @return source fields
	 */
	public String[] getSourceFields() {
		return sourceFields;
	}

	/**
	 * Get all source fields as a list.
	 * 
	 * @return source field list
	 */
	public List<String> getSourceFieldsList() {
		return Arrays.asList(sourceFields);
	}
	
	/**
	 * Do we have a certain field with given field name?
	 * 
	 * @param fieldName field name
	 * @return true if so
	 */
	public boolean containsSourceField(String fieldName) {
		
		for (int i = 0; i < sourceFields.length; i++) {
			if (sourceFields[i].equals(fieldName))
				return true;
		}
		return false;
	}
	
	/**
	 * Get the index of a source field name.
	 * 
	 * @param fieldName field name
	 * @return index
	 */
	public int indexOfSourceField(String fieldName) {
		
		for (int i = 0; i < sourceFields.length; i++) {
			if (sourceFields[i].equals(fieldName))
				return i;
		}
		return -1;
	}

	/**
	 * Overwrite source fields. Can be done for special cases.
	 * The CSV reader does this in certain cases.
	 *  
	 * @param sourceFields new source fields
	 */
	public void overwriteSourceFields(String sourceFields[]) {
		this.sourceFields = sourceFields;
	}
	
	/**
	 * First entity being processed?
	 * 
	 * @return true if so
	 */
	public boolean isFirst() {
		return first;
	}
	
	/**
	 * Last entity being processed?
	 * 
	 * @return true if so
	 */
	public boolean isLast() {
		return last;
	}
	
	
	/**
	 * Don't bother, the interface processor is
	 * marking this entity the first entity!
	 */
	void markFirst() {
		this.first = true;
	}
	
	/**
	 * Don't bother, the interface processor is
	 * marking this entity the last entity!
	 */
	void markLast() {
		this.last = true;
	}
	
	
	@Override
	public final int compareTo(String anotherEntity) {
		return entity.compareTo(anotherEntity);
	}
	
	@Override
	public final boolean equals(Object o) {
		if (o == this)
            return true;
        if (!(o instanceof SourceEntity))
            return false;
		return entity.equals(((SourceEntity) o).getEntity());
	}

	@Override
	public final String toString() {
		return entity;
	}
	
}
