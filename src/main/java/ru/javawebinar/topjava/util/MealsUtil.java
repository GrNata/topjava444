package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {

    private static Integer Id = 1;
    public static MealRepository repository = new MealRepository();

    public MealsUtil() {
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        repository.addMeal(new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410));
    }

//    public static List<Meal> meals = Arrays.asList(
//            new Meal(Id, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
//            new Meal(Id ++, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
//    );

    public static void main(String[] args) {
//        List<Meal>  meals = Arrays.asList(
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
//                new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
//        );

//        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        mealsTo.forEach(System.out::println);


        MealsUtil mealsUtil = new MealsUtil();

        int id = 3;
        for (Meal m : repository.getAllMeals()) {
            System.out.println(m);
        }
//        Meal updateMeal = new Meal(id, LocalDateTime.of(2024, Month.JANUARY, 30, 20, 0), "????", 500);
        Meal newMeal = new Meal(Id++, LocalDateTime.of(2024, Month.JANUARY, 30, 20, 0), "????", 500);
        repository.addMeal(newMeal);
        System.out.println("AFTER");
        for (Meal m : repository.getAllMeals()) {
            System.out.println(m);
        }
        System.out.println("ID =" + Id);


    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }

//    public static List<Meal>  getMeals() {
//        return meals;
//    }
//
//    public static void setMeals(List<Meal> meals) {
//        MealsUtil.meals = meals;
//    }

    public static Integer getId() {
        return Id;
    }

    public static void setId(Integer id) {
     Id = id;
    }
}
