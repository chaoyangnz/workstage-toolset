<?xml version="1.0" encoding="UTF-8"?>
<sch:schema xmlns:sch="http://www.ibm.com.cn/XML/schematron">
	<sch:pattern name="Validate Action class Reference.">
        <sch:rule context="//*">
	        <sch:assert test="not(@class)">
	             		The class cannot be resolved.ATTRIBUTEREFclassREFATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
    
    <sch:pattern name="duplicated action name.">
        <sch:rule context="*/action">
	        <sch:assert test="not(@name) or name(preceding-sibling::*[@name=current()/@name])!=name()">
	             		Duplicated action 'name'.ATTRIBUTEnameATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
    
     <sch:pattern name="duplicated result name.">
        <sch:rule context="*/action/result">
	        <sch:assert test="not(@name) or name(preceding-sibling::*[@name=current()/@name])!=name()">
	             		Duplicated result 'name'.ATTRIBUTEnameATTRIBUTE
	        </sch:assert>
        </sch:rule>	
    </sch:pattern>
</sch:schema>