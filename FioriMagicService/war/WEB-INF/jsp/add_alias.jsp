<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<title>Add Alias Name</title>
</head>

<body>

	<s:div
		style="margin: 0 auto; border-style: solid; border-width: 2px; width: 500px; height: 600px;">
		<s:div style="height: 100px; text-align: center;">
			<h2>Simple OAuth Example for App Engine!</h2>
		</s:div>
		<s:div style="height: 390px; margin-top: 10px; text-align: center;">
			<s:form action="%{action}">
				<s:push value="alias">
					<s:hidden name="key"/>
					<s:textfield name="aliasName" label="Alias Name"  />
					<s:textfield name="url" label="Domain URL" readonly="%{readonly}"/>
					<s:submit value="%{label}"></s:submit>
				</s:push>
			</s:form>
			<a href='<s:url action='ShowAlias'/>'>go back</a>
		</s:div>
	</s:div>
</body>
</html>
