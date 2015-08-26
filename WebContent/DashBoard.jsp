<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.adobe.utils.com.*"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.SortedSet"%>
<%@page import="java.util.TreeSet"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="org.apache.commons.io.*"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<link rel="stylesheet" href="style.css" type="text/css" />
		<!-- 
		<script type="text/javascript" src="http://code.jquery.com/jquery-1.11.3.js"></script>
		<script src="http://code.highcharts.com/highcharts.js"></script>
		<script src="http://code.highcharts.com/modules/data.js"></script>
		<script src="http://code.highcharts.com/modules/drilldown.js"></script>
		<script type="text/javascript" src="http://malsup.github.io/jquery.blockUI.js"></script>
		 -->
		<script type="text/javascript" src="./js/framework/jquery-1.11.3.js"></script>
		<script type="text/javascript" src="./js/framework/jquery.blockUI.js"></script>
		<script src="./js/framework/highcharts.js"></script>
		<script src="./js/framework/data.js"></script>
		<script src="./js/framework/drilldown.js"></script>
		<!-- <script src="./js/framework/exporting.js"></script> -->
		<script src="./js/framework/sand-signika.js"></script>
		<script type="text/javascript" src="./js/wait.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerA.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerB.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerC.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerD.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerE.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerF.js"></script>
		<script type="text/javascript" src="./js/HightChart_ContainerG.js"></script>
		<%
		DBUtils utils = new DBUtils();
		
		StringBuilder DBData = new StringBuilder();
		StringBuilder TotOFTUserData = new StringBuilder();
		StringBuilder TotOFTConVUserData = new StringBuilder();
		StringBuilder TotSuiteOFTConVUserData = new StringBuilder();
		StringBuilder TotExtenAvailedOFTUserData = new StringBuilder();
		StringBuilder TotExtenAvailedProdWiseOFTUserData = new StringBuilder();
		StringBuilder TotExtenAvailedSuiteWiseOFTUserData = new StringBuilder();
		
		int CountofOFTUser = 0, CountofOFTConverteesUser = 0;
		
		ArrayList<String> DefExtData = new ArrayList<String>();
		ArrayList<String> ProductWiseData = new ArrayList<String>();
		ArrayList<String> ProductWiseConvertedData = new ArrayList<String>();
		ArrayList<String> SuiteWiseConvertedData = new ArrayList<String>();
		ArrayList<String> ExtavailedOFTUserData = new ArrayList<String>();
		ArrayList<String> ExtavailedProdWiseOFTUserData = new ArrayList<String>();
		ArrayList<String> ExtavailedSuiteWiseOFTUserData = new ArrayList<String>();
		
		HashMap<String, Object> inputData = null;
		if(request.getParameter("playChartviaDB") != null && request.getParameter("playChartviaDB").equalsIgnoreCase("SYNC from DB")){
			utils.loadData.clear();
		}
		if (request.getParameter("playChartviaDB") != null) {
			if (request.getParameter("playChartviaDB").equalsIgnoreCase("SYNC from DB")) {
				utils.defaultTrialProgramWithExtensions("PROD");
				inputData = utils.loadData;
			}				
		} else {
			if(utils.loadData.size() > 0 ) { 
				inputData = utils.loadData;
			} else {
				utils.defaultTrialProgramWithExtensions("PROD");
				inputData = utils.loadData;
			}
		}
		System.out.println("No. of Cache Data in Server : " + inputData.size());
			
		if(inputData.containsKey("DefaultTrialProgramWithExtensions")) {
			DefExtData = (ArrayList)inputData.get("DefaultTrialProgramWithExtensions");
			for (int j = 0; j < DefExtData.size(); j++) {
				DBData.append(DefExtData.get(j));
				/* System.out.println(DefExtData.get(j)); */
			}
		} 
		if (inputData.containsKey("ProductWiseOFTUsers")) {
			ProductWiseData = (ArrayList) inputData.get("ProductWiseOFTUsers");
			for (int j = 0; j < ProductWiseData.size(); j++) {
				TotOFTUserData.append(ProductWiseData.get(j));
				/* System.out.println(ProductWiseData.get(j)); */
			}
		}
		if (inputData.containsKey("ProductWiseConvertedOFTUsers")) {
			ProductWiseConvertedData = (ArrayList) inputData.get("ProductWiseConvertedOFTUsers");
			for (int j = 0; j < ProductWiseConvertedData.size(); j++) {
				TotOFTConVUserData.append(ProductWiseConvertedData.get(j));
				/* System.out.println(ProductWiseConvertedData.get(j)); */
			}
		}
		if (inputData.containsKey("SuiteWiseConvertedOFTUsers")) {
			SuiteWiseConvertedData = (ArrayList) inputData.get("SuiteWiseConvertedOFTUsers");
			for (int j = 0; j < SuiteWiseConvertedData.size(); j++) {
				TotSuiteOFTConVUserData.append(SuiteWiseConvertedData.get(j));
				/* System.out.println(SuiteWiseConvertedData.get(j)); */
			}
		}
		if (inputData.containsKey("ExtensionAvailedNoOFTUsers")) {
			ExtavailedOFTUserData = (ArrayList) inputData.get("ExtensionAvailedNoOFTUsers");
			for (int j = 0; j < ExtavailedOFTUserData.size(); j++) {
				TotExtenAvailedOFTUserData.append(ExtavailedOFTUserData.get(j));
				/* System.out.println(ExtavailedOFTUserData.get(j)); */
			}
		}
		if (inputData.containsKey("ExtensionAvailedProductWiseNoOFTUsersConV")) {
			ExtavailedProdWiseOFTUserData = (ArrayList) inputData.get("ExtensionAvailedProductWiseNoOFTUsersConV");
			for (int j = 0; j < ExtavailedProdWiseOFTUserData.size(); j++) {
				TotExtenAvailedProdWiseOFTUserData.append(ExtavailedProdWiseOFTUserData.get(j));
				/* System.out.println(ExtavailedProdWiseOFTUserData.get(j)); */
			}
		}
		if (inputData.containsKey("ExtensionAvailedSuiteWiseNoOFTUsersConV")) {
			ExtavailedSuiteWiseOFTUserData = (ArrayList) inputData.get("ExtensionAvailedSuiteWiseNoOFTUsersConV");
			for (int j = 0; j < ExtavailedSuiteWiseOFTUserData.size(); j++) {
				TotExtenAvailedSuiteWiseOFTUserData.append(ExtavailedSuiteWiseOFTUserData.get(j));
				/* System.out.println("\n\n\n\n" +ExtavailedSuiteWiseOFTUserData.get(j)); */
			}
		}				
		if (inputData.containsKey("NoOfUniqueUserInOFT")) {
			CountofOFTUser = Integer.parseInt(inputData.get("NoOfUniqueUserInOFT").toString());
			/* System.out.println(CountofOFTUser); */
		}
		if (inputData.containsKey("NoOfUniqueConverteeUserInOFT")) {
			CountofOFTConverteesUser = Integer.parseInt(inputData.get("NoOfUniqueConverteeUserInOFT").toString());
			/* System.out.println(CountofOFTConverteesUser); */
		}				
		%>
		<%
			Integer hitsCount = (Integer)application.getAttribute("hitCounter");
			if( hitsCount ==null || hitsCount == 0 ){
				/* First visit */
				hitsCount = 1;
			}else{
			   /* return visit */
			   hitsCount += 1;
			}
			application.setAttribute("hitCounter", hitsCount);
		%>
		
		<title>OFT Dashboard</title>
	</head>
	<body bgcolor="#D6D6D6">
	  <form action="DashBoard.jsp" method="post">
		<h1 align="center">* OFT DASHBOARD *</h1>
		<p align="right">Total Unique OFT Users &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;<b><i><%=CountofOFTUser%></i></b><br>Total Unique OFT Convertees &nbsp;&nbsp;&nbsp;&nbsp;: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b><i><%=CountofOFTConverteesUser%></i></b></p>
	   	<p align="left">Data Cached On : <b><%=inputData.get("currentTime")%></b></p>
		<div>
			<input type="hidden" value="SYNC from DB" name="playChartviaDB" onclick="deshabilitarBoton()" class="waitScript" disabled>
		</div>
	   	<hr>
	   	<div class="containerB" style="min-width: 310px; height: 550px; margin: 0 auto"></div>
	   	<pre id="csv" style="display:none">
			<%=TotOFTUserData%>
	   	</pre>
	   	<hr>
	   	<div class="containerC" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	   	<pre id="rsv" style="display:none">
		   	<%=TotOFTConVUserData%>
	   	</pre>
	   	<hr>
		<div class="containerD" style="min-width: 310px; height: 600px; margin: 0 auto"></div>
	   	<pre id="psv" style="display:none">
	   		<%=TotSuiteOFTConVUserData%>
	   	</pre>
	 	<hr>
	   	<div class="containerE" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	   	<pre id="asv" style="display:none">
			<%=TotExtenAvailedOFTUserData%>
	   	</pre>
	   	<hr>
	   	<div class="containerF" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	   	<pre id="bsv" style="display:none">
			<%=TotExtenAvailedProdWiseOFTUserData%>
	   	</pre>
	   	<hr>
	   	<div class="containerG" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	   	<pre id="dsv" style="display:none">
			<%=TotExtenAvailedSuiteWiseOFTUserData%>
	   	</pre>
	   	<hr>
	   	<div class="containerA" style="min-width: 310px; height: 400px; margin: 0 auto"></div>
	   	<pre id="tsv" style="display:none">
			<%=DBData%>
	   	</pre>
	   	<hr>
	   	<a href="https://wiki.corp.adobe.com/display/Suites/OFT+Dashboard" target=_blank>Click Here for More Info</a>
		<p align="right">Total number of visits: <%= hitsCount%></p>
	   </form>
	</body>
</html>
