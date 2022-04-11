package page;

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

    // get Barn Name by Index
    By barnName_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[2]");
    }

    // Get Barn Start Periode by Index
    By barnStartPeriode_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[3]");
    }

    // Get Barn End Periode by Index
    By barnEndPeriode_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[4]");
    }

    // Get Barn Carrot Amount by Index
    By barnCarrotAmount_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[5]");
    }

    // Get Barn Distributed Carrot by Index
    By barnDistributedCarrot_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[6]");
    }

    // Get Barn Status by Index
    By barnStatus_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[7]");
    }

    // Get Manage Button by Index
    public By manage_button_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[8]/button[1]");
    }

    // Get History Button by Index
    public By history_button_loc(int index) {
        return By.xpath("//table[@id='list-of-barns']/tbody[" + index + "]/tr/td[8]/button[2]");
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

    public HashMap<String, String> getBarnInfoByManageButton() {
        HashMap<String, String> barnInfo = new HashMap<String, String>();
        barnInfo.put("Name", (this.driver.findElement(By.name("barnName")).getAttribute("value")));
        barnInfo.put("Start Periode", (this.driver.findElement(By.name("startPeriode")).getAttribute("value")));
        barnInfo.put("End Periode", (this.driver.findElement(By.name("endPeriode")).getAttribute("value")));
        barnInfo.put("Carrot Amount", (this.driver.findElement(By.name("carrotAmount")).getAttribute("value")));
        return barnInfo;
    }

    public int getLastTableIndex() {
        return this.driver.findElements(By.xpath("//tbody/tr")).size();
    }

    public void clickCreateBarnButton() {
        WebElement btnCreate = driver.findElement(createBarn_button_loc);
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click();", btnCreate);
        // this.driver.findElement(createBarn_button_loc).click();
    }

    public void waitForAlert() throws InterruptedException
    {
       int i=0;
       while(i++<5)
       {
            try
            {
                Alert alert = driver.switchTo().alert();
                break;
            }
            catch(NoAlertPresentException e)
            {
              Thread.sleep(1000);
              continue;
            }
       }
    }

    public void fillCreateBarnForm(String barnName, String year, String yearPlusOne,  String carrotAmount) throws InterruptedException {
        this.driver.findElement(By.name("barnName")).sendKeys(barnName);
        this.driver.findElement(By.name("startPeriode")).sendKeys("\t\t"+year);
        this.driver.findElement(By.name("endPeriode")).sendKeys("\t\t"+yearPlusOne);
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.xpath("//button[@type='button'][normalize-space()='Create Barn']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn created successfully");
        driver.switchTo().alert().accept();
    }

    public void fillCreateBarnFormSame(String barnName, String year, String yearPlusOne,  String carrotAmount) throws InterruptedException {
        this.driver.findElement(By.name("barnName")).sendKeys(barnName);
        this.driver.findElement(By.name("startPeriode")).sendKeys("\t\t"+year);
        this.driver.findElement(By.name("endPeriode")).sendKeys("\t\t"+yearPlusOne);
        this.driver.findElement(By.name("carrotAmount")).sendKeys(carrotAmount);
        this.driver.findElement(By.xpath("//button[@type='button'][normalize-space()='Create Barn']")).click();
        waitForAlert();
        assertEquals(driver.switchTo().alert().getText(), "Barn creation failed");
        driver.switchTo().alert().accept();
    }

}
