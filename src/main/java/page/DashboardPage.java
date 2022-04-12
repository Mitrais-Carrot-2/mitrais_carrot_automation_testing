package page;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    WebDriver driver;

    By btn_to_manager = By.id("btn-manager");

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void assertInDashboard(){
        String expectedURL="http://localhost:3000/";
        Assert.assertEquals(this.driver.getCurrentUrl(),expectedURL);
    }

    public void toManagerPage(){
        this.driver.findElement(btn_to_manager).click();
    }
}
