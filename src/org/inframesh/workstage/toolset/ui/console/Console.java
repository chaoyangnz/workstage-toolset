package org.inframesh.workstage.toolset.ui.console;

import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;
import org.inframesh.workstage.toolset.resource.ResourceToolkit;


public class Console {
	private static MessageConsole console = new MessageConsole("SOAD Console", ResourceToolkit.getImageRegistry().getDescriptor(ResourceToolkit.ORMAPPER));;
	static {
		console.activate();
		ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ console });
	}
	public static MessageConsoleStream out = console.newMessageStream();
	
//	public static MessageConsole getConsole() {
//		if(console == null) {
//			console = new MessageConsole("SOAD Console", null);
//			console.activate();
//			ConsolePlugin.getDefault().getConsoleManager().addConsoles(new IConsole[]{ console });
//		}
//		return console;
//	}
	
//	public static MessageConsoleStream getConsoleStream() {
//		if(stream == null) {
//			stream = PluginConsole.getConsole().newMessageStream();
//		}
//		return stream;
//	}
	
//	public static void main(String[] args) {
//		PluginConsole.getConsoleStream().println("test");
//	}
}
