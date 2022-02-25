package com.nextbasecrm.tests.sprint1;

import com.nextbasecrm.utilities.CRM_Utilities;
import com.nextbasecrm.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class US11_ProfilePageAccess {
    //As a user, I want to access my profile page.
    WebDriver driver;

    @DataProvider(name = "logins")
    public Object[][] logins(){
        return new Object[][]{
                {"hr25@cydeo.com"},{"helpdesk26@cydeo.com"},
                {"marketing27@cydeo.com"}
        };
    }

    @BeforeMethod
    public void setup(){
        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://login1.nextbasecrm.com");
    }

    @AfterMethod
    public void teardown(){driver.quit();}

    @Test(dataProvider = "logins") // this going to perform 3 tests with different logins because of @DataProvider
    public void verifyMyProfileTextIsDisplayed(String login){
        CRM_Utilities.crm_login(driver,login);

        WebElement userProfile = driver.findElement(By.xpath("//span[@id='user-name']"));
        userProfile.click();
        WebElement myProfileElement = driver.findElement(By.xpath("//a[.='My Profile']"));

        assertTrue(myProfileElement.isDisplayed());
        assertEquals(myProfileElement.getText(), "My Profile");


        /*AC1: EXPECTED
1. The “My Profile” option should be displayed when the user clicks the user profile
 from the homepage.
               ACTUAL: Test Passed
         */

    }


    @Test(dataProvider = "logins")
    public void verifyTabsArePresent(String login){
        CRM_Utilities.crm_login(driver, login);

        WebElement userProfile = driver.findElement(By.xpath("//span[@id='user-name']"));
        userProfile.click();
        WebElement myProfileElement = driver.findElement(By.xpath("//a[.='My Profile']"));
        myProfileElement.click();

        List<WebElement> profileTabs = driver.findElements(By.xpath("//div[@id='profile-menu-filter']//a"));

        List<String> expectedList = new ArrayList<>(Arrays.asList("General","Drive","Tasks","Calendar","Conversations"));
        List<String> actualList = new ArrayList<>();

        for(WebElement each: profileTabs){
            actualList.add(each.getText());
        }

        assertEquals(actualList.size(), 5);
        assertEquals(actualList, expectedList);


        /*AC2:
        2. There should be five tabs on my profile page:
       “General “Drive” “Tasks” “Calendar ” “conversations”

       ACTUAL:  Test Passes
        */
    }

}
