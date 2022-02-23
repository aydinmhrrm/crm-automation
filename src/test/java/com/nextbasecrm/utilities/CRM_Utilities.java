package com.nextbasecrm.utilities;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CRM_Utilities {

    public static void crm_login(WebDriver driver, String username){
        WebElement loginInput = driver.findElement(By.xpath("//input[@name='USER_LOGIN']"));
        loginInput.sendKeys(username);
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='USER_PASSWORD']"));
        passwordInput.sendKeys("UserUser");
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        loginButton.click();
    }

    public static void crm_login(WebDriver driver){
        WebElement loginInput = driver.findElement(By.xpath("//input[@name='USER_LOGIN']"));
        loginInput.sendKeys("hr25@cydeo.com");
        WebElement passwordInput = driver.findElement(By.xpath("//input[@name='USER_PASSWORD']"));
        passwordInput.sendKeys("UserUser");
        WebElement loginButton = driver.findElement(By.xpath("//input[@value='Log In']"));
        loginButton.click();
    }

}
