<?xml version="1.0"?>

<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
	xmlns:sch="http://www.ibm.com.cn/XML/schematron"
	xmlns:xml-validation="http://www.w3.org/1999/XSL/TransformAlias"> 
	
<xsl:namespace-alias stylesheet-prefix="xml-validation" result-prefix="xsl"/>
<xsl:output method="xml"/> 

<xsl:template match="sch:schema | schema">
   <xml-validation:stylesheet version="1.0">   
      <xsl:apply-templates mode="sch-keyword" select="sch:pattern/sch:rule | pattern/rule "/> 
      <xml-validation:template match="/">
         <xsl:apply-templates mode="validate-patterns"/> 
      </xml-validation:template> 
      
      <xml-validation:template match="*" mode="error-element-full-path">
         <xml-validation:apply-templates select="parent::*"
      mode="error-element-full-path"/>
         <xml-validation:text>/</xml-validation:text> 
  		 <xml-validation:value-of select="name()"/> 
         <xml-validation:text>[</xml-validation:text>
         <xml-validation:value-of
      select="1+count(preceding-sibling::*[name()=name(current())])"/>
         <xml-validation:text>]</xml-validation:text>
      </xml-validation:template>
      <xsl:apply-templates/> 
   </xml-validation:stylesheet>
</xsl:template>

<xsl:template match="sch:pattern | pattern" mode="validate-patterns">  
      <xml-validation:apply-templates select="/" mode="Pattern{count(preceding-sibling::*)}"/>
</xsl:template> 

<xsl:template match="sch:assert | assert">
   <xsl:if test="not(@test)">
      <xsl:message>xml Assert Error: no test attribute</xsl:message>
   </xsl:if>           
   <xml-validation:choose>
      <xml-validation:when test="{@test}"/>
      <xml-validation:otherwise> 
         <xsl:call-template name="report-error" />
      </xml-validation:otherwise>
   </xml-validation:choose> 
</xsl:template> 

<xsl:template match="sch:pattern | pattern">
      <xsl:apply-templates/> 
      <xml-validation:template match="text()" priority="-1" 
   mode="Pattern{count(preceding-sibling::*)}"> 
      </xml-validation:template> 
</xsl:template> 
 
<xsl:template match="sch:rule | rule">
   <xsl:if test="not(@context)">
      <xsl:message>XML Rule Error: no context attribute</xsl:message>
   </xsl:if>
   <xml-validation:template match="{@context}" 
priority="{count(following-sibling::*)}" 
mode="Pattern{count(../preceding-sibling::*)}"> 
      <xsl:apply-templates/>
      <xml-validation:apply-templates mode="Pattern{count(../preceding-sibling::*)}"/>
   </xml-validation:template>
</xsl:template> 

<xsl:template match="text()" priority="-1" mode="sch-keyword" />  
 
<xsl:template name="report-error"> 
	<xsl:text>XML Rule Assert fails: XMLErrorXMLMessage"</xsl:text>
	<xsl:value-of select="normalize-space(.)"/>"XMLMessageXMLXPATH<xml-validation:apply-templates mode="error-element-full-path" select="." />XMLXPATHXMLError
</xsl:template>
 
</xsl:stylesheet>