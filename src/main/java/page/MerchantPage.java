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
    By exchangeLoc = By.xpath("//button[normalize-space()='Exchange']");

    By btn_add_member = By.id("add-member-button");
    By btn_staff_group = By.id("staff-group-button");
    By btn_create_group = By.id("create-group-button");
    By btn_modal_save = By.id("modal-save-button");

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

    public void goToExchange(){
        WebElement exchangeButton = this.driver.findElement(this.exchangeLoc);
        exchangeButton.click();
    }

    public void goToStaffGroup(){
        driver.findElement(this.btn_staff_group).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(this.btn_create_group));
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

    public void updateItem(String item, String price, String qty, String desc ){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Integer rowSize = getRows();
        Integer selectedIndex = getRandomInts(3,rowSize);

        By itemNameLoc = By.id("item-name-input");
        By itemPriceLoc = By.id("item-price-input");
        By itemQtyLoc = By.id("item-qty-input");
        By itemDescLoc = By.id("item-desc-input");

        By updateItemButtonLoc = By.xpath("//button[normalize-space()='Update Item']");

        //locate the edit/update button
        By updateButtonLoc = By.xpath("//tbody/tr["+selectedIndex+"]/td[8]/button[2]");
        WebElement updateButton = this.driver.findElement(updateButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", updateButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Update Item']")));


        //locate item name field
        if (item != ""){
            WebElement itemNameInput = this.driver.findElement(itemNameLoc);
            itemNameInput.clear();
            itemNameInput.sendKeys(item);
        } else{
            WebElement itemNameInput = this.driver.findElement(itemNameLoc);
            itemNameInput.clear();
        }


        //locate item price field
        if (price != ""){
            WebElement itemPriceInput = this.driver.findElement(itemPriceLoc);
            itemPriceInput.clear();
            itemPriceInput.sendKeys(price);
        }else{
            WebElement itemPriceInput = this.driver.findElement(itemPriceLoc);
            itemPriceInput.clear();
        }

        //locate item qty field
        WebElement itemQtyInput = this.driver.findElement(itemQtyLoc);
        itemQtyInput.clear();
        itemQtyInput.sendKeys(qty);

        //locate item desc field
        WebElement itemDescInput = this.driver.findElement(itemDescLoc);
        itemDescInput.clear();
        itemDescInput.sendKeys(desc);

        WebElement putItem = this.driver.findElement(updateItemButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", putItem);


    }


    public void updateItemImage(String imgPath){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Integer rowSize = getRows();
        Integer selectedIndex = getRandomInts(3,rowSize);

        By imageElmLoc = By.id("item-image-input");
        By updateItemImgButtonLoc = By.xpath("//button[normalize-space()='Change Item Image']");

        //locate the edit/update button
        By updateButtonLoc = By.xpath("//tbody/tr["+selectedIndex+"]/td[8]/button[1]");
        WebElement updateButton = this.driver.findElement(updateButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", updateButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Upload Item Image']")));

        //locate input file field
        WebElement inputImage = this.driver.findElement(imageElmLoc);
        inputImage.sendKeys(imgPath);

        //locate and send image
        WebElement sendImage = this.driver.findElement(updateItemImgButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendImage);
    }

    public void approveItem(){
        //Pre-condition: choose first index
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Integer selectedIndex = 1;

        By approveButtonLoc = By.xpath("//tbody//tr["+selectedIndex+"]//td[7]//button[1]");
        By sendApprovalLoc = By.xpath("//button[normalize-space()='Approve']");

        //Locate approval button on selected row
        WebElement approveButton = this.driver.findElement(approveButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", approveButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Approve Item?']")));

        //Click approve for updating the approval
        WebElement sendApproval = this.driver.findElement(sendApprovalLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendApproval);
    }

    public void denyItem(){
        //Pre-condition: choose first index
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Integer selectedIndex = 1;

        By denyButtonLoc = By.xpath("//tbody//tr["+selectedIndex+"]//td[7]//button[2]");
        By sendDenyLoc = By.xpath("//button[normalize-space()='Deny']");

        //Locate approval button on selected row
        WebElement denyButton = this.driver.findElement(denyButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", denyButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Deny Item?']")));

        //Click approve for updating the approval
        WebElement sendDeny = this.driver.findElement(sendDenyLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sendDeny);
    }

    public void createStaffGroup(String name, String note, String carrot){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        //locators
        By groupNameLoc = By.xpath("//input[@name='groupName']");
        By groupNoteLoc = By.xpath("//input[@name='groupNote']");
        By carrotLoc = By.xpath("//input[@name='groupAllocation']");
        By managerLoc = By.cssSelector(".css-6j8wv5-Input");

        By createGroupButtonLoc = By.xpath("//button[normalize-space()='Create Group']");

        //click Create New Group
        WebElement createGroup = this.driver.findElement(btn_create_group);
        createGroup.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Create Group']")));

        //input group name
        WebElement groupName = driver.findElement(groupNameLoc);
        groupName.sendKeys(name);

        //input group note
        WebElement createNote = this.driver.findElement(groupNoteLoc);
        createNote.sendKeys(note);

        //input group carrot allocation
        WebElement createCarrot = this.driver.findElement(carrotLoc);
        createCarrot.sendKeys(carrot);

        //input manager id
        WebElement managerName = driver.findElement(managerLoc);
        managerName.click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        //click create group button
        WebElement createGroupButton = this.driver.findElement(createGroupButtonLoc);
        createGroupButton.click();

    }

    public void updateStaffGroup(String name, String note, String carrot){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        Integer rowSize = getRows();
        Integer selectedIndex = getRandomInts(3,rowSize);

        By groupNameLoc = By.id("group-name-input");
        By groupNoteLoc = By.id("group-notes-input");
        By groupCarrotLoc = By.id("group-carrot-input");
//        By managerLoc = By.cssSelector(".css-6j8wv5-Input");
        By managerLoc = By.name("manager-id");

        By editButtonLoc = By.xpath("//tbody/tr["+selectedIndex+"]/td[9]/button[2]");

        By saveButtonLoc = By.xpath("//button[normalize-space()='Update Group']");

        //locate edit button
        WebElement editButton = this.driver.findElement(editButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click()", editButton);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Update Group']")));

        //input group name
        if (name == ""){
            WebElement groupName = this.driver.findElement(groupNameLoc);
            groupName.clear();
        }else{
            WebElement groupName = this.driver.findElement(groupNameLoc);
            groupName.clear();
            groupName.sendKeys(name);
        }
        WebElement groupName = this.driver.findElement(groupNameLoc);
        groupName.clear();
        groupName.sendKeys(name);

        //input group note
        WebElement groupNotes = this.driver.findElement(groupNoteLoc);
        groupNotes.clear();
        groupNotes.sendKeys(note);

        //input carrot allocation
        WebElement carrotGroup = this.driver.findElement(groupCarrotLoc);
        carrotGroup.clear();
        carrotGroup.sendKeys(carrot);

        //save group
        WebElement saveGroup = this.driver.findElement(saveButtonLoc);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", saveGroup);

