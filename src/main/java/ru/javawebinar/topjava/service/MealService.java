package ru.javawebinar.topjava.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

    public  Collection<MealTo> getAllByFilterDateTime(String startD, String endD, String starT, String endT) {
        log.info("filter by date: {} -{}, time {} - {}", startD, endD, starT, endT);
        Collection<MealTo> mealToList = getAll(SecurityUtil.authUserId());
        Collection<MealTo> mealTosFilter = new ArrayList<>();

        LocalTime timeStart = starT.isEmpty() ? LocalTime.MIN : DateTimeUtil.toLocalTime(starT);
        LocalTime timeEnd = endT.isEmpty() ? LocalTime.MAX : DateTimeUtil.toLocalTime(endT);
        LocalDate dateStart = startD.isEmpty() ? LocalDate.MIN : DateTimeUtil.toLocalDate(startD);
        LocalDate dateEnd = endD.isEmpty() ? LocalDate.MAX : DateTimeUtil.toLocalDate(endD);

        for (MealTo m : mealToList) {
            if (DateTimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), timeStart, timeEnd) &&
                DateTimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalDate(), dateStart, dateEnd)) {
                mealTosFilter.add(m);
            }
        }
        log.info("Size of List after filter = {}", mealTosFilter.size());
        return mealTosFilter;
    }

//    public  Collection<MealTo> getAllByFilterDate(LocalDate startDate, LocalDate endDate) {
//        log.info("filter by date: {} - {}", startDate, endDate);
//        Collection<MealTo> mealToList = getAll(SecurityUtil.authUserId());
//        Collection<MealTo> mealTosFilter = new ArrayList<>();
//        for (MealTo m : mealToList) {
//            if (DateTimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalDate(), startDate, endDate)) {
//                mealTosFilter.add(m);
//            }
//        }
//        return mealTosFilter;
//    }
//
//    public Collection<MealTo> getAllByFilterTime(LocalTime startTime, LocalTime endTime) {
//        log.info("filter by time: {} - {}", startTime, endTime);
//        Collection<MealTo> mealToList = getAll(SecurityUtil.authUserId());
//        Collection<MealTo> mealTosFilter = new ArrayList<>();
//        for (MealTo m : mealToList) {
//            if (DateTimeUtil.isBetweenHalfOpen(m.getDateTime().toLocalTime(), startTime, endTime)) {
//                mealTosFilter.add(m);
//            }
//        }
//        return mealTosFilter;
//    }


}