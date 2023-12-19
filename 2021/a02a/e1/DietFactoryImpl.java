package a02a.e1;

import java.util.*;
import java.util.function.*;

import a02a.e1.Diet.Nutrient;

public class DietFactoryImpl implements DietFactory {

    private Diet generic(BiPredicate<Map<String, Map<Nutrient, Integer>>, Map<String, Double>> checkValidity) {
        return new Diet() {
            Map<String, Map<Nutrient, Integer>> food = new HashMap<>();

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                food.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return checkValidity.test(food, dietMap);
            }

        };
    }

    private int calcOverallCalories(Map<String, Map<Nutrient, Integer>> table, Map<String, Double> food) {
        int tot = 0;
        for (Map.Entry<String, Double> entry : food.entrySet()) {
            tot += table.get(entry.getKey()).get(Nutrient.CARBS) * (entry.getValue() / 100);
            tot += table.get(entry.getKey()).get(Nutrient.PROTEINS) * (entry.getValue() / 100);
            tot += table.get(entry.getKey()).get(Nutrient.FAT) * (entry.getValue() / 100);
        }
        return tot;
    }

    private int calcCaloriesOfNutrient(Map<String, Map<Nutrient, Integer>> table, Map<String, Double> food,
            Nutrient nutrient) {
        int tot = 0;
        for (Map.Entry<String, Double> entry : food.entrySet()) {
            tot += table.get(entry.getKey()).get(nutrient) * (entry.getValue() / 100);
        }
        return tot;
    }

    @Override
    public Diet standard() {
        return generic(
                (table, food) -> calcOverallCalories(table, food) >= 1500 && calcOverallCalories(table, food) <= 2000);
    }

    @Override
    public Diet lowCarb() {
        return generic(
                (table, food) -> calcOverallCalories(table, food) >= 1000 && calcOverallCalories(table, food) <= 1500
                        && calcCaloriesOfNutrient(table, food, Nutrient.CARBS) <= 300);
    }

    @Override
    public Diet highProtein() {
        return generic(
                (table, food) -> calcOverallCalories(table, food) >= 2000 && calcOverallCalories(table, food) <= 2500
                        && calcCaloriesOfNutrient(table, food, Nutrient.CARBS) <= 300
                        && calcCaloriesOfNutrient(table, food, Nutrient.PROTEINS) >= 1300);
    }

    @Override
    public Diet balanced() {
        return generic(
                (table, food) -> calcOverallCalories(table, food) >= 1600 && calcOverallCalories(table, food) <= 2000
                        && calcCaloriesOfNutrient(table, food, Nutrient.CARBS) >= 600
                        && calcCaloriesOfNutrient(table, food, Nutrient.PROTEINS) >= 600
                        && calcCaloriesOfNutrient(table, food, Nutrient.PROTEINS) >= 400
                        && calcCaloriesOfNutrient(table, food, Nutrient.PROTEINS)
                                + calcCaloriesOfNutrient(table, food, Nutrient.FAT) <= 1400);
    }

}
