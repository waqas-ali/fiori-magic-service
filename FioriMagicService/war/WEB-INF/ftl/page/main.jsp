<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>Convergent Home</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link rel="stylesheet" type="text/css"
	href="/assets/styles/jquery.countdown.css" />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/jquery-ui-custom.css" />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/stylemain.css" />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/carousel.css" />
<link rel="stylesheet" type="text/css"
	href="/assets/styles/jquery.gzoom.css" />
<script src="/assets/scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
<script src="/assets/scripts/jquery.countdown.js" type="text/javascript"></script>
<script src="/assets/scripts/grid.locale-en.js" type="text/javascript"></script>
<script src="/assets/scripts/jquery-ui-custom.min.js"
	type="text/javascript"></script>
<script src="/assets/scripts/ui.multiselect.js" type="text/javascript"></script>
<script src="/assets/scripts/jquery.layout.js" type="text/javascript"></script>
<script src="/assets/scripts/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<script src="/assets/scripts/jquery.mousewheel.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="/assets/scripts/jquery.carouFredSel.js"></script>
<script type="text/javascript" src="/assets/scripts/ui.core.min.js"></script>
<script type="text/javascript" src="/assets/scripts/jquery.gzoom.js"></script>
<script type="text/javascript" src="/assets/scripts/convergent.js"></script>
<script src="/assets/scripts/jquery.timeago.js" type="text/javascript"></script>
</head>
<body>

	<script>
		var CURRENT_USERID = ${Session.CURRENT_USER};
	</script>

	<div class="wrapper">
		<div id="bodycontainer">
			<a href="Logout" title="Log Out">Logout</a> <img id="refreshicon"
				src="/assets/images/Refresh.ico"
				onclick="Convergent.refreshJqGrid()" />
			<div style="clear: both"></div>
			<div>
				<table id="list">
					<tr>
						<td></td>
					</tr>
				</table>
				<div id="page"></div>
				<input type="submit" id="loadnext" value="Load Next &gt; "
					style="margin: 10px;" onclick="Convergent.loadNext();"></input>
				<h2 id="resultstatus">
					<center>No Results Found</center>
				</h2>
				<div id="datacontainer" class="ui-widget-content">

					<span id="buttons">
						<p id="defaultCountdown"></p>
						<input id="addminute" type="submit" value="Add Minute" /><input
						id="savebutton" type="submit" value="Save"
						onclick="Convergent.storeResult();" />
					</span>
					<p id="text"></p>
					<div class="image_carousel"
						style="width: 550px; height: 320px; float: left">
						<div id="foo2"></div>
						<div class="clearfix"></div>
						<a class="prev" id="foo2_prev" href="#"><span>prev</span></a> <a
							class="next" id="foo2_next" href="#"><span>next</span></a>
						<div class="pagination" id="foo2_pag"></div>
					</div>
					<form action="">
						<table id="datatable">

							<tr>
								<td width=42%>Vendor</td>
								<td><input id="vendor" name="vendor" size=24px type="text"
									value="xxx"></input><input type="hidden" id="resultid"
									name="resultid" value=""></input></td>
							</tr>
							<tr>
								<td>Date</td>
								<td><input id="date" name="date" size=24px type="text"
									value=""></input><input type="hidden" id="resulthistoryid"
									name="resulthistoryid" value=""></input></td>
							</tr>
							<tr>
								<td>Currency</td>
								<td><input id="currency" name="currency" size=24px
									type="text" value=""></input><input type="hidden"
									id="sessionid" name="sessionid" value=''></input></td>
							</tr>
							<tr>
								<td>Category</td>
								<td><input id="category" name="category" size=24px
									type="text" value=""></input></td>
							</tr>
							<tr>
								<td>Sub Total</td>
								<td><input id="subtotal" name="subtotal" size=24px
									type="text" value=""></input></td>
							</tr>
							<tr>
								<td>Tax</td>
								<td><input id="tax" name="tax" size=24px type="text"
									value=""></input></td>
							</tr>
							<tr>
								<td>Total</td>
								<td><input id="total" name="total" size=24px type="text"
									value=""></input></td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>

</body>
</html>