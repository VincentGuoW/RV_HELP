package com.example.Backend;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void login(String username, String password) {
        // 查找用户名和密码输入框并输入信息
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        // 查找并点击登录按钮
        //WebElement loginButton = driver.findElement(By.className("ci-button"));
        //loginButton.click();
        
        //Diff betwen JavascriptExectuor & straight codeing
        //You can see it ==> Straight loginButton.click();
        //Control from backend ==> JavascriptExecutor
        WebElement loginButton = driver.findElement(By.className("ci-button"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", loginButton);


        // 等待跳转到工具页面
        wait.until(ExpectedConditions.urlContains("tools"));
        //test
        
    }
}
