package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo3 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = System.getenv("LT_USERNAME") == null ? "arpitsoni09" : System.getenv("LT_USERNAME");
        String authkey = System.getenv("LT_ACCESS_KEY") == null ? "ehD1SXg9FS7SAHJVjYpt84R1pQYn49uapKhvSVDEN3aPnTqtKL" : System.getenv("LT_ACCESS_KEY");
        ;
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "MacOS Catalina");
        caps.setCapability("browserName", "Chrome");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Tag", "Moderate" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);
    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("Clicking: Input Form Submit");
        driver.findElement(By.linkText("Input Form Submit")).click();
        
        System.out.println("Clicking on Submit button");
        driver.findElement(By.xpath("//*[@Class = 'text-right mt-20']/button")).click();
    	
        String val_Msg = driver.findElement(By.xpath("//*[@id = 'name']")).getAttribute("validationMessage");
        
        Assert.assertEquals(val_Msg, "Please fill out this field.");
        System.out.println("AssertEquals Test Passed");
        
        System.out.println("Filling the form details");
        driver.findElement(By.xpath("//*[@id = 'name']")).sendKeys("User Name");
        driver.findElement(By.xpath("//*[@id = 'inputEmail4']")).sendKeys("testemail@test.com");
        driver.findElement(By.xpath("//*[@id = 'inputPassword4']")).sendKeys("Password");
        driver.findElement(By.xpath("//*[@name = 'company']")).sendKeys("TCS");
        driver.findElement(By.cssSelector("#websitename")).sendKeys("www.tcs.com");
		Select drpCountry = new Select(driver.findElement(By.name("country")));
		drpCountry.selectByVisibleText("United States");
		driver.findElement(By.id("inputCity")).sendKeys("New York");
		driver.findElement(By.id("inputAddress1")).sendKeys("inputAddress1");
		driver.findElement(By.id("inputAddress2")).sendKeys("inputAddress2");
		driver.findElement(By.id("inputState")).sendKeys("State");
		driver.findElement(By.name("zip")).sendKeys("123456");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//*[@Class = 'text-right mt-20']/button")).click();
		
		
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		System.out.println("Form is submitted");
		
		String frm_validation = driver.findElement(By.xpath("//*[@class = 'success-msg hidden']")).getText();
		if (frm_validation.equals("Thanks for contacting us, we will get back to you shortly."))
		{
			System.out.println("Form submited and validation is verified.");
		}
		else
		{
			System.out.println("Form submited and validation is not verified.");
		}

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}