package page;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Date;

public class AdminPage {
    WebDriver driver;
    // //button[@id='btn-administrator']
    By btn_admin_in_dashboard = By.xpath("//button[@id='btn-administrator']");

    public AdminPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToAdminPage() {
        WebElement admin_in_dashboard = driver.findElement(this.btn_admin_in_dashboard);
        admin_in_dashboard.click();
    }

    public void goToCreateNewUser() {
        WebElement create_new_user = driver.findElement(By.xpath("//button[normalize-space()='Create New User']"));
        create_new_user.click();
    }

    public void assertMessage(String actualMsg, String expectedErr) {
        Assert.assertEquals(expectedErr, actualMsg);
    }

    public void insertNewUserData(
            String username,
            String password,
            String firstName,
            String lastName,
            String email,
            String phone,
            String address,
            // String gender,
            String birthDate
    // int supervisorId,
    // String jobFamily,
    // String jobGrade,
    // String office,
    // String[] role
    ) {
        WebElement username_input = driver.findElement(By.cssSelector("input[placeholder='Username']"));
        username_input.sendKeys(username);

        WebElement password_input = driver.findElement(By.cssSelector("input[placeholder='Password']"));
        password_input.sendKeys(password);

        WebElement firstName_input = driver.findElement(By.cssSelector("input[placeholder='First name']"));
        firstName_input.sendKeys(firstName);

        WebElement lastName_input = driver.findElement(By.cssSelector("input[placeholder='Last name']"));
        lastName_input.sendKeys(lastName);

        WebElement email_input = driver.findElement(By.cssSelector("input[placeholder='Email']"));
        email_input.sendKeys(email);

        WebElement phone_input = driver.findElement(By.cssSelector("input[placeholder='Phone']"));
        phone_input.sendKeys(phone);

        WebElement address_input = driver.findElement(By.cssSelector("input[placeholder='Address']"));
        address_input.sendKeys(address);

        WebElement birthDate_input = driver.findElement(By.cssSelector("input[name='birthDate']"));
        birthDate_input.sendKeys(birthDate);

        WebElement gender_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(12) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        gender_input.click();
        Actions keyDown = new Actions(driver);
        keyDown.sendKeys(Keys.chord(Keys.ENTER)).perform();

        WebElement supervisorId_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(20) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        supervisorId_input.click();
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        WebElement jobFamily_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(22) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        jobFamily_input.click();
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        WebElement jobGrade_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(24) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        jobGrade_input.click();
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        WebElement office_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(26) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        office_input.click();
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.ENTER)).perform();

        WebElement role_input = driver.findElement(By.cssSelector(
                "body > div:nth-child(2) > section:nth-child(1) > div:nth-child(1) > div:nth-child(2) > div:nth-child(28) > div:nth-child(3) > div:nth-child(1) > div:nth-child(2)"));
        role_input.click();
        keyDown.sendKeys(Keys.chord(Keys.DOWN, Keys.DOWN, Keys.DOWN, Keys.ENTER)).perform();

        WebElement btnSubmit = driver.findElement(By.cssSelector("button[type='submit']"));
        btnSubmit.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        assertMessage(msg, "Successfully updated");
    }
}
