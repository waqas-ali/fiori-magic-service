var jsonObj = [];
var destArr = [];
var currentselected='';
var resultHistoryId='';
/*function test(id) {
	$("#datacontainer").slideDown("slow");
	$('#list tr').attr("disabled", false);
	$('#list tr').removeClass("ui-state-highlight");
	$('#' + id).attr("disabled", true);
	$('#' + id).addClass("ui-state-highlight");
	//Timer
	var newYear = new Date();
	newYear.setSeconds(newYear.getSeconds() + parseInt(180));
	$('#defaultCountdown').countdown('destroy');
	$('#defaultCountdown').countdown( {
		until : newYear,
		onExpiry : liftOff,
		layout : ' {mn} {ml} {sn} {sl}'
	});
}*/
function startTimer(resultHistoryId){
	var newYear = new Date();
	newYear.setSeconds(newYear.getSeconds() + parseInt(40));
	$('#defaultCountdown').countdown('destroy');
	$('#defaultCountdown').countdown( {
		until : newYear,
		onExpiry : function liftOff() {
			$.ajax({
	    	      url: "/odata.svc/ResultHistory("+resultHistoryId+")",
	    	      type: "PUT",
	    	      async:false,
	    	      //contentType: "application/json;odata=verbose",
	    	      contentType: 'application/json; charset=utf-8',
	    	      data: JSON.stringify({}),
	    	      dataType: "json",
	    	      success: function (data) {
	    	      }
		      }); 
			$("#datacontainer").slideUp();
		},
		layout : ' {mn} {ml} {sn} {sl}'
	});
}
function storeResult() {
	//console.log( $('form').serializeArray());
	$.ajax({
	      url: "/odata.svc/Result("+$('#resultid').val()+")",
	      type: "PUT",
	      async:false,
	      //contentType: "application/json;odata=verbose",
	      contentType: 'application/json; charset=utf-8',
	      data:  JSON.stringify({"vendor":$('#vendor').val(),"date":$('#date').val(),"currency":$('#currency').val(),"category":$('#category').val(),"tax":$('#tax').val(),"total":$('#total').val()}),
	      dataType: "json",
	      success: function (response) {
	    	 // console.log(response);
	      }
	});
	$.ajax({
	      url: "/odata.svc/ResultHistory("+$('#resulthistoryid').val()+")",
	      type: "PUT",
	      async:false,
	      //contentType: "application/json;odata=verbose",
	      contentType: 'application/json; charset=utf-8',
	      data: JSON.stringify({}),
	      dataType: "json",
	      success: function (data) {
	    	  console.log(data);
	      }
    }); 
	$("#datacontainer").slideUp();
	
	
}
function linkFormatter(cellvalue, options, rowObject) {
	return '<a style="text-decoration:none;" href="javascript:test('
			+ options.rowId + ');" >' + cellvalue + '</a>';
}

