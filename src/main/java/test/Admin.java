package test;

import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.AdminPage;
import page.UserProfilePage;
import page.LoginPage;

import java.time.Duration;

public class Admin {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static AdminPage adminPage;

    @BeforeClass
    public static void loginToUserProfilePage() {
        System.setProperty("webdriver.chrome.driver", "webdriver\\chromedriver.exe");

        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        adminPage = new AdminPage(driver);
    }

    @Before
    public void goToLoginPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        loginPage.login("admin", "admin"); // Change to username and password for farmer
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dashboard-title")));

        // userProfilePage.goToUserSetting();
        adminPage.goToAdminPage();
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".jsx-d0974244a599c8e3.features.py-3")));
    }

    @Test
    public void createNewUser() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        adminPage.goToCreateNewUser();
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.cssSelector("div[class='container mx-auto sm: px-4 search-box py-3']")));

        // randomized username function
        String random_number = String.valueOf(Math.random()).substring(2, 6);
        String random_username = "staff" + random_number;
        // randomized first name

        adminPage.insertNewUserData(
                random_username,
                random_username,
                "staff",
                random_number,
                random_username + "@mitrais.com",
                "08123456789",
                "Yogya",
                "04031997");
    }

//     After each test
    @After
    public void clearCache() {
        // Delete cookies to logout user
        driver.manage().deleteAllCookies();
    }

//     After all tests
    @AfterClass
    public static void closeBrowser() {
        // Terminate the WebDriver
        driver.quit();
    }

}
