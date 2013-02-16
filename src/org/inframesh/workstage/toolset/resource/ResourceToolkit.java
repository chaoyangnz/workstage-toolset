package org.inframesh.workstage.toolset.resource;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

public class ResourceToolkit {
	
	public static final String PLUGINICON = "icon";
	public static final String ORMAPPER = "ormapper";
	public static final String UNCHECKED = "unchecked";
	public static final String CHECKED = "checked";
	
	private static final String PATH = "/icons/";
	
	private static final String[][] ENTRYS = new String[][] {
		{PLUGINICON, "icon.jpg"},
		{ORMAPPER, "orm.jpg"},
		{UNCHECKED, "unchecked.gif"},
		{CHECKED, "checked.gif"}
	};
	
	//Provide ImageRegistry Singleton Object
	private static ImageRegistry imageRegistry;
	public static ImageRegistry getImageRegistry() {
		if(imageRegistry==null) {
			imageRegistry = new ImageRegistry();
			for(int i=0; i < ENTRYS.length; ++i) {
				imageRegistry.put(ENTRYS[i][0], ImageDescriptor.createFromFile(ResourceToolkit.class, PATH + ENTRYS[i][1]));
			}
		}
		
		return imageRegistry;
	}
}
