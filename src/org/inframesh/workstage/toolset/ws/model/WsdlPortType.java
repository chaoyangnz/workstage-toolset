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
public class WsdlPortType {
	private String name;
	private List<WsdlOperation> operations = new ArrayList<WsdlOperation>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<WsdlOperation> getOperations() {
		return operations;
	}
	public void setOperations(List<WsdlOperation> operations) {
		this.operations = operations;
	}
	
	public void addOperation(WsdlOperation operation) {
		operations.add(operation);
	}
}
