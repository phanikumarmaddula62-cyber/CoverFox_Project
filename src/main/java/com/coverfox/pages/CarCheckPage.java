package com.coverfox.pages;

import com.coverfox.utils.ScreenshotUtil;
import org.openqa.selenium.*;

public class CarCheckPage extends BasePage {
    private final By car_xpath = By.xpath("//*[@id='content']/div/ul/li[2]");
    private final By boughtNewCarXpath = By.xpath("(//div/span[@class='dls-link'])[2]");
    private final By carVariant = By.xpath("(//div[@class='top-models__item '])[2]");
    private final By brandXpath = By.xpath("//div/img[@src='https://assets.coverfox.com/static/img/car-product/make-logos/ford.png']");
    private final By fuelType = By.xpath("(//div/ul/li[@class=' '])[1]");
    private final By variantType = By.xpath("//div[@class='variants-list']/div[1]");
    private final By carRegSearchBox = By.xpath("//input[@id='SearchBox']");
    private final By saveAndcontinuebtn = By.xpath("//button[@title='Save & Continue']");
    private final By enterMobileNoSearch = By.xpath("//input[@type='tel']");
    private final By ClickOkNoBtn = By.xpath("//button[@title='View Quotes']");
    private final By errorMsg = By.xpath("//div[@class='error-label ']");

    public CarCheckPage(WebDriver driver) { super(driver); }

    public String runFlowAndCaptureError() {
        driver.get("https://www.coverfox.com/");
        click(car_xpath);
        click(boughtNewCarXpath);
        click(brandXpath);
        click(carVariant);
        click(fuelType);
        click(variantType);
        type(carRegSearchBox, "TN-14  Chennai Sholinganallur");
        waitVisible(carRegSearchBox).sendKeys(Keys.ENTER);
        click(saveAndcontinuebtn);
        type(enterMobileNoSearch, "555555555");
        click(ClickOkNoBtn);
        WebElement err = waitVisible(errorMsg);
        String screenshot = ScreenshotUtil.capture(driver, "car-error");
        return err.getText();
    }
}
