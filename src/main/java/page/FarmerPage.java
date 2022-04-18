package page;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class FarmerPage {
    WebDriver driver;

    // Define locators in Barn Dashboard

    By dasboard_button_loc = By.xpath("//button[normalize-space()='Dashboard']");
    By distribution_button_loc = By.xpath("//button[normalize-space()='Distribution']");
    By barn_table_loc = By.xpath("//tbody/tr");

    // get Barn Name by Index
    By barnName_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[2]/div");
    }

    // Get Barn Start Periode by Index
    By barnStartPeriode_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[3]/div");
    }

    // Get Barn End Periode by Index
    By barnEndPeriode_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[4]/div");
    }

    // Get Barn Carrot Amount by Index
    By barnCarrotAmount_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[5]/div");
    }

    // Get Barn Distributed Carrot by Index
    By barnDistributedCarrot_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[6]/div");
    }

    // Get Barn Status by Index
    By barnStatus_loc(int index) {
        return By.xpath("//div["+(index+1)+"]/div/div[7]/div");
    }

    // Get Manage Button by Index
    public By manage_button_loc(int index) {
        return By.xpath("//div["+index+"]/div[8]/button[1]");
    }

    // Get History Button by Index
    public By history_button_loc(int index) {
        return By.xpath("//div["+index+"]/div[8]/button[2]");
    }

    // Get Create Barn Button
    public By createBarn_button_loc = By.xpath("//button[@id='btn-create-barn']");

    public FarmerPage(WebDriver driver) {
        this.driver = driver;
    }

    // Define Methods that are commonly used
    public void assertInFarmerPage() {
        // Assert URL is as expected
        Assert.assertEquals(this.driver.getCurrentUrl(), "http://localhost:3000/farmer");
    }

    public void assertInDistributionPageActive() {
        driver.findElement(By.xpath("//h4[normalize-space()='Distribution Details:']"));
    }

    public void assertInDistributionPageInactive() {
        driver.findElement(By.xpath("//h1[normalize-space()='No Active Barn']"));
    }

    public void clickDashboardButton() {
        this.driver.findElement(dasboard_button_loc).click();
    }

    public void clickDistributionButton() {
        this.driver.findElement(distribution_button_loc).click();
    }

    public void clickManageButton(int index) {
        this.driver.findElement(manage_button_loc(index)).click();
    }

    public void clickHistoryButton(int index) {
        this.driver.findElement(history_button_loc(index)).click();
    }

    public String getBarnName(int index) {
        return this.driver.findElement(barnName_loc(index)).getText();
    }

    public String getBarnStartPeriode(int index) {
        return this.driver.findElement(barnStartPeriode_loc(index)).getText();
    }

    public String getBarnEndPeriode(int index) {
        return this.driver.findElement(barnEndPeriode_loc(index)).getText();
    }

    public String getBarnCarrotAmount(int index) {
        return this.driver.findElement(barnCarrotAmount_loc(index)).getText();
    }

    public String getBarnDistributedCarrot(int index) {
        return this.driver.findElement(barnDistributedCarrot_loc(index)).getText();
    }

    public String getBarnStatus(int index) {
        return this.driver.findElement(barnStatus_loc(index)).getText();
    }

    public HashMap<String, String> getBarnInfo(int index) {
        HashMap<String, String> barnInfo = new HashMap<String, String>();
        barnInfo.put("Name", this.getBarnName(index));
        barnInfo.put("Start Periode", this.getBarnStartPeriode(index));
        barnInfo.put("End Periode", this.getBarnEndPeriode(index));
        barnInfo.put("Carrot Amount", this.getBarnCarrotAmount(index));
        // barnInfo.put("Distributed Carrot",this.getBarnDistributedCarrot(index));
        // barnInfo.put("Status",this.getBarnStatus(index));
        return barnInfo;
    }

    public HashMap<String, String> getBarnInfoWithStatus(int index) {
        HashMap<String, String> barnInfo = new HashMap<String, String>();
        barnInfo.put("Name", this.getBarnName(index));
        barnInfo.put("Start Periode", this.getBarnStartPeriode(index));
        barnInfo.put("End Periode", this.getBarnEndPeriode(index));
        barnInfo.put("Carrot Amount", this.getBarnCarrotAmount(index));
        // barnInfo.put("Distributed Carrot",this.getBarnDistributedCarrot(index));
        barnInfo.put("Status", this.getBarnStatus(index));
        return barnInfo;
    }

    public HashMap<String, String> getBarnInfoByManageButton() {
        HashMap<String, String> barnInfo = new HashMap<String, String>();
        barnInfo.put("Name", (this.driver.findElement(By.name("barnName")).getAttribute("value")));
        barnInfo.put("Start Periode", (this.driver.findElement(By.name("startPeriode")).getAttribute("value")));
        barnInfo.put("End Periode", (this.driver.findElement(By.name("endPeriode")).getAttribute("value")));
        barnInfo.put("Carrot Amount", (this.driver.findElement(By.name("carrotAmount")).getAttribute("value")));
        return barnInfo;
    }

    public int getLastBarnIndex() {
        return this.driver.findElements(barn_table_loc).size();
    }

    public void clickCreateBarnButton() {
        WebElement btnCreate = driver.findElement(createBarn_button_loc);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", btnCreate);
        // this.driver.findElement(createBarn_button_loc).click();
    }

    public void waitForAlert() throws InterruptedException {
        int i = 0;
        while (i++ < 5) {
            try {
                Alert alert = driver.switchTo().alert();
                break;
            } catch (NoAlertPresentException e) {
                Thread.sleep(1000);
                continue;
            }
        }
    }

    public void fillCreateBarnForm(String barnName, String year, String yearPlusOne, String carrotAmount)
            throws InterruptedException {
        this.driver.findElement(By.name("barnName")).sendKeys(barnName);
        this.driver.findElement(By.name("startPeriode")).sendKeys("\t\t" + year);
        this.driver.findElement(By.name("endPeriode")).sendKeys("\t\t" + yearPlusOne);
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.xpath("//button[@type='button'][normalize-space()='Create Barn']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn created successfully");
        driver.switchTo().alert().accept();
    }

    public void fillCreateBarnFormSame(String barnName, String year, String yearPlusOne, String carrotAmount)
            throws InterruptedException {
        this.driver.findElement(By.name("barnName")).sendKeys(barnName);
        this.driver.findElement(By.name("startPeriode")).sendKeys("\t\t" + year);
        this.driver.findElement(By.name("endPeriode")).sendKeys("\t\t" + yearPlusOne);
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.xpath("//button[@type='button'][normalize-space()='Create Barn']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn creation failed");
        driver.switchTo().alert().accept();
    }

    public void changeDateOnly(String startDate, String endDate) throws InterruptedException {
        String[] startDateSplit = startDate.split("-");
        this.driver.findElement(By.name("startPeriode"))
                .sendKeys(startDateSplit[0] + startDateSplit[1] + "\t" + startDateSplit[2]);
        String[] endDateSplit = endDate.split("-");
        this.driver.findElement(By.name("endPeriode"))
                .sendKeys(endDateSplit[0] + endDateSplit[1] + "\t" + endDateSplit[2]);
        this.driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn updated");
        driver.switchTo().alert().accept();
        Thread.sleep(1000);
        this.driver.findElement(By.cssSelector(".text-red-600")).click();
    }

    public void fillEditBarnForm(String barnName, String endYear, String carrotAmount) throws InterruptedException {
        this.driver.findElement(By.name("barnName")).clear();
        this.driver.findElement(By.name("barnName")).sendKeys(barnName);
        // this.driver.findElement(By.name("endPeriode")).clear();
        String[] endYearSplit = endYear.split("-");
        this.driver.findElement(By.name("endPeriode")).sendKeys("\t" + "\t" + endYearSplit[2]);
        this.driver.findElement(By.name("carrotAmount")).clear();
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.xpath("//button[normalize-space()='Save']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn updated");
        driver.switchTo().alert().accept();
        this.driver.findElement(By.xpath("//button[normalize-space()='Close']")).click();
    }

    public int getLastRewardIndex() {
        return this.driver.findElements(By.xpath("//table[@name='rewardTable']//tr")).size() - 1;
    }

    public void fillBarnReward(String rewardName, String carrotAmount, String rewardType) throws InterruptedException {
        this.driver.findElement(By.name("rewardDescription")).sendKeys(rewardName);
        this.driver.findElement(By.xpath("(//input[@name='carrotAmount'])[2]")).sendKeys(carrotAmount);
        this.driver.findElement(By.name("givingConditional")).sendKeys(rewardType);
        this.driver.findElement(By.cssSelector(".btn-primary")).click();

        int lastIndex = this.getLastRewardIndex();
        String rewardNameActual = this.driver
                .findElement(By.xpath("//table[@name='rewardTable']//tr[" + lastIndex + "]/td[2]/input"))
                .getAttribute("value");
        String rewardCarrotAmountActual = this.driver
                .findElement(By.xpath("//table[@name='rewardTable']//tr[" + lastIndex + "]/td[3]/input"))
                .getAttribute("value");
        String rewardTypeActual = this.driver
                .findElement(By.xpath("//table[@name='rewardTable']//tr[" + lastIndex + "]/td[4]/input"))
                .getAttribute("value");

        assertEquals(rewardName, rewardNameActual);
        assertEquals(carrotAmount, rewardCarrotAmountActual);
        assertEquals(rewardType, rewardTypeActual);

    }

    public void fillEmptyBarnReward() throws InterruptedException {
        this.driver.findElement(By.name("rewardDescription")).sendKeys("");
        this.driver.findElement(By.xpath("(//input[@name='carrotAmount'])[2]")).sendKeys("");
        this.driver.findElement(By.name("givingConditional")).sendKeys("");
        this.driver.findElement(By.cssSelector(".btn-primary")).click();

        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Reward creation failed");
        driver.switchTo().alert().accept();

    }

    public String getHistoryName() {
        return this.driver.findElement(By.cssSelector(".text-3xl:nth-child(1)")).getText();
    }

    public String transferToManager(String carrotAmount, String message) throws InterruptedException {
        this.driver.findElement(By.xpath("//div[2]/button")).click();
        this.driver.findElement(By.cssSelector(".css-6j8wv5-Input")).click();
        this.driver.findElement(By.id("react-select-2-option-0")).click();
        String receiver = this.driver.findElement(By.name("manager-name")).getAttribute("value");
        this.driver.findElement(By.name("carrotAmount")).clear();
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.name("message")).sendKeys(message);
        this.driver.findElement(By.cssSelector(".bg-green-600")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Successfully distributed "+ carrotAmount +" carrot to " + receiver);
        driver.switchTo().alert().accept();
        this.driver.findElement(By.xpath("//button[normalize-space()='Close']")).click();
        return receiver;
    }

    public int getLastTransaction() {
        return this.driver.findElements(By.xpath("//table[@class='jsx-a88a12f75b797cab']//tr")).size();
    }

    public String[] getTransactionDetails(int index) {
        String[] transactionDetails = new String[3];
        transactionDetails[0] = this.driver
                .findElement(By.cssSelector("tbody tr:nth-child(" + index + ") td:nth-child(2)")).getText();
        transactionDetails[1] = this.driver
                .findElement(By.cssSelector("tbody tr:nth-child(" + index + ") td:nth-child(3)")).getText();
        transactionDetails[2] = this.driver
                .findElement(By.cssSelector("tbody tr:nth-child(" + index + ") td:nth-child(5)")).getText();
        return transactionDetails;
    }

    public int searchForActiveBarn() {
        int lastBarn = this.getLastBarnIndex();
        int activeBarn = 0;
        for (int i = 1; i <= lastBarn; i++) {
            String barnStatus = this.getBarnStatus(i);
            if (barnStatus.equals("Active")) {
                activeBarn = i;
                return activeBarn;
            }
        }
        return activeBarn;
    }

    public void setToInactiveBarn(int index) throws InterruptedException {
        this.clickManageButton(index);
        LocalDate now = LocalDate.now();
        this.changeDateOnly(now.minusYears(2).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")),
                now.minusYears(1).format(DateTimeFormatter.ofPattern("dd-MMM-yyyy")));

    }

    public void setToActiveBarn(int index) throws InterruptedException {
        this.clickManageButton(index);
        LocalDate now = LocalDate.now();
        this.changeDateOnly(now.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                now.plusYears(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
}
