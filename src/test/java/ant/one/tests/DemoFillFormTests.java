package ant.one.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static io.qameta.allure.Allure.step;

public class DemoFillFormTests extends TestBase {
    String firstName = "Anton";
    String lastName = "Babushkin";
    String email = "ab@mail.ru";
    String gender = "male";
    String mobileNumber = "9242832027";
    String year = "1987";
    String month = "October";
    String day = "24";
    String[] hobbies = {"Sports", "Music"};
    String subjects = "Maths";
    String address = "Moscow, Marinskyi park 35, 122";
    String state = "NCR";
    String city = "Lucknow";
    String filename = "avatar.jpg";



    @Test
    @DisplayName("Заполнение формы регистрации")
    void openPageTest() {
        openPage();
        fillForm();
        checkForm();
    }

    void openPage() {
        //Открыть страницу
        step("Открываем форму регистрации", () -> {
            open("https://demoqa.com/automation-practice-form");
        });
    }

    void fillForm() {
        //заполниь форму
        step("Ввести имя", () -> {
            $("#firstName").setValue(firstName);
        });
        step("Ввести фамилию", () -> {
            $("#lastName").setValue(lastName);
        });
        step("Ввести email", () -> {
            $("#userEmail").setValue(email);
        });
        step("Выбрать пол", () -> {
            $("#gender-radio-1").parent().click();
        });
        step("Ввести номер телефона", () -> {
            $("#userNumber").setValue(mobileNumber);
        });

        step("Выбрать дату рождения", () -> {
            $("#dateOfBirthInput").click();

            $(".react-datepicker__year-select").selectOption(this.year);
            $(".react-datepicker__month-select").selectOption(this.month);
            $(".react-datepicker__day--0" + this.day).click();
        });

        step("Выбрать предмет", () -> {
            $("#subjectsInput").setValue(subjects).pressEnter();
        });
        step("Выбрать хобби", () -> {
            $("#hobbies-checkbox-1").parent().click();
            $("#hobbies-checkbox-3").parent().click();
        });
        step("Загрузить аватарку", () -> {
            $("#uploadPicture").uploadFile(new File("src/test/resources/" + filename));
        });

        step("Ввести адрес", () -> {
            $("#currentAddress").setValue(address).scrollTo();
        });

        step("Выбрать штат", () -> {
            $("#state").parent().click();
            $(byText(state)).click();
        });

//        step("Выбрать город", () -> {
//            $("#city").click();
//            $(byText(city)).click();
//        });
        step("Отправить анкету", () -> {
            $("#submit").click();
        });
    }

    void checkForm() {
        //проверить форму
        step("Проверка отправленной формы", () -> {
            checkValue("Student Name", this.firstName + " " + this.lastName);
            checkValue("Student Email", this.email);
            checkValue("Gender", this.gender);
            checkValue("Mobile", this.mobileNumber);
            checkValue("Date of Birth", this.day + " " + this.month + "," + this.year);
            checkValue("Subjects", this.subjects);
            checkValue("Hobbies", String.join(", ", this.hobbies));
            checkValue("Picture", this.filename);
            checkValue("Address", this.address);
            //checkValue("State and City", this.state + " " + this.city);
        });
    }


    void checkValue(String key, String value) {
        $$(".table-responsive td").find(text(key)).sibling(0).shouldHave(exactText(value));
    }
}