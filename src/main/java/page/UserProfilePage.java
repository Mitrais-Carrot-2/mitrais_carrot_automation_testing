package page;

import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UserProfilePage {
    WebDriver driver;

    By btn_navbar_user = By.xpath("//div[@id='navbar-image']//a[@aria-expanded='false']");
    By btn_navbar_user_setting = By.cssSelector(".text-center:nth-child(1)");

    public UserProfilePage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToUserSetting() {
        WebElement navbarUser = this.driver.findElement(btn_navbar_user);
        navbarUser.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".text-center:nth-child(1)")));

        WebElement navbarSetting = this.driver.findElement(btn_navbar_user_setting);
        navbarSetting.click();
    }

    public void goToChangePassword() {
        WebElement changePassword = this.driver.findElement(By.xpath("//button[normalize-space()='Change Password']"));
        changePassword.click();
    }

    public void insertChangePasswordData(String oldPassword, String newPassword, String confirmPassword) {
        WebElement oldPasswordInput = this.driver.findElement(By.xpath("//input[@placeholder='Current Password']"));
        oldPasswordInput.sendKeys(oldPassword);

        WebElement newPasswordInput = this.driver.findElement(By.xpath("//input[@name='newPassword']"));
        newPasswordInput.sendKeys(newPassword);

        WebElement confirmPasswordInput = this.driver.findElement(By.xpath("//input[@name='newPassword2']"));
        confirmPasswordInput.sendKeys(confirmPassword);

        WebElement btnSubmit = this.driver.findElement(By.xpath("//button[@id='modal-save-button']"));
        btnSubmit.click();
    }

    public void goToUpdateProfile() {
        WebElement updateImage = this.driver.findElement(By.xpath("//button[normalize-space()='Update Profile']"));
        updateImage.click();
    }

    public void insertUpdateProfileData(String currentPassword, String firstName, String lastName, String address,
            String phone) {
        WebElement currentPasswordInput = this.driver.findElement(By.xpath("//input[@placeholder='Current Password']"));
        currentPasswordInput.sendKeys(currentPassword);

        WebElement firstNameInput = this.driver.findElement(By.xpath("//input[@name='firstName']"));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);

        WebElement lastNameInput = this.driver.findElement(By.xpath("//input[@name='lastName']"));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);

        WebElement addressInput = this.driver.findElement(By.xpath("//input[@name='address']"));
        addressInput.clear();
        addressInput.sendKeys(address);

        WebElement phoneInput = this.driver.findElement(By.xpath("//input[@name='Phone']"));
        phoneInput.clear();
        phoneInput.sendKeys(phone);

        WebElement btnSubmit = this.driver.findElement(By.xpath("//button[@id='modal-save-button']"));
        btnSubmit.click();
    }

    public void goToUpdateImage() {
        WebElement updateImage = this.driver.findElement(By.xpath("//button[normalize-space()='Update Image']"));
        updateImage.click();
    }

    public  void insertUpdateImageData(String imgPath){
        By fileInput = By.xpath("//input[@name='image-file']");
        WebElement inputImage = this.driver.findElement(fileInput);
        inputImage.sendKeys(imgPath);

        WebElement btnSubmit = this.driver.findElement(By.xpath("//button[@id='modal-save-button']"));
        btnSubmit.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.alertIsPresent());
        String msg = driver.switchTo().alert().getText();
        Alert alert = driver.switchTo().alert();
        alert.accept();
        assertMessage(msg, "Successfully updated");

    }

    public void assertMessage(String actualMsg, String expectedErr) {
        Assert.assertEquals(expectedErr, actualMsg);
    }
}
