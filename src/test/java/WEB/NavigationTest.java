package WEB;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NavigationTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private POM_FOR_REGISTRATION pom_for_registration;

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
        pom_for_registration = new POM_FOR_REGISTRATION(driver);
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
    @DisplayName("Переход в личный кабинет")
    void NavigateToPrivatePageTest() {
        pom_for_registration.login();
        pom_for_registration.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        String expectedUrl = "https://stellarburgers.education-services.ru/account/profile";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        WebElement profileText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//p[contains(text(), 'В этом разделе вы можете изменить свои персональные данные')]")));
        assertEquals("В этом разделе вы можете изменить свои персональные данные", profileText.getText());
    }

    @Test
    @DisplayName("Переход из личного кабинета в конструктор. По кнопке Конструктор")
    void NavigateToConstructorByButtonTest(){
        pom_for_registration.login();
        pom_for_registration.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        pom_for_registration.safeClick(POM_FOR_NAVIGATION.CONSTRUCTOR_BUTTON);
        String expectedUrl = "https://stellarburgers.education-services.ru";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        WebElement constructorText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(), 'Соберите бургер')]")));
        assertEquals("Соберите бургер", constructorText.getText());

    }
    @Test
    @DisplayName("Переход из личного кабинета в конструктор. По Логотипу")
    void NavigateToConstructorByLogo(){
        pom_for_registration.login();
        pom_for_registration.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        pom_for_registration.safeClick(POM_FOR_NAVIGATION.STELLAR_LOGO);
        String expectedUrl = "https://stellarburgers.education-services.ru";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        WebElement constructorText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//h1[contains(text(), 'Соберите бургер')]")));
        assertEquals("Соберите бургер", constructorText.getText());
    }
    @Test
    @DisplayName("Переход в раздел Булки")
    void shouldNavigateToBunsTab() {
        pom_for_registration.login();

        pom_for_registration.safeClick(POM_FOR_NAVIGATION.BUNS_TAB);

        WebElement bunsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                POM_FOR_NAVIGATION.BUNS_TAB));
        assertTrue(bunsTab.getAttribute("class").contains("tab_tab_type_current"));
    }

    @Test
    @DisplayName("Переход в раздел Соусы")
    void shouldNavigateToSaucesTab() {
        pom_for_registration.login();

        pom_for_registration.safeClick(POM_FOR_NAVIGATION.SAUCES_TAB);

        WebElement saucesTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                POM_FOR_NAVIGATION.SAUCES_TAB));
        assertTrue(saucesTab.getAttribute("class").contains("tab_tab_type_current"));
    }

    @Test
    @DisplayName("Переход в раздел Начинки")
    void shouldNavigateToFillingsTab() {
        pom_for_registration.login();

        pom_for_registration.safeClick(POM_FOR_NAVIGATION.FILLINGS_TAB);

        WebElement fillingsTab = wait.until(ExpectedConditions.visibilityOfElementLocated(
                POM_FOR_NAVIGATION.FILLINGS_TAB));
        assertTrue(fillingsTab.getAttribute("class").contains("tab_tab_type_current"));
    }
    @Test
    @DisplayName("выход по кнопке «Выйти» в личном кабинете")
    void shouldOutFromProfile() {
        pom_for_registration.login();
        pom_for_registration.safeClick(POM_FOR_REGISTRATION.PROFILE_BUTTON);
        pom_for_registration.safeClick(POM_FOR_NAVIGATION.BUTTON_OUT);
        String expectedUrl = "https://stellarburgers.education-services.ru/login";
        assertEquals(expectedUrl, driver.getCurrentUrl());

        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//button[contains(text(), 'Войти')]")));
        assertEquals("Войти", loginButton.getText());
    }

}
