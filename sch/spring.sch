<?xml version="1.0" encoding="UTF-8"?>
<sch:schema xmlns:sch="http://www.ibm.com.cn/XML/schematron">
	<sch:pattern name="Validate dataName Reference.">
        <sch:rule context="//*">
	        <sch:assert test="not(@class)">
	             		The bean 'class' cannot be resolved.ATTRIBUTEREFclassREFATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
    
    <sch:pattern name="bean parent reference and duplicated id.">
        <sch:rule context="//*">
        	<sch:assert test="not(@parent) or preceding-sibling::*[@id=current()/@parent] or following-sibling::*[@id=current()/@parent]">
	             		The bean 'parent' doesn't exist.ATTRIBUTEparentATTRIBUTE
	        </sch:assert>
	        <sch:assert test="not(@id) or name(preceding-sibling::*[@id=current()/@id])!=name()">
	             		Duplicated bean 'id'.ATTRIBUTEidATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
    
    <sch:pattern name="bean property reference and duplicated name.">
        <sch:rule context="//*">
        	<sch:assert test="not(@ref) or parent::*/preceding-sibling::*[@id=current()/@ref] or parent::*/following-sibling::*[@id=current()/@ref]">
	             		The bean property 'ref' doesn't exist.ATTRIBUTErefATTRIBUTE
	        </sch:assert>
	        <sch:assert test="not(@name) or name(preceding-sibling::*[@name=current()/@name])!=name()">
	             		Duplicated bean property 'name'.ATTRIBUTEnameATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
    
    
    
</sch:schema>