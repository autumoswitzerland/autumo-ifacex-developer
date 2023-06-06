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

import java.util.Map;


/**
 * The writer mapping is the IPC representation of the 
 * output mapping for writer where a destination entity 
 * is mapped to a source entity and source fields or
 * source formulas are mapped to output/destination fields.
 * 
 * See also {@link RWC#getMapping(SourceEntity)}.
 * Only a writer {@link RWC} will return a mapping.
 * 
 * Note that a writer has one mapping per source entity
 * associated. So it can have 1-n mappings.
 * 
 * The methods that deliver the mapped output values are 
 * available with or without output mapping; applying of
 * the output mapping consists of applying the custom mappers 
 * and source values or source formulas according to the 
 * configuration of this mapping.
 * 
 * Generally, the methods apply the mapping if the method-name
 * doens't end with 'WithoutMapping'!
 * 
 * Furthermore, and depending on the destination interface,
 * values are escaped, e.g., REST -> HTML-escaped,
 * DB -> SQL-escaped, URL parameters -> URL-escaped, etc.
 */
public interface WriterMapping {

	/**
	 * Get source entity
	 * 
	 * @return source entity
	 */
	SourceEntity getSourceEntity();

	/**
	 * Get destination entity/table for e.g. HTTP POST
	 * 
	 * @return destination entity
	 */
	String getDestinationEntity();

	/**
	 * Has a destination entity?
	 * 
	 * @return true if so
	 */
	boolean hasDestinationEntity();

	/**
	 * Has any field mappings?
	 * 
	 * @return true if so
	 */
	boolean hasFieldMappings();

	/**
	 * Has standard field mappings?
	 * 
	 * @return true if so
	 */
	boolean hasStandardFieldMappings();

	/**
	 * Has insert field mappings?
	 * 
	 * @return true if so
	 */
	boolean hasInsertFieldMappings();

	/**
	 * Get all mapping field names.
	 * 
	 * @return fields
	 */
	String[] getAllFields();
	
	/**
	 * Get field names for creation. They are copied out of the field mappings.
	 * 
	 * @param fields fields
	 * @return insert fields
	 */
	String[] getFieldsForInsert() throws IfaceXException;
	
	/**
	 * Get final values for creation.
	 * 
	 * @param values current source values from batch to process
	 * @return insert values
	 */
	public String[] getValuesForInsert(String values[]) throws IfaceXException;
	
	/**
	 * Create conform entity name; for source entity cases.
	 * 
	 * @param name non-conform entity name.
	 * @return conform entity name ('/' with ':' replaced)
	 */
	String createConformEntityName(String name);

	/**
	 * Get POST parameters according to this mapping & with destination entity/table.
	 * Values are UTF-8 encoded.
	 * 
	 * E.g. 'destEntity=address?param1=value1&param2=value2&param3=value3'.
	 * 
	 * @param values current source values from batch to process
	 * @param insert true for inserts, false for updates
	 * @return post params with entity URL-part
	 * @throws IfaceXException
	 */
	String getPostParamsWithDestinationEntity(String values[], boolean insert) throws IfaceXException;

	/**
	 * Get POST parameters according to this mapping.
	 * Values are UTF-8 encoded.
	 * 
	 * E.g. 'param1=value1&param2=value2&param3=value3'.
	 * 
	 * @param values current source values from batch to process
	 * @param insert true for inserts, false for updates
	 * @return POST parameters
	 * @throws IfaceXException
	 */
	String getPostParams(String values[], boolean insert) throws IfaceXException;

	/**
	 * Create a JSON POST according without destination mapping.
	 * 
	 * E.g. '{"param1":"value1","param2":"value2","param3":"value3"}'.
	 * 
	 * @param values current source values from batch to process
	 * @param entity source entity
	 * @return JSON body
	 * @throws IfaceXException
	 */
	String createJSONBodyWithoutMapping(String values[], SourceEntity entity) throws IfaceXException;

	/**
	 * Create a JSON POST according to this mapping for inserts.
	 * 
	 * E.g. '{"param1":"value1","param2":"value2","param3":"value3"}'.
	 * 
	 * @param values current source values from batch to process
	 * @return JSON body
	 * @throws IfaceXException
	 */
	String createJSONBodyForInsert(String values[]) throws IfaceXException;

	/**
	 * Create a JSON POST according to this mapping for updates.
	 * 
	 * E.g. '{"param1":"value1","param2":"value2","param3":"value3"}'.
	 * 
	 * @param values current source values from batch to process
	 * @return JSON body
	 * @throws IfaceXException
	 */
	String createJSONBodyForUpdate(String values[]) throws IfaceXException;

