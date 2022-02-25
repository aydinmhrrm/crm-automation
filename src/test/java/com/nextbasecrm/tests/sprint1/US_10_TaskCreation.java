package com.nextbasecrm.tests.sprint1;

import com.nextbasecrm.utilities.BrowserUtils;
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

import java.util.concurrent.TimeUnit;

import static org.testng.Assert.assertEquals;

/*group 9
hr25@cydeo.com	helpdesk25@cydeo.com	marketing25@cydeo.com	password: UserUser
hr26@cydeo.com	helpdesk26@cydeo.com	marketing26@cydeo.com
hr27@cydeo.com	helpdesk27@cydeo.com	marketing27@cydeo.com	*/

public class US_10_TaskCreation {
//As a user, I want to create a task with just task content from the TASK tab.

    WebDriver driver;

    @DataProvider(name = "logins")
    public Object[][] logins() {
        return new Object[][]{
                {"hr25@cydeo.com"}, {"helpdesk26@cydeo.com"},
                {"marketing27@cydeo.com"}
        };
    }

    @BeforeMethod()
    public void setup(){
        driver = WebDriverFactory.getDriver("chrome");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://login1.nextbasecrm.com");
    }

    @AfterMethod
    public void teardown(){driver.quit();}

    @Test(dataProvider = "logins")  // this test going to run 3 times with different credentials because of @DataProvider method on top of the class
    public void testTaskCreatedSuccessfully(String login)  {
        CRM_Utilities.crm_login(driver,login); // we log in using method from our utilities package

        WebElement taskButton = driver.findElement(By.xpath("(//span[.='Task'])[2]"));
        taskButton.click();

       // BrowserUtils.sleep(1);
        WebElement titleInputBox = driver.findElement(By.xpath("//input[@data-bx-id='task-edit-title']"));
        titleInputBox.sendKeys("Random Title");

        driver.switchTo().frame(1);//inputTextBox is inside the 2nd iframe

        //while storing this webElement into a variable later unable to call that variable for some reason and throwing StaleElementException; Fixed it by using entering text right away
        driver.findElement(By.xpath("//body[@contenteditable='true' and @style]")).sendKeys("Random Text");

        driver.switchTo().parentFrame();

        WebElement sendButton = driver.findElement(By.xpath("//button[@id='blog-submit-button-save']"));
        sendButton.click();


    /*AC1: EXPECTED:
    1. Once a task is created successfully,
    there should be a confirmation message dimply in a popup.  “Task has been created”*/

        //ACTUAL: Task is created. User is able to login and create task but there's no popup window after task is created with a message: “Task has been created”
    }


    @Test(dataProvider = "logins")  // this test going to run 3 times with different credentials because of @DataProvider method on top of the class
    public void testTaskCreationFailsIfTitleMissing(String login) {
        CRM_Utilities.crm_login(driver, login);

        WebElement taskButton = driver.findElement(By.xpath("(//span[.='Task'])[2]"));
        taskButton.click();
        BrowserUtils.sleep(3);
        //for some reason implicitlyWait doesn't work; the page is not loading in time and throws NoSuchElement; solution: have to use Thread.sleep()

        driver.switchTo().frame(1);//inputTextBox is inside the 2nd iframe

        //while storing this webElement into a variable later unable to call that variable for some reason and throwing StaleElementException; Fixed it by using entering text right away
        driver.findElement(By.xpath("//body[@contenteditable='true' and @style]")).sendKeys("Random Text");

        driver.switchTo().parentFrame();

        WebElement sendButton = driver.findElement(By.xpath("//button[@id='blog-submit-button-save']"));
        sendButton.click();

        WebElement errorMessageElement = driver.findElement(By.xpath("//div[@class='task-message-label error']"));

        String actual = errorMessageElement.getText();
        String expected = "The task name is not specified.";

        assertEquals(actual,expected);


    /*AC2: EXPECTED
     2. “The task name is not specified.” The message should display when the user did not write the task title.   */

        // ACTUAL: Test Passes.

        // Note: particularly in this test couldn't locate the second iframe while trying to type in the Task body; was getting NoSuchFrame exception for some reason. Skipped this part as AC didn't specify entering text
       //Update: working now sleep helped
    }
}


