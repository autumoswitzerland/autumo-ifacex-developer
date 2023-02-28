package ch.autumo.ifacex.developer;

import ch.autumo.ifacex.ExclusionFilter;

/**
 * 
 * Exclusion filters are used on the reader-side for source entities. 
 * They are defined as follows in an IPC (*.ifacex) reader section
 * per source entity; here a 'db_in'-reader example is presented
 * (JDBC Database Reader) for a source entity 'person':
 * 
 * 
 *   db_in_person_exclusion_filter_class=ch.autumo.ifacex.developer.ExampleExclusionFilter
 *   
 * Implements the {@link ch.autumo.ifacex.ExclusionFilter} interface.
 *
 */
public class ExampleExclusionFilter implements ExclusionFilter {

	@Override
	public boolean addRecord(String[] fields, String[] values) {
		// You get all field names and values per record. The fields
		// are always sorted according to the reader's fields-
		// configuration; see developer manual (Readers) for details.
		// So you could directly access the values per index, since you
		// know at what index what value is.
		//
		// Decide if the record should be included on the output
		// respectively writer-side. For example:

		// What we do here in this example:
		// Exclude every person record that has the value 'CEO' in the
		// position fields
		for (int i = 0; i < fields.length; i++) {
			if (fields[i].equals("position"))
				if (values[i].equals("CEO"))
					return false;
		}
		
		// Example; if we know that the field 'position' is defined at
		// 2nd position reader-side:
		//   if (values[1].equals("CEO")) // 2nd position -> index = 1 
		//	     return false;
		
		return true;
	}

}
