package test;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.util.HashMap;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
        wait.until(driver -> driver.findElement(By.id("btn-farmer")));
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
        driver.findElement(By.id("barn-info"));
        HashMap<String, String> barnManageInfo = farmerPage.getBarnInfoByManageButton();
        assertEquals(barnInfo, barnManageInfo);
    }

    @Ignore
    public void createNewBarn() throws InterruptedException{
        HashMap<String, String> lastBarnInfo = farmerPage.getBarnInfo(farmerPage.getLastTableIndex());
        String lastBarnName = lastBarnInfo.get("Name");
        String[] lastBarnNameSplited = lastBarnName.split(" ", 2);
        int barnNumber = Integer.parseUnsignedInt(lastBarnNameSplited[1])+1;
        String newBarnName = lastBarnNameSplited[0]+ " " + barnNumber;

        farmerPage.clickCreateBarnButton();
        driver.findElement(By.xpath("//*[text()='Create Barn']"));
        farmerPage.fillCreateBarnForm(newBarnName, String.valueOf(barnNumber), String.valueOf(barnNumber+1), "1000");
    }

    @Test
    public void createNewBarnWithSameName() throws InterruptedException{
        HashMap<String, String> lastBarnInfo = farmerPage.getBarnInfo(farmerPage.getLastTableIndex());
        String lastBarnName = lastBarnInfo.get("Name");
        String[] lastBarnNameSplited = lastBarnName.split(" ", 2);
        int barnNumber = Integer.parseUnsignedInt(lastBarnNameSplited[1]);

        farmerPage.clickCreateBarnButton();
        driver.findElement(By.xpath("//*[text()='Create Barn']"));
        farmerPage.fillCreateBarnFormSame(lastBarnName, String.valueOf(barnNumber), String.valueOf(barnNumber+1), "1000");
    }

    @After
    public void backToFarmerDashboard(){
        driver.get("http://localhost:3000/farmer");
        WebDriverWait waitReloadToFarmerDashboard = new WebDriverWait(driver,Duration.ofSeconds(3));
        waitReloadToFarmerDashboard.until(driver -> driver.findElement(By.id("farmer-dashboard")));
    }




    
}
