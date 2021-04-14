package tests;

import com.codeborne.selenide.Configuration;
import config.DrvCon;
import io.qameta.allure.selenide.AllureSelenide;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentHelpers.*;

public class TestBase {

    static DrvCon driverConfig = ConfigFactory.create(DrvCon.class);

    @BeforeAll
    static void setup() {
        addListener("AllureSelenide", new AllureSelenide());
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);
        Configuration.browserCapabilities = capabilities;

        Configuration.browser = System.getProperty("web.browser", "chrome");

        String remoteDriver = System.getProperty("remote.web.driver");
        if (remoteDriver != null) {
            String user = driverConfig.remoteUser();
            String password = driverConfig.remotePassword();
            Configuration.remote = String.format(remoteDriver, user, password);

            System.out.println(user);
            System.out.println(password);
            System.out.println(remoteDriver);
            System.out.println(String.format(remoteDriver, user, password));
        }
    }

    @AfterEach
    void afterEach() {
        attachScreenshot("Last screenshot");
        attachPageSource();
        attachAsText("Browser console logs", getConsoleLogs());
        if (System.getProperty("video.storage") != null)
            attachVideo();
        closeWebDriver();
    }
}
