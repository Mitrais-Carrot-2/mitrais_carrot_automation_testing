package page;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.Assert;

import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class MerchantPage {
    WebDriver driver;

    //Define locators in Merchant Page
    By merchantmenu_loc = By.xpath("//button[normalize-space()='MERCHANT']");
//    By merchantMenu_loc = By.id("merchant-button");
    By bazaarButton_loc = By.xpath("//button[1]");


    public MerchantPage(WebDriver driver) {
        this.driver=driver;
    }

    public void goToMerchantMenu(){
        //Find merchant button
        WebElement merchantButton = this.driver.findElement(this.merchantmenu_loc);

        //CLick the button
        merchantButton.click();

    }

    public void goToBazaar(){
        WebElement bazaarButton = this.driver.findElement(this.bazaarButton_loc);

        bazaarButton.click();
    }

    public void createBazaar( String name, String startDate, String endDate){
        By createBazaar = By.xpath("//button[normalize-space()='Create New Bazaar']");
        By inputBazName = By.xpath("//input[@name='bazaarName']");

        //Create New Bazaar - Button click
        WebElement createBazaarButton = this.driver.findElement(createBazaar);
        createBazaarButton.click();

        if (name != "") {
            //Bazaar name form
            WebElement inputBazaarName = this.driver.findElement(inputBazName);
            inputBazaarName.sendKeys(name);
        }

        //Start date form
        WebElement inputStartDate = this.driver.findElement(By.xpath("//input[@name='startDate']"));
        inputStartDate.sendKeys(startDate);

        //End date form
        WebElement inputEndDate = this.driver.findElement(By.xpath("//input[@name='endDate']"));
        inputEndDate.sendKeys(endDate);

        //Create Bazaar post button
        WebElement postBazaar = driver.findElement(By.xpath("//button[normalize-space()='Create Bazaar']"));
        postBazaar.click();driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        //Window alert handling
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();

    }

    public void updateBazaar(String name, String startDate, String endDate) throws InterruptedException {
//        driver.manage().window().maximize();
        Integer dataLength = getRows();
        Integer randIndex = getRandomInts(1, dataLength);
        String pathX = "//tbody/tr["+randIndex+"]/td[6]/button[1]";
        System.out.println("The xpath: " + pathX);

        WebElement putBazaar = driver.findElement(By.xpath(pathX));
//        driver.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", putBazaar);
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", putBazaar);
//        Thread.sleep(2000);
//        putBazaar.click();


//        Point p= putBazaar.getLocation();
//        Actions action = new Actions(driver);
//        action.moveToElement(putBazaar).perform();

        //putBazaar.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
        if (name != "") {
            //Bazaar name form
//            WebElement inputBazaarName = this.driver.findElement(By.xpath("//input[@name='bazaarName']"));
            WebElement inputBazaarName = this.driver.findElement(By.id("bazaar-name-input"));
            inputBazaarName.clear();
            inputBazaarName.sendKeys(name);
        }

        //Start date form
//        WebElement inputStartDate = this.driver.findElement(By.xpath("//input[@name='endDate']"));
        WebElement inputStartDate = this.driver.findElement(By.id("start-bazaar-input"));
        inputStartDate.clear();
        inputStartDate.sendKeys(startDate);

        //End date form
//        WebElement inputEndDate = this.driver.findElement(By.xpath("//input[@name='endDate']"));
        WebElement inputEndDate = this.driver.findElement(By.id("end-bazaar-input"));
        inputEndDate.clear();
        inputEndDate.sendKeys(endDate);

        //Update Bazaar button
//        WebElement postBazaar = driver.findElement(By.xpath("//body[1]/div[1]/section[1]/div[2]/table[1]/tbody[1]/tr[27]/td[6]/div[1]/div[2]/div[1]/div[1]/div[3]/button[2]"));
        WebElement postBazaar = driver.findElement(By.xpath("//button[normalize-space()='Update Bazaar']"));

        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", postBazaar);
//        postBazaar.click();
        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


        //Window alert handling
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public Integer getRows(){
        List<WebElement> rows = driver.findElements(By.xpath("//table/tbody/tr"));
        Integer rowSize = rows.size();
        return rowSize;
    }

    public void assertMerchantBazaar(){
        //expected values
        String expectedURL="http://localhost:3000/merchant/bazaar";

        //Check url
        Assert.assertEquals("Mismatch", expectedURL, this.driver.getCurrentUrl());

    }

    public void assertUpdatedValue(String bazaarName){
        Integer selectedRow = getRows();
        System.out.println("TEsting.......");
        String actualName = driver.findElement(By.xpath("//tbody/tr["+selectedRow+"]/td[3]")).getText();
        System.out.println("Your bazaar name: " + bazaarName.substring(bazaarName.lastIndexOf("]")+1));
        System.out.println("----");
        System.out.println("Actually on the table: " + actualName.substring(actualName.lastIndexOf("]") +1));
        Assert.assertEquals("Name not match!", bazaarName,actualName);
    }

    public void assertMerchantMenu(){
        //expected values
        String expectedURL="http://localhost:3000/merchant";

        //Check url
        Assert.assertEquals("Mismatch", expectedURL, this.driver.getCurrentUrl());

    }

    public void assertFailedAddBazaar(Integer initRow, Integer finalRow){
        Assert.assertEquals("Failed, data keep added!",initRow, finalRow);
    }

    public void assertAddedRow(Integer initRow, Integer finalRow){
        Assert.assertNotEquals("Failed, no new data added!",initRow, finalRow);
    }

    public int getRandomInts(Integer min, Integer max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}
