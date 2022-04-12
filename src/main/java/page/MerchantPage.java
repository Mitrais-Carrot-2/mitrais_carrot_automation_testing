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
    By bazaarItemButton_loc = By.xpath("//button[normalize-space()='Bazaar Item']");


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

    public void goToBazaarItem(){
        WebElement bazaarItemButton = this.driver.findElement(this.bazaarItemButton_loc);
        bazaarItemButton.click();
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

    public String updateBazaar(String name, String startDate, String endDate) {
//        driver.manage().window().maximize();
        Integer dataLength = getRows();
        Integer randIndex = getRandomInts(3, dataLength);
        String pathX = "//tbody/tr["+randIndex+"]/td[6]/button[1]";
        System.out.println("The xpath: " + pathX);

        WebElement putBazaar = driver.findElement(By.xpath(pathX));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", putBazaar);


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

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);


        //Window alert handling
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        return msg;
    }


    public void createBazaarItem(String item, String price, String qty, String desc ){
        By createItemLoc = By.xpath("//button[normalize-space()='Create New Item']");
        By bazaarNameLoc = By.cssSelector(".css-6j8wv5-Input");
        By itemNameLoc = By.xpath("//input[@name='itemName']");
        By itemPriceLoc = By.xpath("//input[@name='itemPrice']");
        By itemQtyLoc = By.xpath("//input[@name='itemQuantity']");
        By itemDescLoc = By.xpath("//input[@name='itemDescription']");

        //post create item
        By postCreateItemLoc = By.xpath("//button[normalize-space()='Create Item']");

        //click create bazaar item
        WebElement createItemButton = driver.findElement(createItemLoc);
        createItemButton.click();

        //input bazaar id
        WebElement bazaarName = driver.findElement(bazaarNameLoc);
        bazaarName.click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();

        //input item name
        WebElement itemName = driver.findElement(itemNameLoc);
        itemName.sendKeys(item);

        //input item price
        WebElement itemPrice = driver.findElement(itemPriceLoc);
        itemPrice.clear();
        itemPrice.sendKeys(price);

        //input item qty
        WebElement itemQuantity = driver.findElement(itemQtyLoc);
        itemQuantity.clear();
        itemQuantity.sendKeys(qty);

        //input item desc
        WebElement itemDesc = driver.findElement(itemDescLoc);
        itemDesc.sendKeys(desc);

        //Create Item Button
        WebElement postCreateItemButton = driver.findElement(postCreateItemLoc);
        postCreateItemButton.click();

//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
//        wait.until(ExpectedConditions.alertIsPresent());
//        Alert alert = driver.switchTo().alert();
//        alert.accept();
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

    public void assertCreateItem(String itemName){
        Integer indexItem = getRows();
        String actualItem = driver.findElement(By.cssSelector("tbody tr:nth-child("+indexItem+") td:nth-child(4)")).getText();
        Assert.assertEquals(itemName,actualItem);
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

    public void assertAlternateError(String actualMsg, String expectedErr){
        Assert.assertEquals(expectedErr,actualMsg);
    }

    public void assertErrorMessage(String actualMsg){

        Assert.assertEquals("Failed: Duplicate data!", actualMsg);
    }
}
