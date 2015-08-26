$(function deshabilitarBoton() {

	Highcharts
			.data({
				csv : document.getElementById('bsv').innerHTML,
				itemDelimiter : ' | ',
				parsed : function(columns) {
					var defSuiteName = {}, defProductData = [], extProduct = {}, drilldownSeries = [], map1 = new Object();

					// Parse percentage strings

					var tempArray = columns[0];
					var tempData, SuiteName = null;
					for (var i = 1; i < tempArray.length; i++) {
						SuiteName = tempArray[i].split(" ")[0];
						tempData = parseInt(tempArray[i].split(" ")[2]);
						/* console.log(SuiteName + " | " + tempData); */
						if (SuiteName in map1) {
							map1[SuiteName] = map1[SuiteName] + tempData;
						} else {
							map1[SuiteName] = tempData;
						}
						/* console.log(map1[SuiteName]); */
					}

					$.each(columns[0], function(i, name) {
						var suiteName;
						var productName;
						var count;

						if (i > 0) {

							// Remove special edition notes
							names = name.split(' | ')[0].trim();

							// Split into brand and version
							suiteName = names.split(' ')[0].trim();
							productName = names.split(' ')[1].trim();
							count = parseInt(names.split(' ')[2]);

							// Create the main data
							if (!defSuiteName[suiteName]) {
								defSuiteName[suiteName] = map1[suiteName];
							}

							// Create the version data
							if (typeof (productName) !== "undefined"
									&& productName !== null) {
								if (!extProduct[suiteName]) {
									extProduct[suiteName] = [];
								}
								extProduct[suiteName]
										.push([ productName, count ]);
							}
						}

					});

					$.each(defSuiteName, function(name, y) {
						defProductData.push({
							name : name,
							y : y,
							drilldown : extProduct[name] ? name : null
						});
					});
					$.each(extProduct, function(key, value) {
						drilldownSeries.push({
							name : 'Extension Name',
							id : key,
							data : value
						});
					});

					// Create the chart
					$('.containerF')
							.highcharts(
									{
										chart : {
											type : 'column'
										},
										title : {
											text : 'Product Wise : Total Convertees (From being Trial Extension Users since 02-AUG-2015)'
										},
										subtitle : {
											text : 'Click the bold text columns to view Extension Availed To Users. Source: PROD Database'
										},
										xAxis : {
											type : 'category'
										},
										yAxis : {
											title : {
												text : 'No. Of Convertees'
											}
										},
										legend : {
											enabled : false
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
											pointFormat : '<span><b>{point.name}</b></span><br>No. Of Users : <b>{point.y}</b><br/>',
											useHTML : true,
											shared : true
										},

										series : [ {
											name : 'ProductName',
											colorByPoint : true,
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
