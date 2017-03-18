<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Domain Management</title>
</head>
<body>
	<div
		style="margin: 0 auto; border-style: solid; border-width: 2px; width: 800px; height: 600px;">
		<div style="height: 100px; text-align: center;">
			<h2>Simple OAuth Example for App Engine!</h2>
		</div>
		<div style="height: 450px; margin-top: 10px; text-align: center;">

			<table>
				<colgroup>
					<col width="20%">
					<col width="55%">
					<col width="25%">
				</colgroup>
				<thead>
					<tr>
						<th>Alias Name</th>
						<th>URL</th>
						<th>functions</th>
					</tr>
				</thead>
				<s:if test="aliasList.size() > 0">
					<tbody>
						<s:iterator value="aliasList">
							<tr>
								<td><s:property value="aliasName" /></td>
								<td><s:property value="url" /></td>
								<s:url var="editURL" action="LoadAlias">
									<s:param name="akey" value="key"></s:param>
								</s:url>
								<s:url var="deleteURL" action="DeleteAlias">
									<s:param name="akey" value="key"></s:param>
								</s:url>
								<td><a href="${editURL}">Edit</a>
									| <a href="${deleteURL}">Delete</a> 
								</td>
							</tr>
						</s:iterator>
					</tbody>
				</s:if>
			</table>
			<a href="<s:url action='ShowDomains'/>">go back</a>
		</div>
	</div>
</body>
</html>