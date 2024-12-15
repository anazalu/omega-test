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

public class UiTest {
    private static WebDriver driver = null;
    private WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        // options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.get("https://omega-vismatestingapp.azurewebsites.net/");
    }

    @Test
    public void testTitle() {
        String actualTitle = driver.getTitle();
        assertTrue(actualTitle.contains("ojumu port"), "Title mismatch");
    }

    @Test
    public void testCreateAndDeleteEvent() {
        WebElement veidotJaunuSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Veidot jaunu')]")));
        veidotJaunuSpan.click();
        WebElement virsrakstsLabel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//label[contains(text(), 'Virsraksts')]")));
        assertEquals(virsrakstsLabel.getText(), "Virsraksts:");
        WebElement titleInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("title")));
        titleInput.clear();
        String title = "Mans jaunais virsraksts";
        titleInput.sendKeys(title);
        WebElement textInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("text")));
        textInput.clear();
        textInput.sendKeys("Mans jaunais teksts");
        WebElement saglabatButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[data-pc-name='button']")));
        saglabatButton.click();
        WebElement sakumsSpan = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'kums')]")));
        sakumsSpan.click();
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
