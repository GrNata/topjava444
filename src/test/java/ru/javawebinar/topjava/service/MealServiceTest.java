package ru.javawebinar.topjava.service;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.MealTestData.getBetween;

import static org.junit.Assert.assertThrows;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest extends TestCase {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    MealService service;

    @Test
    public void testGet() {
        Meal mealDB = service.get(MEAL_ID, USER_ID);
        Meal mealTest = MEAL_LIST.get(0);
        assertEquals(mealDB, mealTest);
    }

    @Test
    public void testNotFoundGet() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_FOUND, USER_ID));
        assertThrows(NotFoundException.class, () -> service.get(MEAL_ID, NOT_FOUND));
        assertThrows(NotFoundException.class, () -> service.get(MEAL_NOT_FOUND, NOT_FOUND));
    }

    @Test
    public void testDelete() {
        service.delete(MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () ->service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void testGetBetweenInclusive() {
        assertMatch(getBetween(), service.getBetweenInclusive(START_DT, END_DT, USER_ID));

    }

    @Test
    public void testGetAll() {
        assertMatch(getAllByDESC(), service.getAll(USER_ID));
    }

    @Test
    public void testUpdate() {
        Meal updateMeal = update();
        System.out.println("!!!");
        System.out.println(updateMeal);
        service.update(updateMeal, USER_ID);
//        Meal meal = service.get(MEAL_ID, USER_ID);

//        System.out.println(meal);
//        assertMatch(update(), service.get(MEAL_ID, USER_ID));
    }

    @Test
    public void testCreate() {
        Meal meal = getNew();
        Meal excpect = service.create(meal, USER_ID);
        meal.setCalories(NEW_MEAL_ID);
        assertEquals(meal, service.get((NEW_MEAL_ID), USER_ID));
    }
}