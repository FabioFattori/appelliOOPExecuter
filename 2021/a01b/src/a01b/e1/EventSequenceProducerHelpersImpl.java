package a01b.e1;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class EventSequenceProducerHelpersImpl implements EventSequenceProducerHelpers {

    private <E> EventSequenceProducer<E> generic(Supplier<Pair<Double, E>> eventsGiver) {
        return new EventSequenceProducer<E>() {

            @Override
            public Pair<Double, E> getNext() throws NoSuchElementException {
                return eventsGiver.get();
            }

        };
    }

    @Override
    public <E> EventSequenceProducer<E> fromIterator(Iterator<Pair<Double, E>> iterator) {
        return this.generic(() -> iterator.next());
    }

    @Override
    public <E> List<E> window(EventSequenceProducer<E> sequence, double fromTime, double toTime) {

        List<E> toRet = new ArrayList<>();
        do {
            try {
                var response = sequence.getNext();
                if (response.get1() >= fromTime && response.get1() <= toTime) {
                    toRet.add(response.get2());
                }
            } catch (Exception e) {
                return toRet;
            }
        } while (true);
    }

    @Override
    public <E> Iterable<E> asEventContentIterable(EventSequenceProducer<E> sequence) {
        return new Iterable<E>() {

            @Override
            public Iterator<E> iterator() {
                return new Iterator<E>() {

                    Pair<Double, E> response;

                    @Override
                    public boolean hasNext() {
                        try {
                            response = sequence.getNext();
                            return true;
                        } catch (Exception e) {
                            return false;
                        }
                    }

                    @Override
                    public E next() {
                        return response.get2();
                    }

                };
            }

        };
    }

    @Override
    public <E> Optional<Pair<Double, E>> nextAt(EventSequenceProducer<E> sequence, double time) {
        try {
            Pair<Double, E> toRet;
            do {
                toRet = sequence.getNext();
            } while (toRet.get1() < time);
            return Optional.of(toRet);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public <E> EventSequenceProducer<E> filter(EventSequenceProducer<E> sequence, Predicate<E> predicate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'filter'");
    }

}
