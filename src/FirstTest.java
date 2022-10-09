import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.ScreenOrientation;
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
        capabilities.setCapability("orientation", "PORTRAIT");

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
    public void swipeArticleTitle() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Appium",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Automation for Apps']"),
                "Cannot find Search Wikipedia element",
                15
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        swipeUpToElement(By.xpath(
                        "//*[@text='View page in browser']"),
                "Cannot find the end of the article",
                20
        );

    }

    @Test
    public void saveFirstArticleToMyList() {

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
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                10
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find input to set name of article folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My List'",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "'",
                5
        );

        swipeElementToLeft(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find name of article folder"
        );

        waitForElementNotPresent(By.xpath("//*[@text='Java (programming language)']"),
                "Cannot delete saved article",
                5);
    }

    @Test
    public void amountOfNotEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );

        String search_line = "Linkin Park Discography";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );

        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";

        waitForElementPresent(
                By.xpath(search_result_locator),
                "Cannot find anything by request " + search_line,
                15
        );

        int amount_of_elements = getAmountOfElements(
                By.xpath(search_result_locator)
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_elements > 0
        );
    }

    @Test
    public void testAmountOfEmptySearch() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );

        String search_line = "zxcvasdfqwer";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );
        String search_result_locator = "//*[@resource-id='org.wikipedia:id/search_results_list']/*[@resource-id='org.wikipedia:id/page_list_item_container']";
        String empty_result_label = "//*[@text='No results found']";

        waitForElementPresent(
                By.xpath(empty_result_label),
                "Cannot find empty result label by the request " + search_line,
                15
        );
        AssertNotPresent(
                By.xpath(search_result_locator),
                "We've found some results by request " + search_line
        );
    }

    @Test
    public void testChangeOrientationOnSearchResults() {
        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );

        String search_line = "Java";

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                search_line,
                "Cannot find search input",
                5
        );


        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find 'Object-oriented programming language' topic searching by " + search_line,
                15
        );

        String title_before_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);
        driver.rotate(ScreenOrientation.LANDSCAPE);
        String title_after_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);
        Assert.assertEquals(
                "Article title changed after rotation",
                title_after_rotation,
                title_after_rotation
        );
        driver.rotate(ScreenOrientation.PORTRAIT);
        String title_after_second_rotation = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article",
                15);
        Assert.assertEquals(
                "Article title changed after rotation",
                title_after_rotation,
                title_after_second_rotation
        );
    }

    @Test
    public void testSearchArticleInBackground() {

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
                "Cannot find 'Search Wikipedia' input",
                15
        );

        driver.runAppInBackground(2);

        waitForElementPresent(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='Object-oriented programming language']"),
                "Cannot find article after returning from background",
                15
        );
    }


    /////////////////////HOMEWORK TO LESSON 3
    @Test
    public void homeworkEx2AssertTextInSearchField() {
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
    public void homeworkEx3ResetSearch() {
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
    public void homeworkEx4CheckSearchResultsContainsWord() {
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

    //////////////////////////////////////
/////////////////////HOMEWORK TO LESSON 4
    @Test
    public void homeworkEx5SaveTwoArticles() {
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
                "Cannot find topic article",
                15
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );
        waitForElementPresent(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.id("org.wikipedia:id/onboarding_button"),
                "Cannot find 'GOT IT' tip overlay",
                10
        );

        waitForElementAndClear(
                By.id("org.wikipedia:id/text_input"),
                "Cannot find input to set name of article folder",
                5
        );

        String name_of_folder = "Learning programming";

        waitForElementAndSendKeys(
                By.id("org.wikipedia:id/text_input"),
                name_of_folder,
                "Cannot find input to set name of article folder",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='OK']"),
                "Cannot press 'OK' button",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My List'",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "'",
                15
        );

        waitForElementPresent(
                By.xpath("//*[@text='Java (programming language)']"),
                "Cannot find saved article",
                5
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='Explore']"),
                "Cannot cannot find 'Explore' element",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[contains(@text,'Search Wikipedia')]"),
                "Cannot find Search Wikipedia element",
                5
        );

        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text,'Search…')]"),
                "Python",
                "Cannot find search input",
                5
        );
        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@text='General-purpose programming language']"),
                "Cannot find topic article",
                15
        );
        waitForElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title",
                15
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageView[@content-desc='More options']"),
                "Cannot find 'More options' button",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@text='Add to reading list']"),
                "Cannot find option to add article to reading list",
                5
        );

        waitForElementAndClick(
                By.xpath("//*[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find created folder '" + name_of_folder + "'",
                10
        );

        waitForElementAndClick(
                By.xpath("//android.widget.ImageButton[@content-desc='Navigate up']"),
                "Cannot close article, cannot find X link",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.FrameLayout[@content-desc='My lists']"),
                "Cannot find navigation button to 'My List'",
                5
        );


        waitForElementAndClick(
                By.xpath("//android.widget.TextView[@resource-id='org.wikipedia:id/item_title'][@text='" + name_of_folder + "']"),
                "Cannot find folder '" + name_of_folder + "'",
                5
        );

        waitForElementPresent(
                By.xpath("//*[@text='Python (programming language)']"),
                "Cannot find saved article",
                5
        );

        String last_article_in_list_xpath = "(//*[@resource-id='org.wikipedia:id/page_list_item_container']//*[@resource-id='org.wikipedia:id/page_list_item_title'])[last()]";

        swipeElementToLeft(
                By.xpath(last_article_in_list_xpath),
                "Cannot find name of article folder"
        );


        int amount_of_elements = getAmountOfElements(
                By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']")
        );

        Assert.assertTrue(
                "We found too few results!",
                amount_of_elements > 0
        );
        Assert.assertTrue(
                "We found too many results!",
                amount_of_elements > 1
        );

        String article_title_in_list = waitForElementAndGetAttribute(
                By.xpath(last_article_in_list_xpath),
                "text",
                "Cannot find title of article in list",
                5
        );

        waitForElementAndClick(
                By.xpath(last_article_in_list_xpath),
                "Cannot find article",
                5
        );

        String article_title_on_page = waitForElementAndGetAttribute(
                By.id("org.wikipedia:id/view_page_title_text"),
                "text",
                "Cannot find title of article on page",
                5
        );

        Assert.assertEquals(article_title_in_list, article_title_on_page);
    }

    @Test
    public void homeworkEx6AssertTitle() {
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
                "Cannot find topic article",
                15
        );
