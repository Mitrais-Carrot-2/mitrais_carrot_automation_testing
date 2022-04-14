package page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemDetailsPage {
    DashboardPage dashboardPage;

    WebDriver driver;

    By btn_buy = By.cssSelector(".btn.btn-carrot");
    By btn_buy_confirm = By.id("btn-buy-confirm");
    By buy_requested_modal = By.id("buy-requested-modal");
    By item_qty = By.id("item-qty");
    By unable_buy_message = By.xpath("//small[@class='text-danger']");
    By item_details_name = By.id("item-details-name");
    By item_details_price = By.xpath("//strong[@class='carrot-orange my-3']");

    public ItemDetailsPage(WebDriver driver) { this.driver = driver; }

    public long getItemQuantity() {
        String qtyText = this.driver.findElement(item_qty).getText().split(" ")[1];
        long qty = Long.parseLong(qtyText);
        return qty;
    }

    public String getItemName() {
        return this.driver.findElement(item_details_name).getText();
    }

    public void checkButtonStatus(boolean state) {
        boolean buttonStatus = this.driver.findElement(btn_buy).isEnabled();
        Assert.assertEquals(state, buttonStatus);
    }

    public void checkButtonMessage(String inputMessage) {
        String btnMessage = this.driver.findElement(unable_buy_message).getText();
        Assert.assertEquals(inputMessage, btnMessage);
    }

    public boolean isCarrotEnough(long carrotAmount) {
        System.out.println("\nChecking if you have enough carrots....");

        long price = Long.parseLong(this.driver.findElement(item_details_price).getText().split(" ")[0]);

        if(carrotAmount < price) {
            String message = "You don't have enough carrots to buy this item.";
            checkButtonMessage(message);
            checkButtonStatus(false);

            System.out.println(message);
            return false;
        }

        System.out.println("You have enough carrots.");
        return true;
    }

    public boolean isItemAvailable() {
        System.out.println("\nChecking if item is available....");

        long qty = getItemQuantity();

        if (qty <= 0) {
            String message = "Item is sold out.";
            checkButtonMessage(message);
            checkButtonStatus(false);

            System.out.println(message);
            return false;
        }

        System.out.println("Item is available.");
        return true;
    }

    public void buyItem(long carrotAmount, WebDriverWait wait) {
        if (!isItemAvailable() && !isCarrotEnough(carrotAmount)) {
            checkButtonStatus(true);

            long prevQty = getItemQuantity();

            wait.until(driver -> driver.findElement(btn_buy));
            this.driver.findElement(btn_buy).click();
            wait.until(driver -> driver.findElement(btn_buy_confirm));

//            this.driver.findElement(btn_buy_confirm).click();
//            wait.until(driver -> driver.findElement(buy_requested_modal));

//            long newQty = getItemQuantity();
//
//            Assert.assertEquals((prevQty-1), newQty);
        }

        if (!isItemAvailable()) {
            System.out.println("\nBuy item failed - Item is sold out");
        }

        if (!isCarrotEnough(carrotAmount)) {
            System.out.println("\nBuy item failed - You don't have enough carrots");
        }
    }
}
