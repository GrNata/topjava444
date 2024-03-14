package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
//        MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public synchronized Meal save(Meal meal, int userId) {
        log.info("save meal {} for userId {}", meal, userId);
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        if (meal.getUserId() == userId) {
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        log.info("delete meal-id {} for userId {}", id, userId);
//        return repository.remove(id) != null;
        return repository.get(id).getUserId() == userId ? repository.remove(id) != null : false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("get meal-id {} for userId {}", id, userId);
//        return repository.get(id);
        return repository.get(id).getUserId() == userId ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        log.info("getAll meals for userId {}", userId);
//        return repository.values();
        Comparator<Meal> dateTimeComparator = Comparator.comparing(Meal::getDateTime).reversed();
        List<Meal> mealList = new ArrayList<>(repository.values());
        mealList = mealList.stream()
                        .filter(meal -> meal.getUserId() == userId)
                        .collect(Collectors.toList());
        Collections.sort(mealList, dateTimeComparator);

        return mealList;
    }
}

