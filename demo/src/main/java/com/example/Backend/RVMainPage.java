package com.example.Backend;

import java.time.Duration;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RVMainPage {
    private WebDriver driver;
    private WebDriverWait wait;

    public RVMainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(600));
    }

    public void refreshRV(String rvInput, String rvTime, String rvTimeBackup, int waitTime) {
        try {
            Thread.sleep(3000);// Use this time to pick diff day if need
        } catch (Exception e) {
        }

        boolean isRVFound = false;
        int testNumber = 0;

        // int refreshCount = 0; // 计数器
        // final int maxRefresh = 100; // 每 100 次暂停一次
        // final long restTime = 5000; // 休息 5 秒

        while (!isRVFound) {
            testNumber++;
            System.err.println("Test Number: " + testNumber);
            // Test waiting time
            // if (refreshCount >= maxRefresh) {
            // System.out.println("Take 5 second break...");
            // try {
            // Thread.sleep(restTime);
            // } catch (InterruptedException e) {
            // System.out.println("休息时线程被中断");
            // }
            // refreshCount = 0; // 重置计数器
            // }
            // refreshCount++;

            // Main
            try {
                Thread.sleep(waitTime);
            } catch (Exception e) {
            }
            try {

                /*
                WebElement RVTimeMainRadioBtn = driver
                .findElement(By.cssSelector("input[type='radio'][value='" + rvTime + "']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()",
                RVTimeMainRadioBtn);
                
                WebElement SaveBtn = driver
                .findElement(By.cssSelector(
                "input[type='button'][onclick*='confirmSaveActionAndSubmit']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", SaveBtn);
                 */
                WebElement SaveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("input[type='button'][onclick*='confirmSaveActionAndSubmit']")));
                WebElement RVTimeMainRadioBtn = driver
                        .findElement(By.cssSelector("input[type='radio'][value='" + rvTime + "']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", RVTimeMainRadioBtn);    //
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", SaveBtn);

                Alert alert = driver.switchTo().alert();
                alert.accept();
                isRVFound = true;
                // When avaliable shows, pop up notice
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "RV时间  首选  已出现");

                //
            } catch (NoSuchElementException e) {
                // 如果元素没有找到，捕获异常
                System.out.println("Main Radio button not found.");
            } catch (Exception e) {
                System.out.println("Unknow error might be website/computer issue");
                continue;
            }

            // back up
            try {
                WebElement SaveBtn = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input[type='button'][onclick*='confirmSaveActionAndSubmit']")));
            

                WebElement RVTimeBackUpRadioBtn = driver
                        .findElement(By.cssSelector("input[type='radio'][value='" + rvTimeBackup + "']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", RVTimeBackUpRadioBtn);

                //WebElement SaveBtn = driver
                //        .findElement(By.cssSelector("input[type='button'][onclick*='confirmSaveActionAndSubmit']"));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click()", SaveBtn);

                Alert alert = driver.switchTo().alert();
                alert.accept();
                isRVFound = true;
                // When avaliable shows, pop up notice
                JDialog dialog = new JDialog();
                dialog.setAlwaysOnTop(true);
                JOptionPane.showMessageDialog(dialog, "RV时间  备选  已出现");

                //
            } catch (NoSuchElementException e) {
                // 如果元素没有找到，捕获异常
                if (!"".equals(rvTimeBackup)) {
                    System.out.println("Backup Radio button not found.");
                }
            } catch (Exception e) {
                System.out.println("Unknow error might be website/computer issue");
                continue;
            }


            // Waiting for few second before next fresh
            try {

                WebElement avbBtn = driver
                        .findElement(By.cssSelector("input[type='button'][onclick*='beforeSubmit()']"));
                //((JavascriptExecutor) driver).executeScript("arguments[0].click()", avbBtn);
                boolean avbBtnClicked = false;
                while (!avbBtnClicked) { 
                    if (avbBtn.isEnabled()) {
                        try {
                            ((JavascriptExecutor) driver).executeScript("arguments[0].click()", avbBtn);
                            avbBtnClicked=true;
                        } catch (Exception e) {
                            System.out.println("Error while clicking the button: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Save button is still disabled. Attempting again...");
                    }
                }
            } catch (NoSuchElementException e) {
                System.out.println("Thread was interrupted while sleeping.");
            } catch (Exception e) {
                System.out.println("Unknow error might be website/computer issue");
                
            }


        }

    }

}
