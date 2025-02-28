package com.example.Backend;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RVInputPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public RVInputPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void enterRVInfo(String rvInput) {
        driver.navigate().refresh();
        // 切换到 iframe
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("iframe[src*='AppointmentQuery']")));
        driver.switchTo().frame(iframe);

        // 查找并输入 RV 信息
        
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("ids")));
        textarea.sendKeys(rvInput);
        System.out.println("RV INPUT SUCCESS");

        //<input type="radio" name="inputType" value="rv">
        // 点击 RV radio button
        WebElement RadioBtnRV = driver.findElement(By.cssSelector("input[type='radio'][value='rv']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click()", RadioBtnRV);
        System.out.println("RV CHANGE RQ RADIO BUTTON SUCCESS");


        //not sure how this works, but it won't click login button with out it.
        WebElement submitButton = driver.findElement(By.cssSelector("input[type='button'][value='Submit']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submitButton);
        System.out.println("RV CHANGE PAGE SUBMIT");



        WebElement rvHref  = driver.findElement(By.linkText(rvInput));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", rvHref);
        System.out.println("RV HREF SUCCESS");

        WebElement modifyButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='button'][value='Modify date/time']")));
        //WebElement modifyButton = driver.findElement(By.cssSelector"input[type='button'][value='Modify date/time']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", modifyButton);
        System.out.println("RV HREF PAGE SUBMIT");
        sleep(1);
        
        try {
            
            Alert alert = driver.switchTo().alert();
                alert.accept();
                
        } catch (Exception e) {
            System.out.println("No Alert");
            
        }

    }

    static void sleep(int second){
        try {
            Thread.sleep(second*1000);
        } catch (Exception e) {
        }
    }
}
