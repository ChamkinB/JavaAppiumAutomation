import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import jdk.nashorn.internal.runtime.Context;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.internal.runners.statements.Fail;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "AndroidTestDevice");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");
        capabilities.setCapability("app", "C:\\Users\\Xiero\\OneDrive\\Desktop\\JavaAppiumAutomation\\JavaAppiumAutomation\\apks\\org.wikipedia.apk");

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void firstTest() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by 'Java'",
                15
        );
        System.out.println("First test run");
    }

    @Test
    public void testCancelSearch() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClear(
                By.id("org.wikipedia:id/search_src_text"),
                "Cannot clear field",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );
        waitForElementNotPresent(
                By.id("org.wikipedia:id/search_close_btn"),
                "X is till present on the page",
                5
        );

    }

    @Test
    public void compareArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Java",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find Search Wikipedia element",
                15
        );
        WebElement title_element = waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );
        String article_title = title_element.getAttribute("text");

        Assert.assertEquals(
                "We see unexpected title! ",
                "Java (programming language)",
                article_title
        );
    }

    @Test
    public void homeworkTest1AssertTextInSearchField() {
        String expected_text = "Java";
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                expected_text,
                "Cannot find search input",
                5
        );
        assertElementHasText(
                By.id("org.wikipedia:id/search_src_text"),
                expected_text,
                "Cannot find text \"" + expected_text + "\""
        );
    }

    @Test
    public void homeworkTest2ResetSearch() {
        String search_text = "Java";
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find search input",
                5
        );

        waitForElementsAndCount(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Not enough articles in search results",
                15
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5
        );

        waitForElementNotPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),
                "Articles is still present on the page",
                5
        );

    }

    @Test
    public void homeworkTest3CheckSearchResultsContainsWord() {
        String search_text = "JAVA";
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container"),
                "Cannot find 'Search Wikipedia' element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_text,
                "Cannot find search input",
                5
        );

        checkElementAttributeContainsText(
                By.id("org.wikipedia:id/page_list_item_title"),
                "text",
                search_text
        );
    }


    private WebElement waitForElementPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    private WebElement waitForElementPresent(By by, String error_message) {
        return waitForElementPresent(by, error_message, 5);
    }

    private WebElement waitForElementAndClick(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.click();
        return element;
    }

    private List<WebElement> waitForElementsAndCount(By by, String error_message, long timeoutInSeconds) {
        List<WebElement> webElementList = new WebDriverWait(driver, timeoutInSeconds)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        long amount_of_element = webElementList.size();
        if (amount_of_element <= 1) {
            Assert.fail(error_message);
        }
        System.out.println(amount_of_element);
        return webElementList;
    }

    private void checkElementAttributeContainsText(By by, String attribute, String text) {
        List<WebElement> webElementList = new WebDriverWait(driver, 15)
                .until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
        for (WebElement element : webElementList) {
            String attribute_text = element.getAttribute(attribute);
            System.out.println(attribute_text);
            Assert.assertTrue(
                    "Elements attribute = '" + attribute + "' does not contains text = '" + text.toLowerCase() + "'",
                    attribute_text.toLowerCase().contains(text.toLowerCase())
            );

        }
    }


    private WebElement waitForElementAndSendKeys(By by, String value, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.sendKeys(value);
        return element;
    }


    private boolean waitForElementNotPresent(By by, String error_message, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
        wait.withMessage(error_message + "\n");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    private WebElement waitForElementAndClear(By by, String error_message, long timeoutInSeconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
        element.clear();
        return element;
    }

    private void assertElementHasText(By by, String expected_text, String error_message) {
        WebElement element = waitForElementPresent(by, "Can not find element", 15);
        String actual_text = element.getAttribute("text");
        Assert.assertEquals(error_message, expected_text, actual_text);
    }

}
