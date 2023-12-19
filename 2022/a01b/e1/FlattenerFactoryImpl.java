package a01b.e1;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FlattenerFactoryImpl implements FlattenerFactory {

    private <I, O> Flattener<I, O> genericSingleFlattener(Function<List<I>, List<O>> mapper) {
        return new Flattener<I, O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                List<O> toRet = new ArrayList<>();
                for (List<I> innerList : list) {
                    toRet.addAll(mapper.apply(innerList));
                }

                return toRet;
            }

        };
    }

    private <I, O> Flattener<I, O> genericDoubleFlattener(BiFunction<List<I>, List<I>, List<O>> mapper,
            Function<List<O>, List<O>> checkEndResult) {
        return new Flattener<I, O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                List<O> toRet = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (i + 1 < list.size()) {
                        toRet.addAll(mapper.apply(list.get(i), list.get(i + 1)));
                        i++;
                    } else {
                        toRet.addAll(mapper.apply(list.get(i), null));
                    }
                }

                toRet = checkEndResult.apply(toRet);

                return toRet;
            }

        };
    }

    private <I, O> Flattener<I, O> genericFlatter(BiFunction<List<I>, List<I>, List<O>> mapper,
            BiPredicate<Integer, Integer> toEmit, Function<List<O>, List<O>> changeState) {
        return new Flattener<I, O>() {

            @Override
            public List<O> flatten(List<List<I>> list) {
                List<O> toRet = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (i + 1 < list.size()) {
                        toRet.addAll(mapper.apply(list.get(i), list.get(i + 1)));
                        i++;
                    } else {
                        toRet.addAll(mapper.apply(list.get(i), null));
                    }
                }

                while (toEmit.test(list.stream().findAny().get().size(), toRet.size())) {
                    toRet = changeState.apply(toRet);
                }

                return toRet;
            }

        };
    }

    @Override
    public Flattener<Integer, Integer> sumEach() {
        return this.genericFlatter((ls1, ls2) -> (ls2 != null)
                ? List.of(ls1.stream().mapToInt(x -> x).sum(), ls2.stream().mapToInt(x -> x).sum())
                : List.of(ls1.stream().mapToInt(x -> x).sum()), (expectedSize, actualSize) -> false, null);
        // return this.genericSingleFlattener((innerList) ->
        // List.of(innerList.stream().mapToInt(x -> x).sum()));
    }

    @Override
    public <X> Flattener<X, X> flattenAll() {
        return this.genericFlatter((ls1,
                ls2) -> (ls2 != null) ? Stream.concat(ls1.stream(), ls2.stream()).collect(Collectors.toList()) : ls1,
                (expectedSize, actualSize) -> false, null);
        // return this.genericSingleFlattener((innerList) -> innerList);
    }

    @Override
    public Flattener<String, String> concatPairs() {
        return this.genericFlatter((ls1, ls2) -> (ls2 != null)
                ? List.of(String.join("", Stream.concat(ls1.stream(), ls2.stream()).collect(Collectors.toList())))
                : List.of(String.join("", ls1)), (expectedSize, actualSize) -> false, null);
        // return this.<String, String>genericDoubleFlattener((lst1, lst2) -> {
        // List<String> summed = new ArrayList<>();
        // lst1.forEach(l -> summed.add(l));
        // if (lst2 != null) {
        // lst2.forEach(l -> summed.add(l));
        // }
        // return List.of(String.join("", summed));
        // }, (res) -> res);
    }

    @Override
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return this.genericFlatter((ls1, ls2) -> (ls2 != null) ? List.of(mapper.apply(ls1), mapper.apply(ls2))
                : List.of(mapper.apply(ls1)), (expectedSize, actualSize) -> false, null);
        // return this.genericSingleFlattener((innerList) ->
        // List.of(mapper.apply(innerList)));
    }

    private List<Integer> sumTwoVector(List<Integer> v1, List<Integer> v2) {
        List<Integer> toRet = new ArrayList<>();
        for (int i = 0; i < v1.size(); i++) {
            toRet.add(v1.get(i) + v2.get(i));
        }
        return toRet;
    }

    @Override
    public Flattener<Integer, Integer> sumVectors() {
        return this.<Integer, Integer>genericFlatter((ls1, ls2) -> sumTwoVector(ls1, ls2),
                (expectedSize, actualSize) -> actualSize != expectedSize,
                (state) -> sumTwoVector(state.stream().limit(state.size() / 2).collect(Collectors.toList()),
                        state.stream().skip(state.size() / 2).collect(Collectors.toList())));
    }

}
