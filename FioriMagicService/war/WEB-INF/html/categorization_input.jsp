<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib prefix="s" uri="/struts-tags" %>
 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<!-- The HTML 4.01 Transitional DOCTYPE declaration-->
<!-- above set at the top of the file will set     -->
<!-- the browser's rendering engine into           -->
<!-- "Quirks Mode". Replacing this declaration     -->
<!-- with a "Standards Mode" doctype is supported, -->
<!-- but may lead to some differences in layout.   -->

<html>
  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title>Categorization Input</title>
  </head>

  <body>
    <div style="margin: 0 auto; border-style: solid; border-width: 2px; width: 500px; height: 600px;">
	    <div style="height: 100px; text-align: center;">
	    	<h2>Simple OAuth Example for App Engine!</h2>
	    </div>
	    <div style="height:200px; margin-top: 10px; text-align: center;">
	    	<s:form action="CategoryDocument" >
	    		<s:label value="Input String" />&nbsp;&nbsp;&nbsp;&nbsp;
	    		<br>
	    		<s:textarea rows="5" cols="30" name="input"></s:textarea>
	    		<br/><br/>
	    		<s:submit value="Extract" />
	    	</s:form>
	    	<br>
	    	<a href="<s:url action='Index'/>">go back</a>
	    </div>
	    
		
	    
    </div>
  </body>
</html>
