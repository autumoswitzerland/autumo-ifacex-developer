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
 * Data-mapper interface for customized additional parsing.
 */
public interface DataMapper {

	/**
	 * Customized parsing based on a single output field mapping.
	 * 
	 * The source values are in the exact order as the source entity's source fields.
	 * If the current mapping haven't been parsed, an empty string MUST be returned!
	 * 
	 * @param mapping one destination field mapping
	 * @param values current records values to process
	 * @param sourceEntity source entity
	 * @return parsed destination record values
	 * @throws IfaceXException
	 */
	public String parseCustomized(FieldMapping mapping, String sourceValues[], SourceEntity sourceEntity) throws IfaceXException;
	
	/**
	 * Determine, if the field mapping and the source values should
	 * be parsed further: Only when this mapper has parsed the current
	 * field mapping and produced a destination value, the internal
	 * standard parsing must go on, otherwise return always false!
	 * 
	 * If you have parsed a value and you return true, your value is
	 * overwritten! If true, the standard source formula parsing takes place
	 * to produce an output value instead of this custom data mapping.
	 * 
	 * @return true or false
	 */
	public boolean parseFurther();
	
}
