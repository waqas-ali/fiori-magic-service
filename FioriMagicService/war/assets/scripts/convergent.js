var Convergent= {
		submissionJsonArray : [],
		grid: "list",
		currentselected: '',
		resultHistoryId: '',
		images: "foo2",
		userId: '',
		dataContainerId:"datacontainer",
		timerLimitInSeconds: 180,
		refreshLimitInseconds: 30000,
		accessToken:'',
		status:["READY","PROCESSING","EXTENDED_PROCESSING","COMPLETED"],
		statusColor:'#66CC33',
		count:0,
		currentseconds:0
};

Convergent.select=function(element){
	postData={};
	var ulid=$(element).closest("ul").attr("id");
	var name=$(element).find("input").attr("name");
	var status=["READY","PROCESSING","EXTENDED_PROCESSING","COMPLETED"];
	var markIndex=status.indexOf(name);
	for ( var i = 0; i < 3; i++) {
		if(i<=markIndex){
			var pointer=status[i];
			$('#'+ulid+' input:radio[name="'+pointer+'"]').attr("selected", true);
			var id=$(" input[type=radio][selected='selected']").parent().parent().parent();
			id.css("background-color",Convergent.statusColor);
		}
	}
	postData["status"]=name;
	if($(element).attr("name")=="COMPLETED"){
		postData["completedDate"]= new Date();	
	}
	Convergent.updateSubmissionStatus(ulid,postData);
};

//Refresh jqGrid
Convergent.refreshJqGrid=function() {
	var submissionsArray=Convergent.filterSubmissions();
	Convergent.constructGrid(submissionsArray);
	$("#"+Convergent.grid).jqGrid('clearGridData');
	$("#"+Convergent.grid).jqGrid('setGridParam', {
		data : submissionsArray,datatype : "local"
	}).trigger('reloadGrid');
};
//global method to update Data 
Convergent.updateData=function(requestUri,dataToUpdate){
	var result=$.ajax({
	    url: requestUri,
	    type: "PUT",
	    async:false,
	    cache: true,
	    contentType: 'application/json; charset=utf-8',
	    data: dataToUpdate,
	    dataType:"json",
	    success: function(data){
			//Convergent.log(data);
		},
	    error: function (XMLHttpRequest, textStatus, errorThrown) {
	      Convergent.log("Found error: " + XMLHttpRequest + " - " + textStatus + ", Error: " +  errorThrown,"error");
	    }
	})
    return result;
};

//calculate Duration
Convergent.getDuration=function(cellvalue, options, rowObject) {
	//prettyDate("2008-01-28T20:24:17Z");
	return jQuery.timeago(new Date(parseInt(cellvalue.slice(6,cellvalue.length-1))));
};

Convergent.startTimer=function(){
	Convergent.count=0;
	var newYear = new Date();
	newYear.setSeconds(newYear.getSeconds() + parseInt(Convergent.timerLimitInSeconds));
	$('#defaultCountdown').countdown('destroy');
	$("#text").empty();
	$("#addminute").unbind( "click" );
	$('#defaultCountdown').countdown( {
		until : newYear,
		onExpiry : function liftOff() {
				  Convergent.unLockSubmission(Convergent.resultHistoryId);	
				  $("#"+Convergent.dataContainerId).slideUp("slow");
		},
		layout : ' {mn} {ml} {sn} {sl}'
	},$("#addminute").click(function(event) {
		  var periods=$('#defaultCountdown').countdown('getTimes');
		  currentseconds =periods[6];
		  Convergent.count+=1;
		  periods[6]+=60;
		  $("#text").empty();
		  $("#text").append("extra ");
		  $("#text").append(Convergent.count+" Minute(s) added");
		  $("#defaultCountdown").countdown('option', {until:  periods[0] + 'y ' + periods[1] + 'o ' + periods[2] + 'w ' + periods[3] + 'd ' + periods[4] + 'h ' + periods[5] + 'm ' + periods[6] + 's'});
	}));
};
Convergent.loadNext=function() {
	var submissionsArray=Convergent.filterSubmissions();
	Convergent.loadResults(submissionsArray[0].submissionId,Convergent.userId);
};

