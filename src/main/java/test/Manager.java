package test;

import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import page.DashboardPage;
import page.LoginPage;
import page.ManagerPage;

import java.time.Duration;

public class Manager {
    static WebDriver driver;
    static ManagerPage managerPage;
    static LoginPage loginPage;

    @BeforeClass
    public static void setUp(){
        loginPage.login ("manager","manager");
    }

    @Test
    public void shareToStaff(){
        managerPage.shareToStaff();
    }
}