package ch.autumo.ifacex.server.web;

import ch.autumo.beetroot.Initializer;
//import ch.autumo.search.SearchEngine;

/**
 * ifaceX Initializer for beetRoot server/service.
 */
public class IfaceXInitializer implements Initializer {

	@Override
	public void initModules(boolean isWithinServlet, String fullConfigBasePath) throws Exception {

		// ifaceX initializes here the search engine module
		// --> this is a separate autumo module
		// SearchEngine.getInstance().initialize(fullConfigBasePath);
		
		// TODO
	}

}