	/**
	 * Get parameters for given filter fields without destination mapping.
	 * Values are UTF-8 encoded.
	 * 
	 * E.g. "filter1=value1&filter2=value2&filter3=value3".
	 * 
	 * @param values current source values from batch to process
	 * @param filterFields filter fields
	 * @return parameter string
	 * @throws IfaceXException
	 */
	String getParamsForFilterWithoutMapping(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get parameters for given filter fields.
	 * Values are UTF-8 encoded.
	 * 
	 * E.g. "filter1=value1&filter2=value2&filter3=value3".
	 * 
	 * @param values current source values from batch to process
	 * @param filterFields filter fields
	 * @return parameter string
	 * @throws IfaceXException
	 */
	String getParamsForFilter(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get map for update and insert operation without destination mapping.
	 * 
	 * @param values current source values from batch to process
	 * @param source entity
	 * @return parameter map
	 * @throws IfaceXException
	 */
	Map<String, String> getMapForUpdateAndInsertWithoutMapping(String values[], SourceEntity entity) throws IfaceXException;

	/**
	 * Get map for update and insert operation.
	 * 
	 * @param values current source values from batch to process
	 * @return parameter map
	 * @throws IfaceXException
	 */
	Map<String, String> getMapForUpdateAndInsert(String values[]) throws IfaceXException;

	/**
	 * Get values for DB insert with extended mapping.
	 * 
	 * E.g. "'value1', 'value2', 'value3'".
	 * 
	 * @param values current source values from batch to process
	 * @return DB insert values
	 * @throws IfaceXException
	 */
	String getDbValuesForInsertExtended(String values[]) throws IfaceXException;

	/**
	 * Get values for DB insert.
	 * 
	 * E.g. "'value1', 'value2', 'value3'".
	 * 
	 * @param values current source values from batch to process
	 * @return DB insert values
	 */
	String getDbValuesForInsert(String values[]) throws IfaceXException;

	/**
	 * Get values for DB insert without destination mapping.
	 * 
	 * E.g. "'value1', 'value2', 'value3'".
	 * 
	 * @param values current source values from batch to process
	 * @return DB insert values
	 */
	String getDbValuesForInsertWithoutMapping(String values[]) throws IfaceXException;

	/**
	 * Get DB where clause for update operation without destination mapping.
	 * 
	 * E.g. "field1='value1', field2='value2', field3='value3'".
	 * 
	 * @param values current source values from batch to process
	 * @return parameter string
	 * @throws IfaceXException
	 */
	String getDbWhereClauseForUpdateWithoutMapping(String sourceFields[], String values[]) throws IfaceXException;

	/**
	 * Get DB where clause for update operation.
	 * 
	 * E.g. "field1='value1', field2='value2', field3='value3'".
	 * 
	 * @param values current source values from batch to process
	 * @return parameter string
	 * @throws IfaceXException
	 */
	String getDbWhereClauseForUpdate(String values[]) throws IfaceXException;

	/**
	 * Get field names for DB insert without destination mapping.
	 * 
	 * E.g. "field1, field2, field3".
	 * 
	 * @param fields fields
	 * @return DB insert fields
	 */
	String getDbFieldsForInsertWithoutMapping(String sourceFields[]) throws IfaceXException;

	/**
	 * Get field names for DB insert.
	 * 
	 * E.g. "field1, field2, field3".
	 * 
	 * @param fields fields
	 * @return DB insert fields
	 */
	String getDbFieldsForInsert() throws IfaceXException;

	/**
	 * Get a plain text record. 
	 * 
	 * @param values current source values from batch to process
	 * @return mapped text record
	 * @throws IfaceXException
	 */
	String[] getTextValues(String values[]) throws IfaceXException;

	/**
	 * Get CSV record without destination mapping. The values are
	 * separated by the value defined by the parameter 'value_delimiter' 
	 * and enclosed by the value defined by the parameter 'value_enclosure'
	 * in 'cfg/ifacex.cfg'. Enclosing is on done if a enclosure-character
	 * is present.
	 * 
	 * @param values current source values from batch to process
	 * @return CSV record
	 * @throws IfaceXException
	 */
	String getCSVValuesWithoutMapping(String values[]) throws IfaceXException;

	/**
	 * Get CSV record without destination mapping. The values are
	 * separated by the value defined by the parameter 'value_delimiter' 
	 * and enclosed by the value defined by the parameter 'value_enclosure'
	 * in 'cfg/ifacex.cfg'. Enclosing is on done if a enclosure-character
	 * is present.
	 * 
	 * @param values current source values from batch to process
	 * @return CSV record
	 * @throws IfaceXException
	 */
	String getCSVValues(String values[]) throws IfaceXException;

	/**
	 * Get Mongo where clause map for filter calls without destination mapping; 
	 * filters are 1 to 1 mapped to source field names.
	 * 
	 * @param values current source values from batch to process
	 * @param entity source entity
	 * @param fields filter fields
	 * @return Where clause map
	 * @throws IfaceXException
	 */
	Map<String, String> getFilterMapWithoutMapping(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get Mongo where clause map for filter calls.
	 * 
	 * @param values current source values from batch to process
	 * @param entity source entity
	 * @param fields filter fields
	 * @return Where clause map
	 * @throws IfaceXException
	 */
	Map<String, String> getFilterMap(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get DB where clause for filter calls without destination mapping; 
	 * filters are 1 to 1 mapped to source field names.
	 * 
	 * E.g. "filter1='value1' AND filter2='value2' AND filter3='value3'".
	 * 
	 * @param values current source values from batch to process
	 * @param fields filter fields
	 * @return DB where string
	 * @throws IfaceXException
	 */
	String getDbWhereFilterClauseWithoutMapping(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get DB where clause for filter calls.
	 * 
	 * E.g. "filter1='value1' AND filter2='value2' AND filter3='value3'".
	 * 
	 * @param values current source values from batch to process
	 * @param entity source entity
	 * @param fields filter fields
	 * @return DB where string
	 * @throws IfaceXException
	 */
	String getDbWhereFilterClause(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get single filter ID without destination mapping.
	 * Value is UTF-8 encoded.
	 * 
	 * E.g. "3" as in https:/host/entity/3
	 * 
	 * @param values current source values from batch to process
	 * @param filterFields filter fields
	 * @return single filter ID value
	 * @throws IfaceXException
	 */
	String getValueForSingleFilterWithoutMapping(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

	/**
	 * Get single filter ID.
	 * Value is UTF-8 encoded.
	 * 
	 * E.g. "3" as in https:/host/entity/3
	 * 
	 * @param values current source values from batch to process
	 * @param filterFields filter fields
	 * @return single filter ID value
	 * @throws IfaceXException
	 */
	String getValueForSingleFilter(String values[], SourceEntity entity, String filterFields[]) throws IfaceXException;

}
