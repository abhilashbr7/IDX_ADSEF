package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.IExecutionListener;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.AgencySave;
import com.qa.data.Users;
import com.qa.util.TestUtil;

public class IDX_ADSEF extends TestBase implements IExecutionListener {

	TestBase testbase;
	RestClient restclient;
	String serviceurl;
	String apiurl;
	String url;
	String token,credentials,Searchreasonbyinputparameter,CitizenSearch,CitizenSearchTickets,CitizenSearchTicketTransaction;
	String SearchReasonByClient,CitizenSearchShipRenewal,CitizenSearchShipFees,CitizenDetails,CitizenRetryAgency;
	String citizen_referenceId,citizen_referenceId_tickets,citizen_referenceId_ticket_transaction,citizen_referenceId_ShipRenewal;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeMethod

	public void setup() throws ClientProtocolException, IOException {
		testbase = new TestBase();
		serviceurl = prop.getProperty("URL");
		credentials = prop.getProperty("credentials");
		credentials = serviceurl + credentials;
		SearchReasonByClient = prop.getProperty("SearchReasonByClient");
		SearchReasonByClient = serviceurl + SearchReasonByClient;
		Searchreasonbyinputparameter = prop.getProperty("Searchreasonbyinputparameter");
		Searchreasonbyinputparameter = serviceurl + Searchreasonbyinputparameter;
		CitizenSearch = prop.getProperty("CitizenSearch");
		CitizenSearch = serviceurl + CitizenSearch;
		CitizenDetails = prop.getProperty("CitizenDetails");
		CitizenDetails = serviceurl + CitizenDetails;
		CitizenRetryAgency = prop.getProperty("CitizenAgency");
		CitizenRetryAgency = serviceurl + CitizenRetryAgency;
		
	}
	@Test(priority = 1)
	public void Credentilas() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");

		String jsonpayload = "{\r\n"
				+ "    \"Username\": \"adsefdev\",\r\n"
				+ "    \"Password\": \"*GYIQ935DBpw\",\r\n"
				+ "    \"APIKey\": \"256e705a-8cc9-4167-b8ac-99ec89458f7c\",\r\n"
				+ "    \"Email\": \"mayanka@yahoo.com\"\r\n"
				+ "}";
		

		closeableHttpResponse = restclient.post(credentials, jsonpayload, headermap);


//			//1.GET status code
		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

//			//2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//			//To get value from JSON Array
		token = TestUtil.getValueByjpath(responseJson, "/access_token");
		System.out.println("Token is : " + token);
//		    			
//			//3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
			allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 2)
	public void SearchReasonByClient() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("isCitizen", "true");
		headermap.put("isCorporate", "false");
		headermap.put("isIDValidation", "false");
		headermap.put("Authorization", "Bearer " + token);
		

		closeableHttpResponse = restclient.get(SearchReasonByClient, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String SearchReasonId = TestUtil.getValueByjpath(responseJson, "/searchReasons[0]/id");
		System.out.println("Response From Json is : " + SearchReasonId);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 3)
	public void Searchreasonbyinputparameter() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("searchReasonId", "10200");
		headermap.put("searchType", "CITIZEN");
		headermap.put("Authorization", "Bearer " + token);
		

		closeableHttpResponse = restclient.get(Searchreasonbyinputparameter, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String InputParametername = TestUtil.getValueByjpath(responseJson, "/parameters[0]/name");
		System.out.println("Tickets Response From Json is : " + InputParametername);
		String InputParameterrequired = TestUtil.getValueByjpath(responseJson, "/parameters[0]/required");
		System.out.println("Tickets Response From Json is : " + InputParameterrequired);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}

	@Test(priority = 4)
	public void CitizenSearch() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("Authorization", "Bearer " + token);
		

		String jsonpayload = "{\r\n"
				+ "  \"ssn\": \"581888730\",\r\n"
				+ "  \"searchReasonId\": 10200,\r\n"
				+ "  \"firstName\":\"Ruth\",\r\n"
				+ "  \"lastName\":\"Aviles\",\r\n"
				+ "  \"mailingAddressLine1\": \"100, Carr 842\",\r\n"
				+ "  \"mailingAddressLine2\": \"\",\r\n"
				+ "  \"mailingCity\": \"San Juan\",\r\n"
				+ "  \"mailingZip\": \"00926\",\r\n"
				+ "  \"employeeUsername\": \"\",\r\n"
				+ "  \"email\":\"kvn921@gmail.com\"\r\n"
				+ "}";
		

		closeableHttpResponse = restclient.post(CitizenSearch, jsonpayload, headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String citizen_referenceId = TestUtil.getValueByjpath(responseJson, "/citizen_referenceId");
		System.out.println("Fee Response From Json is : " + citizen_referenceId);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}
	
	
	@Test(priority = 5)
	public void CitizenDetails() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "cbba98b2-85c9-403a-904a-792051fe7ee7");
		headermap.put("Authorization", "Bearer " + token);
		
		closeableHttpResponse = restclient.get(CitizenDetails,headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);

//		// To get value from JSON Array
		String citizen_AgencyName = TestUtil.getValueByjpath(responseJson, "/data[0]/Agency");
		System.out.println("Ticket Transaction message Response From Json is : " + citizen_AgencyName);

		// 3.All Headers
		Header[] headersArray = closeableHttpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();

		for (Header header : headersArray) {
		allHeaders.put(header.getName(), header.getValue());

		}
		System.out.println("Headers Array-->" + allHeaders);

	}
	
	@Test(priority = 6)
	public void CitizenRetryAgency() throws ClientProtocolException, IOException {
		restclient = new RestClient();
		HashMap<String, String> headermap = new HashMap<String, String>();
		headermap.put("content-type", "application/json");
		headermap.put("referenceId", "cbba98b2-85c9-403a-904a-792051fe7ee7");
		headermap.put("agencyCode", "DRNA");
		headermap.put("Authorization", "Bearer " + token);

		
		closeableHttpResponse = restclient.post_nobody(CitizenRetryAgency,headermap);

		int statuscode = closeableHttpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code:" + statuscode);
		Assert.assertEquals(statuscode, RESPONSE_STATUS_CODE_200, "status code is not 200");

		// //2.Json String
		String responsestring = EntityUtils.toString(closeableHttpResponse.getEntity(), "UTF-8");
		JSONObject responseJson = new JSONObject(responsestring);
		System.out.println("JSON response from API---->" + responseJson);
	}

}
