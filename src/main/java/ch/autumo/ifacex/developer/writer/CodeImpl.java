package ch.autumo.ifacex.developer.writer;

import ch.autumo.ifacex.IPC;
import ch.autumo.ifacex.RWC;
import ch.autumo.ifacex.utils.IPCRunner;
import ch.autumo.ifacex.writer.Code;

/**
 * Example code implementation for the code-runner writer 'code'.
 * Implementations must implement {@link ch.autumo.ifacex.writer.Code}.
 */
public class CodeImpl implements Code {

	private String how = null;
	
	/**
	 * The method {@link IPC#getValue(String, String)} is similar to
	 * the method {@link RWC#getConfig(String, String)}, but doesn't
	 * use a reader- or writer prefix!
	 */
	@Override
	public void initialize(String rwName, IPC config) throws Exception {
		how = config.getValue("how_to_execute", "name"); 
		System.out.println("Initialized.");
	}

	/**
	 * It is guaranteed, that this method is executed once only!
	 * This is even the case, if you would define more than one source
	 * entity beside the pseudo source entity 'NULL' with a 'null_in'
	 * reader when using the code runner 'code' calling this method.
	 */
	@Override
	public void execute(String rwName) throws Exception {
		switch (how) {
			case "id": // database ID
				IPCRunner.run(35);
				break;
			case "name": // the name is unique for IPCs
				IPCRunner.runByName("Exec");
				break;
			case "file": // on path necessary, only file name, first match is used!
				IPCRunner.runByFileName("099-rest-in-autumo-ifaceX-Exec.ifacex");
				break;
			default:
				System.out.println("Nothing to do.");
				break;
		}
		System.out.println("Executed.");
	}

	/**
	 * 'Finish' is always called once from the writer's close-method.
	 */
	@Override
	public void finish(String rwName) throws Exception {
		System.out.println("PS: Code implementations writer's name is always '"+rwName+"'!"); // 'code'!
		System.out.println("Finished.");
	}
}
