package com.adobe.utils.com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

public class DBUtils {

	public static HashMap<String, Object> loadData = new HashMap<String, Object>();

	String ActiveProgramQuery = "select program_name, trial_duration, status from honesty.lm_product_trial_config "
			+ "order by program_name";

	String ActiveExtensionQuery = "select extension_program_name, duration, status,extension_end_date from lm_extension_trial_config";

	String TotalOftUser = "select /*+FULL(tm)*/ tm.product_id, count(unique(tm.delegate_guid)) count from lm_trial_master tm "
			+ "group by tm.product_id "
			+ "order by tm.product_id";

	String TotalOftUserProdWiseConvertee = "select tm.product_id as productname, count(unique(tm.delegate_guid)) as count from "
			+ "subscribers sub , lm_trial_master tm, skus s, subscriptions sub1, entitlements ent "
			+ "where sub.userid = tm.delegate_guid " + "and sub1.userid = tm.delegate_guid "
			+ "and sub.userid = sub1.userid " + "and trim(s.skuname) = trim(sub1.sku) "
			+ "and tm.product_id = ent.productid " + "and s.entid = ent.id " + "and sub.created >= '16-JUN-15' "
			+ "and to_date(tm.trial_start_date, 'DD-MM-YY') != to_date(sub.created, 'DD-MM-YY') "
			+ "group by tm.product_id "
			+ "order by tm.product_id";

	String TotalOftUserSuiteWiseConvertee = "select ent.productid as suitename, tm.product_id as productname, count(unique(tm.delegate_guid)) as count from "
			+ "subscribers sub , lm_trial_master tm, skus s, subscriptions sub1, entitlements ent "
			+ "where sub.userid = tm.delegate_guid " + "and sub1.userid = tm.delegate_guid "
			+ "and sub.userid = sub1.userid " + "and trim(s.skuname) = trim(sub1.sku) "
			+ "and ent.productid in ('CreativeCloudMembership-2.0-ALL','CreativeCloudTeam-2.0-ALL','PhotoshopLRCC-1.0-ALL','CreativeCloudStk-1.0-ALL','MuseStock-1.0-ALL','CreativeCloudTeamStk-1.0-ALL','PremiereProStock-1.0-ALL','PhotoshopLRCCStock-1.0-ALL','CreativeCloudEnt-1.0-ALL','InDesignStock-1.0-ALL','TechnicalSuite-4.0-ALL','DreamweaverStock-1.0-ALL','IllustratorStock-1.0-ALL','PhotoshopStock-1.0-ALL','AfterEffectsStock-1.0-ALL','InCopyStock-1.0-ALL','FlashProStock-1.0-ALL','AcrobatDCProStock-1.0-ALL') "
			+ "and s.entid = ent.id " + "and sub.created >= '16-JUN-15' "
			+ "and to_date(tm.trial_start_date, 'dd-mm-yy') != to_date(sub.created, 'dd-mm-yy') "
			+ "group by ent.productid, tm.product_id "
			+ "order by ent.productid";

	String TotalOftUsergotExtensions = "select tm.product_id as productname,ext.extension_program_name as extensionname, count(unique(tm.delegate_guid)) as count from "
			+ "honesty.lm_trial_master tm, lm_extension_trial_config ext " + "where tm.ext_fkey = ext.ext_key "
			+ "and tm.ext_fkey != 0 " + "and tm.last_modified_date >= '02-AUG-15' "
			+ "group by tm.product_id,ext.extension_program_name "
			+ "order by tm.product_id";

	String TotalOftUserProdWiseConverteegotExtensions = "select ent.productid as productname, ext.extension_program_name as extensionname ,count(unique(tm.delegate_guid)) as count from "
			+ "subscribers sub , lm_trial_master tm, skus s, subscriptions sub1, entitlements ent, lm_extension_trial_config ext "
			+ "where sub.userid = tm.delegate_guid " + "and sub1.userid = tm.delegate_guid "
			+ "and sub.userid = sub1.userid " + "and trim(s.skuname) = trim(sub1.sku) "
			+ "and tm.product_id = ent.productid " + "and s.entid = ent.id " + "and tm.ext_fkey != 0 "
			+ "and sub.created >= '02-AUG-15' " + "and tm.last_modified_date >= '02-AUG-15' "
			+ "and to_date(tm.last_modified_date, 'DD-MM-YY') != to_date(sub.created, 'DD-MM-YY') "
			+ "and tm.ext_fkey = ext.ext_key " + "group by ent.productid, ext.extension_program_name "
			+ "order by ent.productid";

