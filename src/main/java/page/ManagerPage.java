package page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ManagerPage {
    WebDriver driver;
    DashboardPage dashboardPage;

    By carrot_given = By.id("carrot-given");
    By carrot_left = By.id("carrot-left");

    By tab_staff = By.id("tab_staff");
    By tab_group = By.id("tab_group");

    By btn_share_to_staff = By.id("btn_share_to_staff");

    By staff_id = By.cssSelector(".css-1s2u09g-control");
    By group_id = By.id("group_id");
    By total_member = By.id("total-member");
    By carrot_amount = By.id("carrot-amount");
    By note = By.id("note");

    By btn_send = By.id("btn_send_reward");

    By btn_detail_group = By.xpath("//div[@id='cell-8-1']//button[@id='btn_detail_group']");
    By btn_share_to_group = By.xpath("//div[@id='cell-8-1']//button[@id='btn_share_to_group']");

    By total_transaction = By.xpath("//span[@class='sc-bZkfAO sc-kLLXSd lmGpws OHwJB']");

    public ManagerPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void assertManagerPage() {
        String expectedURL = "http://localhost:3000/manager";
        Assert.assertEquals(this.driver.getCurrentUrl(),expectedURL);
    }

    public void assertFreezerSection() {
        Assert.assertTrue("Freezer Detail Empty! "+this.driver.findElement(this.carrot_left).getAttribute("value")+" Carrots", !this.driver.findElement(this.carrot_left).getAttribute("value").isEmpty());
    }

    public void staff() {
        driver.findElement(this.tab_staff).click();
        driver.findElement(this.btn_share_to_staff).click();

        driver.findElement(this.staff_id).click();
        Actions keyDown = new Actions(driver);
//        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(driver -> driver.findElement(By.className("css-26l3qy-menu")));

        Integer totalStaff = driver.findElements(By.cssSelector(".css-4ljt47-MenuList div")).size();

        Assert.assertTrue("List of Staff empty! "+totalStaff, totalStaff>0);
    }

    public void shareToStaff(Long carrotAmount, String note) {
        Long currentCarrotGiven = Long.parseLong(this.driver.findElement(this.carrot_given).getAttribute("value"));
        Long currentCarrotLeft = Long.parseLong(this.driver.findElement(this.carrot_left).getAttribute("value"));
        Integer totalHistory = Integer.valueOf(this.driver.findElement(this.total_transaction).getText().split(" of ")[1]);

        driver.findElement(this.tab_staff).click();
        driver.findElement(this.btn_share_to_staff).click();

        driver.findElement(this.staff_id).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("freezer-title")));

        Long expectedCarrotGiven = currentCarrotGiven + carrotAmount;
        Long expectedCarrotLeft = currentCarrotLeft - carrotAmount;

        this.assertCarrotAmount(expectedCarrotGiven, expectedCarrotLeft);
        Integer expectedHistory = totalHistory + 1;
        Integer currentHistory = Integer.valueOf(this.driver.findElement(this.total_transaction).getText().split(" of ")[1]);
        Assert.assertEquals("Transaction History not match!", expectedHistory, currentHistory);

        String expectedMessage = "Transfer to Staff Success!";
        String actualMessage = this.driver.findElement(By.id("success-label")).getText();
        Assert.assertEquals("Message not match!", expectedMessage, actualMessage);
    }

    public void shareToStaff_NotEnoughCarrot(Long carrotAmount, String note) {
        driver.findElement(this.tab_staff).click();
        driver.findElement(this.btn_share_to_staff).click();

        driver.findElement(this.staff_id).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("error-label")));

        String expectedMessage = "Not enough Carrot Left!";
        Assert.assertEquals("Message not match!", expectedMessage, this.driver.findElement(By.id("error-label")).getText());
    }

    public void shareToStaff_NoCarrot(Long carrotAmount, String note) {
        Long currentCarrotGiven = Long.parseLong(this.driver.findElement(this.carrot_given).getAttribute("value"));
        Long currentCarrotLeft = Long.parseLong(this.driver.findElement(this.carrot_left).getAttribute("value"));
        Integer totalHistory = Integer.valueOf(this.driver.findElement(this.total_transaction).getText().split(" of ")[1]);

        driver.findElement(this.tab_staff).click();
        driver.findElement(this.btn_share_to_staff).click();

        driver.findElement(this.staff_id).click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();
        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("error-label")));

        String expectedMessage = "Please fill in all the fields!";
        Assert.assertEquals("Message not match!", expectedMessage, this.driver.findElement(By.id("error-label")).getText());
    }

    public void shareToStaff_NoStaff(Long carrotAmount, String note) {
        driver.findElement(this.tab_staff).click();
        driver.findElement(this.btn_share_to_staff).click();

        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("error-label")));

        String expectedMessage = "Please fill in all the fields!";
        Assert.assertEquals("Message not match!", expectedMessage, this.driver.findElement(By.id("error-label")).getText());
    }

    public void shareToGroup(Long carrotAmount, String note) {
        Long currentCarrotGiven = Long.parseLong(this.driver.findElement(this.carrot_given).getAttribute("value"));
        Long currentCarrotLeft = Long.parseLong(this.driver.findElement(this.carrot_left).getAttribute("value"));

        driver.findElement(this.tab_group).click();
        driver.findElement(this.btn_share_to_group).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("total-member")));
        Integer totalMember = Integer.valueOf(driver.findElement(By.id("total-member")).getAttribute("value"));

        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        wait.until(driver -> driver.findElement(By.id("freezer-title")));

        Long expectedCarrotGiven = currentCarrotGiven + (carrotAmount * totalMember);
        Long expectedCarrotLeft = currentCarrotLeft - (carrotAmount * totalMember);

        this.assertCarrotAmount(expectedCarrotGiven, expectedCarrotLeft);

        String expectedMessage = "Transfer to Group Success!";
        String actualMessage = this.driver.findElement(By.id("success-label")).getText();
        Assert.assertEquals("Message not match!", expectedMessage, actualMessage);
    }

    public void shareToGroup_NoCarrot(Long carrotAmount, String note) {
        Long currentCarrotGiven = Long.parseLong(this.driver.findElement(this.carrot_given).getAttribute("value"));
        Long currentCarrotLeft = Long.parseLong(this.driver.findElement(this.carrot_left).getAttribute("value"));

        driver.findElement(this.tab_group).click();
        driver.findElement(this.btn_share_to_group).click();

        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("error-label")));

        String expectedMessage = "Please fill in all the fields!";
        Assert.assertEquals("Message not match!", expectedMessage, this.driver.findElement(By.id("error-label")).getText());
    }

    public void shareToGroup_NotEnoughCarrot(Long carrotAmount, String note) {
        driver.findElement(this.tab_group).click();
        driver.findElement(this.btn_share_to_group).click();

        driver.findElement(this.carrot_amount).sendKeys(carrotAmount.toString());
        driver.findElement(this.note).sendKeys(note);
        driver.findElement(this.btn_send).click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("error-label")));

        String expectedMessage = "Not enough Carrot Left!";
        Assert.assertEquals("Message not match!", expectedMessage, this.driver.findElement(By.id("error-label")).getText());
    }

    public void assertCarrotAmount(Long expectedGiven, Long expectedLeft) {
        Assert.assertEquals("Expected Carrot Given not match! ", expectedGiven.toString(), this.driver.findElement(this.carrot_given).getAttribute("value"));
        Assert.assertEquals("Expected Carrot Left not match!", expectedLeft.toString(), this.driver.findElement(this.carrot_left).getAttribute("value"));
    }

    public void assertTransactionHistory(){
        Integer totalHistory = Integer.valueOf(this.driver.findElement(this.total_transaction).getText().split(" of ")[1]);
        Assert.assertTrue("Transaction History Empty!", totalHistory>0);
    }

    public void assertGroup(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.findElement(this.tab_group).click();
        wait.until(driver -> driver.findElement(By.cssSelector("div[id='cell-5-1'] div[data-tag='allowRowEvents']")));

        Integer totalGroup = Integer.valueOf(driver.findElement(By.cssSelector("div[id='link2'] div[class='mx-auto'] div span[class='sc-bZkfAO sc-kLLXSd lmGpws OHwJB']")).getText().split(" of ")[1]);
        Assert.assertTrue("Group Empty!", totalGroup>0);
    }

    public void assertStaffGroup(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        driver.findElement(this.tab_group).click();
        wait.until(driver -> driver.findElement(By.cssSelector("div[id='cell-5-1'] div[data-tag='allowRowEvents']")));

        Integer totalMember = Integer.valueOf(this.driver.findElement(By.cssSelector("div[id='cell-5-1'] div[data-tag='allowRowEvents']")).getText());
        driver.findElement(By.xpath("//div[@id='cell-8-1']//button[@id='btn_detail_group']")).click();

        wait.until(driver -> driver.findElement(By.id("group-name-label")));
        String labelMember = driver.findElement(By.cssSelector("div[class='modal-body'] div span[class='sc-bZkfAO sc-kLLXSd lmGpws OHwJB']")).getAttribute("innerHTML").split(" of ")[1];
        Integer actualMember = Integer.valueOf(labelMember);
        Assert.assertEquals("Staff Group not match!", totalMember, actualMember);
//        Assert.assertTrue("Staff Group not match! "+labelMember, false);
    }
}
