package test;

import java.time.Duration;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import page.DashboardPage;
import page.HomePage;
import page.LoginPage;

public class Login {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static HomePage homePage;
    private static DashboardPage dashboardPage;

    //Before All Tests
    @BeforeClass
    public static void beforeLogin(){
//        System.setProperty("webdriver.chrome.driver", "\"D:\\Automation Testing\\contohselenium\\lib\\chromedriver.exe\"");
        driver = new ChromeDriver();
        loginPage = new LoginPage(driver);
        homePage = new HomePage(driver);
        dashboardPage = new DashboardPage(driver);
    }

    //BEfore each test
    // @Before
    // public void beforeLogin(){
    //     //do something
    // }

    


    // There are positive test and negative test suites in this code:
    // 1. Successfully login: standard_login(), performance_glitch_login()
    // 2. Failed to login: locked_out_user_login(), no_username_login(), 
    //      no_password_login(), not_registered_login()
    @Test
    public void standard_login(){
        //Login with valid credentials
//        loginPage.login ("Naufal","string");
        loginPage.login ("manager","manager");
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(driver -> driver.findElement(By.id("dashboard-title")));
        //Assert if user can see Home page
//        homePage.assertInHomePage();
        dashboardPage.assertInDashboard();
    }


    @Test
    public void no_username_login(){
        //Login with empty username
        loginPage.login ("","secret_sauce");
        //Assert if error message correct
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bg-red-500 text-center text-white my-3 rounded animate-pulse']")) );
        String expectedErrorMsg="Username is required";
        loginPage.assertErrorMessage(expectedErrorMsg);
    }

    @Test
    public void no_password_login(){
        //Login with empty password
        loginPage.login ("locked_out_user","");
        //Assert if error message correct
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bg-red-500 text-center text-white my-3 rounded animate-pulse']")) );
        String expectedErrorMsg="Password is required";
        loginPage.assertErrorMessage(expectedErrorMsg);
    }

    @Test
    public void not_registered_login(){
        //Login with unregistered credentials
        loginPage.login ("username","password");
        //Assert if error message correct
        WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='bg-red-500 text-center text-white my-3 rounded animate-pulse']")) );
        String expectedErrorMsg="Username / Password is incorrect";
        loginPage.assertErrorMessage(expectedErrorMsg);
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