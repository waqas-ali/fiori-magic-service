<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8 " />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/stylemain.css" />
<script src="/assets/scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="/assets/scripts/convergent.js"></script>
</head>
<body>
	<div class="wrapper" style="margin: 0 auto">
		<div id="bodycontainer">
			<div class="wrapperin">
				<div id="header">
					<div id="logo"></div>
					<!-- logo ends -->
					<!-- loggedin ends -->
				</div>
				<!-- header ends -->
				<div id="nav"></div>
				<!-- nav ends -->
				<div id="bodycontainer">
					<div id="content">
						<fieldset>
							<legend>Log In</legend>
							<s:property value="#session.errorMessage" />
							<p id="loginResult">Invalid login.. try again.</p>

							<s:form action="UserAuthenticate" id="login" method="post">
								<s:push value="user">
									<s:hidden name="domainId"/>
									<s:textfield id="username" name="loginId" label="Username" />
									<s:password id="password" name="password" label="Password" />
									<s:submit value="Login" />
								</s:push>
							</s:form>
						</fieldset>
					</div>
					<!--content ends-->
				</div>
				<!-- body container ends -->
			</div>
			<!-- wrapperin ends -->
		</div>
		<!-- body container ends -->
	</div>
	<!-- Wrapper ends-->
</body>
</html>