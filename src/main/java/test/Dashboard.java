package test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.*;

import java.time.Duration;

public class Dashboard {
    static WebDriver driver;
    static DashboardPage dashboardPage;
    static LoginPage loginPage;
    static MerchantPage merchantPage;
    static ItemDetailsPage itemDetailsPage;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(2500));

    long carrotAmount;

    @BeforeClass
    public static void beforeLogin(){
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        merchantPage = new MerchantPage(driver);
        itemDetailsPage = new ItemDetailsPage(driver);
    }

    @Before
    public void setUp() throws InterruptedException {
        Thread.sleep(800);
        loginPage.login ("rara","rara");
        wait.until(driver -> driver.findElement(By.id("dashboard-title")));
        dashboardPage.assertInDashboard();
        this.carrotAmount = dashboardPage.getCarrotAmount();
    }

    @Test
    public void checkItemDetails() {
        System.out.println("\nTest1: Check Random Item Details");
        dashboardPage.assertItemDetailsContent(merchantPage, itemDetailsPage, wait, 0);
    }

    @Test
    public void buyItem_Success() {
        System.out.println("\nTest2: Buy Item - Success");
        Integer itemIndex = 3;
        dashboardPage.buyItem(merchantPage, itemDetailsPage, wait, carrotAmount, itemIndex);
    }

    @Test
    public void buyItem_NotEnoughCarrot() {
        System.out.println("\nTest3: Buy Item - Not Enough Carrot");
        Integer itemIndex = 1;
        dashboardPage.buyItem(merchantPage, itemDetailsPage, wait, carrotAmount, itemIndex);
    }

    @Test
    public void buyItem_ItemSoldOut() {
        System.out.println("\nTest4: Buy Item - Item is Sold Out");
        Integer itemIndex = 2;
        dashboardPage.buyItem(merchantPage, itemDetailsPage, wait, carrotAmount, itemIndex);
    }

    @Test
    public void buyItem() {
        System.out.println("\nTest5: Buy Random Item");
        Integer itemIndex = 0;
        dashboardPage.buyItem(merchantPage, itemDetailsPage, wait, carrotAmount, itemIndex);
    }

    @Test
    public void checkTransferHistory() {
        dashboardPage.buyItemCheckTransferHistory(merchantPage, itemDetailsPage, wait, carrotAmount, 7);
    }

//    @After
//    public void clearCache(){
//        driver.manage().deleteAllCookies();
//    }

//    @AfterClass
//    public static void closeBrowser(){
//        driver.quit();
//    }
}
