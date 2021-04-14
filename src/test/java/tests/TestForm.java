package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class TestForm extends TestBase {

    @Test
    void FillForm() {
        Faker faker = new Faker();

        String firstName = faker.name().firstName(),
                lastName = faker.name().lastName(),
                userEmail = faker.internet().emailAddress(),
                gender = "Other",
                userNumber = faker.number().digits(10),
                monthBirth = "8",
                yearBirth = "1980",
                dayBirth = "003",
                subject1 = "arts",
                hobby = "Music",
                picture = "joka.jpg",
                currentAddress = faker.address().fullAddress(),
                state = "ncr",
                city = "gurgaon";

        step("Открываем сайт", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Заполняем форму", () -> {
            $("#firstName").setValue(firstName);
            $("#lastName").setValue(lastName);
            $("#userEmail").setValue(userEmail);
            $(byText(gender)).click();
            $("#userNumber").setValue(userNumber);
        });

        step("Заполняем дату", () -> {
            $("#dateOfBirthInput").click();
            $(".react-datepicker__month-select").selectOptionByValue(monthBirth);
            $(".react-datepicker__year-select").selectOptionByValue(yearBirth);
            $(".react-datepicker__day.react-datepicker__day--" + dayBirth).click();
        });

        step("Заполняем subject и hobby", () -> {
            $("#subjectsInput").setValue(subject1).pressEnter();
            $(byText(hobby)).click();
        });

        step("Загружаем картинку", () -> {
            $("#uploadPicture").uploadFromClasspath(picture);
        });

        step("Заполняем адрес", () -> {
            $("#currentAddress").setValue(currentAddress);
            $("#react-select-3-input").setValue(state).pressEnter();
            $("#react-select-4-input").setValue(city).pressEnter();
        });

        step("Подтверждение формы", () -> {
            $("#submit").click();
        });
        step("Проверка формы", () -> {
            $(".table-responsive").shouldHave(text(firstName + " " + lastName),
                    text(userEmail),
                    text(gender),
                    text(userNumber),
                    text("03 September,1980"),
                    text(subject1),
                    text(hobby),
                    text(picture),
                    text(currentAddress),
                    text(state + " " + city));
        });
    }
}
