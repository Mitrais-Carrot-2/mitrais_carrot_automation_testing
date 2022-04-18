package page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;


public class DashboardPage {

//    static MerchantPage merchantPage;
//    static ItemDetailsPage itemDetailsPage;

    WebDriver driver;


    public Integer numItem;

    By btn_view_item_details(Integer index) {
        return By.id("btn-item-" + index);
    }
    By btn_view_transfer_history = By.xpath("//button[normalize-space()='View']");
    By btn_to_manager = By.id("btn-manager");
    By carrot_amount_loc = By.id("carrot-amount-dashboard");
    By item_name(Integer index) {return By.id("item-name-" + index);}

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void assertInDashboard(){
        String expectedURL="http://localhost:3000/";
        Assert.assertEquals(this.driver.getCurrentUrl(),expectedURL);
    }

    public void getNumItem(MerchantPage merchantPage, WebDriverWait wait) {
//        WebDriverWait wait = new WebDriverWait(this.driver, Duration.ofSeconds(3));

        merchantPage.goToMerchantMenu();
        wait.until(driver -> driver.findElement(By.xpath("//button[normalize-space()='Bazaar Item']")));

        merchantPage.goToBazaarItem();
        wait.until(driver -> driver.findElement(By.xpath("//h2[normalize-space()='Bazaar Item Dashboard']")));

        this.numItem = merchantPage.getRows();
        System.out.println("num item = "+ this.numItem);

        try {
            wait.until(driver -> driver.findElement(By.id("last-data")));
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public long getCarrotAmount() {
        String carrotAmount = this.driver.findElement(carrot_amount_loc).getText().split(" ")[0];
        return Long.parseLong(carrotAmount);
    }

    public String getItemName(int index) {
        return this.driver.findElement(item_name(index)).getText();
    }

    public void goToDashboard() {
        this.driver.findElement(By.xpath("//a[@class='navbar-brand']")).click();
    }

    public void assertItemDetailsContent(MerchantPage merchantPage, ItemDetailsPage itemDetailsPage, WebDriverWait wait, Integer inputIndex) {
        getNumItem(merchantPage, wait);

        goToDashboard();
        wait.until(driver -> driver.findElement(By.id("carrot-amount-dashboard")));

        int index;
        if(inputIndex == 0) index = merchantPage.getRandomInts(1, this.numItem);
        else index = inputIndex;

        System.out.println("index = "+ index);
        System.out.println("btn id = " + btn_view_item_details(index));

        String expectedName = getItemName(index);
        System.out.println("item name dashboard = "+ expectedName);

        this.driver.findElement(btn_view_item_details(index)).click();
        wait.until(driver -> this.driver.findElement(By.id("item-details-name")));

        String actualName = itemDetailsPage.getItemName();
        System.out.println("item name details = "+ actualName);

        Assert.assertEquals("Item name doesn't match", expectedName, actualName);
    }

    public void buyItem(MerchantPage merchantPage, ItemDetailsPage itemDetailsPage, WebDriverWait wait, long carrotAmount, Integer index) {
        assertItemDetailsContent(merchantPage, itemDetailsPage, wait, index);
        itemDetailsPage.buyItem(carrotAmount, wait);
    }

    public long checkTransferHistory(WebDriverWait wait) {
        goToTransferHistoryPage();
        wait.until(driver -> this.driver.findElement(By.xpath("//span[@class='sc-bZkfAO sc-kLLXSd lmGpws OHwJB']")));

        long numTransfer = Long.parseLong(this.driver.findElement(By.xpath("//span[@class='sc-bZkfAO sc-kLLXSd lmGpws OHwJB']")).getText().split(" ")[2]);

        goToDashboard();
        wait.until(driver -> driver.findElement(By.id("carrot-amount-dashboard")));

        return numTransfer;
    }

    public void buyItemCheckTransferHistory(MerchantPage merchantPage, ItemDetailsPage itemDetailsPage, WebDriverWait wait, long carrotAmount, Integer index) {
        long prevNumTransfer = checkTransferHistory(wait);
        System.out.println("prev num transfer = " + prevNumTransfer);

        buyItem(merchantPage, itemDetailsPage, wait, carrotAmount, index);

        goToDashboard();
        wait.until(driver -> driver.findElement(By.id("carrot-amount-dashboard")));

        long newNumTransfer = checkTransferHistory(wait);
        System.out.println("new num transfer = " + newNumTransfer);

        Assert.assertEquals(newNumTransfer, (prevNumTransfer + 1));
    }

    public void goToTransferHistoryPage() {
        this.driver.findElement(btn_view_transfer_history).click();
    }

    public void toManagerPage(){
        this.driver.findElement(btn_to_manager).click();
    }
    
    public void goToFarmerPage(){
        this.driver.findElement(By.xpath("//button[@id='btn-farmer']")).click();
    }

}
