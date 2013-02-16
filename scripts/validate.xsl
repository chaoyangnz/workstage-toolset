<?xml version="1.0" encoding="UTF-8"?>
<xml-validation:stylesheet
	xmlns:xml-validation="http://www.w3.org/1999/XSL/Transform" 
	xmlns:sch="http://www.ibm.com.cn/XML/schematron"
	version="1.0">
	<xml-validation:template match="/">
		<xml-validation:apply-templates mode="Pattern0"	select="/" />

		<xml-validation:apply-templates mode="Pattern1"	select="/" />
	</xml-validation:template>
	
	<xml-validation:template mode="error-element-full-path"	match="*">
		<xml-validation:apply-templates mode="error-element-full-path" select="parent::*" />
		<xml-validation:text>/</xml-validation:text>
		<xml-validation:value-of select="name()" />
		<xml-validation:text>[</xml-validation:text>
		<xml-validation:value-of
			select="1+count(preceding-sibling::*[name()=name(current())])" />
		<xml-validation:text>]</xml-validation:text>
	</xml-validation:template>

	<xml-validation:template mode="Pattern0"
		priority="0" match="bean">
		<xml-validation:choose>
			<xml-validation:when test="not(@class)" />
			<xml-validation:otherwise>
				XML Rule Assert fails: XMLErrorXMLMessage"The class cannot be
				resolved.ATTRIBUTEREFclassREFATTRIBUTE "XMLMessageXMLXPATH
				<xml-validation:apply-templates	select="." mode="error-element-full-path" />
				XMLXPATHXMLError
			</xml-validation:otherwise>
		</xml-validation:choose>
		<xml-validation:apply-templates mode="Pattern0" />
	</xml-validation:template>
	<xml-validation:template mode="Pattern0"
		priority="-1" match="text()" />


	<xml-validation:template mode="Pattern1"
		priority="0" match="//*">
		<xml-validation:choose>
			<xml-validation:when test="not(@ref) or /child::*/property[@id=current()/@ref]" />
			<xml-validation:otherwise>
				XML Rule Assert fails: XMLErrorXMLMessage"The 'ref' doesn't
				exist.ATTRIBUTErefATTRIBUTE"XMLMessageXMLXPATH
				<xml-validation:apply-templates	select="." mode="error-element-full-path" />
				XMLXPATHXMLError
			</xml-validation:otherwise>
		</xml-validation:choose>
		
		<xml-validation:choose>
			<xml-validation:when
				test="not(@id) or name(preceding-sibling::*[@id=current()/@id])!=name()" />
			<xml-validation:otherwise>
				XML Rule Assert fails: XMLErrorXMLMessage"Duplicated
				id.ATTRIBUTEidATTRIBUTE"XMLMessageXMLXPATH
				<xml-validation:apply-templates
					select="." mode="error-element-full-path" />
				XMLXPATHXMLError
			</xml-validation:otherwise>
		</xml-validation:choose>
		<xml-validation:apply-templates mode="Pattern1" />
	</xml-validation:template>
	
	<xml-validation:template mode="Pattern1" priority="-1" match="text()" />
</xml-validation:stylesheet>
