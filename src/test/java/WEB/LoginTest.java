package WEB;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginTest {

    private WebDriver driver;
    private WebDriverWait wait;
    private POM_FOR_REGISTRATION pom;
    private final String validEmail = "cucumber66@gmail.com";
    private final String validPassword = "cucumber66";

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
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }


    @Test
    @DisplayName("Вход через кнопку «Личный кабинет»")
    void LoginFromButtonPrivateAccount() {
        pom.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        wait.until(ExpectedConditions.urlContains("/login"));

        // 2. ПЕРВОЕ поле = EMAIL (НЕ name!)
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]")));  // ← ПЕРВОЕ!
        pom.safeClear(emailField);
        emailField.sendKeys(validEmail);

        // 3. ВТОРОЕ поле = ПАРОЛЬ
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]")));  // ← ВТОРОЕ!
        pom.safeClear(passwordField);
        passwordField.sendKeys(validPassword);

        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_BUTTON);
        wait.until(ExpectedConditions.urlContains("/"));


        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));
    }

    @Test
    @DisplayName("вход по кнопке «Войти в аккаунт» на главной")
void LoginFromButtonLogInAccount() {
        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_MAIN);
        wait.until(ExpectedConditions.urlContains("/login"));

        // 2. ПЕРВОЕ поле = EMAIL (НЕ name!)
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]")));  // ← ПЕРВОЕ!
        pom.safeClear(emailField);
        emailField.sendKeys(validEmail);

        // 3. ВТОРОЕ поле = ПАРОЛЬ
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]")));  // ← ВТОРОЕ!
        pom.safeClear(passwordField);
        passwordField.sendKeys(validPassword);

        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_BUTTON);
        wait.until(ExpectedConditions.urlContains("/"));


        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));
    }

    @Test
    @DisplayName("вход через кнопку в форме регистрации")
    void LoginFromButtonLogInRegistrationForm() {
        pom.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        wait.until(ExpectedConditions.urlContains("/login"));

        pom.safeClick(POM_FOR_REGISTRATION.REGISTER_LINK);

        pom.safeClick(POM_FOR_REGISTRATION.LOG_IN_IN_REGISTRATION);
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]")));  // ← ПЕРВОЕ!
        pom.safeClear(emailField);
        emailField.sendKeys(validEmail);

        // 3. ВТОРОЕ поле = ПАРОЛЬ
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]")));  // ← ВТОРОЕ!
        pom.safeClear(passwordField);
        passwordField.sendKeys(validPassword);
        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_BUTTON);
        wait.until(ExpectedConditions.urlContains("/"));


        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));

    }

    @Test
    @DisplayName("вход через кнопку в форме восстановления пароля")
    void LoginFromPasswordForgotPage(){
        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_MAIN);
        wait.until(ExpectedConditions.urlContains("/login"));
        pom.safeClick(POM_FOR_REGISTRATION.BUTTON_FORGOT_PASSWORD);
        pom.safeClick(POM_FOR_REGISTRATION.LOG_IN_IN_REGISTRATION);
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[1]")));  // ← ПЕРВОЕ!
        pom.safeClear(emailField);
        emailField.sendKeys(validEmail);

        // 3. ВТОРОЕ поле = ПАРОЛЬ
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("(//input[contains(@class, 'input__textfield')])[2]")));  // ← ВТОРОЕ!
        pom.safeClear(passwordField);
        passwordField.sendKeys(validPassword);

        pom.safeClick(POM_FOR_REGISTRATION.LOGIN_BUTTON);
        wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Оформить заказ')]")));

    }


}