function getValues(cellvalue, options, rowObject) {
	var status=["READY","PROCESSING","EXTENDED_PROCESSING","COMPLETED"];
	var markIndex=status.indexOf(cellvalue);
	var str = '<div class="checkboxes">';
	for ( var i = 0; i < 4; i++) {
		//console.log(status[i]);
		if(i<=markIndex){
			str += '<label><input type="checkbox" href="#" value=' + rowObject.submissionId + ' id='
			+ status[i] + ' onclick=updateSubmission(this) checked="checked" >' + (i+1) + "</label>";
		}else{
			str += '<label><input type="checkbox" href="#" value=' + rowObject.submissionId + ' id='
			+ status[i] + ' onclick=updateSubmission(this)>' + (i+1) + "</label>";
		}		
	}
	str+="</div>";
	return str;
}
function updateSubmission(element) {
	postData={};
	var id=$(element).attr("id");
	var status=["READY","PROCESSING","EXTENDED_PROCESSING","COMPLETED"];
	var markIndex=status.indexOf(id);
	for ( var i = 0; i < 4; i++) {
		if(i<=markIndex){
			var pointer=status[i];
		//	console.log($("#"+pointer));
			$("#"+pointer).attr('checked', "checked");
		}
	}
	//console.log("id  "+id);
//console.log($('#PROCESSING').html());
	postData["status"]=$(element).attr("id");
	if($(element).attr("id")=="COMPLETED"){
		postData["completedDate"]= new Date();	
	}
	$.ajax( {
		url : "/odata.svc/Submission("+$(element).val()+")",
		type : "PUT",
		//contentType: "application/json;odata=verbose",
		contentType : 'application/json; charset=utf-8',
		data :JSON.stringify(postData),
		success : function(data) {
		}
	});
}
function getMins(cellvalue, options, rowObject) {
	//prettyDate("2008-01-28T20:24:17Z");
	return jQuery.timeago(new Date(parseInt(cellvalue.slice(6,cellvalue.length-1))));
}
function loadNext() {
	$.ajax( {
		url : "/odata.svc/Submission?$orderby=createdDate",
		type : "GET",
		//contentType: "application/json;odata=verbose",
		contentType : 'application/json; charset=utf-8',
		dataType : "json",
		async:false,
		success : function(data) {
			var response;
			var i = 0;
			for (i = 0; i < data.d.results.length; i++) {
				$.ajax( {
					url : "/odata.svc/ResultHistory?$format=json&$filter=endTime eq null and submissionId eq "+data.d.results[i].submissionId,
					type : "GET",
					//contentType: "application/json;odata=verbose",
					async : false,
					contentType : 'application/json; charset=utf-8',
					dataType : "json",
					success : function(response) {
						currentselected=data.d.results[i].submissionId;
						console.log(response.d.results.length);
								if(response.d.results.length>0)
									//Reading result data
	  				    	    	  $.ajax({
			  				  	    	      url: "/odata.svc/Result?$format=json&$select=&$filter=submissionId eq "+data.d.results[i].submissionId,
			  				  	    	      type: "GET",
			  				  	    	      async:false,
			  				  	    	      contentType: 'application/json; charset=utf-8',
			  				  	    	      dataType: "json",
			  				  	    	      success: function (data) {
			  				  	    	    	  console.log(currentselected);
			  				  	    	    	if(data.d.results.length>0){
					  				  	    	    	$("#resulthistoryid").attr("value",response.d.results[0].resultHistoryId);
					  				  	    	    	$("#resultid").attr("value",data.d.results[0].resultId);
					  				  	    	    	$("#vendor").attr("value",data.d.results[0].vendor);
					  				  	    	    	$("#date").attr("value",new Date(parseInt(data.d.results[0].createdDate.slice(6, 19))));
					  				  	    	    	$("#currency").attr("value",data.d.results[0].currency);
					  				  	    	    	$("#category").attr("value",data.d.results[0].category);
					  				  	    	    	$("#tax").attr("value",data.d.results[0].tax);
					  				  	    	    	$("#total").attr("value",data.d.results[0].total);
				  				  	    	    
					  				  	    	    $("#resultstatus").slideUp("slow");
					  	  							$("#datacontainer").slideDown("slow");
					  	  						//Timer
					  	  							startTimer(response.d.results[0].resultHistoryId);
					  	  						//Loading Image
					  	  							$.ajax({
					  	  				  	    	      url: "/odata.svc/Image?$format=json&$select=&$filter=submissionId eq "+submissionrecord.id ,
					  	  				  	    	      type: "GET",
					  	  				  	    	      async:false,
					  	  				  	    	      contentType: 'application/json; charset=utf-8',
					  	  				  	    	      dataType: "json",
					  	  				  	    	      success: function (data) {
					  	  				  	    	    	  console.log("image data");
					  	  				  	    	    	for(var i=0;i<data.d.results.length;i++){
					  	  				  	    	    		if(data.d.results[i].IMAGE_URL != '' && data.d.results[i].IMAGE_URL != null)
					  	  				  	    	    		$("#foo2").append("<img src="+data.d.results[i].IMAGE_URL+" alt='hangmat' width='520' height='240' />");
					  	  				  	    	    	}
					  	  				  	    	    	
					  	  				  	    	      }
					  	  							});
			  				  	    	    	}else{
			  				  	    	    		currentselected = '';
			  				  	    	    		$("#resultstatus").slideDown("slow");
				  				  	    	    }
			  				  	    	    }
	  				    	    	  });
								}
				});
				
			}
		}
	});
}
function getJSON() {
	var submissionId = [];
	$.ajax( {
				url : "/odata.svc/Submission?$orderby=createdDate",
				type : "GET",
				//contentType: "application/json;odata=verbose",
				contentType : 'application/json; charset=utf-8',
				dataType : "json",
				async:false,
				success : function(data) {
					var response;
					var i = 0;
					for (i = 0; i < data.d.results.length; i++) {
						$.ajax( {
									url : "/odata.svc/User?$format=json&$select=domainId&$filter=userId eq "
											+ data.d.results[i].userId,
									type : "GET",
									//contentType: "application/json;odata=verbose",
									async : false,
									contentType : 'application/json; charset=utf-8',
									dataType : "json",
									success : function(response) {
										jsonObj
												.push( {
													"id" : data.d.results[i].submissionId,
													"userid":data.d.results[i].userId,
													"trip" : data.d.results[i].trip,
													"status" : data.d.results[i].status,
													"count" : data.d.results[i].count,
													"submissionId" : data.d.results[i].submissionId,
													"createdDate" : data.d.results[i].createdDate,
													"domainId" : response.d.results[0].domainId
												});
									}
								});
					}
					$.ajax( {
						url : "/odata.svc/ResultHistory?$format=json&$select=submissionId&$filter=endTime eq null",
						type : "GET",
						//contentType: "application/json;odata=verbose",
						async : false,
						contentType : 'application/json; charset=utf-8',
						dataType : "json",
						success : function(response) {
							for(var i=0;i<response.d.results.length;i++){
							submissionId[i]=response.d.results[i].submissionId;
							console.log(submissionId[i]);
							}
						}
					});
					var count=0;
					if(submissionId.length>0){
						for(i in jsonObj){ 
							count=0;
						for(var j=0;j<submissionId.length;j++){
							        if(jsonObj[i].submissionId!=submissionId[j]) {
							        	count++;
							        }
							    }
						if(count==submissionId.length)
							destArr[i]=jsonObj[i];
						}
					}else{
						 destArr=jsonObj;
					}
					jsonObj = [];
					var j=0;
					for(var i = 0; i < destArr.length; i++){
						if(destArr[i]!=null){
						jsonObj[j]=destArr[i];
						j++;
						}
					
					}
				}
			});
}
function refresh() {
	jsonObj = [];
	destArr=[];
	var submissionId='';
	//console.log($("#list").getGridParam('selarrrow'));
	//console.log($(".ui-state-highlight").attr('id'));
	getJSON();
	$('#list').jqGrid('clearGridData');
	$('#list').jqGrid('setGridParam', {
		data : jsonObj,datatype : "local"
	}).trigger('reloadGrid');
	//$('#list').jqGrid('setData', destArr);
}