	String TotalOftUserSuiteWiseConverteegotExtensions = "select ent.productid as suitename, ext.extension_program_name as extensionname ,count(unique(tm.delegate_guid)) as count from "
			+ "subscribers sub , lm_trial_master tm, skus s, subscriptions sub1, entitlements ent, lm_extension_trial_config ext "
			+ "where sub.userid = tm.delegate_guid " + "and sub1.userid = tm.delegate_guid "
			+ "and sub.userid = sub1.userid " + "and trim(s.skuname) = trim(sub1.sku) "
			+ "and ent.productid in ('CreativeCloudMembership-2.0-ALL','CreativeCloudTeam-2.0-ALL','PhotoshopLRCC-1.0-ALL','CreativeCloudStk-1.0-ALL','MuseStock-1.0-ALL','CreativeCloudTeamStk-1.0-ALL','PremiereProStock-1.0-ALL','PhotoshopLRCCStock-1.0-ALL','CreativeCloudEnt-1.0-ALL','InDesignStock-1.0-ALL','TechnicalSuite-4.0-ALL','DreamweaverStock-1.0-ALL','IllustratorStock-1.0-ALL','PhotoshopStock-1.0-ALL','AfterEffectsStock-1.0-ALL','InCopyStock-1.0-ALL','FlashProStock-1.0-ALL','AcrobatDCProStock-1.0-ALL') "
			+ "and s.entid = ent.id " + "and tm.ext_fkey != 0 " + "and sub.created >= '02-AUG-15' "
			+ "and tm.last_modified_date >= '02-AUG-15' "
			+ "and to_date(tm.trial_start_date, 'DD-MM-YY') != to_date(sub.created, 'DD-MM-YY') "
			+ "and tm.ext_fkey = ext.ext_key " + "group by ent.productid, ext.extension_program_name "
			+ "order by ent.productid";

	String TotalUniqueOFTUser = "select /*+CARDINALITY(lm_trial_master,14)*/ count(unique(delegate_guid)) as count from honesty.lm_trial_master";

	String TotalUniqueOFTConvertees = "select count(unique(tm.delegate_guid)) as count from "
			+ "subscribers sub , lm_trial_master tm, subscriptions sub1 " + "where sub.userid = tm.delegate_guid "
			+ "and sub1.userid = tm.delegate_guid " + "and sub.created >= '16-JUN-15' "
			+ "and to_date(tm.trial_start_date, 'DD-MM-YY') != to_date(sub.created, 'DD-MM-YY')";

	String host, port, user, pass, service;
	Connection connection = null;
	Statement statement0, statement1, statement2, statement3, statement4, statement5, statement6, statement7,
			statement8, statement9 = null;

