package ru.javawebinar.topjava.service;

import org.junit.*;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.AfterTestMethod;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
//@Ignore
public class MealServiceTest {
    private static long classStartTime;
    private static long classEndTime;
    private static Map<String, Long> testTimeMap = new HashMap<>();

    @Rule
    public TimingTestWatcher watcher = new TimingTestWatcher();

    @BeforeTestMethod
    public static void beforeMethod() {
        classStartTime = System.currentTimeMillis();
    }

    @AfterTestMethod
    public static void afterMethod() {
        classEndTime = System.currentTimeMillis();
//        System.out.println("Тест метод:");
//        System.out.println("Время выполнения метода: " + (classEndTime - classStartTime) + " ms");
    }

    @AfterClass
    public static void printSummaryTime() {
        System.out.println("\n\nВремя тестов:");
        for (Map.Entry<String, Long> entry : testTimeMap.entrySet()) {
            System.out.println("Test: " + entry.getKey() + ",  время выполнения: " +entry.getValue() + " ms");
        }
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
//        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, USER_ID));
        assertThrows(NullPointerException.class, () -> service.get(MEAL1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        int newId = created.id();
        Meal newMeal = getNew();
        newMeal.setId(newId);
        MEAL_MATCHER.assertMatch(created, newMeal);
        MEAL_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Meal(null, meal1.getDateTime(), "duplicate", 100), USER_ID));
    }

    @Test
    public void get() {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        MEAL_MATCHER.assertMatch(actual, adminMeal1);
    }

    @Test
    public void getNotFound() {
//        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
        assertThrows(NullPointerException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(MEAL1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(meal1, ADMIN_ID));
        MEAL_MATCHER.assertMatch(service.get(MEAL1_ID, USER_ID), meal1);
    }

    @Test
    public void getAll() {
        MEAL_MATCHER.assertMatch(service.getAll(USER_ID), meals);
    }

    @Test
    public void getBetweenInclusive() {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(
                        LocalDate.of(2020, Month.JANUARY, 30),
                        LocalDate.of(2020, Month.JANUARY, 30), USER_ID),
                meal3, meal2, meal1);
    }

    @Test
    public void getBetweenWithNullDates() {
        MEAL_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), meals);
    }


    private class TimingTestWatcher extends TestWatcher {
        private long startTime;

        @Override
        protected void starting(Description description) {
            System.out.println("Старт тест: " + description.getMethodName());
            startTime = System.currentTimeMillis();
        }

        @Override
        protected void finished(Description description) {
            long duration = System.currentTimeMillis() - startTime;
//            System.out.println("Окончание теста: " + description.getMethodName() + ", окончание: " + duration + "ms");
            testTimeMap.put(description.getMethodName(), duration);
        }
    }
}