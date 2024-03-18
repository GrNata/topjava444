package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static int MEAL_ID = START_SEQ + 3;
    public static int MEAL_NOT_FOUND = 20;
    public static int NEW_MEAL_ID = 100012;

    public static LocalDate START_DT = LocalDate.of(2020, Month.JANUARY, 30);
    public static LocalDate END_DT = LocalDate.of(2020, Month.JANUARY, 30);

    public static final List<Meal> MEAL_LIST = Arrays.asList(
            new Meal(MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(MEAL_ID + 1, LocalDateTime.of( 2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(MEAL_ID + 2, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(MEAL_ID + 3, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(MEAL_ID + 4, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(MEAL_ID + 5, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(MEAL_ID + 6, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    public static Meal getNew() {
        return new Meal(null, LocalDateTime.of(2024, Month.MARCH, 18, 15, 00), "new Eat", 700);
    }

    public static Meal update() {
        Meal update = new Meal(MEAL_LIST.get(0));
//        update.setDateTime(LocalDateTime.of(2024, Month.MARCH, 19, 19, 00));
        update.setDescription("Update Eat");
        update.setCalories(900);
        return update;
    }

    public static List<Meal> getBetween() {
        List<Meal> result = new ArrayList<>();
        result.add(MEAL_LIST.get(2));
        result.add(MEAL_LIST.get(1));
        result.add(MEAL_LIST.get(0));
//        result.add(MEAL_LIST.get(3));
//        result.add(MEAL_LIST.get(4));

        return result;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }

    public static List<Meal> getAllByDESC() {
        List<Meal> result = new ArrayList<>();
        for (int i = 6; i>=0; i--) {
            result.add(MEAL_LIST.get(i));
        }
        return result;
    }
}
