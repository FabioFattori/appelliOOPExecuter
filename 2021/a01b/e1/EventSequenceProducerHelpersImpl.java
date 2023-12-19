package a01b.e1;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    private <E> Optional<E> getElement(Supplier<E> supplier) {

        try {
            return Optional.of(supplier.get());
        } catch (Exception ex) {
            return Optional.empty();
        }

    }

    private <E> Stream<Pair<Double,E>> fromSequenceToStream(EventSequenceProducer<E> sequence){
        return Stream.generate(()->getElement(sequence::getNext)).takeWhile(Optional::isPresent).map(Optional::get);
    }

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                if (iterator.hasNext()) {
                    return iterator.next();
                }
                throw new NoSuchElementException("iterator has finisched");
            }

        };
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {
        return this.fromSequenceToStream(sequence).filter((e)->e.get1()>=fromTime&&e.get1()<=toTime).map((e)->e.get2()).collect(Collectors.toList());
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return this.window(sequence, 0, Integer.MAX_VALUE);
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        return this.fromSequenceToStream(sequence).filter((e)->e.get1()>time).findFirst();
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        return this.fromIterator(this.fromSequenceToStream(sequence).filter((e)->predicate.test(e.get2())).iterator());
    }

}
