package ch.autumo.ifacex.developer.server;

import ch.autumo.beetroot.server.message.ClientAnswer;
import ch.autumo.beetroot.server.message.ServerCommand;
import ch.autumo.ifacex.server.Server;

/**
 * Create your own ifaceX Server.
 * 
 * Note: The ifaceX Server isn't explained in detail,
 * but if you want to extend it, you should sub-class it.
 * You can overwrite some methods, but you always have
 * to call the super-methods FIRST, because the ifaceX
 * server itself is already based on a framework:
 * The autumo beetRoot framework (Open Source, MIT License).
 *
 */
public class MyServer extends Server {

	/**
	 * You have to call the super constructor.
	 */
	public MyServer(String[] params) {
		super(params);
	}

	@Override
	protected void initializeLogging(String logCfgFile) {
		// Usually not necessary, just define your logging
		// configuration file within the configuration
		// parameter 'ws_log_cfg'.
		// The logging implementation can be defined within
		// the 'logging_implementation' parameter.
		super.initializeLogging(logCfgFile); // 
	}
	@Override
	public ClientAnswer processServerCommand(ServerCommand command) {
		// Processing server commands is undocumented.
		// It is used for the distributed dispatchers;
		// the ifaceX Scheduler modul uses it.
		return super.processServerCommand(command);
	}
	@Override
	protected boolean beforeStart() {
		// Do something before starting the server
		return super.beforeStart();
	}
	@Override
	protected void afterStart() {
		// Do something after starting the server
		super.afterStart();
	}
	@Override
	protected void beforeStop() {
		// Do something before stopping the server
		super.beforeStop();
	}
	@Override
	protected boolean printHealthStatus(boolean hasNoIssues) {
		// Print out the server health status
		// add your status lines to it.
		return super.printHealthStatus(hasNoIssues);
	}
	@Override
	public String getHelpText() {
		// Customized console help text; will be called if
		// program argument is '-help' or '-h' or
		// is the argument is not 'start|stop|health'.
		return super.getHelpText();
	}

	public static void main(String[] args) {
		// Must: Do basic server initialization; always!
		Server.mainInit(args);
		
		// TODO: do something necessary
		
		// create your server
		new MyServer(args);
	}
	
}
