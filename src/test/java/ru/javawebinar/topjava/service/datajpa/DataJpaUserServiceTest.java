package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL_MATCHER;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
//        User user = service.getWithMeals(USER_ID);
//        USER_MATCHER.assertMatch(user, UserTestData.user);
//        MEAL_MATCHER.assertMatch(user.getMeals(), MealTestData.meals);
        User admin = service.getWithMeals(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, UserTestData.admin);

        System.out.println("____________");
        System.out.println("admin: " +admin);
        System.out.println("test: " +UserTestData.admin);

        System.out.println("____________");
        System.out.println("admin.getMeal: " +admin.getMeals());
        System.out.println("test.2: " +MealTestData.adminMeal2);
        System.out.println("test.1: " +MealTestData.adminMeal1);

        MEAL_MATCHER.assertMatch(admin.getMeals(), MealTestData.adminMeal2, MealTestData.adminMeal1);

    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithMeals(NOT_FOUND));
    }
}