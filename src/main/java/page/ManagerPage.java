package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

public class ManagerPage {
    WebDriver driver;
    DashboardPage dashboardPage;

    By carrot_given = By.id("carrot-given");
    By carrot_left = By.id("carrot-left");

    By tab_staff = By.id("tab_staff");
    By tab_group = By.id("tab_group");

    By btn_share_to_staff = By.id("btn_share_to_staff");

    By staff_id = By.xpath("//input[@name='staff-id']");
    By group_id = By.id("group_id");
    By total_member = By.id("total-member");
    By carrot_amount = By.id("carrot-amount");
    By note = By.id("note");

    By btn_send = By.id("btn_send_reward");

    By btn_detail_group = By.xpath("//div[@id='cell-8-1']//button[@id='btn_detail_group']");
    By btn_share_to_group = By.xpath("//div[@id='cell-8-1']//button[@id='btn_share_to_group']");

    public ManagerPage(WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void shareToStaff(){
        driver.get("http://localhost:3000/manager");
        driver.findElement(tab_staff).click();
        driver.findElement(btn_share_to_staff).click();

        driver.findElement(staff_id).sendKeys("8");
        driver.findElement(carrot_amount).sendKeys("10");
        driver.findElement(note).sendKeys("Automation Test");
        driver.findElement(btn_send).click();
    }
}