Convergent.hideResultArea=function(){
	$("#"+Convergent.dataContainerId).slideUp("slow");
	$("#loadnext").slideDown("slow");
};
Convergent.showResultArea=function(){
	$("#"+Convergent.dataContainerId).slideDown("slow");
};
//
Convergent.storeResult=function() {
	var postData={"vendor":$('#vendor').val(),"createdDate":new Date($('#date').val()),"currency":$('#currency').val(),"category":$('#category').val(),"tax":$('#tax').val(),"total":$('#total').val()};
	Convergent.updateResult($('#resultid').val(),postData);
	Convergent.unLockSubmission($('#resulthistoryid').val());
	postData={};
	postData["status"]="COMPLETED";
	Convergent.updateSubmissionStatus(Convergent.currentselected,postData);
	Convergent.hideResultArea();
};
Convergent.loadImages=function(images) {
	$("#"+Convergent.images).empty();
  	for(var i=0;i<images.length;i++){
  		if(images[i].IMAGE_URL != '' && images[i].IMAGE_URL != null)
  		$("#"+Convergent.images).append("<img id='image"+i+"' src="+images[i].IMAGE_URL+" alt='hangmat' width='520' height='320' />");
  	}
  	Convergent.applyCarousel();
  	Convergent.applyZoom();
};
Convergent.loadResults=function(submissionId,userId){
	var results=Convergent.getResultBySubmissionId(submissionId);
	if(results.length>0){
		var resultHistory=Convergent.lockSubmission(submissionId,results[0].resultId);
		Convergent.currentselected=submissionId;
		Convergent.resultHistoryId=resultHistory.resultHistoryId;
			$("#resulthistoryid").attr("value",resultHistory.resultHistoryId);
	    	$("#resultid").attr("value",results[0].resultId);
	    	$("#vendor").attr("value",results[0].vendor);
	    	$("#date").attr("value",new Date(parseInt(results[0].createdDate.slice(6, 19))));
	    	$("#currency").attr("value",results[0].currency);
	    	$("#category").attr("value",results[0].category);
	    	$("#tax").attr("value",results[0].tax);
	    	$("#total").attr("value",results[0].total);
    	$("#resultstatus").slideUp("slow");
    	$("#loadnext").slideUp("slow");
    	Convergent.showResultArea();
	//Timer
	Convergent.startTimer(resultHistory.resultHistoryId);
	
	var images=Convergent.getImageBySubmissionId(submissionId);
	Convergent.loadImages(images);
	}else{
  		Convergent.currentselected = '';
  		Convergent.hideResultArea();
  		$("#resultstatus").slideDown("slow");
  		$("#loadnext").slideDown("slow");
  	}	
};
//to arrange status values
Convergent.getStatusValues=function(cellvalue, options, rowObject) {
	var status=Convergent.status;
	var markIndex=status.indexOf(cellvalue);
	var str = '<div id="displayByObj" class="displayType" style="float: left;"><ul class="toggleGroup horizontal" id="'+rowObject.submissionId+'">';
	for ( var i = 0; i < 3; i++) {
		if(i<=markIndex){
			str += "<li class='toggleGroup horizontal' style='background-color:"+Convergent.statusColor+"'><label><a href='#' class='buttonWrapper' onclick='Convergent.select(this);'><input type='radio' class='render_displayBy' name='"+
			status[i]+"'  value='"+status[i]+"' selected='selected'></a> "+ (i+1) +"</label></li>";
			
		}else{
			str += "<li class='toggleGroup horizontal'><label><a href='#' class='buttonWrapper' onclick='Convergent.select(this);'><input type='radio' class='render_displayBy' name='"+
			status[i]+"'  value='"+status[i]+"'></a> "+ (i+1) +"</label></li>";
			
		}		
	}
	str += "</ul></div>";
	return str;
};
//to constructjqGrid
Convergent.constructGrid = function (submissionsArray) {
	var submissionRecord = '';
	var completedRowIds=[];
	var postdata = 
    {
			filters:'{"groupOp":"AND","rules":['+
	        '{"field":"status","op":"eq","data":"READY"}'+
	        ',{"field":"status","op":"eq","data":"PROCESSING"}'+
	        ',{"field":"status","op":"eq","data":"EXTENDED_PROCESSING"}'+
	        ',{"field":"status","op":"ne","data":"COMPLETED"}]}'
    };
	var statusstr = "ALL:ALL;READY:READY;PROCESSING:PROCESSING;EXTENDED_PROCESSING:EXTENDED_PROCESSING;COMPLETED:COMPLETED";
	var grid=$("#"+Convergent.grid).jqGrid(
			{
				data : submissionsArray,
				altRows : false,
				deepempty : false,
				autowidth : true,
				ignoreCase : true,
				datatype : "local",
				mtype : "GET",
				colNames : ["id", "userid", "Submission", "Trip", "Created", "Images",
						"Status","Status", "Domain" ],
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
					search:false
				}, {
					name : "trip",
					index : 'trip',
					search:false
				}, {
					name : "createdDate",
					index : 'createdDate',
					formatter : Convergent.getDuration,
					search:false
				}, {
					name : "count",
					index : 'count',
					search:false
				},{
					name : "status",
					index : 'status',
					hidden:true,
					search:true,
					stype: 'select', 
					searchoptions:{ searchhidden: true,sopt:['eq'], value: statusstr }
				},{
					name : "status",
					index : 'status',
					formatter : Convergent.getStatusValues,
					cellattr: function (rowId, tv, rawObject, cm, rdata) {
			                //conditional formatting
			                 if (rawObject.status == 'COMPLETED') {
			                	 completedRowIds.push(rdata.id);
			                 }
			              return true;   
			            },
					width : 300,
					search:false
				}, {
					name : "domainId",
					index : 'domainId',
					search:false
				} ],
				pagination : true,
				beforeProcessing: function (data) {
			        var $self = $(this), model = data.model, name, $colHeader, $sortingIcons;
			        if (model) {
			            for (name in model) {
			                if (model.hasOwnProperty(name)) {
			                    /*$colHeader = $("#jqgh_" + $.jgrid.jqID(this.id + "_" + name));
			                    $sortingIcons = $colHeader.find(">span.s-ico");
			                    $colHeader.text(model[name].label);
			                    $colHeader.append($sortingIcons);*/
			                }
			            }
			        }
			    },
				rowNum : 5,
				rowList : [5,10,15],
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
		del : false,
		search:true
			},
			{},{},{},{closeAfterSearch:true, closeAfterReset:true,
						   onClose:function(data)
						   {
							   if($(".input-elm")[0].selectedIndex==0){
									   var grid = $("#list");
									    grid.jqGrid('setGridParam',{search:false});
				
									    var postData = grid.jqGrid('getGridParam','postData');
									    $.extend(postData,{filters:""});
									    // for singe search you should replace the line with
									    // $.extend(postData,{searchField:"",searchString:"",searchOper:""});
									    grid.trigger("reloadGrid",[{page:1}]);
							   	}
							}
						}
			);
	/*$("#"+Convergent.grid).jqGrid('filterToolbar', {autosearch: true});
	$("#"+Convergent.grid).setGridParam({datatype: 'local', data : submissionsArray});
	$("#"+Convergent.grid)[0].triggerToolbar();*/
	
	$("#"+Convergent.grid).jqGrid('setGridParam',{
		beforeSelectRow: function (rowid, e) {
		    var $self = $(this),
		        iCol = $.jgrid.getCellIndex($(e.target).closest("td")[0]),
		        
		        cm = $self.jqGrid("getGridParam", "colModel");
				    if (cm[iCol].name === "status") {
				        return false;
				    }
				    if (iCol === 10) {
				        OpenPopupWindow(rowid);
				    }
		    return true;
		},
		onSelectRow : function(rowid) {
			Convergent.isSelected();
			var sel_id = $("#"+Convergent.grid).jqGrid('getGridParam', 'selrow');
			submissionRecord = $("#"+Convergent.grid).jqGrid('getRowData',sel_id);
			Convergent.loadResults(submissionRecord.id,submissionRecord.userid);
		}
	});
	
	// hide the completed ones 
	for (var i = 0; i < completedRowIds.length; i++) {
		$("#"+completedRowIds[i]).hide();
	}
	
};

