$(function deshabilitarBoton() {
	$('.containerC')
			.highcharts(
					{
						chart : {
							type : 'column'
						},
						title : {
							text : 'Product Wise : Total Convertees (From being OFT Trial Users since CC2015 launch)'
						},
						subtitle : {
							text : 'Source: PROD Database'
						},
						xAxis : {
							type : 'category'
						},
						data : {
							csv : document.getElementById('rsv').innerHTML,
							itemDelimiter : ' | '
						},
						yAxis : {
							title : {
								text : 'No. of Convertees'
							}
						},
						credits : {
							position : {
								align : 'left',
								x : 10
							},
							href : 'mailto:Rajasekaran Radhakrishnan <rajar@adobe.com>',
							enabled : true, // Enable/Disable the credits
							text : 'Created By : R Rajasekaran'
						},
						legend : {
							enabled : false
						},
						plotOptions : {
							series : {
								borderWidth : 0,
								showInLegend: true,
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
							showInLegend: true
						} ],
					});
});
