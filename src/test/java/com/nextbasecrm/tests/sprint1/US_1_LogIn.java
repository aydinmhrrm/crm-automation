package com.nextbasecrm.tests.sprint1;

import com.nextbasecrm.utilities.BrowserUtils;
import com.nextbasecrm.utilities.CRM_Utilities;
import com.nextbasecrm.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class US_1_LogIn {

    WebDriver driver;

    //Scenarios: 2. Verify Users (HR, marketing, Helpdesk) login successfully
    @DataProvider
    public Object[][] logins(){
        return new Object[][]{{"hr25@cydeo.com"},{"hr26@cydeo.com"},{"hr27@cydeo.com"},{"helpdesk25@cydeo.com"},
                {"helpdesk26@cydeo.com"},{"helpdesk27@cydeo.com"},{"marketing25@cydeo.com"},{"marketing26@cydeo.com"},
                {"marketing27@cydeo.com"}};
    }

    @BeforeMethod
    public void setUp() {
        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        //navigating to login page
        driver.get("https://login2.nextbasecrm.com/");
    }

    //Scenarios: 2. Verify Users (HR, marketing, Helpdesk) login successfully
    @Test(dataProvider = "logins")
    public void loginWithValidCredentials(String login){

        //AC: 1. The login page title should be “Authorization”
        String expectedTitle = "Authorization";
        BrowserUtils.verifyTitle(driver,expectedTitle);

        //AC: 2. The user should go to the homepage after login in successfully.
        CRM_Utilities.crm_login(driver,login);

        expectedTitle = "Portal";
        BrowserUtils.verifyTitle(driver,expectedTitle);

    }

    //AC:       3. “Incorrect login or password” should be displayed when a user enters the wrong username or password.
    //Scenario: 3. Verify Users see “Incorrect login or password” for the invalid login attempt
    @Test
    public void loginWithInvalidCredentials(){
        String invalidUsername = "incorrect@incorrect.com";
        String invalidPassword = "incorrect";

        WebElement loginInputField = driver.findElement(By.xpath("//input[@placeholder='Login']"));
        WebElement passwordInputField = driver.findElement(By.xpath("//input[@placeholder='Password']"));

        loginInputField.sendKeys(invalidUsername);
        passwordInputField.sendKeys(invalidPassword);

        //click "Log In" button
        driver.findElement(By.xpath("//input[@value='Log In']")).click();

        //"Incorrect login or password" error message is displayed under "Authorization" header
        String expectedErrMessage = "Incorrect login or password";
        String actualErrMessage = driver.findElement(By.xpath("//div[@class='log-popup-header']/following-sibling::div[1]")).getText();

        Assert.assertEquals(actualErrMessage,expectedErrMessage,"Incorrect error message is displayed");

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
/*
US1 :                  As a user, I should be able to log in to the NextBaseCRM

Acceptance Criteria:   1. The login page title should be “Authorization.”
                       2. The user should go to the homepage after login in successfully.
                       3. “Incorrect username or password” should be displayed when a user enters the wrong username or password.

Scenarios :
                       1. Verify the tile
                       2. Verify Users (HR, marketing, Helpdesk) login successfully
                       3. Verify Users see “Incorrect username or password” for the invalid login attempt

 */