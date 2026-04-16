package WEB;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class POM_FOR_NAVIGATION {
    private WebDriver driver;
    private WebDriverWait wait;

    public POM_FOR_NAVIGATION(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public static final By CONSTRUCTOR_BUTTON = By.xpath("//p[contains(text(), 'Конструктор')]");
    public static final By STELLAR_LOGO = By.xpath("//svg[@width='290' and @height='50']");
    public static final By BUTTON_OUT = By.xpath("//button[contains(text(), 'Выход')]");
}