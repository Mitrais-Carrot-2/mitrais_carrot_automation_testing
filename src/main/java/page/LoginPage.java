package page;



import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
    WebDriver driver;

    //Define locators in Login Page
//    By username_loc=By.xpath("//input[@placeholder='Username']");
//    By password_loc=By.xpath("//input[@placeholder='Password']");
    By username_loc=By.id("username");
    By password_loc=By.id("password");
    By loginButton_loc=By.id("login-button");
    By errorMsg_loc=By.id("errorMsg");


    public LoginPage(WebDriver driver){
        this.driver=driver;
    }

    //Login method, adding webdriver as parameter so that other classes can reuse the method
    public void login(String username, String password){
        driver.get("http://localhost:3000/sign-in");

        //Get Username and password input field
        WebElement username_elm=this.driver.findElement(this.username_loc);
        WebElement password_elm=this.driver.findElement(this.password_loc);
        
        //Type the username and password into the field
        username_elm.sendKeys(username);
        password_elm.sendKeys(password);

        //Find the login button
        WebElement loginButton=this.driver.findElement(By.id("login-button"));

        //Click the login button
        loginButton.click();
    }

    // Method to check if the Login error message matches the Expected Error Message
    public void assertErrorMessage(String expectedErrorMsg){
        Assert.assertEquals(driver.findElement(this.errorMsg_loc).getText(),expectedErrorMsg);
    }
    
}
