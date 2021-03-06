package test;

import static org.junit.Assert.assertEquals;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.junit.After;
import org.junit.AfterClass;
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
    public static void loginToFarmerPage() {
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        farmerPage = new FarmerPage(driver);

        loginPage.login("farmer", "farmer"); // Change to username and password for farmer
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("btn-farmer")));
        dashboardPage.goToFarmerPage();
        wait.until(driver -> driver.findElement(By.id("farmer-dashboard")));

    }

    @Test
    public void assertInFarmerPage() {
        farmerPage.assertInFarmerPage();
    }

    @Test
    public void showManageBarn() {
        HashMap<String, String> barnInfo = farmerPage.getBarnInfo(1);
        farmerPage.clickManageButton(1);
        driver.findElement(By.id("barn-info"));
        HashMap<String, String> barnManageInfo = farmerPage.getBarnInfoByManageButton();
        assertEquals(barnInfo, barnManageInfo);
    }

    @Ignore
    public void createNewBarn() throws InterruptedException {
        HashMap<String, String> lastBarnInfo = farmerPage.getBarnInfo(farmerPage.getLastBarnIndex());
        String lastBarnName = lastBarnInfo.get("Name");
        String[] lastBarnNameSplited = lastBarnName.split(" ", 2);
        int barnNumber = Integer.parseUnsignedInt(lastBarnNameSplited[1]) + 1;
        String newBarnName = lastBarnNameSplited[0] + " " + barnNumber;

        farmerPage.clickCreateBarnButton();
        driver.findElement(By.xpath("//*[text()='Create Barn']"));
        farmerPage.fillCreateBarnForm(newBarnName, String.valueOf(barnNumber), String.valueOf(barnNumber + 1), "1000");
    }

    @Test
    public void createNewBarnWithSameName() throws InterruptedException {
        HashMap<String, String> barnInfo = farmerPage.getBarnInfo(1);
        String barnName = barnInfo.get("Name");
        String[] barnNameSplited = barnName.split(" ", 2);
        int barnNumber = Integer.parseUnsignedInt(barnNameSplited[1]);

        farmerPage.clickCreateBarnButton();
        driver.findElement(By.xpath("//*[text()='Create Barn']"));
        farmerPage.fillCreateBarnFormSame(barnName, String.valueOf(barnNumber), String.valueOf(barnNumber + 1),
                "1000");
    }

    @Test
    public void editBarn() throws InterruptedException {
        HashMap<String, String> barn = farmerPage.getBarnInfoWithStatus(1);
        this.showManageBarn();
        HashMap<String, String> barnBefore = (HashMap<String, String>) barn.clone();
        HashMap<String, String> barnAfter = (HashMap<String, String>) barn.clone();
        barnAfter.put("Name", "Test Edit");
        LocalDate date = LocalDate.parse(barnBefore.get("End Periode"), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        date = date.plusYears(1);
        barnAfter.put("End Periode", date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        barnAfter.put("Carrot Amount", String.valueOf(Integer.parseUnsignedInt(barnAfter.get("Carrot Amount")) + 1000));

        farmerPage.fillEditBarnForm(barnAfter.get("Name"), date.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")),
                String.valueOf(barnAfter.get("Carrot Amount")));

        HashMap<String, String> barnInfoAfterEdit = farmerPage.getBarnInfoWithStatus(1);
        // chcek is active
        LocalDate today = LocalDate.now();
        LocalDate startPeriode = LocalDate.parse(barnInfoAfterEdit.get("Start Periode"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalDate endPeriode = LocalDate.parse(barnInfoAfterEdit.get("End Periode"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        if (today.isAfter(startPeriode.minusDays(1)) && today.isBefore(endPeriode)) {
            barnAfter.put("Status", "Active");
        } else {
            barnAfter.put("Status", "Inactive");
        }
        assertEquals(barnAfter, barnInfoAfterEdit);

        // Reset to original
        this.showManageBarn();
        farmerPage.fillEditBarnForm(barnBefore.get("Name"),
                date.minusYears(1).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")),
                String.valueOf(barnBefore.get("Carrot Amount")));
    }

    @Test
    public void createNewBarnReward() throws InterruptedException {
        farmerPage.clickManageButton(1);
        driver.findElement(By.id("barn-info"));
        farmerPage.fillBarnReward("Test", "10", "USER_BIRTHDAY");
    }

    @Test
    public void createEmptyBarnReward() throws InterruptedException {
        farmerPage.clickManageButton(1);
        driver.findElement(By.id("barn-info"));
        farmerPage.fillEmptyBarnReward();
    }

    @Test
    public void checkBarnHistory() {
        String barnName = farmerPage.getBarnName(1);
        farmerPage.clickHistoryButton(1);
        String historyName = farmerPage.getHistoryName();

        assertEquals("History of " + barnName, historyName);

    }

    @Test
    public void assertDistributionPageActive() {
        // int activeBarn = farmerPage.searchForActiveBarn();
        // if (activeBarn != 0) {
        //     farmerPage.clickDistributionButton();
        //     farmerPage.clickDashboardButton();
        //     farmerPage.clickDistributionButton();
        //     farmerPage.assertInDistributionPageActive();
        // }
        farmerPage.clickDistributionButton();
        farmerPage.assertInDistributionPageActive();
    }

    @Ignore
    public void assertDistributionPageInactive() throws InterruptedException {
        int activeBarn = farmerPage.searchForActiveBarn();
        while (activeBarn != 0) {
            farmerPage.setToInactiveBarn(activeBarn);
            activeBarn = farmerPage.searchForActiveBarn();
        }
        if (activeBarn == 0) {
            farmerPage.clickDistributionButton();
            farmerPage.clickDashboardButton();
            farmerPage.clickDistributionButton();
            farmerPage.assertInDistributionPageInactive();
        }

    }

    @Test
    public void transferToManager() throws InterruptedException {
        // int activeBarn = farmerPage.searchForActiveBarn();
        // if (activeBarn == 0) {
        //     farmerPage.setToActiveBarn(1);
        // }

        this.assertDistributionPageActive();
        String carrotAmount = "10";
        String message = "test";
        String receiver = farmerPage.transferToManager(carrotAmount, message);

        // String[] transactionDetails = farmerPage.getTransactionDetails(farmerPage.getLastBarnIndex());
        // assertEquals(receiver, transactionDetails[0]);
        // assertEquals(carrotAmount, transactionDetails[1]);
        // assertEquals(message, transactionDetails[2]);


    }

    @After
    public void backToFarmerDashboard() {
        driver.get("http://localhost:3000/farmer");
        WebDriverWait waitReloadToFarmerDashboard = new WebDriverWait(driver, Duration.ofSeconds(3));
        waitReloadToFarmerDashboard.until(driver -> driver.findElement(By.id("farmer-dashboard")));
    }

    @AfterClass
    public static void closeBrowser() {
        driver.manage().deleteAllCookies();
        driver.quit();
    }
}
