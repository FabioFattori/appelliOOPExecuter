package a03b.e1;

import java.util.List;
import java.util.function.*;
import java.util.stream.*;

public class LensFactoryImpl implements LensFactory {

    private <A, B> Lens<A, B> generic(Function<A, B> getter, BiFunction<B, A, A> setter) {
        return new Lens<A, B>() {

            @Override
            public B get(A s) {
                return getter.apply(s);
            }

            @Override
            public A set(B a, A s) {
                return setter.apply(a, s);
            }

        };
    }

    @Override
    public <E> Lens<List<E>, E> indexer(int i) {
        return this.generic((lst) -> lst.get(i),
                (newEl, lst) -> lst.stream().map(e -> (lst.get(i).equals(e)) ? newEl : e).collect(Collectors.toList()));
    }

    @Override
    public <E> Lens<List<List<E>>, E> doubleIndexer(int i, int j) {
        return this.generic((lst) -> lst.get(i).get(j), (el, lst) -> lst.stream()
                .map(insideList -> (lst.get(i).equals(insideList)) ? insideList.stream()
                        .map(e -> (lst.get(i).get(j).equals(e)) ? el : e).collect(Collectors.toList()) : insideList)
                .collect(Collectors.toList()));
    }

    @Override
    public <A, B> Lens<Pair<A, B>, A> left() {
        return this.generic((pair) -> pair.get1(), (el, pair) -> new Pair<>(el, pair.get2()));
    }

    @Override
    public <A, B> Lens<Pair<A, B>, B> right() {
        return this.generic((pair) -> pair.get2(), (el, pair) -> new Pair<>(pair.get1(), el));
    }

    @Override
    public <A, B, C> Lens<List<Pair<A, Pair<B, C>>>, C> rightRightAtPos(int i) {
        return this.generic((lst) -> lst.get(i).get2().get2(),
                (el, lst) -> lst.stream()
                        .map(firstPair -> (lst.get(i).equals(firstPair))
                                ? new Pair<>(firstPair.get1(), new Pair<>(firstPair.get2().get1(), el))
                                : firstPair)
                        .collect(Collectors.toList()));
    }

}