//        waitForElementPresent(
//                By.id("org.wikipedia:id/view_page_title_text"),
//                "Cannot find article title",
//                15
//        );
        AssertElementPresent(
                By.id("org.wikipedia:id/view_page_title_text"),
                "Cannot find article title"
        );
    }

    //////////////////////////////////////
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

    protected void swipeUp(int time0fSwipe) {
        TouchAction action = new TouchAction(driver);
        Dimension size = driver.manage().window().getSize();
        int x = size.width / 2;
        int start_y = (int) (size.height * 0.8);
        int end_y = (int) (size.height * 0.2);

        action.
                press(x, start_y).
                waitAction(time0fSwipe).
                moveTo(x, end_y).
                release().
                perform();
    }

    protected void swipeUpQuick() {
        swipeUp(200);
    }

    protected void swipeUpToElement(By by, String error_message, int max_swipes) {
        int already_swiped = 0;
        while (driver.findElements(by).size() == 0) {
            if (already_swiped > max_swipes) {
                waitForElementPresent(by, "Cannot find element by swiping up.\n" + error_message, 0);
                return;
            }
            swipeUpQuick();
            already_swiped++;
        }
    }

    protected void swipeElementToLeft(By by, String error_message) {
        WebElement element = waitForElementPresent(
                by,
                error_message,
                10);
        int left_x = element.getLocation().getX();
        int right_x = left_x + element.getSize().getWidth();
        int upper_y = element.getLocation().getY();
        int lower_y = upper_y + element.getSize().getHeight();
        int middle_y = (upper_y + lower_y) / 2;
        TouchAction action = new TouchAction(driver);
        action.
                press(right_x, middle_y)
                .waitAction(300)
                .moveTo(left_x, middle_y)
                .release()
                .perform();
    }

    private int getAmountOfElements(By by) {
        List elements = driver.findElements(by);
        return elements.size();
    }

    private void AssertNotPresent(By by, String error_message) {
        int amount_of_elements = getAmountOfElements(by);
        if (amount_of_elements > 0) {
            String default_message = "An element '" + by.toString() + "' supposed to be not present";
            throw new AssertionError(default_message + " " + error_message);
        }
    }

    private void AssertElementPresent(By by, String error_message) {
        WebElement element = driver.findElement(by);
        if (element == null) {
            throw new AssertionError(error_message);
        }
    }

    private String waitForElementAndGetAttribute(By by, String attribute, String error_message, long timeoutInseconds) {
        WebElement element = waitForElementPresent(by, error_message, timeoutInseconds);
        return element.getAttribute(attribute);
    }


}
