package test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.DashboardPage;
import page.LoginPage;
import page.ManagerPage;

import java.time.Duration;

public class Manager {
    static WebDriver driver;
    static ManagerPage managerPage;
    static DashboardPage dashboardPage;
    static LoginPage loginPage;
    WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));

    Long carrotAmount = ((long) (Math.random()*(10 - 1))) + 1;

    @BeforeClass
    public static void beforeLogin(){
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");

        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        managerPage = new ManagerPage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    @Before
    public void setUp(){
        loginPage.login ("manager","manager");
        wait.until(driver -> driver.findElement(By.id("dashboard-title")));
        dashboardPage.toManagerPage();
        wait.until(driver -> driver.findElement(By.id("freezer-title")));
        managerPage.assertManagerPage();
    }

    @Test
    public void freezer() {
        managerPage.assertFreezerSection();
    }

    @Test
    public void transactionHistory() {
        managerPage.assertTransactionHistory();
    }

    @Test
    public void staff(){
        managerPage.staff();
    }

    @Test
    public void shareToStaff(){
        managerPage.shareToStaff(this.carrotAmount, "Automation Test");
    }

    @Test
    public void shareToStaff_NoStaff(){
        managerPage.shareToStaff_NoStaff(this.carrotAmount, "Automation Test");
    }

    @Test
    public void shareToStaff_NoCarrot(){
        managerPage.shareToStaff_NoCarrot(this.carrotAmount*-1, "Automation Test");
    }

    @Test
    public void shareToStaff_NotEnoughCarrot(){
        managerPage.shareToStaff_NotEnoughCarrot(100000000L, "Automation Test");
    }

    @Test
    public void shareToGroup(){
        managerPage.shareToGroup(this.carrotAmount, "Automation Test - Group");
    }

    @Test
    public void shareToGroup_NoCarrot(){
        managerPage.shareToGroup_NoCarrot(this.carrotAmount*-1, "Automation Test - Group");
    }

    @Test
    public void shareToGroup_NotEnoughCarrot(){
        managerPage.shareToGroup_NotEnoughCarrot(1000000L, "Automation Test - Group");
    }

    @Test
    public void group(){
        managerPage.assertGroup();
    }

    @Test
    public void staffGroup(){
        managerPage.assertStaffGroup();
    }

    @After
    public void clearCache(){
        driver.manage().deleteAllCookies();
    }

    @AfterClass
    public static void closeBrowser(){
        driver.quit();
    }
}