<a href="Logout">Logout</a>

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
    <title>Hello App Engine</title>
    <script src="/assets/scripts/jquery-1.7.2.min.js" type="text/javascript"></script>
    <script src="/assets/scripts/jquery.jqGrid.min.js" type="text/javascript"></script>
     <link rel="stylesheet" type="text/css" href="/assets/styles/ui.jqgrid.css"/>
     
  </head>

  <body onload="callme()">
 <script type="text/javascript" src="http://www.webtoolkit.info/djs/webtoolkit.aim.js"></script>
  <script type="text/javascript">
  var webhost="";
  var submissionId='';
  var userId='';
  function callme() {
	  alert("here");
  }

		function startCallback() {
			// make something useful before submit (onStart)
			return true;
		}

		function completeCallback(response) {
			// make something useful after (onComplete)
		console.log(response);
		}
 // $(document).ready(function(){
    /*
	 
     	   $.ajax({
		      url: webhost+"/odata.svc/User",
		      type: "POST",
		      //contentType: "application/json;odata=verbose",
		      contentType: 'application/json; charset=utf-8',
		      async:false,
		      data: JSON.stringify({"userName":"test1@test.com","password":"test1"}),
		      dataType: "json",
				      success: function (data) {
				    	  console.log(data);
								    	  $.ajax({
								    	      url: webhost+"/odata.svc/UserRole",
								    	      type: "POST",
								    	      async:false,
								    	      //contentType: "application/json;odata=verbose",
								    	      contentType: 'application/json; charset=utf-8',
								    	      data: JSON.stringify({"userId":${Session.CURRENT_USER},"roleTypeId":6597069766656000}),
								    	      dataType: "json",
								    	      success: function (data) {
								    					console.log(data);
								    	      }
					     				 });
								      }
      }); */
