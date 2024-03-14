package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

@Service
public class MealService {

    private static final Logger log = LoggerFactory.getLogger(MealService.class);

    @Autowired
    private MealRepository repository;

    public Collection<MealTo> getAll(int userId) throws NotFoundException {
        try {
            return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
        } catch (NotFoundException e) {
            log.info("Еда не принадлежит пользователю");
        }
        return null;
    }

    public Meal save(Meal meal, int userId) {
        log.info("save meal {} for userId {}", meal, userId);
        try {
            return repository.save(meal, userId);
        } catch (NotFoundException e) {
            log.info("Еда не принадлежит пользователю");
        }
       return null;
    }

    public boolean delete(int id, int userId) throws NotFoundException {
        return repository.delete(id, userId);
    }

    public Meal get(int id, int userId) throws NotFoundException {
        return repository.get(id, userId);
    }

}