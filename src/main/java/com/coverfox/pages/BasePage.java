package com.coverfox.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Actions actions;
    protected JavascriptExecutor js;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait  = new WebDriverWait(driver, Duration.ofSeconds(25)); // global wait
        this.actions = new Actions(driver);
        this.js = (JavascriptExecutor) driver;
    }

    protected WebElement waitClickable(By locator) {
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    protected WebElement waitVisible(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void scrollIntoView(WebElement el) {
        js.executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    protected void safeClickJS(WebElement el) {
        js.executeScript("arguments[0].click();", el);
    }


    protected void dismissBannersIfAny() {
        List<By> candidates = Arrays.asList(
                By.xpath("//button[contains(.,'Accept') or contains(.,'OK') or contains(.,'Got it')]"),
                By.xpath("//a[contains(.,'Accept') or contains(.,'OK') or contains(.,'Got it')]"),
                By.xpath("//div[contains(@class,'cookie') or contains(@class,'consent')]//button")
        );
        for (By c : candidates) {
            try {
                WebElement b = driver.findElement(c);
                if (b.isDisplayed()) {
                    scrollIntoView(b);
                    safeClickJS(b);

                }
            } catch (Exception ignored) {}
        }
    }

    protected void waitForNoOverlay() {
        List<By> overlays = Arrays.asList(
                By.cssSelector(".loader"),
                By.cssSelector(".loading"),
                By.cssSelector(".modal-backdrop"),
                By.cssSelector(".modal"),
                By.cssSelector(".backdrop"),
                By.cssSelector(".overlay"),
                By.cssSelector("[aria-busy='true']")
        );
        WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
        for (By ov : overlays) {
            try {
                shortWait.until(ExpectedConditions.invisibilityOfElementLocated(ov));
            } catch (Exception ignored) {}
        }
    }


    protected void click(By locator) {
        dismissBannersIfAny();
        waitForNoOverlay();

        WebElement el = waitClickable(locator);
        scrollIntoView(el);

        try {
            el.click();
            return;
        } catch (ElementClickInterceptedException e1) {

            try {
                actions.moveToElement(el).pause(Duration.ofMillis(150)).click().perform();
                return;
            } catch (Exception e2) {

                safeClickJS(el);
            }
        }
    }

    protected void type(By locator, String text) {
        waitVisible(locator).sendKeys(text);
    }
}