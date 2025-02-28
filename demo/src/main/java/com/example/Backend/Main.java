package com.example.Backend;

import javax.swing.JOptionPane;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Main {
    public static void main(String rvInputOrigin, String rvTimeOrigin, String rvTimeBackupOrigin, int refreshSpeed ){
        
        // 使用 WebDriverManager 自动下载并配置 ChromeDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        //Remove the space when we copy with space
        String rvInput = spaceRemove(rvInputOrigin);

        String rvTime = formatTime(spaceRemove(rvTimeOrigin));

        String rvTimeBackup = formatTime(spaceRemove(rvTimeBackupOrigin));

        System.out.println("********"+rvInput+"*********");
        System.out.println("********"+rvTime+"*********");
        System.out.println("********"+rvTimeBackup+"*********");

        if(!InputCheck(rvInput, rvTime, rvTimeBackup)){
            System.exit(0);//Normal Exist
        }
        System.out.println("********TRY TO OPEN THE PAGE*********");

        try {
            // 打开网页
            driver.get("https://ecprod.cn.ca/cis/#/auth?logout=true");

            // 创建并执行登录操作
            LoginPage loginPage = new LoginPage(driver);
            loginPage.login("CANA_BRIDGE", "Bridge1986!");

            // 打开预约查询页面
            driver.get("https://ecprod.cn.ca/cis/#/tools/gate-appointment-inquiry");

            // 创建并执行 RV 输入操作
            RVInputPage rvInputPage = new RVInputPage(driver);
            rvInputPage.enterRVInfo(rvInput);

            // 刷RV
            RVMainPage rvMainPage = new RVMainPage(driver);
            rvMainPage.refreshRV(rvInput, rvTime,rvTimeBackup,refreshSpeed);

            
        } catch (Exception e) {
            System.out.println("Error occurred: " + e.getMessage());
        } 
    };

    public static boolean  InputCheck(String rvInput, String rvTime, String rvTimeBackup ) {
        if (!rvInput.matches("^RV\\d{8}$")) {
            JOptionPane.showMessageDialog(null,"rvInput 无效，必须以 'RV' 开头，后跟 8 个数字。您的输入："+rvInput+" 是有问题的。");
            
            return false;
        }

        if (!rvTime.matches("^([01]\\d|2[0-3]):([0-5]\\d)$")) {
            System.out.println("rvTime 无效，必须是 24 小时制时间格式，例如 '01:59' 或 '14:02'。");
            JOptionPane.showMessageDialog(null,"rvTime 无效，必须是 24 小时制时间格式，例如 '01:59' 或 '14:02'。您的输入："+rvTime+" 是有问题的。");
            
            return false;
        }

        if (!("".equals(rvTimeBackup)|| rvTimeBackup.matches("^([01]\\d|2[0-3]):([0-5]\\d)$"))) {
            System.out.println("rvTimeBackup 无效，必须是 24 小时制时间格式，例如 '01:59' 或 '14:02'，或者为空。");
            JOptionPane.showMessageDialog(null,"rvTimeBackup 无效，必须是 24 小时制时间格式，例如 '01:59' 或 '14:02'，或者为空。或者是 \"\" 这个形式 您的输入： " +rvTimeBackup+" 是有问题的。");
            
            return false;
        }


        return true;
    }

    public static String spaceRemove(String inputString){
        String outputString = inputString.replace(" ", "");
        return outputString;
    }

    public static String formatTime(String rvTime){
        String newRVTime;

        if (rvTime.matches("^([01]?\\d|2[0-3])")) {
            newRVTime = String.format("%02d:00", Integer.valueOf(rvTime));
            return newRVTime;
        }
        return rvTime;
    }
}
