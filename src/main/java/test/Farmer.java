package test;

import java.time.Duration;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import page.DashboardPage;
import page.FarmerPage;
import page.LoginPage;

public class Farmer {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static FarmerPage farmerPage;

    @BeforeClass
    public static void loginToFarmerPage(){
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        farmerPage = new FarmerPage(driver);

        loginPage.login("Naufal","string"); //Change to username and password for farmer
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("to-farmer-dashboard")));
        dashboardPage.goToFarmerPage();
        WebDriverWait waitReloadToFarmerDashboard = new WebDriverWait(driver,Duration.ofSeconds(3));
        waitReloadToFarmerDashboard.until(driver -> driver.findElement(By.id("farmer-dashboard")));
        
    }

    @Test
    public void assertInFarmerPage(){
        farmerPage.assertInFarmerPage();
    }

    @Test
    public void showManageBarn(){
        HashMap<String, String> barnInfo = farmerPage.getBarnInfo(1);
        farmerPage.clickManageButton(1);
        // TODO : Compare the information in the model with the information in the HashMap
        driver.findElement(By.id("barn-info"));
    }

    @After
    public void backToFarmerDashboard(){
        driver.get("http://localhost:3000/farmer");
        WebDriverWait waitReloadToFarmerDashboard = new WebDriverWait(driver,Duration.ofSeconds(3));
        waitReloadToFarmerDashboard.until(driver -> driver.findElement(By.id("farmer-dashboard")));
    }




    
}
