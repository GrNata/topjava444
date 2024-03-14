package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;

public class MealRepository {

    private List<Meal> mealList;

    public MealRepository() {
        this.mealList = new ArrayList<>();
    }

    public List<Meal> getAllMeals() {
        return mealList;
    }

    public Meal getMealById(int id) {
        for (Meal meal : mealList) {
            if (meal.getId() == id) {
                return meal;
            }
        }
        return null;
    }

    public void addMeal(Meal meal) {
        System.out.println("addMeal: " + meal);
        Integer id = MealsUtil.getId();
        meal.setId(id++);
        MealsUtil.setId(id++);

        List<Meal> meals = getAllMeals();
        System.out.println("size meals = " + meals.size());
//        mealList.add(meal);
        meals.add(meal);

        for (Meal m : getAllMeals()) {
            System.out.println(m);
        }
    }

    public Meal updateMeal(int id, Meal updateMeal) {
        for (int i = 0; i < mealList.size(); i++) {
            Meal meal = mealList.get(i);
            if (meal.getId() == id) {
                mealList.set(i, updateMeal);
                return updateMeal;
            }
        }
        return null;
    }

    public void deleteMeal(int id) {
        mealList.removeIf(meal -> meal.getId() == id);
    }

}