//is selected row
Convergent.isSelected=function(){
	if(Convergent.currentselected!=''){
		Convergent.unLockSubmission(Convergent.resultHistoryId);
	}
};
// global method to get the oData
Convergent.getData=function(requestUri){
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
// global method to insert Data 
Convergent.insertData=function(requestUri,dataToInsert){
	var result=$.ajax({
	    url: requestUri,
	    type: "POST",
	    async:false,
	    cache: true,
	    contentType: 'application/json; charset=utf-8',
	    data: dataToInsert,
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
// to get submission
Convergent.getSubmissionList=function(){
		var submissionURL="/odata.svc/Submission";
		return Convergent.getData(submissionURL);
};

Convergent.getUserById=function(userId){
	var getUserURL="/odata.svc/User?$select=domainId&$filter=userId eq "+userId;
	return Convergent.getData(getUserURL);
};

Convergent.getResultBySubmissionId=function(submissionId){
	var getResultURL="/odata.svc/Result?$format=json&$select=&$filter=submissionId eq "+submissionId;
	return Convergent.getData(getResultURL);
};

Convergent.getLockedSubmissionIds=function(){
	var resultHistoryURL="/odata.svc/ResultHistory?$format=json&$select=submissionId&$filter=endTime eq null";
	return Convergent.getData(resultHistoryURL)
};

Convergent.lockSubmission=function(submissionId,resultId){
	var createResultHistoryURL="/odata.svc/ResultHistory";
	var dataToInsert=JSON.stringify({"submissionId":submissionId,"resultId":resultId,"userId":CURRENT_USERID})
	return Convergent.insertData(createResultHistoryURL,dataToInsert);
};

Convergent.unLockSubmission=function(resultHistoryId){
	var updateResultHistoryURL="/odata.svc/ResultHistory("+resultHistoryId+")";
	var dataToUpdate=JSON.stringify({});
	return Convergent.updateData(updateResultHistoryURL,dataToUpdate);
};

Convergent.updateSubmissionStatus=function(submissionId,postData){
	var submissionURL="/odata.svc/Submission("+submissionId+")";
	var dataToUpdate=JSON.stringify(postData);
	return Convergent.updateData(submissionURL,dataToUpdate);
}

Convergent.updateResult=function(resultId,postData){
	var resultURL="/odata.svc/Result("+resultId+")";
	var dataToUpdate=JSON.stringify(postData);
	return Convergent.updateData(resultURL,dataToUpdate);
}

Convergent.getImageBySubmissionId=function(submissionId){
	var getImageURL="/odata.svc/Image?$format=json&$select=&$filter=submissionId eq "+submissionId;
	return Convergent.getData(getImageURL);
};

//Convergent.getRoleOfUser=function(){
//	var roleTypeId=Convergent.getRoleTypeId()[0].roleTypeId;
//	console.log(roleTypeId);
//	var getRoleURL="/odata.svc/RoleType?$format=json&$select=roleName&$filter=roleTypeId eq "+roleTypeId;
//	return Convergent.getData(getRoleURL);
//};

//Convergent.getRoleTypeId=function(){
//	var getUserRoleURL="/odata.svc/UserRole?$format=json&$select=roleTypeId&$filter=userId eq "+Convergent.accessToken;
//	return Convergent.getData(getUserRoleURL);
//};

/*
Convergent.getAccessToken=function(userName,password){
	var getUserURL="/odata.svc/User?$format=json&$select=userId&$filter=userName eq '"+userName+"' and password eq '"+password+"'";
	return Convergent.getData(getUserURL);
};
*/

Convergent.searchSubmission=function(arr,key){
		var low = 0;
		var high = arr.length - 1;
		while (low<=high)
		{
		 var mid = low + ((high-low) / 2);
		 if ((mid % 1) > 0) { mid = Math.ceil(mid); }
		  if(arr[mid].submissionId < key)
		        low = mid + 1;
		  else if (arr[mid].submissionId > key)
		        high = mid - 1;
		  else {
		        return mid;// key found
		  }
		} 
		  return -(low+1);// key not found
};

Convergent.filterSubmissions=function(){
	Convergent.submissionJsonArray = [];
	var submissions=Convergent.getSubmissionList();
	$.each(submissions, function(index,submission) {
		// get user data and get the domain details
		var user=Convergent.getUserById(submission.userId);
		Convergent.userId=submission.userId;
		var domainId="";
		if(user!="undefined" && user.length>0){
			domainId=user[0].domainId
		}
		// add to filter json
		Convergent.submissionJsonArray
			.push( {
				"id" : submission.submissionId,
				"userid":submission.userId,
				"trip" : submission.trip,
				"status" : submission.status,
				"count" : submission.count,
				"submissionId" : submission.submissionId,
				"createdDate" : submission.createdDate,
				"domainId" : domainId
			});
	});
	var lockedSubmissions=Convergent.getLockedSubmissionIds();
	$.each(lockedSubmissions, function(index,lockedSubmission) {
		var foundIndex='';
		foundIndex=Convergent.searchSubmission(Convergent.submissionJsonArray,lockedSubmission.submissionId);
		Convergent.submissionJsonArray.splice(foundIndex,1);
	});
	return Convergent.submissionJsonArray;
};
Convergent.applyCarousel=function(){
	//jQuery carousel
	$("#"+Convergent.images).carouFredSel( {
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
};
// zooming 
Convergent.applyZoom=function(){
	$("#"+Convergent.images).gzoom( {
	sW : 550,
	sH : 285,
	lW : 1024,
	lH : 768,
	lightbox : true
	})
};
//Log
Convergent.log = function(m,type){
  if (typeof console != "undefined" ){
    if ((type === 'exception'|| type==='error') && !console.exception ) {
      console.log(m.stack);
    }
    else {
      console.log(m);
    }
  }
};
// Error 
Convergent.error = function(m){
	Cart.log(m, 'error');
};
// init
Convergent.init=function(){
	var submissionsArray=Convergent.filterSubmissions();
	Convergent.constructGrid(submissionsArray);
	setInterval("Convergent.refreshJqGrid()", Convergent.refreshLimitInseconds);
};

Convergent.loginValidation=function(){
		$('form#login :submit').addClass('inputSubmitBtn').click(function(){
			if($('#username').val() == "" || $('#password').val() == "")
			{
				for(i=0;i<5;i++)
					$("#loginResult").animate({opacity: 'toggle'}, 500 );	
					
					$("#loginResult").fadeIn(500);					
				return false;
			};
		});
	
};

//ready
$(document).ready(function(){
	if(window.location.href.indexOf("Home")>0){
		Convergent.init();
	}
	if(window.location.href.indexOf("Login")>0){
		Convergent.loginValidation();
	}	
});