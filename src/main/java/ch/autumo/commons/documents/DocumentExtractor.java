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
package ch.autumo.commons.documents;

import java.io.File;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.lucene.document.Document;


/**
 * Extract a document.
 */
public interface DocumentExtractor {

	/**
	 * 'autumo Documents' module document source fields.
	 */
	public static final String DOCUMENTS_SOURCE_FIELDS[] 
			= new String[] {"title", "author", "subject", "category", "comments", "description", "keywords", "contents"};
	
	/**
	 * 'autumo Documents' module document all fields.
	 * Additional: file name and file extension before source fields.
	 */
	public static final String DOCUMENTS_ALL_FIELDS[] 
			= ArrayUtils.addAll(new String[] {"filename", "fileext"}, DOCUMENTS_SOURCE_FIELDS);
	
	/**
	 * Extract document to the lucene fields {@link #DOCUMENTS_SOURCE_FIELDS}
	 * 
	 * @param file file
	 * @return extracted document
	 * @throws Exception
	 */
	public Document extract(File file) throws Exception;
	
}
