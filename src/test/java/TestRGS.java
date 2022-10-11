import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@RunWith(Parameterized.class)
public class TestRGS extends BaseTest {

    private final String userName;
    private final String userTel;
    private final String userEmail;
    private final String userAddress;

    public TestRGS(String userName, String userTel, String userEmail, String userAddress) {
        this.userName = userName;
        this.userTel = userTel;
        this.userEmail = userEmail;
        this.userAddress = userAddress;
    }

    @Parameterized.Parameters
    public static Collection params(){
        return Arrays.asList(new Object[][]{
                {"Иванов Иван Иванович","1111111111", "IvanovEmail", "Москва"},
                {"Петров Андрей Викторович","2222222222", "PetrovEmail", "Сочи"},
                {"Абрамов Максим Сергеевич","3333333333", "AbramovEmail", "Иркутск"},});
    }

    @Test
    public void testRGS(){

        //Выбор пункта меню "Компаниям"
        String baseMenuXpath = "//a[text() = 'Компаниям' and contains(@href, 'companies')]";
        WebElement baseMenu = driver.findElement(By.xpath(baseMenuXpath));
        waitUtilElementToBeClickable(baseMenu);
        baseMenu.click();

        //Проверка нажатия "Компания"
        String activeBaseMenuXpath = "//a[text() = 'Компаниям' and contains(@class, 'active')]";
        WebElement activeBaseMenu = driver.findElement(By.xpath(activeBaseMenuXpath));
        waitUtilElementToBeVisible(activeBaseMenu);
        Assert.assertTrue("Клик по кнопке \"Компаниям\" не осуществлен", activeBaseMenu.getAttribute("class").contains("active"));

        //Переключение на ifame всплывающего окна
        String searchIframeXpath = "//iframe[@id = 'fl-616371']";
        By searchIframe = By.xpath(searchIframeXpath);
        driver.switchTo().frame(driver.findElement(searchIframe));

        //Закрытие всплывающего окна
        String closeSubscriptionWindowsXpath = "//div[@data-fl-track = 'click-close-login']";
        WebElement closeSubscriptionWindows = driver.findElement(By.xpath(closeSubscriptionWindowsXpath));
        waitUtilElementToBeClickable(closeSubscriptionWindows);
        closeSubscriptionWindows.click();

        //Переключение на основной iframe
        driver.switchTo().defaultContent();

        //Выбор пункта подменю "Здоровье"
        String subMenuHealthXpath = "//span[text() = 'Здоровье' and contains(@class, 'padding')]";
        WebElement subMenuHealth = driver.findElement(By.xpath(subMenuHealthXpath));
        waitUtilElementToBeClickable(subMenuHealth);
        subMenuHealth.click();

        //Проверка нажатия "Здоровье"
        WebElement activeSubMenuHealth = subMenuHealth.findElement(By.xpath("./parent::li"));
        waitUtilElementToBeVisible(activeSubMenuHealth);
        Assert.assertTrue("Клик по кнопке \"Здоровье\" не осуществлен", activeSubMenuHealth.getAttribute("class").contains("active"));

        //Выбор пункта подменю "Добровольное медицинское страхование"
        String subMenuInsuranceXpath = "//a[text() = 'Добровольное медицинское страхование']";
        WebElement sumMenuInsurance = driver.findElement(By.xpath(subMenuInsuranceXpath));
        waitUtilElementToBeClickable(sumMenuInsurance);
        sumMenuInsurance.click();

        //Проверка наличия заголовка
        String titleHealthXpath = "//h1[contains(text(), 'Добровольное медицинское страхование')]";
        WebElement titleHealth = driver.findElement(By.xpath(titleHealthXpath));
        waitUtilElementToBeVisible(titleHealth);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Добровольное медицинское страхование", titleHealth.getText());

        //Закрытие сообщения Cookie
        String cookieMessageXpath = "//button[@class = 'btn--text' and contains(text(), 'Хорошо')]";
        WebElement cookieMessage = driver.findElement(By.xpath(cookieMessageXpath));
        waitUtilElementToBeClickable(cookieMessage);
        cookieMessage.click();

        //Нажатие на кнопку "Отправить заявку"
        String sendRequestXpath = "//span[text() = 'Отправить заявку']";
        WebElement sendRequest = driver.findElement(By.xpath(sendRequestXpath));
        waitUtilElementToBeClickable(sendRequest);
        sendRequest.click();

        //Проверка наличия заголовка
        String titlePolicyXpath = "//h2[contains(@class, 'title--h2') and contains(text(), 'Оперативно перезвоним')]";
        WebElement titlePolicy = driver.findElement(By.xpath(titlePolicyXpath));
        waitUtilElementToBeVisible(titlePolicy);
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Оперативно перезвоним\n" +
                        "для оформления полиса", titlePolicy.getText());

