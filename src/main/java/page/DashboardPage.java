package page;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    WebDriver driver;

    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    public void assertInDashboard(){
        String expectedURL="http://localhost:3000/";
        Assert.assertEquals(this.driver.getCurrentUrl(),expectedURL);
    }
}