/*
      
	    /* $.ajax({
    	      url: webhost+"/odata.svc/Domain",
    	      type: "POST",
    	      //contentType: "application/json;odata=verbose",
    	      contentType: 'application/json; charset=utf-8',
    	      data:JSON.stringify({"name":"customer.com"}),
    	      dataType: "json",
    		  success: function (data) { console.log(data);}
	      });  
        */
	 /* $.ajax({
    	      url: webhost+"/odata.svc/Result(5504155208646656)",
    	      type: "PUT",
    	      async:false,
    	      //contentType: "application/json;odata=verbose",
    	      contentType: 'application/json; charset=utf-8',
    	      data: JSON.stringify({"submissionId":6603666836422656,"vendor":"xyz","total":100.00,"tax":0.0,"currency":"Rs","category":"food"}),
    	      dataType: "json",
    	      success: function (data) {
						console.log(data);
    	      }
	      });   */
		  /* $.ajax({
    	      url: webhost+"/odata.svc/ResultHistory(5381009906335744)",
    	      type: "PUT",
    	      async:false,
    	      //contentType: "application/json;odata=verbose",
    	      contentType: 'application/json; charset=utf-8',
    	      data: JSON.stringify({"processingDuration":20}),
    	      dataType: "json",
    	      success: function (data) {
						console.log(data);
    	      }
	      });    */
    //});
    
				    getData=function(requestUri){
								var result=$.ajax({
							    url: requestUri,
							    type: "GET",
							    async:false,
							    cache: true,
							    dataType:"json",
							    success: function(data){
									//Convergent.log(data);
								},
							    error: function (XMLHttpRequest, textStatus, errorThrown) {
							      Convergent.log("Found error: " + XMLHttpRequest + " - " + textStatus + ", Error: " +  errorThrown,"error");
							    }
							})
							result=jQuery.parseJSON(result.responseText);
							var data; 
						    if ((typeof result === 'object') && (('d' in result) && ('results' in result.d))) {
						        data = result.d.results; 
						    } else { 
						        data = result.d || result; 
						    }
						    return data;
					 };
				 createSubmission=function(){
							      $.ajax({
						    	      url: webhost+"/odata.svc/Submission",
						    	      type: "POST",
						    	      async:false,
						    	      //contentType: "application/json;odata=verbose",
						    	      contentType: 'application/json; charset=utf-8',
						    	      data: JSON.stringify({"trip":document.getElementById('trip').value,"count":document.getElementById('imagescount').value,"userId":${Session.CURRENT_USER}}),
						    	      dataType: "json",
						    	      success: function (data) {
						    	      	console.log(data);
						    	      	 $("#forms").empty();
						    	      	  $("#forms").append("<u>Upload Images</u>");
						    	      	 var urls=(data.d.blobUrls).split("[")[1].split("]")[0];
										       var a=urls.split(",");
						    	    	 for(var i=0;i<data.d.count;i++){
						    	    		 $("#forms").append("<form id='action"+i+"' action=''  method='post' enctype='multipart/form-data' onsubmit='return AIM.submit(this, {\'onStart\' : startCallback, \'onComplete\' : completeCallback})'><input type='hidden' name='submissionId' class='submissionid' value=''><input type='file' name='form[file]' accept='image/*'/><input type='submit' id='form1' name='image1' value='Upload'></form>");
										      	 console.log(a[i]);
										        $('#action'+i).attr('action',a[i]);
										        $('.submissionid').attr('value',data.d.submissionId);
										 }
						    	    	 $.ajax({
						    	    	      url: webhost+"/odata.svc/Result",
						    	    	      type: "POST",
						    	    	      async:false,
						    	    	      //contentType: "application/json;odata=verbose",
						    	    	      contentType: 'application/json; charset=utf-8',
						    	    	      data: JSON.stringify({"submissionId":data.d.submissionId,"vendor":"xyz","total":100.00,"tax":0.0,"currency":"Rs","category":"food"}),
						    	    	      dataType: "json",
						    	    	      success: function (data) {
						    							console.log(data);
						    	    	      }
						    		      });    
						              }  
				       
		  });  
		  
		};//function close  
		
		getSubmissions=function(){
			var JsonArray=[];
			var submissions=getData("/odata.svc/Submission");
						    	      		$.each(submissions, function(index,submission) {
												// add to filter json
												JsonArray
													.push({
														"trip" : submission.trip,
														"status" : submission.status,
														"count" : submission.count,
														"submissionId" : submission.submissionId,
														"createdDate" : submission.createdDate,
													});
											});
				  return JsonArray;
			};
		 showSubmissions=function(){
		 	var submissionsArray=getSubmissions();
		 	$("#list").jqGrid('clearGridData');
			$("#list").jqGrid('setGridParam', {
				data : submissionsArray,datatype : "local"
			}).trigger('reloadGrid');
			 	$("#list").jqGrid(
				{
					data : submissionsArray,
					altRows : false,
					deepempty : false,
					autowidth : true,
					ignoreCase : true,
					caption: "Submissions",
					datatype : "local",
					mtype : "GET",
					colNames : ["Submission", "Trip", "Created", "Images","Status"],
					colModel : [{
						
						name : "submissionId",
						index : 'submissionId',
						sorttype : 'int'
					}, {
						name : "trip",
						index : 'trip'
					}, {
						name : "createdDate",
						index : 'createdDate',
					}, {
						name : "count",
						index : 'count'
					}, {
						name : "status",
						index : 'status',
						width : 300
					}]
		});
		$("#list").jqGrid('setGridParam', {
			onSelectRow : function(rowid) {
			var sel_id = $("#list").jqGrid('getGridParam', 'selrow');
			submissionRecord = $("#list").jqGrid('getRowData',sel_id);
			loadResults(submissionRecord.submissionId);
		}
	});
		 };
		 loadResults=function(submissionId){
		 var results=getData("/odata.svc/Result?$format=json&$select=&$filter=submissionId eq "+submissionId);
	    	$("#datatable").css("display","block");
	    	$("#submissionId").html(submissionId);
	    	$("#vendor").html(results[0].vendor);
	    	$("#date").html(results[0].createdDate);
	    	$("#currency").html(results[0].currency);
	    	$("#category").html(results[0].category);
	    	$("#tax").html(results[0].tax);
	    	$("#total").html(results[0].total);
		 };
  </script>
    <h1>Convergent API ClientTest</h1>
    <u>Creating Submission</u>
    <form id="create" action="" onsubmit="return AIM.submit(this, {'onStart' : startCallback, 'onComplete' : completeCallback})">
    <table>
      <tr> <td>Trip:</td><td><input type="text" value="" id="trip"></td></tr>
      <tr><td>No.of Images to upload</td><td><input type="text" value="" id="imagescount"></td></tr>
      <tr ><td><input type="submit" value="create Submission" onClick="callme();">
      <td><td> <input type="submit" value="show Submissions" onClick="showSubmissions();"></td></tr>
    </table>
    </form></br></br>
    <div id="forms">
    
    </div>
    <table id="list" border='2px'></table></br></br>
    <div id="datatable" style="display:none;">
    <u>Results of submission(<label id="submissionId"></label>)</u></br></br>
    <table border='1'>
			<tr><td width=42%>Vendor</td><td id="vendor" name="vendor" size=24px  ></td></tr>
			<tr><td>Date</td><td id="date" name="date" size=24px  value=""></td></tr>
			<tr><td>Currency</td><td id="currency" name="currency" size=24px  value=""></td></tr>
			<tr><td>Category</td><td id="category" name="category" size=24px  value=""></td></tr>
			<tr><td>Tax</td><td id="tax" name="tax" size=24px  value=""></td></tr>
			<tr><td>Total</td><td id="total" name="total" size=24px  value=""></td></tr>
	</table>
	</div>
  </body>
</html>