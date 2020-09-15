package TestRozetka;

import org.junit.*;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestRozetka {
    private static ChromeDriver driver;
    private static Wait<WebDriver> wait;
    @BeforeClass
    public static void setUpTesting() {
        System.setProperty("webdriver.chrome.driver", "C:/WebDriver/chromedriver.exe/");
        TestRozetka.driver = new ChromeDriver();

        TestRozetka.wait = new FluentWait<WebDriver>(TestRozetka.driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);
    }

    @AfterClass
    public static void FinishTesting() {
        TestRozetka.driver.close();
    }


    void waitUntilPageIsLoadedAndGetTitle() {
         wait.until(webDriver -> {
            Object result = ((JavascriptExecutor) webDriver).executeScript("return document.readyState");
            return result.equals("complete");
        });
    }

    @Test
    public void test1RozetkaOpened() {
        TestRozetka.driver.get("https://rozetka.com.ua/");
        String rozetkaTitle = "Интернет-магазин ROZETKA™: официальный сайт самого популярного онлайн-гипермаркета в Украине";
        Assert.assertEquals(rozetkaTitle, TestRozetka.driver.getTitle());
    }

    @Test
    public void test2Iphone11Searched() {
        WebElement searchField = TestRozetka.driver.findElementByClassName("search-form__input");
        searchField.sendKeys("iphone 11 red");
        searchField.sendKeys(Keys.ENTER);

        String iphoneSearchTitle = "ROZETKA — Результаты поиска: \"iphone 11 red\" | Поиск";

        waitUntilPageIsLoadedAndGetTitle();

        Assert.assertEquals(iphoneSearchTitle, TestRozetka.driver.getTitle());
    }

    @Test
    public void test3IsPickIphone() {

        String iphone11RedLinkText = "Мобильный телефон Apple iPhone 11 128GB PRODUCT Red Официальная гарантия ";
        By linkToIphoneSelector = By.cssSelector("a[title='" + iphone11RedLinkText + "']");

        WebElement linkToIphone = TestRozetka.wait.until(ExpectedConditions.presenceOfElementLocated(linkToIphoneSelector));
        System.out.println(linkToIphone);
        linkToIphone.click();
        String iphoneTitle = "ROZETKA | Мобильный телефон Apple iPhone 11 128GB PRODUCT Red Официальная гарантия. Цена, купить Мобильный телефон Apple iPhone 11 128GB PRODUCT Red Официальная гарантия в Киеве, Харькове, Днепропетровске, Одессе, Запорожье, Львове. Мобильный телефон Apple iPhone 11 128GB PRODUCT Red Официальная гарантия: обзор, описание, продажа";

        TestRozetka.wait.until(ExpectedConditions.titleIs(iphoneTitle));

        Assert.assertEquals(iphoneTitle, TestRozetka.driver.getTitle());
    }

    @Test
    public void test4IsIphoneRed() {
        WebElement colorOfIphone = TestRozetka.driver.findElement(By.cssSelector(".var-options__color.var-options__block_state_active .var-options__color-preview"));

        String actualElementStyle = colorOfIphone.getAttribute("style");
        String expectedElementStyle = "background-color: rgb(255, 0, 0);";

        Assert.assertEquals(expectedElementStyle, actualElementStyle);
    }

    @Test
    public void test5AddIphoneToCart() {
        WebElement button = TestRozetka.driver.findElement(By.cssSelector(".buy-button.button.button_with_icon.button_color_green.button_size_large"));
        button.click();

    }

}
