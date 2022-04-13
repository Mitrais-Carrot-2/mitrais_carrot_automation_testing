package test;

import org.junit.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


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
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );
    }

//    @Test
//    public void goToMerchantPage(){
//
//        merchantPage.goToMerchantMenu();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );
//
//        merchantPage.assertMerchantMenu();
//
//
//    }
//
//    @Test
//    public void goToBazaar(){
//        merchantPage.goToMerchantMenu();
//
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("merchant-container")) );
//
//        merchantPage.goToBazaar();
//
//        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));
//
//        merchantPage.assertMerchantBazaar();
//    }
//
    //Success create bazaar
    @Test
    public void createNewBazaar(){


        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

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

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

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

    //         /////////////        //
    //         UPDATE BAZAAR        //
    //         /////////////        //
    @Test
    public void updateBazaar()  {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));


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
    public void updateBazaarFailed(){
        //Bazaar with name "main" is the default bazaar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        merchantPage.goToBazaar();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Dashboard']")));

        Integer initialRow = merchantPage.getRows();
        String bazaarName = "main";
        String startDate = "01012022";
        String endDate = "30122023";

        String msg = merchantPage.updateBazaar(bazaarName,startDate,endDate);

        merchantPage.assertErrorMessage(msg);

    }

    //         /////////////        //
    //          CREATE ITEM         //
    //         /////////////        //

    //success case
    @Test
    public void createItem(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        //load bazaar item page
        merchantPage.goToBazaarItem();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        //Initialize input value
        String itemName = "Item "+ getRandomInts(100,200);
        String itemPrice = ""+getRandomInts(50,200);
        String itemQty = ""+getRandomInts(1,20);
        String itemDesc = "Description " + getRandomInts(100,1000);

        merchantPage.createBazaarItem(itemName,itemPrice,itemQty,itemDesc);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        merchantPage.assertCreateItem(itemName);
    }

    //fail case
    @Test
    public void createItemFail(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        //load bazaar item page
        merchantPage.goToBazaarItem();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        //Initialize input value
        String itemName = "";
        String itemPrice = ""+getRandomInts(50,200);
        String itemQty = ""+getRandomInts(1,20);
        String itemDesc = "Description " + getRandomInts(100,1000);

        merchantPage.createBazaarItem(itemName,itemPrice,itemQty,itemDesc);
        wait.until(ExpectedConditions.alertIsPresent());
        String errMsg = driver.switchTo().alert().getText();
        String expectedError = "Failed";
        Alert alert = driver.switchTo().alert();
        alert.accept();

        merchantPage.assertAlternateError(errMsg,expectedError);
    }

    //         /////////////       //
    //         UPDATE ITEM         //
    //        /////////////        //

    @Test
    public void updateItem(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        //load bazaar item page
        merchantPage.goToBazaarItem();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        //Initialize input value
        String itemName = "Item Update "+ getRandomInts(100,200);
        String itemPrice = ""+getRandomInts(50,200);
        String itemQty = ""+getRandomInts(1,20);
        String itemDesc = "Description Update: " + getRandomInts(100,1000);

        merchantPage.updateItem(itemName, itemPrice, itemQty, itemDesc);

        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertUpdatedItem(msg, itemName);
    }

    @Test
    public void updateItemFailed(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        //load bazaar item page
        merchantPage.goToBazaarItem();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        //Initialize input value
        String itemName = "";
        String itemPrice = "0";
        String itemQty = "0";
        String itemDesc = "Description Update: " + getRandomInts(100,1000);

        merchantPage.updateItem(itemName, itemPrice, itemQty, itemDesc);

        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertAlternateError(msg, "Update Error!");
    }

    //         //////////////////        //
    //          UPDATE ITEM IMAGE        //
    //         //////////////////        //
    @Test
    public void updateItemImage(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        String imagePath = "C:\\Users\\Arel_B313\\Documents\\default.png";


        //load bazaar item page
        merchantPage.goToBazaarItem();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        merchantPage.updateItemImage(imagePath);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Successfully updated");
    }

    //         //////////////////////        //
    //          APPROVE/DENY REQUEST         //
    //         //////////////////////        //
    @Test
    public void approveItem(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        //load exchange
        merchantPage.goToExchange();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Exchange Dashboard']")));

        merchantPage.approveItem();
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Success!");
    }

    @Test
    public void denyItem(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        //load exchange
        merchantPage.goToExchange();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Exchange Dashboard']")));

        merchantPage.denyItem();
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Success!");
    }

    //         //////////////////        //
    //             ADD MEMBER            //
    //         //////////////////        //
//    @Test
//    public void addNewMember(){
//        merchantPage.assertNewMember();
//    }
//
//    @Test
//    public void addNewMemberExist(){
//        merchantPage.assertNewMemberExist();
//    }

    //         //////////////////        //
    //            CREATE GROUP           //
    //         //////////////////        //
    @Test
    public void createGroup(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        //init input data
        String groupName = "Group " + getRandomInts(10,100);
        String groupNote = "Notes " + getRandomInts(100, 500);
        String carrot = ""+getRandomInts(5,50);

        merchantPage.createStaffGroup(groupName,groupNote,carrot);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='Staff Group Dashboard']")));

        merchantPage.assertCreateItem(groupName);

    }

    @Test
    public void createGroupFailMissingData(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        //init input data, missing group name
        String groupName = "";
        String groupNote = "Notes " + getRandomInts(100, 500);
        String carrot = ""+getRandomInts(5,50);

        merchantPage.createStaffGroup(groupName,groupNote,carrot);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Failed: Missing Data!");
    }

    @Test
    public void createGroupFailDuplicateData(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        //init input data, missing group name
        //pre-condition: group with "main" name exists
        String groupName = "Main";
        String groupNote = "Notes " + getRandomInts(100, 500);
        String carrot = ""+getRandomInts(5,50);

        merchantPage.createStaffGroup(groupName,groupNote,carrot);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Failed: Duplicate data!");
    }

    //         //////////////////        //
    //            UPDATE GROUP           //
    //         //////////////////        //

    @Test
    public void updateStaffGroup(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        String groupName = "Group Update " + getRandomInts(100, 500);
        String groupNote = "Notes Update " + getRandomInts(100, 500);
        String carrot = ""+getRandomInts(5,50);

        merchantPage.updateStaffGroup(groupName,groupNote,carrot);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Updated!");
    }

    @Test
    public void updateGroupFailMissing(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        String groupName = "";
        String groupNote = "Notes Update " + getRandomInts(100, 500);
        String carrot = "0";

        merchantPage.updateStaffGroup(groupName,groupNote,carrot);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Update Error: Missing Data!");
    }

    @Test
    public void updateGroupFailDuplicateName(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        merchantPage.goToStaffGroup();

        String groupName = "Main";
        String groupNote = "Notes Update " + getRandomInts(100, 500);
        String carrot = ""+getRandomInts(5,50);

        merchantPage.updateStaffGroup(groupName,groupNote,carrot);
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        merchantPage.assertMessage(msg, "Update Error: Duplicate Data!");
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
