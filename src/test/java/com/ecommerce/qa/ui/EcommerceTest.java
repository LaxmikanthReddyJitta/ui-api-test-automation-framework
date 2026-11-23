package com.ecommerce.qa.ui;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

@Epic("Ecommerce Website")
public class EcommerceTest {
	WebDriver driver;
	WebDriverWait wait;
	String baseUrl="https://automationexercise.com";
	
	
	@BeforeMethod
	public void setUp() {
		ChromeOptions options = new ChromeOptions();
				options.addArguments("--window-size=1920,1080");
				
				driver= new ChromeDriver(options);
				wait= new WebDriverWait(driver,Duration.ofSeconds(10));
				
				driver.get(baseUrl);
		
	}
	@Feature("User Registration")
	@Description("Register a new user with valid details and verify the account is created")
	@Severity(SeverityLevel.BLOCKER)
	@Test(priority=1)
	public void testUserRegestration() {
		System.out.println("browser opened succesfully");
		driver.get(baseUrl+"/login");
		
		String uniqueEmail= "laxmikanth"+System.currentTimeMillis()+"@test.com";
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='signup-name']"))).sendKeys("laxmikanth");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='signup-email']"))).sendKeys(uniqueEmail);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[data-qa='signup-button']"))).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("id_gender1")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password"))).sendKeys("Asdf@12345");
		
		WebElement dropDownElement= driver.findElement(By.id("days"));
		Select dropDown= new Select(dropDownElement);
		dropDown.selectByValue("25");
		dropDownElement= driver.findElement(By.id("months"));
		dropDown= new Select(dropDownElement);
		dropDown.selectByValue("5");
		dropDownElement= driver.findElement(By.id("years"));
		dropDown= new Select(dropDownElement);
		dropDown.selectByValue("2001");
		
		driver.findElement(By.id("first_name")).sendKeys("Laxmikanth Reddy");
		driver.findElement(By.id("last_name")).sendKeys("Jitta");
		driver.findElement(By.id("company")).sendKeys("Private");
		driver.findElement(By.id("address1")).sendKeys("Blake st");
		driver.findElement(By.id("address2")).sendKeys("1343");
		
		dropDownElement= driver.findElement(By.id("country"));
		dropDown= new Select(dropDownElement);
		dropDown.selectByValue("United States");
		
		driver.findElement(By.id("state")).sendKeys("CT");
		driver.findElement(By.id("city")).sendKeys("Newhaven");
		driver.findElement(By.id("zipcode")).sendKeys("06515");
		driver.findElement(By.id("mobile_number")).sendKeys("1234567890");
		WebElement createacount= driver.findElement(By.cssSelector("button[data-qa=\"create-account\"]"));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", createacount);
		createacount.click();


		// Checking the account was created successfully
		
		WebElement message= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2[data-qa='account-created']")));
		Assert.assertEquals(message.getText(), "ACCOUNT CREATED!");

	}
	
	@Feature("Login")
	@Description("Login with a wrong email and password and verify an error message is shown")
	@Severity(SeverityLevel.CRITICAL)	
	@Test(priority=2)
	public void testLoginWithInvalidCredentials() {
		
		driver.get(baseUrl + "/login");	
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[data-qa='login-email']"))).sendKeys("wrongemail@gmail.com");
		driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys("wrongpassword");
		driver.findElement(By.cssSelector("button[data-qa='login-button']")).click();	
	
		// Validating the error message of invalid login credentials
		
		WebElement errorMessage = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("p[style='color: red;']")));
		Assert.assertTrue(errorMessage.getText().contains("incorrect"),
    "Expected login error message was not displayed");
	}
	
	@Feature("Product Search")
	@Description("Search for a product and verify the search results page is displayed")
	@Severity(SeverityLevel.NORMAL)
	@Test(priority=3)
	public void testSearchProduct() {
		driver.get(baseUrl+ "/products");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("search_product"))).sendKeys("jeans");
		WebElement button=wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("submit_search")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", button);
		button.click();

		//validating the searched product is right one
		
		WebElement tittle= wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.title.text-center")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", tittle);

		Assert.assertEquals(tittle.getText(), "SEARCHED PRODUCTS");
	}
	
	@Feature("Shopping Cart")
	@Description("Add a product to the cart and verify it appears in the cart page")
	@Severity(SeverityLevel.CRITICAL)
	@Test(priority=4)
	public void testAddProductToCart() {
		driver.get(baseUrl+"/products?search=jeans");
		
		WebElement addToCart=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href='/product_details/33']")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCart);
		addToCart.click();
		
		if (driver.getCurrentUrl().contains("#google_vignette") || !driver.getCurrentUrl().contains("/product_details/33")) {
		    driver.get(baseUrl + "/product_details/33");
		}

		WebElement addToCart1 = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-default.cart")));
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", addToCart1);
		
		addToCart1.click();
		
		WebElement viewCart=wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#cartModal a[href='/view_cart']")));
		viewCart.click();
		
	// Validating the  right product is added to cart
	WebElement product= wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("product-33")));
	Assert.assertTrue(product.isDisplayed(), "Product was not added to the cart");
	}
	
	@AfterMethod
	public void quitBrowser() {
		driver.quit();
	
	}	
	 
}
