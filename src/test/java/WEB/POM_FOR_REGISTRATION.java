package WEB;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static io.restassured.RestAssured.given;


public class POM_FOR_REGISTRATION {
    private WebDriver driver;
    private WebDriverWait wait;
    private final String validEmail = "cucumber66@gmail.com";
    private final String validPassword = "cucumber66";

    public POM_FOR_REGISTRATION(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }
    public static final By REGISTER_BUTTON = By.xpath("//button[text()='Зарегистрироваться']");
    public static final By PROFILE_BUTTON = By.xpath("(//a[contains(@class, 'AppHeader_header__link')])[3]");
    public static final By EMAIL_FIELD_REGISTRATION = By.xpath("(//input[contains(@class, 'input__textfield')])[2]");
    public static final By NAME_FIELD_REGISTRATION = By.xpath("(//input[contains(@class, 'input__textfield')])[1]");
    public static final By PASSWORD_FIELD_REGISTRATION = By.xpath("(//input[contains(@class, 'input__textfield')])[3]");
    public static final By LOGIN_BUTTON = By.xpath("//button[contains(text(), 'Войти')]");
    public static final By LOGIN_MAIN = By.xpath("//button[contains(text(), 'Войти в аккаунт')]");
    public static final By REGISTER_LINK = By.xpath("//a[contains(text(), 'Зарегистрироваться')]");
    public static final By LOG_IN_IN_REGISTRATION = By.xpath("//a[contains(text(), 'Войти')]");
    public static final By BUTTON_FORGOT_PASSWORD = By.xpath("//a[contains(text(), 'Восстановить пароль')]");
    public static final By BUTTON_REGISTRATION = By.xpath("//button[contains(text(), 'Зарегистрироваться')]");

    public void safeClick(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true); arguments[0].click();", element);
        }
    }

    public void safeClear(WebElement element) {
        element.clear();
    }

    public void login() {
        safeClick(PROFILE_BUTTON);
        wait.until(ExpectedConditions.urlContains("/login"));


        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]")));
        safeClear(emailField);
        emailField.sendKeys(validEmail);


        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]")));
        safeClear(passwordField);
        passwordField.sendKeys(validPassword);

        safeClick(LOGIN_BUTTON);
        wait.until(ExpectedConditions.urlContains("/"));
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));

    }
}
