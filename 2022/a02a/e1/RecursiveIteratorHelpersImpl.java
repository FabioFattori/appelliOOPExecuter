package a02a.e1;

import java.util.*;
import java.util.function.*;;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    private <X> RecursiveIterator<X> genericIterator(Function<Integer, Pair<X, Integer>> getElementGivenIndex,
            Supplier<Integer> checkEnd) {
        return new RecursiveIterator<X>() {
            int currentIndex = 0;
            int maxIndex = -1;

            @Override
            public X getElement() {
                Pair<X, Integer> res = getElementGivenIndex.apply(currentIndex);
                this.maxIndex = res.getY();
                return res.getX();
            }

            @Override
            public RecursiveIterator<X> next() {
                currentIndex++;
                return (currentIndex < this.maxIndex && checkEnd.get() == 1) ? this : null;
            }

        };
    }

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        return (list.isEmpty()) ? null
                : this.genericIterator((index) -> new Pair<>(list.get(index), list.size()), () -> 1);
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        List<X> toRet = new ArrayList<>();
        int counter = 0;
        while (input != null && counter < max) {
            toRet.add(input.getElement());
            input = input.next();
            counter++;
        }

        return toRet;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return this.genericIterator(
                (index) -> new Pair<>(new Pair<X, Y>(first.getElement(), second.getElement()), index + 2),
                () -> (first.next() != null && second.next() != null) ? 1 : 0);
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return this.genericIterator(
                (index) -> new Pair<>(new Pair<X, Integer>(iterator.getElement(), index), index + 2),
                () -> (iterator.next() != null) ? 1 : 0);
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
        return null;
    }

}
