package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

public class MealRestController {
    private MealService service;

    public Collection<MealTo> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal save(Meal meal) {
        return service.save(meal, SecurityUtil.authUserId());
    }

    public boolean delete(int id) {
        return service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

}