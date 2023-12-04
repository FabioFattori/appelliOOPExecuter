package a02b.e1;

import static a02b.e1.UniversityProgram.Sector.COMPUTER_ENGINEERING;
import static a02b.e1.UniversityProgram.Sector.COMPUTER_SCIENCE;
import static a02b.e1.UniversityProgram.Sector.MATHEMATICS;
import static a02b.e1.UniversityProgram.Sector.PHYSICS;
import static a02b.e1.UniversityProgram.Sector.THESIS;

import java.util.*;
import java.util.function.BiPredicate;

import a02b.e1.UniversityProgram.Sector;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private final int requiredCredits = 60;

    private UniversityProgram createGenericProgram(
            BiPredicate<Set<String>, HashMap<String, Pair<Sector, Integer>>> validatePredicate) {
        return new UniversityProgram() {
            private HashMap<String, Pair<Sector, Integer>> courses = new HashMap<>();

            @Override
            public void addCourse(String name, Sector sector, int credits) {
                courses.put(name, new Pair<UniversityProgram.Sector, Integer>(sector, credits));
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                return validatePredicate.test(courseNames, courses);
            }

        };
    }

    @Override
    public UniversityProgram flexible() {
        return this.createGenericProgram(
                (names, insertedCourses) -> (calculateOverAllCredits(names, insertedCourses) == requiredCredits));
    }

    private int calculateOverAllCredits(Set<String> names, HashMap<String, Pair<Sector, Integer>> insertedCourses) {
        int toRet = 0;

        for (String string : names) {
            toRet += insertedCourses.get(string).get2();
        }
        return toRet;
    }

    private int calculateSectorCredits(Sector sector, Set<String> names,
            HashMap<String, Pair<Sector, Integer>> insertedCourses) {
        int toRet = 0;

        for (String string : names) {
            if (insertedCourses.get(string).get1() == sector) {
                toRet += insertedCourses.get(string).get2();
            }
        }
        return toRet;
    }

    @Override
    public UniversityProgram scientific() {
        return this.createGenericProgram(
                (names, insertedCourses) -> (calculateOverAllCredits(names, insertedCourses) == requiredCredits)
                        && calculateSectorCredits(MATHEMATICS, names, insertedCourses) >= 12
                        && calculateSectorCredits(COMPUTER_SCIENCE, names, insertedCourses) >= 12
                        && calculateSectorCredits(PHYSICS, names, insertedCourses) >= 12);
    }

    @Override
    public UniversityProgram shortComputerScience() {
        return this.createGenericProgram(
                (names, insertedCourses) -> (calculateOverAllCredits(names, insertedCourses) >= 48)
                        && calculateSectorCredits(COMPUTER_SCIENCE, names, insertedCourses)
                                + calculateSectorCredits(COMPUTER_ENGINEERING, names, insertedCourses) >= 30);
    }

    @Override
    public UniversityProgram realistic() {
        return this.createGenericProgram(
                (names, insertedCourses) -> (calculateOverAllCredits(names, insertedCourses) == 120)
                        && calculateSectorCredits(COMPUTER_SCIENCE, names, insertedCourses)
                                + calculateSectorCredits(COMPUTER_ENGINEERING, names, insertedCourses) >= 60
                        && calculateSectorCredits(PHYSICS, names, insertedCourses)
                                + calculateSectorCredits(MATHEMATICS, names, insertedCourses) <= 18
                        && calculateSectorCredits(THESIS, names, insertedCourses) == 24);
    }

}
