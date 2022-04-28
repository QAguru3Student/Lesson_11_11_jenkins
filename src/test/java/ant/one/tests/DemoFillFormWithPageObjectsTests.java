package ant.one.tests;

import org.junit.jupiter.api.Test;

import java.io.File;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DemoFillFormWithPageObjectsTests extends TestBase {
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
    String city = "Agra";
    String filename = "avatar.jpg";



    @Test
    void openPageTest() {
        openPage();
        fillForm();
        checkForm();
    }

    void openPage() {
        //Открыть страницу
        registrationsPage.openPage();
    }

    void fillForm() {
        //заполниь форму
        $("#firstName").setValue(firstName);
        $("#lastName").setValue(lastName);
        $("#userEmail").setValue(email);
        $("#gender-radio-1").parent().click();
        $("#userNumber").setValue(mobileNumber);

        $("#dateOfBirthInput").click();
        $(".react-datepicker__year-select").selectOption(this.year);
        $(".react-datepicker__month-select").selectOption(this.month);
        $(".react-datepicker__day--0" + this.day).click();

        $("#subjectsInput").setValue(subjects).pressEnter();
        $("#hobbies-checkbox-1").parent().click();
        $("#hobbies-checkbox-3").parent().click();
        $("#uploadPicture").uploadFile(new File("src/test/resources/" + filename));

        $("#currentAddress").setValue(address).scrollTo();

        $("#state").parent().click();
        $(byText(state)).click();
        // $("#city").click();
       // $(byText(city)).click();
        $("#submit").click();
    }

    void checkForm() {
        //проверить форму
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
    }

    void checkValue(String key, String value) {
        $$(".table-responsive td").find(text(key)).sibling(0).shouldHave(exactText(value));
    }
}
