$(function deshabilitarBoton() {
	$('.containerE')
			.highcharts(
					{
						chart : {
							type : 'bar'
						},
						title : {
							text : 'Extension Wise : Total # of OFT Users Availed Extension Since 02-AUG-2015'
						},
						subtitle : {
							text : 'Source: PROD Database' 
						},
						xAxis : {
							type : 'category'
						},
						data : {
							csv : document.getElementById('asv').innerHTML,
							itemDelimiter : ' | '
						},
						yAxis : {
							title : {
								text : 'No. of User'
							}
						},
						credits : {
							position: {
								align: 'left',
								x: 10
								},
							href : 'mailto:Rajasekaran Radhakrishnan <rajar@adobe.com>' ,
							enabled : true, // Enable/Disable the credits
							text : 'Created By : R Rajasekaran'
						},
						legend : {
							enabled : true
						},
						plotOptions : {
							series : {
								borderWidth : 0,
								dataLabels : {
									enabled : true,
									format : '{point.y}'
								}
							}
						},

						tooltip : {
							headerFormat : '<span style="font-size:11px">ExtensionName</span> : ',
							pointFormat : '<span><b>{point.name}</b></span><br>No. Of Users : <b>{point.y}</b><br/>',
							useHTML : true,
							shared : true
						},
						series : [ {
							name : 'ExtensionName',
							colorByPoint : true,
						} ],
					});
});
