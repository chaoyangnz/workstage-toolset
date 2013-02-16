/*  
 *  Copyright(c) 2010-2011 Drowell Information Technology Limited 
 *  
 *  Licensed under the Drowell Business Software License (DBSL), 
 *	Version 1.0 ; you may obtain a copy of the license at
 *
 *  	http://www.drowell.com/licenses/LICENSE-1.0 
 *  
 *  Software distributed under the License is distributed  on an "AS IS" 
 *  BASIS but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the License 
 *  for more details.
 */
package org.inframesh.workstage.toolset.ws.model;

/**
 * 
 * @since soadtoolkit
 * @version 
 *
 * @author <a href="mailto:Josh.Yoah@gmail.com">杨超 </a>
 */
public class SchemaListType extends SchemaType {
	private String itemName;
	private String itemType;
	private boolean builtin = false;
	
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public String getItemType() {
		return itemType;
	}
	
	public void setBuiltin(boolean builtin) {
		this.builtin = builtin;
	}

	public boolean isBuiltin() {
		return builtin;
	}

	@Override
	public boolean equals(Object obj) {
		if(! super.equals(obj)) return false;
		
		if(itemType != null && itemType.equals(((SchemaListType)obj).itemType)) {
			return true;
		}

		return false;
	}
}
