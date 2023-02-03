package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class PopUp {

    private SelenideElement title = $x("//div[contains (text(), 'Ошибка')]");
    private SelenideElement text = $x("//*[@data-test-id='error-notification']//*[@class='notification__content']");
    private SelenideElement icon = $x("//*[@data-test-id='error-notification']//button");

    public LoginPage notValidPopUp() {
        title.shouldBe(Condition.visible);
        text.shouldHave(Condition.text("Ошибка! Неверно указан логин или пароль")).shouldBe(Condition.visible);
        icon.click();
        return new LoginPage();
    }
}
