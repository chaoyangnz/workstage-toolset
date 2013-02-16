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
package org.inframesh.workstage.toolset.ws.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @since soadtoolkit
 * @version 
 *
 * @author <a href="mailto:josh.yoah@gmail.com">杨超 </a>
 */
public class WsdlBinding {
	private String name;
	private String portType;
	private List<WsdlBindingOperation> bindingOperations = new ArrayList<WsdlBindingOperation>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPortType() {
		return portType;
	}
	public void setPortType(String portType) {
		this.portType = portType;
	}
	public List<WsdlBindingOperation> getBindingOperations() {
		return bindingOperations;
	}
	public void setBindingOperations(List<WsdlBindingOperation> bindingOperations) {
		this.bindingOperations = bindingOperations;
	}
	
	public void addBindingOperation(WsdlBindingOperation bindingOperation) {
		bindingOperations.add(bindingOperation);
	}
	
	
	
}
