package a02b.e1;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiPredicate;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private UniversityProgram generic(BiPredicate<Map<String, Pair<Sector, Integer>>, Set<String>> checkValidity) {
        return new UniversityProgram() {

            Map<String, Pair<Sector, Integer>> courses = new HashMap<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                this.courses.put(name, new Pair<UniversityProgram.Sector, Integer>(sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                return checkValidity.test(courses, courseNames);
            }

        };
    }

    private int calcOverAllCredits(Map<String, Pair<Sector, Integer>> table, Set<String> courses) {
        return courses.stream().mapToInt(s -> table.get(s).get2()).sum();
    }

    private int calcSectorCredit(Map<String, Pair<Sector, Integer>> table, Set<String> courses, Sector sector) {
        return courses.stream().map(s -> table.get(s)).filter(entry -> entry.get1() == sector).mapToInt(s -> s.get2())
                .sum();
    }

    @Override
    public UniversityProgram flexible() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 60);
    }

    @Override
    public UniversityProgram scientific() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 60
                && calcSectorCredit(table, courses, Sector.MATHEMATICS) >= 12
                && calcSectorCredit(table, courses, Sector.COMPUTER_SCIENCE) >= 12
                && calcSectorCredit(table, courses, Sector.PHYSICS) >= 12);
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return this.generic((table,courses)->calcOverAllCredits(table, courses) >= 48
                && calcSectorCredit(table, courses, Sector.COMPUTER_SCIENCE) + calcSectorCredit(table, courses, Sector.COMPUTER_ENGINEERING) >= 30);
    }

    @Override
    public UniversityProgram realistic() {
        return this.generic((table,courses)->calcOverAllCredits(table, courses) == 120
                && calcSectorCredit(table, courses, Sector.COMPUTER_SCIENCE) + calcSectorCredit(table, courses, Sector.COMPUTER_ENGINEERING) >= 60
                && calcSectorCredit(table, courses, Sector.MATHEMATICS) + calcSectorCredit(table, courses, Sector.PHYSICS) <= 18
                && calcSectorCredit(table, courses, Sector.THESIS) == 24);
    }

}
