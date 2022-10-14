package com.lambdatest;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestNGTodo1 {

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
        caps.setCapability("browserName", "Safari");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "TestNG With Java");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    @Test
    public void basicTest() throws InterruptedException {
        System.out.println("Loading Url");
        driver.get("https://www.lambdatest.com/selenium-playground");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        System.out.println("Clicking: Simple Form Demo");
        driver.findElement(By.linkText("Simple Form Demo")).click();

        System.out.println("Checking URL contains “simple-form-demo”");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        String current_url = driver.getCurrentUrl();
        
        if(current_url.contains("simple-form-demo"))
        {
        		System.out.println("The URL contains the text: simple-form-demo");
        }
        else
        {
        	System.out.println("The URL does not contains the text: simple-form-demo");
        }
        
        System.out.println("Entering values in the “Enter Message” text box.");
        String input_String = "Welcome to LambdaTest";
        driver.findElement(By.xpath("//*[@class='flex smtablet:block']/div/input")).sendKeys(input_String);

        System.out.println("Clicking “Get Checked Value”.");
        driver.findElement(By.xpath("//*[@class='flex smtablet:block']/div/button")).click();

        
        String msg_Display = driver.findElement(By.xpath("//*[text()='Welcome to LambdaTest']")).getText();
        if (input_String.endsWith(msg_Display))
        {
        	System.out.println("Same text message is displayed as entered.");
        }

        System.out.println("TestFinished");

    }

    @AfterMethod
    public void tearDown() {
        driver.executeScript("lambda-status=" + Status);
        driver.quit();
    }

}