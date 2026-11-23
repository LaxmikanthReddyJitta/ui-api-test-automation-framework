package com.ecommerce.qa.api;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.Assert;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

@Epic("REST API Testing")
public class ApiTest {
	
	String baseUrl = "https://api.restful-api.dev";
	
	static String objectId;
	
	
	@BeforeMethod
	public void setUp() {
		RestAssured.baseURI=baseUrl;
		
	}
	@Test(priority=1)
	@Feature("Get Objects")
	@Description("Send a GET request for all objects and verify status code 200 and a non-empty list")
	@Severity(SeverityLevel.CRITICAL)
	public void testGetAllObjects() {
		
		Response response = RestAssured.get("/objects");
		
		System.out.println("This is get all objects test");
		System.out.println(response.getStatusCode());
		System.out.println(response.getTime());
		
		response.prettyPrint();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertTrue(response.jsonPath().getList("").size()>0, "object list is empty");	
	}
	
	@Test(priority=2)
	@Feature("Create Object")
	@Description("Send a POST request with a JSON body and verify the object is created")
	@Severity(SeverityLevel.BLOCKER)
	public void testPostMethod() {
		
		String jsonpayload="""
				{
				
				  "name": "Hp Elite Book",
				  "data": {
				    "year": 2019,
				    "price": 800,
				    "CPU model": "Intel Core i7",
				    "Hard disk size": "1 TB"
				  }
				}
				""";
		Response response= 
			given()
				.contentType(ContentType.JSON)
				.body(jsonpayload)
				
			.when()
				.post("/objects")
				
			.then()
				.extract()
				.response();
		
		System.out.println("This is post test");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		Assert.assertEquals(response.getStatusCode(), 200);
		Assert.assertEquals(response.jsonPath().getString("name"),"Hp Elite Book","Object is not posted successfuly");
		
		objectId=response.jsonPath().getString("id");
		System.out.println("Id: "+objectId);	
	}
	
	@Test(priority=5, dependsOnMethods="testPostMethod")
	@Feature("Get Objects")
	@Description("Get the created object by id and verify status code 200")
	@Severity(SeverityLevel.CRITICAL)
	public void testGetMethod() {
		
		Response response= RestAssured.get("/objects/"+objectId);
		
		System.out.println("This is get by id test");
		System.out.println(response.statusCode());
		response.prettyPrint();
		Assert.assertEquals(response.statusCode(),200);
	}
	
	@Test(priority=3, dependsOnMethods="testPostMethod")
	@Feature("Update Object")
	@Description("Replace the whole object with a PUT request and verify the changes")
	@Severity(SeverityLevel.CRITICAL)
	public void testPutMethod() {
		
		String jsonpayload= """
				{
				"name": "Hp Elite Book",
				  "data": {
				    "year": 2020,
				    "price": 2049.99,
				    "CPU model": "Intel Core i9",
				    "Hard disk size": "1 TB"
				  }
				}
				""";
		Response response=
				given()
				.contentType(ContentType.JSON)
				.body(jsonpayload)
				
				.when()
				.put("/objects/"+objectId)
				
				.then()
				.extract()
				.response();
		
		System.out.println("This is Put test");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		Assert.assertEquals(response.statusCode(), 200);
	}
	
	@Test(priority=4, dependsOnMethods="testPostMethod")
	@Feature("Update Object")
	@Description("Change only the price with a PATCH request and verify the new price")
	@Severity(SeverityLevel.NORMAL)
	public void testPatchMethod() {
		
		String jsonpayload="""
				{
				"name": "Hp Elite Book",
				"data":
				{
				"price":"2000"
				}
				}
				
				""";
		
		Response response=
				given()
				.contentType(ContentType.JSON)
				.body(jsonpayload)
				
				.when()
				.patch("/objects/"+objectId)
				
				.then()
				.extract()
				.response();
		
		System.out.println("This is Patch test");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		Assert.assertEquals(response.jsonPath().getString("data.price"), "2000");		
	}
	
	@Test(priority=6, dependsOnMethods="testPostMethod")
	@Feature("Delete Object")
	@Description("Delete the object with a DELETE request and verify status code 200")
	@Severity(SeverityLevel.CRITICAL)
	public void testDeleteMethod() {
		
		Response response= RestAssured.delete("/objects/"+objectId);
		
		System.out.println("This is Delete test");
		System.out.println(response.statusCode());
		response.prettyPrint();
		
		Assert.assertEquals(response.statusCode(), 200);
		
	}
	
	@Test(priority=7, dependsOnMethods="testDeleteMethod")
	@Feature("Delete Object")
	@Description("Get the deleted object and verify a 404 response")
	@Severity(SeverityLevel.NORMAL)
	public void testGetDeletedObject() {
		
		Response response= RestAssured.get("/objects/"+objectId);
		
		System.out.println("This is Get Deleted Object test");
		System.out.println(response.statusCode());

		Assert.assertEquals(response.getStatusCode(), 404);
	}

}
