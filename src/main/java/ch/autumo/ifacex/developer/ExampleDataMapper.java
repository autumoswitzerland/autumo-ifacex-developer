package ch.autumo.ifacex.developer;

import java.util.List;

import ch.autumo.ifacex.DataMapper;
import ch.autumo.ifacex.FieldMapping;
import ch.autumo.ifacex.IfaceXException;
import ch.autumo.ifacex.SourceEntity;

/**
 * 
 * The data mapper is implemented whenever the output mapping of
 * a writer needs to do something further than just assigning
 * 1-n input fields (from the source entity reader-side; here
 * 'contacts') to an output field (to a destination entity
 * server-side; here 'address').
 * 
 * 
 * Here, the example mapping in the IPC (*.ifacex) would be for
 * a 'db_out'-writer (JDBC Database Writer).
 * The processing is done in the order of the lexical sorting 
 * of the parameters, hence they must have a 3-digit sorting 
 * number at the end. Also, here you have one 'insert' mapping, 
 * that inserts the static value 'default' to the output field 'type':
 * 
 * 
 *   # The data mapper class (this class file of this source);
 *   # Only used for for the input source (reader-side) 'contacts'
 *   db_out_contacts_data_mapper_class=ch.autumo.ifacex.developer.ExampleDataMapper
 * 
 *   # Which fields are used to lookup a possibly existing record;
 *   # they are the business-related unique identification fields,
 *   # can be multiple fields, comma-separated
 *   # -> Writer decides to 'update' or 'create'!
 *   db_out_contacts_dest_filter_fields=client_id
 * 
 *   # The technical unique id destination-side; when it is a FB
 *   # usually this is a DB-generated sequence
 *   db_out_contacts_dest_unique_id_field=id
 * 
 *   # Destination entity; when it is a DB, this is the table name
 *   db_out_contacts_mapping_entity=address
 * 
 *   # Insert/Update mappings
 *   db_out_contacts_mapping_field_000=client_id:{properties.source_id}
 *   db_out_contacts_mapping_field_001=first_name:{properties.first_name}
 *   db_out_contacts_mapping_field_002=last_name:{properties.last_name}
 *   db_out_contacts_mapping_field_003=name:*
 *   db_out_contacts_mapping_field_004=contact_person:*
 *   db_out_contacts_mapping_field_005=email:{properties.email}
 *   db_out_contacts_mapping_field_006=phone:{properties.phone}
 *   db_out_contacts_mapping_field_007=address:{properties.address}
 *   db_out_contacts_mapping_field_008=zip_code:{properties.zip_code}
 *   db_out_contacts_mapping_field_009=city:{properties.city}
 *   db_out_contacts_mapping_field_010=country:{properties.country}
 *   db_out_contacts_mapping_field_011=company_type:0
 * 
 *   # Static insert mappings
 *   db_out_contacts_mapping_insert_000=type:default
 * 
 * 
 * The above mapping is usually done with the 'ifaceX Studio' GUI, where
 * association can be comfortably be made by Drag'n'Drop.  
 * 
 */
public class ExampleDataMapper implements DataMapper {

	/**
	 * A boolean that indicates, if parsing should continue is always needed.
	 * The mapper should called every output field mapping, until we have the
	 * output field mapping that needs special logic.
	 */
	boolean parseFurther = true;
	
	@Override
	public String parseCustomized(FieldMapping mapping, String[] sourceValues, SourceEntity sourceEntity) throws IfaceXException {
		
		String valueResult = null;
			
		// Get current destination mapping
		final String destField = mapping.getDestField();
		List<String> sfields = null;

		// If destination filed 'contact_person' is processed...
		// (Note: above we assign '*'; could be anything and will
		//  be ignored; the value is set here!)
		if (destField.equals("contact_person")) {
			
			// Get all selected source fields from the source entity;
			// they have been selected by a reader
			sfields = sourceEntity.getSourceFieldsList();
			
			// Manual mapping of source field 'properties.segment'
			// 1. Get the index of the source field name (header index)
			// 2. Get the source value with the field name index
			final int idx = sfields.indexOf("properties.segment");
			final String segment = sourceValues[idx].trim().toLowerCase();
			
			// Decide what to do depending on the value of the source field
			if (segment.equals("PrivateCustomer")) {
				// In this case map an empty string to the output field 'contact_person'
				valueResult = "";
			} else {
				// In this case map the 'first_name' and 'last_name' together as one string to the output field 'contact_person'
				valueResult = sourceValues[sfields.indexOf("properties.first_name")] + " " + sourceValues[sfields.indexOf("properties_lastname")];
			}

			// work is done, we don't need to consume further destination
			// mapping fields, to map something
			parseFurther = false; 
			
			// If destination filed 'name' is processed...
		} else if (destField.equals("name")) {
			
			// Get all selected source fields from the source entity;
			// they have been selected by a reader
			sfields = sourceEntity.getSourceFieldsList();

			// Manual mapping of source field 'properties.segment'
			// 1. Get the index of the source field name (header index)
			// 2. Get the source value with the field name index
			final int idx = sfields.indexOf("properties.segment");
			final String segment = sourceValues[idx].trim().toLowerCase();
			
			// Decide what to do depending on the value of the source field
			if (segment.equals("PrivateCustomer")) {
				// In this case map the 'first_name' and 'last_name' together as one string to the output field 'contact_person'
				valueResult = sourceValues[sfields.indexOf("properties.firstname")] + " " + sourceValues[sfields.indexOf("properties.lastname")];
			} else {
				// In this case map the 'company' the output field 'name'
				valueResult = sourceValues[sfields.indexOf("properties.company")];
			}
			
			// Work is done, we don't need to consume further destination
			// mapping fields, to map something
			parseFurther = false; 
			
		} else {
			
			// Not the right destination mapping yet; process further
			parseFurther = true; 
			
		}
		
		return valueResult;
	}

	@Override
	public boolean parseFurther() {
		// Tell the parser if it should parse further or not;
		// This is a performance-related feature and always
		// should be done
		return parseFurther;
	}	

}
