package com.coverfox.pages;

import com.coverfox.utils.ScreenshotUtil;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class HealthSortPage extends BasePage {
    private final By getStarted = By.xpath("//button[contains(text(),'Get Started')]");
    private final By wife = By.xpath("//div[@class='ms-title' and text()='Wife']");
    private final By nextBtn = By.xpath("//div[@class='next-btn']");
    private final By youAge = By.xpath("//select[@id='Age-You']");
    private final By wifeAge = By.xpath("//select[@id='Age-Spouse']");
    private final By pincode = By.xpath("//input[@placeholder='6 Digit Pincode']");
    private final By mobile = By.xpath("//input[@placeholder='Your mobile number']");
    private final By continueBtn = By.xpath("//div[contains(text(),'Continue')]");


    private final By planCard = By.className("plan-card-container");


    private final By[] sumHeaderCandidates = new By[] {
            By.xpath("//div[contains(@class,'sumA') or contains(@class,'sum-assured')]//div[contains(@class,'fc-filter')]"),
            By.xpath("//div[contains(@class,'fc-filter')][.//span[contains(.,'Sum Insured') or contains(.,'Sum Assured') or contains(.,'Cover')]]"),
            By.xpath("//div[contains(@class,'fc-filter')]")
    };


    private final By anyBand = By.xpath("//div[contains(@class,'sumA') or contains(@class,'sum-assured')]//div[contains(@class,'radio-ui')]");
    private final By exactBand_1to3 = By.xpath("//div[contains(@class,'sumA') or contains(@class,'sum-assured')]//div[contains(@class,'radio-ui')][.//span[normalize-space()='1L to 3L']]");

    private final By looseBand_1to3 = By.xpath("//div[contains(@class,'sumA') or contains(@class,'sum-assured')]//div[contains(@class,'radio-ui')][.//span[contains(.,'1L')]][.//span[contains(.,'3L')]]");

    private final By sortSelect = By.xpath("//select");

    public HealthSortPage(WebDriver driver) { super(driver); }

    public List<String[]> getTopThreePoliciesLowPremium() {
        driver.get("https://www.coverfox.com/");


        click(getStarted);
        click(wife);
        click(nextBtn);

        new Select(waitVisible(youAge)).selectByVisibleText(" 22 years ");
        new Select(waitVisible(wifeAge)).selectByVisibleText(" 21 years ");
        click(nextBtn);

        waitVisible(pincode).sendKeys("600001");
        String firstDigit = String.valueOf(6 + (int) (Math.random() * 4));
        long remainingNine = (long) (Math.random() * 1_000_000_000L);
        String randomReach = firstDigit + String.format("%09d", remainingNine);
        waitVisible(mobile).sendKeys(randomReach);

        WebElement cont = waitVisible(continueBtn);
        scrollIntoView(cont);
        cont.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(planCard));


        boolean opened = openSumInsuredFilter();
        if (!opened) {

            ScreenshotUtil.capture(driver, "sum-filter-not-found");
        }


        selectBandOrFallback();


        new Select(waitVisible(sortSelect)).selectByVisibleText(" Premium (low-high) ");


        List<WebElement> cards = driver.findElements(planCard);
        List<String[]> out = new ArrayList<>();
        for (WebElement c : cards) {
            if (out.size() >= 3) break;
            try {
                String insurer = c.findElement(By.xpath(".//img[@class='logo']")).getAttribute("alt");
                String price = c.findElement(By.className("rupee-val")).getText();
                String premium = c.findElement(By.xpath(".//span[contains(@class, 'pcc-premium-bold')]//div[@class='rupee-val']")).getText();
                out.add(new String[]{insurer, price, premium});
            } catch (Exception ignored) {}
        }
        return out;
    }

    private boolean openSumInsuredFilter() {

        for (By header : sumHeaderCandidates) {
            try {
                WebElement el = waitVisible(header);
                scrollIntoView(el);
                safeClickJS(el);
                if (!driver.findElements(anyBand).isEmpty()) {
                    return true;
                }
            } catch (Exception ignored) {}
        }

        for (int i = 0; i < 3; i++) {
            js.executeScript("window.scrollBy(0, 400);");
        }
        for (By header : sumHeaderCandidates) {
            try {
                WebElement el = driver.findElement(header);
                scrollIntoView(el);
                safeClickJS(el);
                if (!driver.findElements(anyBand).isEmpty()) {
                    return true;
                }
            } catch (Exception ignored) {}
        }
        return false;
    }

    private void selectBandOrFallback() {

        try {
            WebElement exact = driver.findElement(exactBand_1to3);
            scrollIntoView(exact);
            safeClickJS(exact);
            return;
        } catch (NoSuchElementException ignored) {}

        try {
            WebElement loose = driver.findElement(looseBand_1to3);
            scrollIntoView(loose);
            safeClickJS(loose);
            return;
        } catch (NoSuchElementException ignored) {}

        List<WebElement> bands = driver.findElements(anyBand);
        if (!bands.isEmpty()) {
            WebElement first = bands.get(0);
            scrollIntoView(first);
            safeClickJS(first);
        } else {

            ScreenshotUtil.capture(driver, "sum-band-not-found");
        }
    }
}