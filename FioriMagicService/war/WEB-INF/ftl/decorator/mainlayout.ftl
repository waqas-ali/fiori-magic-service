<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html" charset=utf-8" />
<link href="/assets/styles/stylemain.css" type="text/css" rel="stylesheet" />
<title>${title}</title> 
     <link rel="stylesheet" type="text/css" href="/assets/styles/stylemain.css"/>
     <link rel="stylesheet" type="text/css" href="/assets/styles/jquery.countdown.css"/>
     <link rel="stylesheet" type="text/css" href="/assets/styles/ui.jqgrid.css"/>
     <link rel="stylesheet" type="text/css"  href="/assets/styles/jquery-ui-custom.css"/>
     <link rel="stylesheet" type="text/css"  href="/assets/styles/carousel.css"/>
    
     <script src="/assets/scripts/jquery-1.7.js" type="text/javascript"></script>
     <script src="/assets/scripts/jquery.countdown.js" type="text/javascript"></script>
     <script src="/assets/scripts/jquery.jqGrid.src.js" type="text/javascript"></script>
     <script type="text/javascript" src="/assets/scripts/jquery.carouFredSel.js"></script>
    <script type="text/javascript">
	 jQuery(document).ready(function() {
        //Grid Display
         $("#list").jqGrid(
             {
                 url: "test.json",
                 altRows: true,
                 deepempty: true,
                 autowidth: true,
                 ignoreCase: true,
                 datatype: "json",
                 mtype: "GET",
                 jsonReader :
                 {root: "rows",
					page: "page",
					total: "total",
					records: "records",
					repeatitems: false,
					cell: "",
					id: "0",
					}                    ,

                 colNames:  ["SubmissionID","UserID","Status"],
                 colModel:  [
                             { name: "SubmissionID",index:'SubmissionID', width:55,sorttype:'int'}, 
								{ name: "UserID",index:'UserID', width:55},
								{ name: "Status",index:'Status', width:55}
							
								],
								
                 pager: "#pager",
                 rowNum: 20,
                 rowList: [20, 50, 100],
                 sortname: "name",
                 sortorder: "asc",
                 viewrecords: true,
                 gridview: true,
                 autoencode: true,
                 caption: "Submission Details"
             });
         $("#list").jqGrid('setGridParam', {ondblClickRow: function(rowid,iRow,iCol,e){alert('double clicked');}});
         //jQuery carousel
         $("#foo2").carouFredSel({
				circular: false,
				infinite: false,
				auto 	: false,
				 items       : 1,
				prev	: {	
					button	: "#foo2_prev",
					key		: "left"
				},
				next	: { 
					button	: "#foo2_next",
					key		: "right"
				},
				pagination	: "#foo2_pag"
			});
     	//Timer
		 var newYear = new Date(); 
		 newYear.setMinutes(newYear.getMinutes() + parseInt(3));
         $('#defaultCountdown').countdown({
        	 until: newYear,
        	 layout:'{s<}{snn}{sl}{s>}'	 
         }); 
	 });
 </script>
 ${head}
</head>
<body>
	<div class="wrapper">
		Hello India
		${body}
	</div> <!-- Wrapper ends-->
</body>
</html>
