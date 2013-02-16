/* 
 *  Copyright (c)2009-2010 The Inframesh Software Foundation (ISF)
 *
 *  Licensed under the Inframesh Software License (the "License"), 
 *	Version 1.0 ; you may obtain a copy of the license at
 *
 *  	http://www.inframesh.org/licenses/LICENSE-1.0
 *
 *  Software distributed under the License is distributed  on an "AS IS" 
 *  BASIS but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the License 
 *  for more details.
 *  
 *  Inframesh Software Foundation is donated by Drowell Technology Limited.
 */
package org.inframesh.workstage.toolset;

import java.io.IOException;
import java.io.PrintStream;

import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

/**
 * 
 * @since workstage-toolset
 * @version 
 *
 * @author <a href="mailto:josh.yoah@gmail.com">杨超 </a>
 */
public class ToolsetConsole {
	
	public static MessageConsole console = new MessageConsole(Activator.getDefault().getBundle().getSymbolicName(), null);
	
	public static MessageConsoleStream out;
	
	static void init(boolean flag) {
		
		IConsoleManager manager = ConsolePlugin.getDefault().getConsoleManager();
		manager.addConsoles(new IConsole[] {console});
		
		out = console.newMessageStream();
		out.setColor( new Color(null, 255, 0, 0));
		
		if(flag) {
			System.setOut(new PrintStream(out));//将System.out都流向console
		}
	}
	
	static void dispose() {
		if(out != null && !out.isClosed()) {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
