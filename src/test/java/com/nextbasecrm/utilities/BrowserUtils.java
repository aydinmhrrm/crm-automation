package com.nextbasecrm.utilities;

import org.openqa.selenium.WebDriver;

import static org.testng.Assert.*;

public class BrowserUtils {

    //this method will accept int(in seconds) and execute Thread.sleep() for given duration
    public static void sleep(int seconds){
        seconds*=1000;
        try {
            Thread.sleep(seconds);
        }catch (InterruptedException e){
        }
    }

    //When this method is called, it should switch window and verify title.
    public static void switchWindowAndVerify(WebDriver driver, String expectedInUrl, String expectedInTitle){
        for(String each: driver.getWindowHandles()){
            driver.switchTo().window(each);
            if(driver.getCurrentUrl().contains(expectedInUrl)){
                break;
            }
        }
        assertTrue(driver.getTitle().contains(expectedInTitle));
    }

    //this method accepts a String 'expected title' and asserts if it's true
    public static void verifyTitle(WebDriver driver, String expectedTitle){
        assertEquals(driver.getTitle(), expectedTitle);
    }

}
