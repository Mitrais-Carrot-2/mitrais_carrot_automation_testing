package test;

import org.junit.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import page.HomePage;
import page.MerchantPage;
import page.LoginPage;

import java.time.Duration;
import java.util.Random;

public class Merchant {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static MerchantPage merchantPage;

    //inisiasi
    @BeforeClass
    public static void beforeLogin(){
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");

        driver = new ChromeDriver();
        loginPage=new LoginPage(driver);
        merchantPage=new MerchantPage(driver);
    }

    @Before
    public void goToLoginPage(){
        loginPage.login("merchant", "merchant");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.xpath("//h2[normalize-space()='DASHBOARD']")));
        merchantPage.goToMerchantMenu();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("staff-group-button")) );
    }

    @Test
    public void goToMerchantPage(){

        merchantPage.goToMerchantMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );

        merchantPage.assertMerchantMenu();


    }

    @Test
    public void goToBazaar(){
        merchantPage.goToMerchantMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );

        merchantPage.goToBazaar();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));

        merchantPage.assertMerchantBazaar();
    }

    //Success create bazaar
    @Test
    public void createNewBazaar(){
        merchantPage.goToMerchantMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );

        merchantPage.goToBazaar();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));

        Integer initialRow = merchantPage.getRows();
        String bazaarName = "Test Bazaar " + getRandomInts(100,200);
        String startDate = "01012022";
        String endDate = "30122023";

        merchantPage.createBazaar(bazaarName,startDate,endDate);
        Integer finalRow = merchantPage.getRows();

        merchantPage.assertAddedRow(initialRow,finalRow);
    }

    //Fail Create Bazaar - Missing field
    @Test
    public void createBazaarEmptyValue(){
        merchantPage.goToMerchantMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );

        merchantPage.goToBazaar();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));

        Integer initialRow = merchantPage.getRows();
        String bazaarName = "";
        String startDate = "";
        String endDate = "30122023";

        merchantPage.createBazaar(bazaarName,startDate,endDate);
        Integer finalRow = merchantPage.getRows();

        merchantPage.assertFailedAddBazaar(initialRow,finalRow);
    }

    @Test
    public void updateBazaar() throws InterruptedException {
        merchantPage.goToMerchantMenu();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );

        merchantPage.goToBazaar();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));

        Integer initialRow = merchantPage.getRows();
        String bazaarName = "Test update " + getRandomInts(100,200);
        String startDate = "01012022";
        String endDate = "30122023";

        merchantPage.updateBazaar(bazaarName,startDate,endDate);

        merchantPage.assertUpdatedValue(bazaarName);
    }

    @Test
    public void addNewMember(){
        merchantPage.assertNewMember();
    }

    //After each test
    @After
    public void clearCache(){
        //Delete cookies to logout user
        driver.manage().deleteAllCookies();
    }

    //After all tests
    @AfterClass
    public static void closeBrowser(){
        //Terminate the WebDriver
        driver.quit();
    }

    public int getRandomInts(Integer min, Integer max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
