package es1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import com.google.common.base.Predicate;

public class CursorHelpersImpl implements CursorHelpers {

    private <X> Cursor<X> genericCursor(Function<Integer, X> getElementF, Predicate<Integer> check) {
        return new Cursor<X>() {
            private int index = 0;

            @Override
            public X getElement() {
                return getElementF.apply(index);
            }

            @Override
            public boolean advance() {

                if (check.test(index + 1)) {
                    index++;
                    return true;
                } else {
                    return false;
                }
            }

        };
    }

    @Override
    public <X> Cursor<X> fromNonEmptyList(List<X> list) {
        return this.genericCursor((index) -> list.get(index), (index) -> list.size() > index);
    }

    @Override
    public Cursor<Integer> naturals() {
        return this.genericCursor((index) -> index, (index) -> true);
    }

    @Override
    public <X> Cursor<X> take(Cursor<X> input, int max) {
        return this.genericCursor((index) -> input.getElement(), (index) -> index < max && input.advance());
    }

    @Override
    public <X> void forEach(Cursor<X> input, Consumer<X> consumer) {
        consumer.accept(input.getElement());
        cycleOnAllCursor(input, consumer, (i)->true);
        
    }

    private <X> void cycleOnAllCursor(Cursor<X> input,Consumer<X> consumer,Predicate<Integer> condition){
        int nCycles=1;
        while (input.advance() && condition.test(nCycles)) {
            consumer.accept(input.getElement()); 
            nCycles++;
        }
    }

    @Override
    public <X> List<X> toList(Cursor<X> input, int max) {
        List<X> toRet = new ArrayList<>();
        toRet.add(input.getElement());
        cycleOnAllCursor(input, (element)->toRet.add(input.getElement()), (i)->i<max);
        
        return toRet;
    }

}
