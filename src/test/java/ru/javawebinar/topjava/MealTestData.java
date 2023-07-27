package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final int MEAL_ID = START_SEQ + 3;
    public static final int NOT_FOUND = 100;

    public static final Meal mealUser1 = new Meal(MEAL_ID, LocalDateTime.of(2023, 7, 25, 10, 15),"завтрак юзера", 580);
    public static final Meal mealAdmin1 = new Meal(MEAL_ID + 1, LocalDateTime.of(2023, 7, 25, 9, 55),"завтрак админа", 700);
    public static final Meal mealUser2 = new Meal(MEAL_ID + 2, LocalDateTime.of(2023, 7, 25, 20, 0),"ужин юзера", 1400);
    public static final Meal mealAdmin2 = new Meal(MEAL_ID + 3, LocalDateTime.of(2023, 7, 25, 14, 0),"обед админа", 2200);
    public static final Meal mealUser3 = new Meal(MEAL_ID + 4, LocalDateTime.of(2020, 1, 30, 10, 0),"Завтрак", 500);
    public static final Meal mealUser4 = new Meal(MEAL_ID + 5, LocalDateTime.of(2020, 1, 30, 13, 0),"Обед", 1000);
    public static final List<Meal> USER_MEALS = Arrays.asList(mealUser2, mealUser1, mealUser4, mealUser3);
    public static final List<Meal> ADMIN_MEALS = Arrays.asList(mealAdmin2, mealAdmin1);

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2023, 7, 27, 15, 0), "новыйМил", 555);
    }

    public static Meal getUpdatedUserMeal() {
        Meal updated = new Meal(mealUser1);
        updated.setId(MEAL_ID);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(30);
        updated.setDateTime(LocalDateTime.of(2023, 7, 27, 17, 0));
        return updated;
    }

    public static Meal getUpdatedAdminMeal() {
        Meal updated = new Meal(mealAdmin1);
        updated.setId(MEAL_ID + 1);
        updated.setDescription("UpdatedDescription");
        updated.setCalories(40);
        updated.setDateTime(LocalDateTime.of(2023, 7, 27, 16, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToComparingFieldByField(expected);
    }


    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }
}
