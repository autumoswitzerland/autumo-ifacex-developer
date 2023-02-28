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
 
/**
 * Exclusion filter interface - implementation decides if a record is
 * ignored on the reader side and hence not added to processing.
 */
public interface ExclusionFilter {

	/**
	 * Decision if record should be added.
	 * 
	 * @param fields the fields of the record
	 * @param values the values of the record
	 * @return true, if record should be added, else false.
	 */
	public boolean addRecord(String fields[], String values[]);

}
