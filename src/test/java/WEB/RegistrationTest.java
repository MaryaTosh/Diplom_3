package WEB;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class RegistrationTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private POM_FOR_REGISTRATION pom;
    private String validEmail = "cucumber66@gmail.com";
    private String validPassword = "cucumber66";
    private String validName = "cucumber66";

    @BeforeEach
    public void setUp() {
        String browser = System.getProperty("browser", "chrome"); // по умолчанию Chrome
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "yandex":
                ChromeOptions yandexOptions = new ChromeOptions();
                yandexOptions.setBinary("C:\\Program Files\\Yandex\\YandexBrowser\\Application\\browser.exe");
                driver = new ChromeDriver(yandexOptions);
                break;
            default:
                throw new IllegalArgumentException("Поддерживаем только chrome или yandex, а ты дал: " + browser);
        }
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        pom = new POM_FOR_REGISTRATION(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
        driver.get("https://stellarburgers.education-services.ru/");

        validEmail = "cucumber" + new Random().nextInt(10000) + "@yopmail.com";
        validPassword = "pass" + new Random().nextInt(9999);
        validName = "User" + new Random().nextInt(999);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Успешная регистрация с корректными данными")
    public void shouldRegisterSuccessfully() {
        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_MAIN);
        pom.safeClick(POM_FOR_REGISTRATION.REGISTER_LINK);

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.NAME_FIELD_REGISTRATION));
        pom.safeClear(nameField);
        nameField.sendKeys(validName);

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.EMAIL_FIELD_REGISTRATION));
        pom.safeClear(emailField);
        emailField.sendKeys(validEmail);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.PASSWORD_FIELD_REGISTRATION));
        pom.safeClear(passwordField);
        passwordField.sendKeys(validPassword);

        pom.safeClick(POM_FOR_REGISTRATION.BUTTON_REGISTRATION);
        wait.until(ExpectedConditions.urlContains("/login"));

        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]"))).sendKeys(validEmail);
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]"))).sendKeys(validPassword);

        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));

    }

    @Test
    @DisplayName("Ошибка при некорректном пароле (меньше 6 символов)")
    public void shouldShowErrorForShortPassword() {
        String name = "TestUser";
        String email = "testuser" + System.currentTimeMillis() + "@example.com";
        String password = "12345"; // 5 символов
        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_MAIN);
        pom.safeClick(POM_FOR_REGISTRATION.REGISTER_LINK);

        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.NAME_FIELD_REGISTRATION));
        pom.safeClear(nameField);
        nameField.sendKeys(name);

        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.EMAIL_FIELD_REGISTRATION));
        pom.safeClear(emailField);
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(POM_FOR_REGISTRATION.PASSWORD_FIELD_REGISTRATION));
        pom.safeClear(passwordField);
        passwordField.sendKeys(password);
        pom.safeClick(POM_FOR_REGISTRATION.REGISTER_BUTTON);

        WebElement error = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'Некорректный пароль')]")));

        assertEquals("Некорректный пароль", error.getText());
    }
}