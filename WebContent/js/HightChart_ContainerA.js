$(function deshabilitarBoton() {
	Highcharts.setOptions({
		lang : {
			drillUpText : 'Back To {series.name}'
		}
	});

	Highcharts
			.data({
				csv : document.getElementById('tsv').innerHTML,
				itemDelimiter : ' | ',
				parsed : function(columns) {

					var defProduct = {}, defProductData = [], extProduct = {}, drilldownSeries = [];

					// Parse percentage strings
					columns[1] = $.map(columns[0], function(value) {
						value = columns[1];
						return value;
					});

					$.each(columns[0], function(i, name) {
						var productName;
						var extproductName;
						var extdur;

						if (i > 0) {

							// Remove special edition notes
							name = name.split(' | ')[0];

							// Split into brand and version
							productName = name.split(' ')[0];
							extproductName = name.split(' ')[1];
							extdur = parseInt(name.split(' ')[2]);

							// Create the main data
							if (!defProduct[productName]) {
								defProduct[productName] = columns[1][i];
							}

							// Create the version data
							if (typeof (extproductName) !== "undefined"
									&& extproductName !== null) {
								if (!extProduct[productName]) {
									extProduct[productName] = [];
								}
								extProduct[productName].push([ extproductName,
										extdur ]);
							}
						}

					});

					$.each(defProduct, function(name, y) {
						defProductData.push({
							name : name,
							y : y,
							drilldown : extProduct[name] ? name : null
						});
					});
					$.each(extProduct, function(key, value) {
						drilldownSeries.push({
							name : 'Extensions Name',
							id : key,
							showInLegend : true,
							data : value
						});
					});

					// Create the chart
					$('.containerA')
							.highcharts(
									{
										chart : {
											type : 'column'
										},

										title : {
											text : 'Active Trial Programs With Active Extensions'
										},
										subtitle : {
											text : 'Click the bold text columns to view Extensions. Source: PROD Database'
										},
										xAxis : {
											type : 'category'
										},
										yAxis : {
											title : {
												text : 'Duration (days)'
											}
										},
										credits : {
											position : {
												align : 'left',
												x : 10
											},
											href : 'mailto:Rajasekaran Radhakrishnan <rajar@adobe.com>',
											enabled : true, // Enable/Disable
															// the credits
											text : 'Created By : R Rajasekaran'
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
											headerFormat : '<span style="font-size:11px">{series.name}</span> : ',
											pointFormat : '<span><b>{point.name}</b></span><br> Durations : <b>{point.y} days</b><br/>',
											useHTML : true,
											shared : true
										},

										series : [ {
											name : 'Default Trial Programs',
											colorByPoint : true,
											showInLegend : true,
											data : defProductData
										} ],
										drilldown : {
											series : drilldownSeries,
											drillUpButton : {
												theme : {
													fill : 'white',
													'stroke-width' : 1,
													stroke : 'silver',
													r : 0,
													states : {
														hover : {
															fill : '#B4F4BF'
														},
														select : {
															stroke : '#039',
															fill : '#bada55'
														}
													}
												}

											}
										}
									});
				}
			});
});
