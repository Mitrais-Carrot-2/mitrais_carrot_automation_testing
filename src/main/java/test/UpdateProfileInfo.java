package test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.UserProfilePage;
import page.LoginPage;

import java.time.Duration;

public class UpdateProfileInfo {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static UserProfilePage userProfilePage;

    @BeforeClass
    public static void loginToUserProfilePage() {
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");

        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        userProfilePage = new UserProfilePage(driver);
    }

    @Before
    public void goToLoginPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        loginPage.login("manager", "manager"); // Change to username and password for farmer
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard-title")));

        userProfilePage.goToUserSetting();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container.mx-auto.my-5.break-words")));
    }

    @Test
    public void changePassword() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        userProfilePage.goToChangePassword();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(1)")));

        userProfilePage.insertChangePasswordData("manager","manager","manager");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container.mx-auto.my-5.break-words")));
    }

    @Test
    public void updateProfile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        userProfilePage.goToUpdateProfile();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(1)")));

        userProfilePage.insertUpdateProfileData("manager","firstName","lastName","Yogyakarta","081234567489");
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container.mx-auto.my-5.break-words")));
    }

    @Test
    public void changeImage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        userProfilePage.goToUpdateImage();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > div:nth-child(1) > div:nth-child(1) > div:nth-child(5) > div:nth-child(1)")));

        String imagePath = "D:\\repo\\mitrais_carrot_automation_testing\\src\\main\\java\\image\\avatar-profile.jpg";

        userProfilePage.insertUpdateImageData(imagePath);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".container.mx-auto.my-5.break-words")));
    }
    //After each test
    @After
    public void clearCache(){
        //Delete cookies to logout user
        driver.manage().deleteAllCookies();
    }

    //After all tests
    @AfterClass
    public static void closeBrowser(){
        //Terminate the WebDriver
        driver.quit();
    }

}
