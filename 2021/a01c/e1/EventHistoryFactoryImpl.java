package a01c.e1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventHistoryFactoryImpl implements EventHistoryFactory {

    private <E> EventHistory<E> generic(Iterator<Pair<Double, E>> iter, Pair<Double, E> initial) {
        return new EventHistory<E>() {
            Pair<Double, E> current = initial;

            @Override
            public double getTimeOfEvent() {
                return current.get1();
            }

            @Override
            public E getEventContent() {
                return current.get2();
            }

            @Override
            public boolean moveToNextEvent() {
                if (iter.hasNext()) {
                    current = iter.next();
                    if (current.equals(initial)) {
                        current = iter.next();
                    }
                    return true;
                }
                return false;
            }

        };
    }

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        return this.generic(
                map.entrySet().stream().map(e -> new Pair<>(e.getKey(), e.getValue()))
                        .sorted((e1, e2) -> Double.compare(e1.get1(), e2.get1())).iterator(),
                map.entrySet().stream().map(e -> new Pair<>(e.getKey(), e.getValue()))
                        .sorted((e1, e2) -> Double.compare(e1.get1(), e2.get1())).findFirst().get());
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        List<Pair<Double, E>> lst = new ArrayList<>();
        while (times.hasNext() && content.hasNext()) {
            lst.add(new Pair<Double, E>(times.next(), content.next()));
        }
        return this.generic(lst.iterator(), lst.get(0));
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return this.generic(
                content.stream().map((e) -> new Pair<Double, E>(initial + delta * content.indexOf(e), e)).iterator(),
                new Pair<Double, E>(initial, content.get(0)));
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        List<Pair<Double, E>> lst = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            double time = Stream.iterate(0.0, x -> x + Math.random()).limit(size).collect(Collectors.toList()).get(i);
            lst.add(new Pair<>(time, content.get()));
        }
        return this.generic(lst.iterator(), lst.get(0));
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        List<Pair<Double, String>> lst = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            lst.add(new Pair<Double, String>(Double.parseDouble(line.split(":")[0]), line.split(":")[1]));
        }
        return this.generic(lst.iterator(), lst.get(0));
    }

}
