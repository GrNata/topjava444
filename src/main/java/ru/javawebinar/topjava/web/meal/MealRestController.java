package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

//    public Collection<MealTo> getAllByFilterDate(LocalDate startDate, LocalDate endDate) {
//        log.info("filter by date: {} - {}", startDate, endDate);
//        return null;
//    }
//
//    public Collection<MealTo> getAllByFilterTime(LocalTime startTime, LocalTime endTime) {
//        log.info("filter by time: {} - {}", startTime, endTime);
//        return service.getAllByFilterTime(startTime, endTime);
//    }

    public Collection<MealTo> getAllByFilterDateTime(String startD, String endD, String starT, String endT) {
        log.info("filter by date - time: {}, {} - {}, {}", startD, endD, starT, endT);
        return service.getAllByFilterDateTime(startD, endD, starT, endT);
    }

}