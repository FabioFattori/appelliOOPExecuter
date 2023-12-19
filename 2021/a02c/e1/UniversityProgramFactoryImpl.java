package a02c.e1;

import java.util.Map;
import java.util.Set;
import java.util.function.*;

import a02c.e1.UniversityProgram.Group;

public class UniversityProgramFactoryImpl implements UniversityProgramFactory {

    private UniversityProgram generic(BiPredicate<Map<String, Pair<Set<Group>, Integer>>, Set<String>> checkValidity) {
        return new UniversityProgram() {
            private Map<String, Pair<Set<Group>, Integer>> courses;

            @Override
            public void setCourses(Map<String, Pair<Set<Group>, Integer>> courses) {
                this.courses = courses;
            }

            @Override
            public boolean isValid(Set<String> courseNames) {
                return checkValidity.test(this.courses, courseNames);
            }

        };
    }

    private int calcOverAllCredits(Map<String, Pair<Set<Group>, Integer>> table, Set<String> courses) {
        return courses.stream().mapToInt(c -> table.get(c).get2()).sum();
    }

    private int calcGroupCredits(Map<String, Pair<Set<Group>, Integer>> table, Set<String> courses, Group group) {
        return courses.stream()
                .map(c -> table.get(c))
                .filter(pair -> pair.get1().contains(group))
                .mapToInt(pair -> pair.get2())
                .sum();
    }

    @Override
    public UniversityProgram flexible() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 48);
    }

    @Override
    public UniversityProgram fixed() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 60
                && calcGroupCredits(table, courses, Group.MANDATORY) == 12
                && calcGroupCredits(table, courses, Group.OPTIONAL) == 36
                && calcGroupCredits(table, courses, Group.THESIS) == 12);
    }

    @Override
    public UniversityProgram balanced() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 60
                && calcGroupCredits(table, courses, Group.MANDATORY) == 24
                && calcGroupCredits(table, courses, Group.OPTIONAL) >= 24
                && calcGroupCredits(table, courses, Group.FREE) <= 12
                && calcGroupCredits(table, courses, Group.THESIS) <= 12);
    }

    @Override
    public UniversityProgram structured() {
        return this.generic((table, courses) -> calcOverAllCredits(table, courses) == 60
                && calcGroupCredits(table, courses, Group.MANDATORY) == 12
                && calcGroupCredits(table, courses, Group.OPTIONAL_A) >= 6
                && calcGroupCredits(table, courses, Group.OPTIONAL_B) >= 6
                && calcGroupCredits(table, courses, Group.OPTIONAL) == 30
                && calcGroupCredits(table, courses, Group.FREE) + calcGroupCredits(table, courses, Group.THESIS) == 18);
    }

}
