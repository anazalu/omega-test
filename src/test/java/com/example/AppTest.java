package com.example;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

import dev.failsafe.internal.util.Assert;


public class AppTest {
    private static WebDriver driver = null;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("https://omega-vismatestingapp.azurewebsites.net/");
    }

    @Test
    public void testTitle() {
        String actualTitle = driver.getTitle();
        assertTrue(actualTitle.contains("ojumu port"), "Title mismatch");
    }

    private void hamburgerClick(String buttonText) {
        WebElement hamburgerSpan = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a.p-menubar-button")));
        hamburgerSpan.click();
        WebElement veidotJaunuSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), '" + buttonText + "')]")));
        veidotJaunuSpan.click();

    }

    @Test
    public void testCreateAndDeleteEvent() {
        hamburgerClick("Veidot");
        WebElement virsrakstsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), 'Virsraksts')]")));
        assertEquals(virsrakstsLabel.getText(), "Virsraksts:");
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
        titleInput.clear();
        String title = "Title1";
        titleInput.sendKeys(title);
        WebElement textInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text")));
        textInput.clear();
        textInput.sendKeys("Text1");
        WebElement saglabatButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-pc-name='button']")));
        saglabatButton.click();
        hamburgerClick("Sākums");
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
            "//div[@class='title' and text()='" + title + "']/following-sibling::div[@class='buttons']//button"
        )));
        deleteButton.click();
    }

    @AfterAll
    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }  
}
