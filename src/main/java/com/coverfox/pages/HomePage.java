package com.coverfox.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage extends BasePage {
    private final String baseUrl = "https://www.coverfox.com/";
    private final By getStartedBtn = By.xpath("//button[contains(text(),'Get Started')]");
    private final By carMenu = By.xpath("//*[@id='content']/div/ul/li[2]");

    public HomePage(WebDriver driver) { super(driver); }

    public HomePage open() {
        driver.get(baseUrl);
        return this;
    }

    public void clickCarMenu() { click(carMenu); }
    public void clickGetStarted() { click(getStartedBtn); }
}
