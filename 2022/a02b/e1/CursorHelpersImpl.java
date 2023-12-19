package a02b.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> genericCursor(Function<Integer, X> getIndexElement, Predicate<Integer> canAdvance) {
        return new Cursor<X>() {

            Integer index = 0;

            @Override
            public X getElement() {
                return getIndexElement.apply(index);
            }

            @Override
            public boolean advance() {
                if (canAdvance.test(index)) {
                    index++;
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return this.genericCursor((index) -> list.get(index), (index) -> index < list.size() - 1);
    }

    @Override
    public Cursor<Integer> naturals() {
        return this.genericCursor((index) -> index, (index) -> index < Integer.MAX_VALUE);
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return this.genericCursor((index) -> input.getElement(), (index) -> (index < max - 1 && input.advance()));
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        do {
            consumer.accept(input.getElement());
        } while (input.advance());
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        var list = new ArrayList<X>();
        this.forEach(take(input, max), list::add);
        return list;


        // Cursor<X> taker = this.take(input, max);
        // List<X> toRet = new ArrayList<>();
        // do  {
        //     toRet.add(taker.getElement());
        // }while(taker.advance());

        // return toRet;
    }

}
