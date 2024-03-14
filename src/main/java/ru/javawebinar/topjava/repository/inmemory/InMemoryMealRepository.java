package ru.javawebinar.topjava.repository.inmemory;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
//        MealsUtil.meals.forEach(this::save);
        MealsUtil.meals.forEach(meal -> save(meal, 1));
    }

    @Override
    public synchronized Meal save(Meal meal, int userId) {
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
//        return repository.remove(id) != null;
        return repository.get(id).getUserId() == userId ? repository.remove(id) != null : false;
    }

    @Override
    public Meal get(int id, int userId) {
//        return repository.get(id);
        return repository.get(id).getUserId() == userId ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(int userId) {
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