        try { Thread.sleep(1000); } catch (InterruptedException ignore) {}


        // заполнить поля данными
        String fieldXPath = "//input[@name='%s']";
        String addressXpath = "//input[@class='vue-dadata__input']";
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userName"))), userName);
        fillInputFieldForPhone(driver.findElement(By.xpath(String.format(fieldXPath, "userTel"))), userTel);
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "userEmail"))), userEmail);
        fillInputFieldForAddress(driver.findElement(By.xpath(addressXpath)), userAddress);


        //Клик по checkBox
        String checkBoxXpath = "//div[contains(@class, 'form__checkbox')]";
        WebElement checkBox = driver.findElement(By.xpath(checkBoxXpath));
        scrollToElementJs(checkBox);
        waitUtilElementToBeVisible(checkBox);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("document.querySelector('label.checkbox',':before').click();");
        WebElement checkBoxChecked = checkBox.findElement(By.xpath("./label[contains(@class, 'checkbox')]"));
        waitUtilElementToBeVisible(checkBoxChecked);
        Assert.assertTrue(checkBoxChecked.getAttribute("class").contains("is-checked"));

        try { Thread.sleep(1000); } catch (InterruptedException ignore) {}

        //Нажатие по кнопке "Свяжитесь со мной"
        String contactMeXpath = "//button[contains(@class, 'form__button-submit')]";
        WebElement contactMe = driver.findElement(By.xpath(contactMeXpath));
        waitUtilElementToBeClickable(contactMe);
        js.executeScript("arguments[0].click();", contactMe);

        //Проверка наличия сообщений об ошибке
        String fieldEmailXpath = "//label[contains(@class, 'input__label') and text()='Ваша почта']";
        WebElement fieldEmail = driver.findElement(By.xpath(fieldEmailXpath));
        waitUtilElementToBeVisible(fieldEmail);
        WebElement errEmail = fieldEmail.findElement(By.xpath("./../span"));
        Assert.assertEquals("Проверка ошибки у поля \"Email\" не была пройдена",
                "Введите корректный адрес электронной почты", errEmail.getText());

//        String fieldAddressXpath = "//label[contains(@class, 'input__label') and text()='Ваш адрес']";
//        WebElement fieldAddress = driver.findElement(By.xpath(fieldAddressXpath));
//        waitUtilElementToBeVisible(fieldAddress);
//        WebElement errAddress = fieldAddress.findElement(By.xpath("./../span"));
//        Assert.assertEquals("Проверка ошибки у поля \"Адрес\" не была пройдена",
//                "Поле обязательно", errAddress.getText());
    }


    private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        boolean checkFlag = wait.until(ExpectedConditions.attributeContains(element, "value", value));
        Assert.assertTrue("Поле было заполнено некорректно", checkFlag);
    }
    private void fillInputFieldForAddress(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        List<WebElement> dropdown = driver.findElements(By.xpath("//span[contains(@class, 'suggestions-item')]"));
        scrollToElementJs(dropdown.get(0));
        waitUtilElementToBeVisible(dropdown.get(0));
        dropdown.get(0).click();
        try { Thread.sleep(500); } catch (Exception ignore) {}
        Assert.assertTrue("Поле было заполнено некорректно", element.getAttribute("value").contains(value));
    }

    private void fillInputFieldForPhone(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.clear();
        element.sendKeys(value);
        String val = element.getAttribute("value").replaceAll("\\D", "");
        Assert.assertEquals("Поле было заполнено некорректно", val, "7" + value);
    }

}