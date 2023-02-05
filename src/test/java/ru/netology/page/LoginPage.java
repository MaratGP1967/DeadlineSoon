package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class LoginPage {
    private SelenideElement loginField = $("[data-test-id=login] input");
    private SelenideElement passwordField = $("[data-test-id=password] input");
    private SelenideElement loginButton = $("[data-test-id=action-login]");
    private SelenideElement title = $x("//div[contains (text(), 'Ошибка')]");
    private SelenideElement text = $x("//*[@data-test-id='error-notification']//*[@class='notification__content']");
    private SelenideElement icon = $x("//*[@data-test-id='error-notification']//button");


    public LoginPage() {
        loginField.shouldBe(visible);
    }

    public VerificationPage setLogin(DataHelper.AuthInfo info) {
        loginField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        loginField.setValue(info.getLogin());
        passwordField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        passwordField.setValue(info.getPassword());
        loginButton.click();
        return new VerificationPage();
    }

    public void notValidPopUp() {
        title.shouldBe(Condition.visible);
        text.shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
        icon.click();
    }


}