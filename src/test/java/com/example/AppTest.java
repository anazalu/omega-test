package com.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;

import dev.failsafe.internal.util.Assert;


public class AppTest {
    private static WebDriver driver = null;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://magento.softwaretestingboard.com/");
    }

    @Test
    public void testTitle() {
        String actualTitle = driver.getTitle();
        String expectedTitle = "Home Page";
        assertEquals(expectedTitle, actualTitle, "Title mismatch");
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }  
}
