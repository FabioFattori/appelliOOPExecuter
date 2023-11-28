package a02a;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import com.google.common.base.Predicate;

public class RecursiveIteratorHelpersImpl implements RecursiveIteratorHelpers {

    private <X> RecursiveIterator<X> genericIterator(Function<Integer, X> f,
            Predicate<Integer> isCurrentIndexMinOfListLenght) {
        return new RecursiveIterator<X>() {
            private int currentIndex = 0;

            @Override
            public X getElement() {
                return f.apply(currentIndex);
            }

            @Override
            public RecursiveIterator<X> next() {
                currentIndex++;
                if (isCurrentIndexMinOfListLenght.test(currentIndex)) {

                    return this;
                } else {
                    return null;
                }
            }

        };
    }

    @Override
    public <X> RecursiveIterator<X> fromList(List<X> list) {
        if (list.isEmpty()) {
            return null;
        } else {
            return this.genericIterator((index) -> list.get(index), (index) -> (list.size() > index));
        }
    }

    @Override
    public <X> List<X> toList(RecursiveIterator<X> input, int max) {
        final List<X> toRet = new ArrayList<>();
        if (max >= 1) {
            toRet.add(input.getElement());
            for (int i = 1; i < max && input.next() != null; i++) {
                toRet.add(input.getElement());
            }
        }

        return toRet;
    }

    @Override
    public <X, Y> RecursiveIterator<Pair<X, Y>> zip(RecursiveIterator<X> first, RecursiveIterator<Y> second) {
        return this.genericIterator((index)->new Pair<>(first.getElement(), second.getElement()), (index)->{
            return first.next() != null && second.next() != null;
        });
    }

    @Override
    public <X> RecursiveIterator<Pair<X, Integer>> zipWithIndex(RecursiveIterator<X> iterator) {
        return this.genericIterator((index)->new Pair<>(iterator.getElement(), index), (index)->iterator.next()!=null);
    }

    @Override
    public <X> RecursiveIterator<X> alternate(RecursiveIterator<X> first, RecursiveIterator<X> second) {
       
      return new RecursiveIterator<X>() {
        RecursiveIterator<X> currentIterator = first; 

        @Override
        public X getElement() {
            X toRet = currentIterator.getElement();
            currentIterator=currentIterator.next();
            return toRet;
        }

        @Override
        public RecursiveIterator<X> next() {
            
            if(currentIterator == first){
                currentIterator = second;
                if(currentIterator == null){
                    currentIterator = first;
                }
            }else{
                currentIterator = first;
                if(currentIterator == null){
                    currentIterator = second;
                }
            }

            return this;
        }


        
      };
    }

}
