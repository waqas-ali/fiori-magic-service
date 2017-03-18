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
		style="margin: 0 auto; border-style: solid; border-width: 2px; width: 900px; height: 600px;">
		<div style="height: 100px; text-align: center;">
			<h2>Simple OAuth Example for App Engine!</h2>
		</div>
		<div style="height: 450px; margin-top: 10px; text-align: center;">
			<a href="<s:url action='AddDomainPage'/>">Add Domain</a>
			&nbsp;&nbsp;&nbsp; | &nbsp;&nbsp;&nbsp; <a
				href="<s:url action='ShowAlias'/>">Show Alias Names</a>
			<table>
				<colgroup>
					<col width="20%">
					<col width="20%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="10%">
					<col width="20%">
				</colgroup>
				<thead>
					<tr>
						<th>Domain Name</th>
						<th>URL</th>
						<th>License Buy</th>
						<th>Active Duration</th>
						<th>Active Users</th>
						<th>Expire Date</th>
						<th>functions</th>
					</tr>
				</thead>
				<s:if test="domains.size() > 0">
					<tbody>
						<s:iterator value="domains" >
							<tr>
								<td><s:property value="domainName" /><s:property value="domainIdCode" /></td>
								<td><s:property value="url" /><s:property value="id" /></td>
								<td><s:property value="noOfLicense" /></td>
								<td><s:property value="userActiveDuration" /></td>
								<td><s:property value="activeUsers" /></td>
								<td><s:property value="licenseExpireDate" /></td>
								<s:hidden name="url" />
								<s:url var="editURL" action="LoadDomain">
									<s:param name="domainId" value="id"></s:param>
								</s:url>
								<s:url var="deleteURL" action="DeleteDomain">
									<s:param name="domainId" value="id"></s:param>
								</s:url>
								<s:url var="aliasURL" action="AddAliasForm">
									<s:param name="aurl" value="url"></s:param>
								</s:url>
								<td><a href="${editURL}">Edit</a>
									| <a href="${deleteURL}">Delete</a> 
									| <a href="${aliasURL}">Add Alias</a></td>
							</tr>
						</s:iterator>
					</tbody>
				</s:if>
			</table>
			<a href="<s:url action='Index'/>">go back</a>
		</div>
	</div>
</body>
</html>