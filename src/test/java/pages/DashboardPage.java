package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class DashboardPage {
    private WebDriver driver;

    // 1. Locators (Private to prevent direct access)
    private By appLogo = By.className("app_logo");

    // 2. Constructor
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
    }

    // 3. Actions (Public methods)
    public boolean appLogoIsDisplayed() {
        boolean appLogoIsDisplayed = driver.findElement(appLogo).isDisplayed();
        return true;
    }

}