	/**
	 * @param Environment
	 * @return
	 * @throws SQLException
	 */
	public Connection dbConnection(String Environment) throws SQLException {

		Connection conn = null;

		String dbType = "oracle:thin:@";
		try {
			Class.forName("oracle.jdbc.OracleDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your " + dbType + " JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println(dbType + " JDBC Driver Registered!");
		String conStr = null;
		if (Environment.equalsIgnoreCase("Stage")) {
			conStr = "jdbc:oracle:thin:@//HOSTNAME:POST/SID";
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(conStr, "USERNAME", "PASSWORD");
		} else if (Environment.equalsIgnoreCase("Prod")) {
			conStr = "jdbc:oracle:thin:@//HOSTNAME:POST/SID";
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(conStr, "USERNAME", "PASSWORD");
		} else {
			conStr = "jdbc:oracle:thin:@//HOSTNAME:POST/SID";
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn = DriverManager.getConnection(conStr, "USERNAME", "PASSWORD");
		}

		return conn;
	}

	/**
	 * @param environment
	 * @throws SQLException
	 */
	@SuppressWarnings("unused")
	public void defaultTrialProgramWithExtensions(String environment) throws SQLException {

		ResultSet trail_ResultSet = null;
		ResultSet ext_ResultSet = null;
		ResultSet count_ResultSet = null;
		ResultSet oftCount_ResultSet = null;
		ResultSet ConVCount_ResultSet = null;
		ResultSet SuiteConVCount_ResultSet = null;
		ResultSet ExtCount_ResultSet = null;
		ResultSet ExtConVProdWiseCount_ResultSet = null;
		ResultSet ExtConVSuiteWiseCount_ResultSet = null;
		ResultSet All_conVCount_ResultSet = null;
		
		Calendar cal = new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		SimpleDateFormat dt = new SimpleDateFormat("DD=MM-YY");

		DateFormat df = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
		Date dateobj = new Date();
		loadData.put("currentTime", df.format(dateobj));

		try {

			if (statement0 == null || statement1 == null || statement2 == null || statement3 == null
					|| statement4 == null || statement5 == null || statement6 == null || statement7 == null
					|| statement8 == null || statement9 == null) {
				ArrayList<String> defaultExtTrialProgram = null;
				connection = dbConnection(environment);
				try {
					statement0 = connection.createStatement();
					System.out.println("Executing the 1st Query : " + ActiveProgramQuery);
					trail_ResultSet = statement0.executeQuery(ActiveProgramQuery);

					defaultExtTrialProgram = new ArrayList<String>();
					defaultExtTrialProgram.add("DefaultTrialProgram ExtProgram ExtDuration | DefDuration \n");
					while (trail_ResultSet.next()) {

						String trailProgramName = trail_ResultSet.getString("PROGRAM_NAME").split("_")[0].trim();
						String trialDuration = trail_ResultSet.getString("TRIAL_DURATION").trim();
						String trialStatus = trail_ResultSet.getString("STATUS").trim();
						if (trialStatus.equalsIgnoreCase("ACTIVE")) {
							defaultExtTrialProgram.add(trailProgramName + " | " + trialDuration + "\n");
						}
					}
				} catch (Exception e) {
					System.err.println("Error message for querying the from 1st table : " + e.getMessage());
					statement0.close();
				}

				try {
					statement1 = connection.createStatement();
					System.out.println("Executing the 2nd Query : " + ActiveExtensionQuery);
					ext_ResultSet = statement1.executeQuery(ActiveExtensionQuery);

					boolean extAvailable = false;
					while (ext_ResultSet.next()) {

						String extName = ext_ResultSet.getString("EXTENSION_PROGRAM_NAME");
						String extDuration = ext_ResultSet.getString("DURATION");
						String extStatus = ext_ResultSet.getString("STATUS");
						if (extStatus.equals("ACTIVE")) {
							try {
								if (extDuration.equals("-1")) {
									java.sql.Timestamp currentDate = new java.sql.Timestamp(cal.getTimeInMillis());
									java.sql.Timestamp extEndDate = ext_ResultSet.getTimestamp("EXTENSION_END_DATE");
									float diff = extEndDate.getTime() - currentDate.getTime();
									float dayDuration = ((float) diff / 86400000);
									if (dayDuration > 0) {
										defaultExtTrialProgram.add(
												extName.split("_")[0] + " " + extName + " " + dayDuration + " | 30\n");
									}
								} else {
									java.sql.Timestamp currentDate = new java.sql.Timestamp(cal.getTimeInMillis());
									java.sql.Timestamp extEndDate = ext_ResultSet.getTimestamp("EXTENSION_END_DATE");
									float diff = extEndDate.getTime() - currentDate.getTime();
									float dayDuration = ((float) diff / 86400000);
									if (dayDuration > 0) {
										defaultExtTrialProgram.add(
												extName.split("_")[0] + " " + extName + " " + extDuration + " | 30\n");
									}
								}
								loadData.put("DefaultTrialProgramWithExtensions", defaultExtTrialProgram);
							} catch (Exception e) {
								System.out.println("Exception thown when parsing the date : " + e.getStackTrace());
								statement1.close();
								continue;
							}
						}
					}
				} catch (Exception e) {
					System.err.println("Error message for querying the from 2nd table : " + e.getMessage());
					statement1.close();
				}

				try {
					ArrayList<String> productWiseUserCount = null;
					statement2 = connection.createStatement();
					System.out.println("Executing the 3rd Query : " + TotalOftUser);
					count_ResultSet = statement2.executeQuery(TotalOftUser);
					productWiseUserCount = new ArrayList<String>();
					productWiseUserCount.add("ProductName | No. of User \n");
					while (count_ResultSet.next()) {

						String trailProgramName = count_ResultSet.getString("PRODUCT_ID").split("_")[0].trim();
						if (trailProgramName.contains("CS7")) {
							trailProgramName = trailProgramName.split("CS7")[0].trim();
						} else if (trailProgramName.contains("GM")) {
							trailProgramName = trailProgramName.split("GM")[0].trim();
						} else {
							trailProgramName = trailProgramName.split("Professional")[0].trim();
						}
						int trailProductID = count_ResultSet.getInt("Count");
						// Date trailStartDate =
						// count_ResultSet.getDate("TRIAL_START_DATE");
						productWiseUserCount.add(trailProgramName + " | " + trailProductID + "\n");
					}
					loadData.put("ProductWiseOFTUsers", productWiseUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 3rd table : " + e.getMessage());
					statement2.close();
				}

				try {
					statement3 = connection.createStatement();
					System.out.println("Executing the 4th Query : " + TotalUniqueOFTUser);
					oftCount_ResultSet = statement3.executeQuery(TotalUniqueOFTUser);

					int uniqueCountOfOFTUser = 0;

					while (oftCount_ResultSet.next()) {
						uniqueCountOfOFTUser = oftCount_ResultSet.getInt("count");
					}
					loadData.put("NoOfUniqueUserInOFT", uniqueCountOfOFTUser);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 4th table : " + e.getMessage());
					statement3.close();
				}

				try {
					ArrayList<String> productWiseConvertedOftUserCount = null;
					statement4 = connection.createStatement();
					System.out.println("Executing the 5th Query : " + TotalOftUserProdWiseConvertee);
					ConVCount_ResultSet = statement4.executeQuery(TotalOftUserProdWiseConvertee);
					productWiseConvertedOftUserCount = new ArrayList<String>();
					productWiseConvertedOftUserCount.add("ProductName | No. of Converted User\n");
					while (ConVCount_ResultSet.next()) {
						String productName = ConVCount_ResultSet.getString("productname").split("-")[0].trim();
						if (productName.contains("CS7")) {
							productName = productName.split("CS7")[0].trim();
						} else if (productName.contains("GM")) {
							productName = productName.split("GM")[0].trim();
						} else {
							productName = productName.split("Professional")[0].trim();
						}
						String UserCount = ConVCount_ResultSet.getString("count");
						productWiseConvertedOftUserCount.add(productName + " | " + UserCount + "\n");
					}
					loadData.put("ProductWiseConvertedOFTUsers", productWiseConvertedOftUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 5th table : " + e.getMessage());
					statement4.close();
				}

				try {
					ArrayList<String> suiteWiseConvertedOftUserCount = null;
					statement5 = connection.createStatement();
					System.out.println("Executing the 6th Query : " + TotalOftUserSuiteWiseConvertee);
					SuiteConVCount_ResultSet = statement5.executeQuery(TotalOftUserSuiteWiseConvertee);
					suiteWiseConvertedOftUserCount = new ArrayList<String>();
					suiteWiseConvertedOftUserCount.add("SuiteName ProductName | No. of Converted User\n");
					while (SuiteConVCount_ResultSet.next()) {
						String suiteName = SuiteConVCount_ResultSet.getString("suitename").split("-")[0].trim();
						String productName = SuiteConVCount_ResultSet.getString("productname").split("-")[0].trim();
						if (productName.contains("CS7")) {
							productName = productName.split("CS7")[0].trim();
						} else if (productName.contains("GM")) {
							productName = productName.split("GM")[0].trim();
						} else {
							productName = productName.split("Professional")[0].trim();
						}
						String UserCount = SuiteConVCount_ResultSet.getString("count");
						suiteWiseConvertedOftUserCount.add(suiteName + " " + productName + " " + UserCount + " | 0\n");
					}
					loadData.put("SuiteWiseConvertedOFTUsers", suiteWiseConvertedOftUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 6th table : " + e.getMessage());
					statement5.close();
				}

				try {
					ArrayList<String> availedExtOftUserCount = null;
					statement6 = connection.createStatement();
					System.out.println("Executing the 7th Query : " + TotalOftUsergotExtensions);
					ExtCount_ResultSet = statement6.executeQuery(TotalOftUsergotExtensions);
					availedExtOftUserCount = new ArrayList<String>();
					availedExtOftUserCount.add("ExtensionName | No. of User\n");
					while (ExtCount_ResultSet.next()) {
						String extensionName = ExtCount_ResultSet.getString("extensionname");
						int UserCount = ExtCount_ResultSet.getInt("count");
						availedExtOftUserCount.add(extensionName + " | " + UserCount + "\n");
					}
					loadData.put("ExtensionAvailedNoOFTUsers", availedExtOftUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 7th table : " + e.getMessage());
					statement6.close();
				}

				try {
					ArrayList<String> availedExtProdWiseConvertedOftUserCount = null;
					statement7 = connection.createStatement();
					System.out.println("Executing the 8th Query : " + TotalOftUserProdWiseConverteegotExtensions);
					ExtConVProdWiseCount_ResultSet = statement7
							.executeQuery(TotalOftUserProdWiseConverteegotExtensions);
					availedExtProdWiseConvertedOftUserCount = new ArrayList<String>();
					availedExtProdWiseConvertedOftUserCount.add("SuiteName ExtensionName No. of User | \n");
					while (ExtConVProdWiseCount_ResultSet.next()) {
						String ProductName = ExtConVProdWiseCount_ResultSet.getString("productname");
						if (ProductName.contains("CS7")) {
							ProductName = ProductName.split("CS7")[0].trim();
						} else if (ProductName.contains("GM")) {
							ProductName = ProductName.split("GM")[0].trim();
						} else {
							ProductName = ProductName.split("Professional")[0].trim();
						}
						String extensionName = ExtConVProdWiseCount_ResultSet.getString("extensionname");
						int UserCount = ExtConVProdWiseCount_ResultSet.getInt("count");
						availedExtProdWiseConvertedOftUserCount
								.add(ProductName + " " + extensionName + " " + UserCount + " | 0\n");
					}
					loadData.put("ExtensionAvailedProductWiseNoOFTUsersConV", availedExtProdWiseConvertedOftUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the 8th from table : " + e.getMessage());
					statement7.close();
				}

				try {
					ArrayList<String> availedExtSuiteWiseConvertedOftUserCount = null;
					statement8 = connection.createStatement();
					System.out.println("Executing the 9th Query : " + TotalOftUserSuiteWiseConverteegotExtensions);
					ExtConVSuiteWiseCount_ResultSet = statement8
							.executeQuery(TotalOftUserSuiteWiseConverteegotExtensions);
					availedExtSuiteWiseConvertedOftUserCount = new ArrayList<String>();
					availedExtSuiteWiseConvertedOftUserCount.add("SuiteName ExtensionName No. of User | \n");
					while (ExtConVSuiteWiseCount_ResultSet.next()) {
						String suiteName = ExtConVSuiteWiseCount_ResultSet.getString("suitename").split("-")[0].trim();
						String extensionName = ExtConVSuiteWiseCount_ResultSet.getString("extensionname");
						int UserCount = ExtConVSuiteWiseCount_ResultSet.getInt("count");
						availedExtSuiteWiseConvertedOftUserCount
								.add(suiteName + " " + extensionName + " " + UserCount + " | 0\n");
					}
					loadData.put("ExtensionAvailedSuiteWiseNoOFTUsersConV", availedExtSuiteWiseConvertedOftUserCount);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 9th table : " + e.getMessage());
					statement8.close();
				}

				try {
					statement9 = connection.createStatement();
					System.out.println("Executing the 10th Query : " + TotalUniqueOFTConvertees);
					All_conVCount_ResultSet = statement0.executeQuery(TotalUniqueOFTConvertees);

					int uniqueConVCountOfOFTUser = 0;

					while (All_conVCount_ResultSet.next()) {
						uniqueConVCountOfOFTUser = All_conVCount_ResultSet.getInt("count");
					}
					loadData.put("NoOfUniqueConverteeUserInOFT", uniqueConVCountOfOFTUser);
				} catch (Exception e) {
					System.err.println("Error message for querying the from 10th table : " + e.getMessage());
					statement9.close();
				}
			}
		} catch (Exception e) {
			System.err.println("Error message for querying the from table : " + e.getMessage());
		} finally {
			if (statement0 != null || statement1 != null || statement2 != null || statement3 != null
					|| statement4 != null || statement5 != null || statement6 != null || statement7 != null
					|| statement8 != null || statement9 != null) {
				connection.close();
				statement0.close();
				statement1.close();
				statement2.close();
				statement3.close();
				statement4.close();
				statement5.close();
				statement6.close();
				statement7.close();
				statement8.close();
				statement9.close();
				connection = null;
				statement0 = null;
				statement1 = null;
				statement2 = null;
				statement3 = null;
				statement4 = null;
				statement5 = null;
				statement6 = null;
				statement7 = null;
				statement8 = null;
				statement9 = null;
				System.out.println("Connection Closed >>>>>>>>>>>>>>>>>>>>>>>>");
			}
		}
	}

}
