package a02a.e1;

import static a02a.e1.Diet.Nutrient.CARBS;
import static a02a.e1.Diet.Nutrient.FAT;
import static a02a.e1.Diet.Nutrient.PROTEINS;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;

import a02a.e1.Diet.Nutrient;

public class DietFactoryImpl implements DietFactory {

    final int MAX_CALORIES = 2000;
    final int MIN_CALORIES = 1500;

    private Diet createGenericDiet(
            BiPredicate<HashMap<String, Map<Nutrient, Integer>>, Map<String, Double>> checkValidity) {
        return new Diet() {
            HashMap<String, Map<Nutrient, Integer>> table = new HashMap<>();

            @Override
            public void addFood(String name, Map<Nutrient, Integer> nutritionMap) {
                table.put(name, nutritionMap);
            }

            @Override
            public boolean isValid(Map<String, Double> dietMap) {
                return checkValidity.test(table, dietMap);
            }

        };
    }

    @Override
    public Diet standard() {
        return this.createGenericDiet((table, dietMap) -> {

            var caloriesmap = calculateCaloriesDifferentTipes(table, dietMap);

            return checkCaloriesAmount((caloriesmap.get(CARBS) + caloriesmap.get(PROTEINS) + caloriesmap.get(FAT)),
                    MIN_CALORIES, MAX_CALORIES);
        });
    }

    @Override
    public Diet lowCarb() {
        return this.createGenericDiet((table, dietmax) -> {

            var caloriesmap = calculateCaloriesDifferentTipes(table, dietmax);

            return checkCaloriesAmount((caloriesmap.get(CARBS) + caloriesmap.get(PROTEINS) + caloriesmap.get(FAT)),
                    1000, 1500)
                    && (caloriesmap.get(CARBS) <= 300);
        });
    }

    @Override
    public Diet highProtein() {
        return this.createGenericDiet((table, dietmax) -> {
            var caloriesmap = calculateCaloriesDifferentTipes(table, dietmax);

            return checkCaloriesAmount((caloriesmap.get(CARBS) + caloriesmap.get(PROTEINS) + caloriesmap.get(FAT)),
                    2000, 2500)
                    && (caloriesmap.get(CARBS) <= 300) && (caloriesmap.get(PROTEINS) >= 1300);
        });
    }

    @Override
    public Diet balanced() {
        return this.createGenericDiet((table, dietmax) -> {
            var caloriesmap = calculateCaloriesDifferentTipes(table, dietmax);

            return checkCaloriesAmount((caloriesmap.get(CARBS) + caloriesmap.get(PROTEINS) + caloriesmap.get(FAT)),
                    1600, 2000)
                    && (caloriesmap.get(CARBS) >= 600) && (caloriesmap.get(PROTEINS) >= 600)
                    && (caloriesmap.get(FAT) >= 400);
        });
    }

    private boolean checkCaloriesAmount(int total, int minCal, int maxCal) {
        if (total >= minCal && total <= maxCal) {
            return true;
        }
        return false;
    }

    private Map<Nutrient, Integer> calculateCaloriesDifferentTipes(HashMap<String, Map<Nutrient, Integer>> table,
            Map<String, Double> dietMap) {
        int CarbsCalories = 0;
        int ProtCalories = 0;
        int FatCalories = 0;
        for (Map.Entry<String, Double> entry : dietMap.entrySet()) {
            CarbsCalories += table.get(entry.getKey()).get(CARBS) * (entry.getValue() / 100);
            FatCalories += table.get(entry.getKey()).get(FAT) * (entry.getValue() / 100);
            ProtCalories += table.get(entry.getKey()).get(PROTEINS) * (entry.getValue() / 100);
        }
        return Map.of(CARBS, CarbsCalories, FAT, FatCalories, PROTEINS, ProtCalories);
    }

}
