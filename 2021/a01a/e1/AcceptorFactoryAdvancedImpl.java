package a01a.e1;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory {

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return this.generalised(0, (s, i) -> Optional.of(i + (s.length() == 0 ? 1 : 0)), (s) -> Optional.of(s));
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return this.generalised(new ArrayList<Integer>(),
                getStateFunction((i, s) -> s.isEmpty() || s.get(s.size() - 1) < i, false),
                (s) -> (s.size() > 1)
                        ? Optional.of(
                                String.join(":", s.stream().map((el) -> el.toString()).collect(Collectors.toList())))
                        : Optional.empty());
    }

    private BiFunction<Integer, ArrayList<Integer>, Optional<ArrayList<Integer>>> getStateFunction(
            BiPredicate<Integer, ArrayList<Integer>> checker, boolean resetIsNeeded) {
        return (i, s) -> {
            if (checker.test(i, s)) {
                s.add(i);
                return Optional.of(s);
            }
            if (resetIsNeeded) {
                s.clear();
            }
            return Optional.empty();
        };
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return this.generalised(new ArrayList<Integer>(), getStateFunction((i, s) -> s.size() < 3, true),
                (s) -> (s.size() == 3) ? Optional.of(s.stream().mapToInt(e -> e).sum()) : Optional.empty());
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return new Acceptor<E, Pair<O1, O2>>() {

            @Override
            public boolean accept(E e) {
                return a1.accept(e) && a2.accept(e);
            }

            @Override
            public Optional<Pair<O1, O2>> end() {
                if (a1.end().isPresent() && a2.end().isPresent()) {
                    return Optional.of(new Pair<>(a1.end().get(), a2.end().get()));
                }
                return Optional.empty();
            }

        };
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {

        return new Acceptor<E, O>() {
            S state = initial;

            @Override
            public boolean accept(E e) {
                var response = stateFun.apply(e, state);
                if (response.isEmpty()) {
                    return false;
                } else {
                    state = response.get();
                    return true;
                }
            }

            @Override
            public Optional<O> end() {
                return outputFun.apply(state);
            }

        };
    }

}