//        .css-1pahdxg-control .css-6j8wv5-Input
    }

    public Integer getRows(){
        List<WebElement> rows = this.driver.findElements(By.xpath("//table/tbody/tr"));
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

    public void assertUpdatedItem(String msg, String itemName){
        Integer updateRow = getRows();
        String actualItem = driver.findElement(By.cssSelector("tbody tr:nth-child("+updateRow+") td:nth-child(4)")).getText();
        Assert.assertEquals("Success Update Item!", msg);
        Assert.assertEquals(itemName, actualItem);

    }

    public void assertFailedAddBazaar(Integer initRow, Integer finalRow){
        Assert.assertEquals("Failed, data keep added!",initRow, finalRow);
    }

    public void assertAddedRow(Integer initRow, Integer finalRow){
        Assert.assertNotEquals("Failed, no new data added!",initRow, finalRow);
    }

    public void assertNewMember(){
        driver.findElement(this.btn_staff_group).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(driver -> driver.findElement(this.btn_create_group));
        driver.findElement(By.xpath("//tr[1]//td[9]//button[1]")).click();
        wait.until(driver -> driver.findElement(this.btn_add_member));
        Integer totalMember = driver.findElements(By.xpath("//tbody/tr")).size();
        driver.findElement(this.btn_add_member).click();
        wait.until(driver -> driver.findElement(this.btn_add_member));

        Integer expectedMember = totalMember + 1;

        WebElement staff = driver.findElement(By.cssSelector(".css-1s2u09g-control"));
        staff.click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        driver.findElement(btn_modal_save).click();

        wait.until(ExpectedConditions.alertIsPresent());
        String errMsg = driver.switchTo().alert().getText();
        String expectedError = "Staff Added!";
        Alert alert = driver.switchTo().alert();
        alert.accept();

        wait.until(driver -> driver.findElement(this.btn_add_member));
        Integer actualMember = driver.findElements(By.xpath("//tbody/tr")).size();

        Assert.assertEquals("Failed, no new member added! "+errMsg, expectedError, errMsg);
        Assert.assertEquals("Failed, no new member added! "+totalMember, expectedMember, actualMember);
    }

    public void assertNewMemberExist(){
        driver.findElement(this.btn_staff_group).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(driver -> driver.findElement(this.btn_create_group));
        driver.findElement(By.xpath("//tr[1]//td[9]//button[1]")).click();
        wait.until(driver -> driver.findElement(this.btn_add_member));
        Integer totalMember = driver.findElements(By.xpath("//tbody/tr")).size();
        driver.findElement(this.btn_add_member).click();
        wait.until(driver -> driver.findElement(this.btn_add_member));

        Integer expectedMember = totalMember + 1;

        WebElement staff = driver.findElement(By.cssSelector(".css-1s2u09g-control"));
        staff.click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        driver.findElement(btn_modal_save).click();

        wait.until(ExpectedConditions.alertIsPresent());
        String errMsg = driver.switchTo().alert().getText();
        String expectedError = "Failed: Duplicate data!";
        Alert alert = driver.switchTo().alert();
        alert.accept();

        wait.until(driver -> driver.findElement(this.btn_add_member));

        Integer actualMember = driver.findElements(By.xpath("//tbody/tr")).size();

        Assert.assertEquals("Add new member aborted! "+errMsg, expectedError, errMsg);
        Assert.assertNotEquals("Add new member aborted! "+totalMember, expectedMember, actualMember);
    }

    public int getRandomInts(Integer min, Integer max){
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }

    public void assertMessage(String actualMsg, String expectedErr){
        Assert.assertEquals(expectedErr,actualMsg);
    }

    public void assertAlternateError(String actualMsg, String expectedErr){
        Assert.assertEquals(expectedErr,actualMsg);
    }

    public void assertErrorMessage(String actualMsg){
        Assert.assertEquals("Failed: Duplicate data!", actualMsg);
    }
}