jQuery(document).ready(function() {
	//Grid Display
		getJSON();
		setInterval("refresh()", 30000);
		$("#list").jqGrid(
				{
					data : jsonObj,
					altRows : false,
					deepempty : false,
					autowidth : true,
					ignoreCase : true,
					datatype : "local",
					mtype : "GET",
					colNames : ["id", "userid", "Submission", "Trip", "Created", "Images",
							"Status", "Domain" ],
					colModel : [ {
						
						name : "id",
						index : 'id',
						hidden:true
					},  {
						
						name : "userid",
						index : 'userid',
						hidden:true
					},	{
						
						name : "submissionId",
						index : 'submissionId',
						sorttype : 'int',
						formatter : linkFormatter
					}, {
						name : "trip",
						index : 'trip'
					}, {
						name : "createdDate",
						index : 'createdDate',
						formatter : getMins
					}, {
						name : "count",
						index : 'count'
					}, {
						name : "status",
						index : 'status',
						formatter :getValues,
						width : 300
					}, {
						name : "domainId",
						index : 'domainId'
					} ],
					pagination : true,
					rowNum : 5,
					rowList : [ 10, 20, 30 ],
					rownumbers : false,
					gridview : true,
					loadonce : true,
					shrinkToFit : true,
					pager : "#page",
					sortname : "createdDate",
					sortorder : "asc",
					viewrecords : true,
					forceFit : true,
					caption : "Submission Details"
				}).jqGrid('navGrid', '#page', {
			edit : false,
			add : false,
			del : false
		});
		$("#list").jqGrid('setGridParam', {
			onSelectRow : function(rowid) {
				console.log("currentselected is"+currentselected);
				if(currentselected!=''){
					console.log("resultHistoryId "+resultHistoryId);
					$.ajax({
			    	      url: "/odata.svc/ResultHistory("+resultHistoryId+")",
			    	      type: "PUT",
			    	      async:false,
			    	      //contentType: "application/json;odata=verbose",
			    	      contentType: 'application/json; charset=utf-8',
			    	      data: JSON.stringify({}),
			    	      dataType: "json",
			    	      success: function (data) {
			    	      }
				      }); 
				}
				var sel_id = $("#list").jqGrid('getGridParam', 'selrow');
				var submissionrecord = $("#list").jqGrid('getRowData',sel_id);
				
				//disable current selected and enabling remaining
				$('#list tr').attr("disabled", false);
				$('#list tr').removeClass("ui-state-highlight");
				$('#' + rowid).attr("disabled", true);
				$('#' + rowid).addClass("ui-state-highlight");
				
				$.ajax({
  	    	      url: "/odata.svc/Result?$format=json&$select=&$filter=submissionId eq "+submissionrecord.id,
  	    	      type: "GET",
  	    	      async:false,
  	    	      //contentType: "application/json;odata=verbose",
  	    	      contentType: 'application/json; charset=utf-8',
  	    	      dataType: "json",
  	    	      success: function (response) {
  	    	    	if(response.d.results.length>0){
  							$.ajax({
  				    	      url: "/odata.svc/ResultHistory",
  				    	      type: "POST",
  				    	      async:false,
  				    	      contentType: 'application/json; charset=utf-8',
  				    	      data: JSON.stringify({"userId":submissionrecord.userid,"resultId":response.d.results[0].resultId,"submissionId":submissionrecord.id}),
  				    	      dataType: "json",
  				    	      success: function (data) {
  				    	    	  resultHistoryId=data.d.resultHistoryId;
  				    	    	//Reading result data
  				    	    	  $.ajax({
		  				  	    	      url: "/odata.svc/Result?$format=json&$select=&$filter=submissionId eq "+submissionrecord.id,
		  				  	    	      type: "GET",
		  				  	    	      async:false,
		  				  	    	      contentType: 'application/json; charset=utf-8',
		  				  	    	      dataType: "json",
		  				  	    	      success: function (data) {
				  				  	    	    	currentselected=submissionrecord.id;
				  				  	    	    	$("#resulthistoryid").attr("value",resultHistoryId);
				  				  	    	    	$("#resultid").attr("value",data.d.results[0].resultId);
				  				  	    	    	$("#vendor").attr("value",data.d.results[0].vendor);
				  				  	    	    	$("#date").attr("value",new Date(parseInt(data.d.results[0].createdDate.slice(6, 19))));
				  				  	    	    	$("#currency").attr("value",data.d.results[0].currency);
				  				  	    	    	$("#category").attr("value",data.d.results[0].category);
				  				  	    	    	$("#tax").attr("value",data.d.results[0].tax);
				  				  	    	    	$("#total").attr("value",data.d.results[0].total);
		  				  	    	      }
  				    	    	  });
  				    	      }
  					      }); 
  							$("#resultstatus").slideUp("slow");
  							$("#datacontainer").slideDown("slow");
  						//Timer
  							startTimer(resultHistoryId);
  						//Loading Image
  							$.ajax({
  				  	    	      url: "/odata.svc/Image?$format=json&$select=&$filter=submissionId eq "+submissionrecord.id ,
  				  	    	      type: "GET",
  				  	    	      async:false,
  				  	    	      contentType: 'application/json; charset=utf-8',
  				  	    	      dataType: "json",
  				  	    	      success: function (data) {
  				  	    	    	for(var i=0;i<data.d.results.length;i++){
  				  	    	    		if(data.d.results[i].IMAGE_URL != '' && data.d.results[i].IMAGE_URL != null)
  				  	    	    		$("#foo2").append("<img src="+data.d.results[i].IMAGE_URL+" alt='hangmat' width='520' height='240' />");
  				  	    	    	}
  				  	    	    	
  				  	    	      }
  							});
  						}else{
  	    	    		currentselected = '';
  	    	    		$("#datacontainer").slideUp("slow");
  	    	    		$("#resultstatus").slideDown("slow");
  	    	    	}	
  	    	      }
				}); 
			}
		});
				 		//jQuery carousel
						$("#foo2").carouFredSel( {
							circular : false,
							infinite : false,
							auto : false,
							items	:{
								visible:1,
							 	filter:"img"
							},
							prev : {
								button : "#foo2_prev",
								key : "left"
							},
							next : {
								button : "#foo2_next",
								key : "right"
							},
							pagination : "#foo2_pag"
						});
						$(function() {
							$("#foo2").gzoom( {
								sW : 550,
								sH : 285,
								lW : 1024,
								lH : 768,
								lightbox : true,
								zoomIcon : 'images/gtk-zoom-in.png'
							});
						});
		$("#datacontainer").css("display", "none");

	});
