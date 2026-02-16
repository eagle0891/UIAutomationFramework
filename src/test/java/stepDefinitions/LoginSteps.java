package stepDefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.DashboardPage;
import pages.LoginPage;

public class LoginSteps {
    WebDriver driver;

    // Inject the driver from Hooks
    LoginPage login = new LoginPage(Hooks.driver);
    DashboardPage dashboard = new DashboardPage(Hooks.driver);

    @Given("the user is on the login page")
    public void user_on_login_page() {
        Hooks.driver.get("https://www.saucedemo.com/");
    }

    @When("the user enters {string} and {string}")
    public void user_enters_credentials(String user, String pass) {
        login.enterUsername(user);
        login.enterPassword(pass);
    }

    @And("clicks the login button")
    public void click_login() {
        login.clickLogin();
    }

    @Then("the user should be redirected to the dashboard")
    public void verify_dashboard() {
        //boolean isDisplayed = driver.findElement(By.className("app_logo")).isDisplayed();
        Assert.assertTrue(dashboard.appLogoIsDisplayed());
        //driver.quit();
    }
}
