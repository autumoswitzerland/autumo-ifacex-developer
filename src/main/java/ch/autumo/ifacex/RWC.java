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
package ch.autumo.ifacex;

import java.util.List;

import ch.autumo.commons.utils.UtilsException;


/**
 * The Reader/Writer configuration object.
 * 
 * The term 'rw-prefix' means Reader/Writer prefix, which can be, and in the
 * context of a reader or writer, e.g.:
 * 
 * - null_in
 * - csv_in
 * - file_in
 * - doc_in
 * - db_in
 * - mongo_in
 * - rest_in
 * - first_out
 * - console_out
 * - csv_out
 * - mail_out
 * - file_out
 * - db_out
 * - mongo_out
 * - rest_out
 * - code
 * - exec
 * - ...and more.

 * 
 * See also interfaces configuration 'cfg/interfaces.cfg'.
 * 
 * See also {@link IPC}.
 */
public interface RWC {

	
	//------------------------------------------------------------------------------
	// General configuration
	//------------------------------------------------------------------------------

	/**
	 * This method can be used for ifaceX developers to get any
	 * configuration parameter within a reader or writer.
	 * 
	 * Possible configuration values are 'yes' or 'no' - case-insensitive.
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @return true if value was 'yes', otherwise, inclusive all other values, false.
	 */
	boolean isYes(String postfix);
	
	/**
	 * This method can be used for ifaceX developers to get any
	 * configuration parameter within a reader or writer.
	 * 
	 * Possible configuration values are 'yes' or 'no' - case-insensitive.
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @param defaultValue default value; true or false
	 * @return true if value was 'yes', otherwise, inclusive all other values, false.
	 */
	boolean isYes(String postfix, boolean defaultValue);
	
	/**
	 * This method can be used for ifaceX developers to get any
	 * configuration parameter within a reader or writer.
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @return configuration value
	 */
	String getConfig(String postfix);

	/**
	 * This method can be used for ifaceX developers to get any
	 * configuration parameter within a reader or writer.
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @param defaultValue default value
	 * @return configuration value or default value if value is not found or invalid
	 */
	String getConfig(String postfix, String defaultValue);
	
	/**
	 * This method can be used for ifaceX developers to get any
	 * configuration parameter within a reader or writer that is
	 * possibly decrypted.
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @return (encrypted) configuration value
	 * @throws UtilsException
	 */
	String getConfigDecodedIfNecessary(String postfix) throws UtilsException;
	
	/**
	 * This method can be used for ifaceX developers to get any
	 * number configuration parameter within a reader or writer.
	 * This method return -1, if the configuration value isn't found. 
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @return configuration number value
	 */
	int getNumber(String postfix);

	/**
	 * This method can be used for ifaceX developers to get any
	 * number configuration parameter within a reader or writer.
	 * This method returns the default value, if the configuration 
	 * value isn't found. 
	 *  
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @param defaultValue default number value
	 * @return configuration number value
	 */
	int getNumber(String postfix, int defaultValue);
	
	/**
	 * Get comma-separated configuration values.
	 * 
	 * @param postfix the post-fix, e.g., '_my_variable'.
	 * @return separated values
	 */
	String[] getSeparatedValues(String postfix);
	
	/**
	 * Get value for '<rw-prefix>_<source-entity>_fields'.
	 * PS: Source-entity name is the source entity ID.
	 * 
	 * @return source fields
	 */
	String[] getFields(String entityId);

	/**
	 * Get value for '<rw-prefix>_<source-entity>_fields'.
	 * 
	 * @return source fields
	 */
	String[] getFields(SourceEntity entity);
	
	/**
	 * Get the exclusion filter if available.
	 * 
	 * @param entity source entity
	 * @return exclusion filter or null
	 * @throws IfaceXException
	 */
	ExclusionFilter getExclusionFilter(SourceEntity entity) throws IfaceXException;

	/**
	 * Get data output mapping.
	 * 
	 * @param entity source entity
	 * @return data output mapping
	 * @throws IfaceXException
	 */
	WriterMapping getMapping(SourceEntity entity) throws IfaceXException;

	/**
	 * Get the value for '<rw-prefix>_params'.
	 * 
	 * @return params value
	 */
	List<String[]> getParams();

