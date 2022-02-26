package com.nextbasecrm.tests.sprint1;

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

public class US_3_LogOut {

    WebDriver driver;
    String URL = "https://login2.nextbasecrm.com/";

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
        driver.get(URL);
    }

    @Test(dataProvider = "logins")
    public void logOutFunctionalityVerification(String login) {
        CRM_Utilities.crm_login(driver,login);

        //AC: 1. The “Log out” option should be displayed when the user clicks the user profile from the homepage.
        driver.findElement(By.xpath("//div[@id='user-block']")).click();
        WebElement logOutOption = driver.findElement(By.xpath("//a[.='Log out']"));

        Assert.assertTrue(logOutOption.isDisplayed());

        //AC: 2. After clicking the logout button, the user should navigate back to the login page
        logOutOption.click();
        String expectedTitle = "Authorization";

        Assert.assertEquals(driver.getTitle(),expectedTitle);

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
/*
Story 3:
As a user, I should log out from the NextBaseCRM app.

Acceptance Criteria:
1. The “Log out” option should be displayed when the user clicks the user profile from the homepage.
2. After clicking the logout button, the user should navigate back to the login page.
 */