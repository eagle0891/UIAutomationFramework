package stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import utils.ConfigReader;

public class Hooks {
    public static WebDriver driver;

    @Before
    public void setUp(Scenario scenario) {
        // Only start the browser if the scenario is NOT an API test
        if (!scenario.getSourceTagNames().contains("@api")) {
            System.out.println("Starting Browser for UI Test...");
        //String browser = ConfigReader.getProperty("browser");
        // Check Command Line first, then config file
        String browser = System.getProperty("browser", ConfigReader.getProperty("browser"));

        // Check Command Line (-Dheadless) first; if null, check properties file
        String headlessCmd = System.getProperty("headless");
        boolean isHeadless = (headlessCmd != null) ?
                Boolean.parseBoolean(headlessCmd) :
                Boolean.parseBoolean(ConfigReader.getProperty("headless"));

        if (browser.equalsIgnoreCase("chrome")) {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            if (isHeadless) {
                options.addArguments("--headless=new");
                // --- ADD THESE FOR JENKINS STABILITY ---
                options.addArguments("--no-sandbox"); // Bypasses OS security model (required for Docker/Jenkins)
                options.addArguments("--disable-dev-shm-usage"); // Uses /tmp instead of memory for shared heap
                options.addArguments("--window-size=1920,1080"); // Sets a default "screen" size for screenshots
            }
            driver = new ChromeDriver(options);
        } else if (browser.equalsIgnoreCase("firefox")) {
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();

            if (isHeadless) {
                options.addArguments("-headless");
            }
            driver = new FirefoxDriver(options);
        }

        String env = System.getProperty("env", "QA"); // Default to TEST if null
        String url;

        switch (env.toUpperCase()) {
            case "STAGING":
                url = "https://www.saucedemo.com";
                break;
            case "PROD":
                url = "https://www.saucedemo.com";
                break;
            default:
                url = "https://www.saucedemo.com"; // Your dev/test environment
        }
        driver.get(url);
        } else {
            System.out.println("API Test detected - Skipping Browser Setup.");
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        // Only proceed if the driver was actually started (UI tests)
        if (driver != null) {
            try {
                if (scenario.isFailed()) {
                    // This is the line that was crashing during API failures
                    final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Failed_Step_Screenshot");
                }
            } catch (Exception e) {
                System.out.println("Could not take screenshot: " + e.getMessage());
            } finally {
                driver.quit();
                driver = null; // Reset to null to avoid side effects in next scenario
            }
        } else {
            System.out.println("No browser session to close for this scenario.");
        }
    }
}
