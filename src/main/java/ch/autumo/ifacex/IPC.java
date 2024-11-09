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

import ch.autumo.ifacex.Configuration.RWConfig;


/**
 * 
 * Interface Processor Configuration (IPC).
 * 
 * This interface lists all helper and configuration access method
 * signatures that are potentially of interest when developing
 * Interface Processor (IP) readers and writers. 
 * 
 * An IPC is always a combined collection of all configuration 
 * parameters of 'cfg/ifacex.cfg' (base parameters for every IPC)
 * and the specific '/any-path/ipc-config.ifacex' IPC configuration 
 * file, which has IP-specific configuration parameters for 1 reader 
 * and 1-n writers. The specific IPC configuration overwrites the 
 * base parameters if there is a configuration parameter with the 
 * same configuration key name.
 * 
 * Note that internally there are {@link RWConfig} objects created:
 * - 1 for the reader
 * - 1-n for the  writers
 * 
 * They are accesses with the methods:
 * - Only use in a reader: {@link #getReaderConfig()} and
 * - Only use in writers: {@link #getWriterConfig(String)}
 *   (parameter: writer name, which is available in writers)
 * 
 * The {@link RWConfig} object holds most of the configuration
 * parameters, general parameters are accessed by this object (IPC).
 * 
 * See also {@link RWC}.
 * 
 */
public interface IPC {

	/**
	 * Get reader name.
	 * 
	 * @return reader name
	 */
	String getReaderName();

	/**
	 * Get all writer names.
	 * 
	 * @return writer names
	 */
	String[] getWriterNames();

	/**
	 * Access reader configuration parameters based
	 * on the reader prefix name, e.g., 'db_in', 'csv_in',
	 * 'rest_in', etc.
	 * 
	 * @return reader configuration
	 */
	RWC getReaderConfig();

	/**
	 * Access writer configuration parameters based
	 * on the reader prefix name, e.g., 'db_out', 'csv_out',
	 * 'rest_out', etc.
	 * 
	 * @param writerName writer name which is available within writer methods
	 * @return writer configuration
	 */
	RWC getWriterConfig(String writerName);

	/**
	 * Get separated values, separated by configured 'value_delimiter'
	 * in 'cfg/ifacex.cfg'.
	 * 
	 * @param line line
	 * @return separated values
	 */
	String[] getCSVSepValues(String line);
	
	/**
	 * Get folder path for file processing.
	 * ('files_folder' in 'cfg/ifacex.cfg').
	 * 
	 * @return folder
	 */
	String getFolder();

	/**
	 * Get CSV delimiter character
	 * ('value_delimiter' in 'cfg/ifacex.cfg').
	 * 
	 * @return delimiter character
	 */
	String getCSVDelimiterChar();

	/**
	 * Get CSV enclosure character
	 * ('value_enclosure' in 'cfg/ifacex.cfg').
	 * 
	 * @return enclosure character
	 */
	String getCSVEnclosureChar();

	/**
	 * Get the processor timeout value
	 * ('proc_timeout' in 'cfg/ifacex.cfg').
	 * 
	 * @return processor timeout value
	 */
	int getProcTimeout();
	
	/**
	 * Get batch processing mode.
	 * ('batch_mode' in 'cfg/ifacex.cfg').
	 * 
	 * @return batch processing mode
	 */
	String getBatchMode();

	/**
	 * Get general batch size.
	 * ('general_batch_size' in 'cfg/ifacex.cfg').
	 * 
	 * @return general batch size
	 */
	int getGeneralBatchSize();

	/**
	 * Write a header? E.g. for CSV files.
	 * 
	 * @return true if so
	 */
	boolean writeHeader();
	
	/**
	 * Get Interface Processor Configuration file name
	 * 
	 * @return IPC file name
	 */
	String getProcCfgFileName();

	/**
	 * Get an integer value from the configuration file.
	 * 
	 * @param key configuration key name
	 * @return integer value
	 */
	int getInt(String key);

	/**
	 * Get a string value from the configuration file.
	 * 
	 * @param key configuration key name
	 * @return string value
	 */
	String getValue(String key);
	
	/**
	 * Get a string value from the configuration file.
	 * 
	 * @param key configuration key name
	 * @param defaultValue default value
	 * @return value or default value if value is not found or invalid
	 */
	String getValue(String key, String defaultValue);

	/**
	 * Get the source interface / reader prefix name,
	 * e.g., 'db_in', 'csv_in', 'rest_in', etc.
	 * ('source_interface' in '/any-path/ipc-config.ifacex').
	 * 
	 * @return source interfaces
	 */
	String getSourceInterface();

	/**
	 * Get the destination interface / writer prefix names,
	 * e.g., 'db_in', 'csv_in', 'rest_in', etc.
	 * Multiple values/writers possible, hence an array!
	 * ('destination_interfaces' in '/any-path/ipc-config.ifacex').
	 * 
	 * @return destination interfaces
	 */
	String[] getDestinationInterfaces();

	/**
	 * Get all source entities to process.
	 * ('source_interface' in '/any-path/ipc-config.ifacex').
	 * 
	 * @return source entities
	 */
	String[] getSourceEntities();

}