	/**
	 * Get values for '<rw-prefix>_dest_filter_fields'.
	 * 
	 * @param entity source entity
	 * @return destination filter fields
	 */
	String[] getDestFilterFields(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_dest_unique_id_field'.
	 * 
	 * @param entity source entity
	 * @return destination unique ID field
	 */
	String getDestUniqueIdField(SourceEntity entity);

	/**
	 * Is a single ID filter used? A single ID filter means, that
	 * there mustn't created some-kind of where clause; e.g.:
	 * 
	 * "3" in https:/host/entity/3
	 * 
	 * instead of
	 * 
	 * "filter1=value1&filter2=value2&filter3=value3"
	 * in https:/host/entity/filter1=value1&filter2=value2&filter3=value3
	 * 
	 * Yes/no-value.
	 * 
	 * @return true if so
	 */
	boolean isSingleIdFilter();

	/**
	 * Get value for
	 *   '<rw-prefix>_<source-entity-name>_fetchSize';
	 * if not available, try to get value for 
	 *   '<rw-prefix>_*_fetchSize';
	 *   
	 * @param entity source entity
	 * @return record fetch size for a specific entity 
	 */
	int getFetchSize(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_max_contents_length'.
	 * 
	 * @return max. content length (max. characters)
	 */
	int getMaxContentsLength();

	/**
	 * Get value for '<rw-prefix>_url'.
	 * 
	 * @return URL
	 */
	String getUrl();

	/**
	 * Get value for '<rw-prefix>_user', possibly already
	 * decrypted!
	 * 
	 * @return user name
	 * @throws UtilsException
	 */
	String getUser() throws UtilsException;

	/**
	 * Get value for '<rw-prefix>_password', possibly already
	 * decrypted!
	 * 
	 * @return password
	 * @throws UtilsException
	 */
	String getPassword() throws UtilsException;

	/**
	 * Decrypt a given value if it is encrypted; enclosed by '{...}'.
	 * 
	 * @return decrypted value
	 * @throws UtilsException
	 */
	String getPossiblyEncryptedValue(String value) throws UtilsException;

	/**
	 * Get value for '<rw-prefix>_order_field'.
	 * 
	 * @return order field
	 */
	String getOrderField(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_host'.
	 * 
	 * @return host
	 */
	String getHost();

	/**
	 * Get value for '<rw-prefix>_auth_host'.
	 * 
	 * @return authentication host
	 */
	String getAuthHost();

	/**
	 * Get value for '<rw-prefix>_auth_grant_type'.
	 * 
	 * @return authentication grant type
	 */
	String getAuthGrantType();

	/**
	 * Get values for '<rw-prefix>_auth_body'.
	 * The list holds string arrays with a key-value pair.
	 * 
	 * In this case, values are separated by ':' and 
	 * key-value-pairs by '|'.
	 * 
	 * @return authentication body values
	 */
	List<String[]> getAuthBody();

	/**
	 * Get value for '<rw-prefix>_port'.
	 * 
	 * @return port number
	 */	
	int getPort() throws IfaceXException;

	/**
	 * Get value for '<rw-prefix>_protocol'.
	 * E.g., 'https'.
	 * 
	 * @return protocol
	 */	
	String getProtocol();

	/**
	 * Get value for '<rw-prefix>_timeout'.
	 * 
	 * @return timeout
	 */	
	int getTimeout();

	/**
	 * Get value for '<rw-prefix>_delete_files_after_processing'.
	 * 
	 * Yes/no value.
	 * 
	 * @return true if so
	 */	
	boolean deleteAfterProcessing();
	
	
	//------------------------------------------------------------------------------
	// Document configuration
	//------------------------------------------------------------------------------

	/**
	 * Get document extractor plug-in class configuration;
	 * value for '<rw-prefix>_extractor_implementation'.
	 * Only used by the document reader 'doc_in'.
	 * 
	 * @return document extractor plug-in class
	 */
	String getDocumentExtractor();
	
	
	//------------------------------------------------------------------------------
	// Code/Exec configuration
	//------------------------------------------------------------------------------

	/**
	 * Get code implementation class configuration;
	 * value for '<rw-prefix>_implementation'.
	 * Used by the code writer/runner 'code'.
	 * 
	 * @return code implementation class
	 */
	String getImplementation();
	
	/**
	 * Get the processing position; '<rw-prefix>_proc_pos'.
	 * 
	 * '0': At the beginning of processing (first batch of first entity)
	 * '1': At the end of processing (last batch of last entity)
	 * 
	 * If no value is configured, '0' is returned. 
	 * 
	 * @return processing position
	 */
	int getProcessingPosition();

	
	//------------------------------------------------------------------------------
	// Mail configuration
	//------------------------------------------------------------------------------
	
	/**
	 * Is authentication used?
	 * Get yes/no-value for '<rw-prefix>_auth'.
	 * 
	 * @return true if so
	 */
	boolean getAuth();

	/**
	 * Get value for '<rw-prefix>_body'.
	 * A body that must be sent.
	 * 
	 * @return body
	 */
	String getBody();

	/**
	 * Get value for '<rw-prefix>_from'.
	 * From email.
	 * 
	 * @return from email
	 */
	String getFrom();

	/**
	 * Get max. email size;
	 * value for '<rw-prefix>_max_size'.
	 * 
	 * @return max email size
	 */
	double getMaxSize();

	/**
	 * Get receiver email addresses;
	 * value for '<rw-prefix>_to'.
	 * 
	 * @return receiver email addresses
	 */
	String[] getTo();

	/**
	 * Get email subject;
	 * value for '<rw-prefix>_subject'.
	 * 
	 * @return email subject
	 */
	String getSubject();

	/**
	 * Is TLS enabled?
	 * Get yes/no-value for '<rw-prefix>_tls_enable'.
	 * 
	 * @return true if so
	 */
	boolean getTlsEnable();

	
	//------------------------------------------------------------------------------
	// Database configuration (Mongo and Relational)
	//------------------------------------------------------------------------------
	
	/**
	 * Get value for
	 *   '<rw-prefix>_<source-entity-name>_create_collection';
	 * if not available, try to get value for 
	 *   '<rw-prefix>_*_create_collection';
	 *   
	 * Create a new Mongo DB collection if it doesn't exist yet?
	 * Yes/no value.
	 * 
	 * @param entity source entity
	 * @return true, if so
	 */
	boolean isCreateCollection(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_db_name'.
	 * 
	 * @return database name
	 */
	String getDatabaseName();

	/**
	 * Get value for '<rw-prefix>_ext_insert_mapping';
	 * Is extended database insert mapping used?
	 * Values such as 'NOW()' and 'NULL' are interpreted
	 * when catch new insert values from data output mapping.
	 * 
	 * Yes/no value.
	 * 
	 * @return true, if so
	 */
	boolean getExtendedInsertMapping();

	//------------------------------------------------------------------------------
	// CSV configuration
	//------------------------------------------------------------------------------

	/**
	 * Get value for '<rw-prefix>_header_size'.
	 * 
	 * @return header size for CSVs
	 */
	int getHeaderSize();

	/**
	 * Get full path file name for entity:
	 * '<folder>/<rw-prefix>_<source-entity-name>.<extension>'
	 * 
	 * The folder is read from '<rw-prefix>_read_from_folder'.
	 * '/' characters are replaced with '-' in source entity!
	 * 
	 * @param entity entity
	 * @param extension without '.', e.g 'csv'
	 * @return file name
	 */
	String getFile(SourceEntity entity, String extension);

	/**
	 * Get full path file name with destination entity name included.
	 * 
	 * A folder value will be read from '<rw-prefix>_read_from_folder'.
	 * 
	 * Then the value of '<folder>/<rw-prefix>_<destination-entity>.<extension>'
	 * will be produced and returned.
	 * '/' characters are replaced with '-' in source entity!
	 * 
	 * @param destEntity destEntity
	 * @param extension without '.', e.g 'csv'
	 * @return file name with full path
	 */
	String getFileWithDestName(String destEntity, String extension);

	/**
	 * Get full path file name with source entity name included.
	 * 
	 * The value of '<rw-prefix>_read_from_writer' will be read and 
	 * this value should be a writer-prefix, it is then further used to
	 * produce the value '<writer-prefix>_<source-entity>.<extension>'!
	 * 
	 * A folder value will be read from '<rw-prefix>_read_from_folder'.
	 * 
	 * The result value created then is:
	 * '<folder>/<writer-prefix>_<source-entity>.<extension>'.
	 * 
	 * Should only be used by readers to pipe CSV's that have been written
	 * by a writer; those reader input files will have a writer-prefix e.g.,
	 * 'csv_out_person.csv'.
	 * 
	 * @param entity source entity
	 * @param extension without '.', e.g 'csv'
	 * @return file name with full path
	 */
	String getFileFromWriter(SourceEntity entity, String extension);

	/**
	 * A folder is read from '<rw-prefix>_read_from_folder', if it isn't found,
	 * the value 'files/' is used.
	 * 
	 * The returning value produced is:
	 * '<folder>/<source-entity>.<extension>' 
	 * 
	 * @param entity entity
	 * @param extension without '.', e.g 'csv'
	 * @return file name with full path
	 */
	String getFileFromCustomFolder(SourceEntity entity, String extension);

	/**
	 * The folder is read from '<rw-prefix>_read_from_folder', if it isn't found,
	 * the value of 'files_folder' from 'cfg/ifacex.cfg' is used.
	 * 
	 * @return folder path
	 */
	String getCustomFolder();
	
	/**
	 * Get full path file name for entity:
	 * '<folder>/<rw-prefix>_<source-entity-name>_2.<extension>'.
	 * '/' characters are replaced with '-' in source entity!
	 * 
	 * The folder is read from '<rw-prefix>_read_from_folder'.
	 * 
	 * @param entity entity
	 * @param extension without '.', e.g 'csv'
	 * @return file name
	 */
	String getSecondaryFile(SourceEntity entity, String extension);

	
	//------------------------------------------------------------------------------
	// REST configuration
	//------------------------------------------------------------------------------
	
	/**
	 * Get value for '<rw-prefix>_api_key'.
	 * You'll receive the decrypted value, if it is encrypted 
	 * in the configuration file.
	 * 
	 * @return API key
	 */
	String getApiKey() throws UtilsException;

	/**
	 * Get value for '<rw-prefix>_api_key_name'.
	 * 
	 * @return API key name
	 */
	String getApiKeyName();

	
	//------------------------------------------------------------------------------
	// REST API configuration
	//------------------------------------------------------------------------------
	
	/**
	 * Get value for '<rw-prefix>_auth_url'.
	 * 
	 * @return authentication URL
	 */
	String getAuthUrl();

	/**
	 * Get value for '<rw-prefix>_entities_url'.
	 * 
	 * @return entities URL, the relative part where entities can be fetched
	 */
	String getEntitiesUrl();

	/**
	 * Get value for '<rw-prefix>_<source-entity>_entities_url'.
	 * If not found, try get a value from '<rw-prefix>_*_entities_url'.
	 * 
	 * On the REST reader side!
	 * 
	 * @return entity URL, the relative part where a specific entity can be fetched
	 */
	String getEntitiesUrl(SourceEntity entity) throws IfaceXException;

	/**
	 * Get value for '<rw-prefix>_<mapping-destination-entity>_entities_url'.
	 * If not found, try get a value from '<rw-prefix>_*_entities_url'.
	 * 
	 * On the REST writer side!
	 * 
	 * @return entity URL, the relative part where a specific entity can be fetched
	 */
	String getEntitiesUrl(WriterMapping mapping) throws IfaceXException;

	
	//------------------------------------------------------------------------------
	// Generic REST API
	//------------------------------------------------------------------------------
	
	/**
	 * Get value for '<rw-prefix>_auth_method'.
	 * 
	 * @return authentication method
	 */
	String getAuthMethod();

	/**
	 * Get value for '<rw-prefix>_param_fetchSize'.
	 * Parameter name for fetch size URL parameter.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_param_fetchSize'.
	 * 
	 * @return URL parameter fetch size
	 */
	String getParamFetchSize(SourceEntity entity);
	
	/**
	 * Get value for '<rw-prefix>_additional_params'.
	 * Additional URL parameters that can be sent.
	 * Key-value pairs are separated by '=' multiple key-value-pairs
	 * are separated by '&'.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_additional_params'.
	 * 
	 * @return URL parameter fetch size
	 */
	List<String[]> getAdditionalParams(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_additional_headers'.
	 * Additional HTTP headers that can be sent.
	 * Key-value pairs are separated by ':' multiple key-value-pairs
	 * are separated by '|'.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_additional_headers'.
	 * 
	 * @return HTTP headers
	 */
	List<String[]> getAdditionalHeaders(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_query'.
	 * Additional JSON body. If present, request is made with a HTTP-Post instead
	 * a HTTP-Get within the REST reader!
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_query'.
	 * 
	 * @return JSON request for HTTP-Post body
	 */
	String getQuery(SourceEntity entity);
	
	/**
	 * Allow absent JSON fields in a source entity? 
	 * Get yes/no-value for '<rw-prefix>_allow_absent_fields'.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_allow_absent_fields'.
	 * 
	 * @return true if so
	 */
	boolean isAllowAbsentFields(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_absent_field_placeholder'.
	 * This value is used if a value can't be found in a JSON
	 * source entity rather than throwing an exception.
	 * Useful, if APIs change, but keeps the interface going.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_absent_field_placeholder'.
	 * 
	 * @return absent field place-holder
	 */
	String getAbsentFieldPlaceholder(SourceEntity entity);

	/**
	 * Is field path access configured? This allows to define
	 * fields that have a JSON path access per '.' character,
	 * e.g., 'object.anotheobject.value'.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_field_path_access'.
	 *  
	 * Get yes/no-value for '<rw-prefix>_field_path_access'.
	 * 
	 * @return true if so
	 */
	boolean isFieldPathAccess(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_id_path'.
	 * It is the JSON path to the unique ID in the target 
	 * web-resource/destination entity defined for every 
	 * source entity, and that is used for an update.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_id_path'.
	 * 
	 * @return JSON path to ID on in the destination interface
	 */
	String getIdPath(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_error_code'.
	 * It is the JSON path to the specific error code in the target 
	 * web-resource/destination answer defined for every 
	 * source entity, and that is used to evaluate if an
	 * error has occurred.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_error_code'.
	 * 
	 * @return JSON error code path in the destination interface
	 */
	String getErrorCodePath(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_error_code_value'.
	 * It is the parameter name for the error value found by above
	 * error code path that is  defined for every 
	 * source entity, and that is read to evaluate if an
	 * error has occurred.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_error_code_value'.
	 * 
	 * @return JSON error code parameter name in the destination interface
	 */
	String getErrorCodeValue(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_param_pageName'.
	 * The URL paging parameter name if any. 
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_param_pageName'.
	 * 
	 * @return URL paging parameter name
	 */
	String getParamPage(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_param_offsetName'.
	 * The URL offset parameter name if any. 
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_param_offsetName'.
	 * 
	 * @return URL offset parameter name
	 */
	String getParamOffset(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_data_path'.
	 * The JSON data path where the JSON array is found to get data.
	 * 
	 * Empty value means it is accessible at the root of the given path. 
	 * 
	 * The values 'FULLARRAY' and '.' or if the value ends with '.FULLARRAY'
	 * should indicate that the JSON result isn't accessed by a path, but rather 
	 * there's a pseudo array starting with '[' directly at the root point;
	 * some web-interfaces really do this! 
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_data_path'.
	 * 
	 * @return data path
	 */
	String getDataPath(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_sub_path'.
	 * The JSON subs path; that path is used within a single JSON record
	 * in case this record has been encapsulated somehow by another object.
	 * Some strange interfaces do this.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_sub_path'.
	 * 
	 * @return sub path
	 */
	String getSubPath(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_pagination_path'.
	 * Access the JSON object where possible paging parameters are available.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_pagination_path'.
	 * 
	 * @return path to pagination object
	 */
	String getPaginationPath(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_pagination_items_per_page'.
	 * Parameter name for amount of records per page in pagination object.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_pagination_items_per_page'.
	 * 
	 * @return items/records per page parameter name
	 */
	String getItemsPerPageAttr(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_pagination_items_total'.
	 * Parameter name for total amount of records in pagination object.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_pagination_items_total'.
	 * 
	 * @return total items/records parameter name
	 */
	String getItemsTotalAttr(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_pagination_last_page'.
	 * Parameter name for last page number in pagination object.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_pagination_last_page'.
	 * 
	 * @return last page number parameter name
	 */
	String getLastPageAttr(SourceEntity entity);

	/**
	 * Get value for '<rw-prefix>_pagination_after_id'.
	 * Parameter name for after ID in pagination object.
	 * It available it is used to directly access a next
	 * fetched batch per ID.
	 * 
	 * If not found, try get a value from '<rw-prefix>_*_pagination_after_id'.
	 * 
	 * @return after ID parameter name
	 */
	String getAfterIdAttr(SourceEntity entity);

	
	//------------------------------------------------------------------------------
	// Exec configuration
	//------------------------------------------------------------------------------
	
	/**
	 * Get value for '<rw-prefix>_cmd'.
	 * Full path of OS program.
	 * 
	 * @return full path to OS program
	 */
	String getExecCmd();

	/**
	 * Get value for '<rw-prefix>_args'.
	 * Arguments for the external program, comma-separated.
	 * 
	 * @return program arguments
	 */
	String[] getExecArgs();

	/**
	 * Get value for '<rw-prefix>_working_dir'.
	 * Get working directory for external program.
	 * 
	 * @return working directory
	 */
	String getExecWorkingDir();

	/**
	 * Get value for '<rw-prefix>_timeout'.
	 * 
	 * @return timeout in seconds
	 */
	int getExecTimeout();

}
