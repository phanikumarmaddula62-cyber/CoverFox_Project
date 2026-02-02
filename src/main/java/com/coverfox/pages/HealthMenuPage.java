package com.coverfox.pages;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

public class HealthMenuPage extends BasePage {
    private final By retail = By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]/span[1]");
    private final By healthMenu = By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]/span[2]/following-sibling::ul/li[3]");
    private final By items = By.xpath("//ul[@class='nav-items-left hidden-xs header-ver2']/li[1]//ul[1]/li[3]//ul/li/a");

    public HealthMenuPage(WebDriver driver) { super(driver); }

    public List<String> getHealthSubmenuItems() {
        driver.get("https://www.coverfox.com/");
        actions.moveToElement(waitVisible(retail)).perform();
        actions.moveToElement(waitVisible(healthMenu)).perform();
        List<WebElement> els = driver.findElements(items);
        List<String> texts = new ArrayList<>();
        for (WebElement e : els) {
            String t = e.getText().trim();
            if (!t.isEmpty()) texts.add(t);
        }
        return texts;
    }
}
