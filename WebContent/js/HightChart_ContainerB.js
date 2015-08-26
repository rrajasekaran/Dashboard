$(function deshabilitarBoton() {
	$('.containerB')
			.highcharts(
					{
						chart : {
							type : 'bar'
						},
						title : {
							text : 'Product Wise : Total # of OFT Users Since CC2015 Launch'
						},
						subtitle : {
							text : 'Source: PROD Database'
						},
						xAxis : {
							type : 'category'
						},
						data : {
							csv : document.getElementById('csv').innerHTML,
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
							backgroundColor: ((Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF'),
							reversed : true,
							align : 'right',
							verticalAlign : 'top',
							x : 0,
							y : 100
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
							headerFormat : '<span style="font-size:11px">ProductName</span> : ',
							pointFormat : '<span><b>{point.name}</b></span><br>No. Of Users : <b>{point.y}</b><br/>',
							useHTML : true,
							shared : true
						},
						series : [ {
							name : 'ProductName',
							colorByPoint : true,
						} ],
					});
});
