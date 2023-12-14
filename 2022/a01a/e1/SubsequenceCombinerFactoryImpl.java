package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    private <I, O> SubsequenceCombiner<I, O> genericSequence(BiPredicate<I, List<I>> readyToMap,
            Function<List<I>, O> mapper, Predicate<List<I>> checkLeftOvers) {
        return new SubsequenceCombiner<I, O>() {

            @Override
            public List<O> combine(List<I> list) {
                List<O> toRet = new ArrayList<>();
                List<I> tmpList = new ArrayList<>();
                for (I element : list) {
                    if (readyToMap.test(element, tmpList)) {
                        toRet.add(mapper.apply(tmpList));
                        tmpList = new ArrayList<>();
                        tmpList.add(element);
                    } else {
                        tmpList.add(element);
                    }
                }

                if (!tmpList.isEmpty() && checkLeftOvers.test(tmpList)) {
                    toRet.add(mapper.apply(tmpList));
                }

                return toRet;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return this.genericSequence((el, l) -> l.size() == 3, (l) -> l.stream().mapToInt((e) -> e).sum(), (l) -> true);
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return this.genericSequence((el, l) -> l.size() == 3, (l) -> l, (l) -> true);
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return this.genericSequence((el, l) -> el == 0, (l) -> (l.contains(0)) ? l.size() - 1 : l.size(),
                (l) -> !(l.stream().mapToInt(e -> e).sum() == 0));
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return this.genericSequence((el, l) -> !l.isEmpty(),
                (l) -> l.stream().map((element) -> function.apply(element)).findFirst().get(), (l) -> true);
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return this.genericSequence((el, l) -> l.stream().mapToInt(e -> e).sum() >= threshold, (l) -> l, (l) -> true);
    }

}
