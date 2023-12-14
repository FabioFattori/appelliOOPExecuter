package a02a.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class ScannerFactoryImpl implements ScannerFactory {

    private <S, I, O> Scanner<I, O> genericScanner(S initialState, BiFunction<I, S, S> updateState,
            Function<S, O> mapToOutPut) {
        return new Scanner<I, O>() {

            @Override
            public O scan(Iterator<I> input) {
                S state = initialState;
                while (input.hasNext()) {
                    I next = input.next();
                    state = updateState.apply(next, state);
                }

                return mapToOutPut.apply(state);
            }

        };
    }

    private <I, O> Scanner<I, O> simpleScanner(O initialState, BiFunction<I, O, O> updateState) {
        return genericScanner(initialState, updateState, x -> x);
    }

    @Override
    public <X, Y> Scanner<X, List<Y>> collect(Predicate<X> filter, Function<X, Y> mapper) {

        return this.simpleScanner(new ArrayList<Y>(), (next, OldState) -> {
            if (filter.test(next)) {
                OldState.add(mapper.apply(next));
            }
            return OldState;
        });

        // return new Scanner<X, List<Y>>() {

        // @Override
        // public List<Y> scan(Iterator<X> input) {
        // List<Y> toRet = new ArrayList<>();
        // while (input.hasNext()) {
        // X next = input.next();
        // if (filter.test(next)) {
        // toRet.add(mapper.apply(next));
        // }
        // }
        // return toRet;
        // }

        // };
    }

    @Override
    public <X> Scanner<X, Optional<X>> findFirst(Predicate<X> filter) {
        return this.simpleScanner(Optional.<X>empty(),
                (next, OldState) -> (filter.test(next) && OldState.isEmpty()) ? Optional.of(next) : OldState);

        // return new Scanner<X, Optional<X>>() {

        // @Override
        // public Optional<X> scan(Iterator<X> input) {
        // Optional<X> toRet = Optional.empty();

        // while (input.hasNext() && toRet.isEmpty()) {
        // X next = input.next();
        // if (filter.test(next)) {
        // toRet = Optional.of(next);
        // }
        // }

        // return toRet;
        // }

        // };
    }

    @Override
    public Scanner<Integer, Optional<Integer>> maximalPrefix() {
        return new Scanner<Integer, Optional<Integer>>() {

            @Override
            public Optional<Integer> scan(Iterator<Integer> input) {
                Optional<Integer> toRet = Optional.of(Integer.MIN_VALUE);

                for (int i = 0; i < 3 && input.hasNext(); i++) {
                    Integer next = input.next();
                    if (toRet.get().intValue() < next.intValue()) {
                        toRet = Optional.of(next);
                    }
                }

                return (toRet.get().equals(Integer.MIN_VALUE)) ? Optional.empty() : toRet;
            }

        };
    }

    @Override
    public Scanner<Integer, List<Integer>> cumulativeSum() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cumulativeSum'");
    }

}
