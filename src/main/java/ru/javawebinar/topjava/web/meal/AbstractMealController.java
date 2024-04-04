package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredTos;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public abstract class AbstractMealController {
    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    @Autowired
    private MealService service;

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get meal {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meak {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealTo> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("All meals for user {}", userId);
        return MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        log.info("create meal {} for user {}", meal, userId);
        checkNew(meal);
        return service.create(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update meal {}, id = {} for user", meal, id, userId);
        assureIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public List<MealTo> getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("get meals start date - {}, start time{} and end date - {}, end time - {}",
                startDate, startTime, endDate, endTime);
        List<Meal> meals = service.getBetweenInclusive(startDate, endDate, userId);
        return getFilteredTos(meals, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);

    }
}
