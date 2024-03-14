package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public Meal save(Meal meal) {
        log.info("save meal {}", meal);
        return service.save(meal, SecurityUtil.authUserId());
    }


    public boolean delete(int id) {
        return service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }

}