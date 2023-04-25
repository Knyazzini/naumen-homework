import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

public class CardTests {

    static WebDriver driver;
    static String OZON_URL = "https://www.dns-shop.ru/";

    @BeforeAll
    public static void setupClass() {
        WebDriverManager.chromedriver().browserVersion("112").setup();
        driver = new ChromeDriver();
        driver.manage().window().setSize(new Dimension(1920, 1280));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    @Test
    void AddToCart() throws InterruptedException {
        driver.get(OZON_URL);

        driver.findElement(By.xpath("//*[@id=\"catalog\"]/div[1]/div[15]/a")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div/div[3]/div[2]/div[1]/div/div[2]/div[1]/a")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[2]/div[8]/div[1]/button")).click();
        Thread.sleep(1000);
        driver.get(OZON_URL + "cart");

        Thread.sleep(2000);

        WebElement itemsInCart = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div/div[1]"));
        String countItemsInCart = itemsInCart.getText();

        Thread.sleep(1000);

        Assertions.assertEquals(String.format("%s", countItemsInCart), "1 товар");

        driver.findElement(By.xpath("//*[@id=\"cart-page-new\"]/div[1]/div[2]/div/div/div[1]/div/div/div/div/div[1]/div[1]/div[1]/div[3]/div/div[3]/div/div[2]/button[2]")).click();

    }

    @Test
    void DeleteFromCart() throws InterruptedException {
        driver.get(OZON_URL + "product/ba9d7399c4f0d763/robot-pylesos-mamibot-provac-titan-belyj/");
        driver.findElement(By.cssSelector(".button-ui_brand")).click();
        Thread.sleep(1000);

        driver.get(OZON_URL + "cart");
        WebElement itemsInCart = driver.findElement(By.xpath("/html/body/div/div[1]/div[1]/div/div[1]"));

        String countItemsInCart = itemsInCart.getText();
        Thread.sleep(1000);

        Assertions.assertEquals(String.format("%s", countItemsInCart), "1 товар");

        driver.findElement(By.xpath("//*[@id=\"cart-page-new\"]/div[1]/div[2]/div/div/div[1]/div/div/div/div/div[1]/div[1]/div[1]/div[3]/div/div[3]/div/div[2]/button[2]")).click();
        String textContent = driver.findElement(By.xpath("/html/body/div/div[1]/div[2]/div[2]/div[1]")).getText();
        Thread.sleep(1000);
        Assertions.assertEquals(String.format("%s", textContent), "Корзина пуста");
    }

    @AfterAll
    static void CleanUp() {
        driver.quit();
    }
}