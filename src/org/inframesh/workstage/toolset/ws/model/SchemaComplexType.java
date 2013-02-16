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

import org.inframesh.fundus.base.Containers;

/**
 * 
 * @since wstool
 * @version 
 *
 * @author <a href="mailto:josh.yoah@gmail.com">杨超 </a>
 */
public class SchemaComplexType extends SchemaType {
	
	private List<SchemaElement> elements = new ArrayList<SchemaElement>();//schema simple type

	public SchemaComplexType() {
		super();
	}
	
	public SchemaComplexType(String name, String description) {
		super(name, description);
	}
	
	public List<SchemaElement> getElements() {
		return elements;
	}
	public void setElements(List<SchemaElement> elements) {
		this.elements = elements;
	}
	
	public void addElement(SchemaElement element) {
		this.elements.add(element);
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		SchemaComplexType other = (SchemaComplexType) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		return true;
	}

}
