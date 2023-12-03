package e1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.text.html.Option;

public class EventHistoryFactoryImpl implements EventHistoryFactory{

    private <E> EventHistory<E> genericEventHistory(BiFunction<Integer,Pair<Double,E>,Optional<Pair<Double,E>>> goNext,Supplier<Pair<Double,E>> startValue){
        return new EventHistory<E>() {

            private Pair<Double,E> currentEvent = startValue.get();
            private int counter=0;

            @Override
            public double getTimeOfEvent() {
                return currentEvent.get1();
            }

            @Override
            public E getEventContent() {
                return currentEvent.get2();
            }

            @Override
            public boolean moveToNextEvent() {
                var response = goNext.apply(counter,currentEvent);
                if(response.isEmpty()){
                    return false;
                }
                counter++;
                currentEvent=response.get();
                return true;
            }
            
        };
    }

    @Override
    public <E> EventHistory<E> fromMap(Map<Double, E> map) {
        
        final Iterator<Pair<Double,E>> iteratorKey = map.entrySet()
        .stream()
        .sorted( (p1,p2) -> p1.getKey()-p2.getKey() > 0.0 ? 1 : -1)
        .map( p -> new Pair<>(p.getKey(),p.getValue()))
        .iterator();
        return this.genericEventHistory((counter,pastEvent)->{
            try {
                var response = iteratorKey.next();

            return Optional.of(new Pair<Double,E>(response.get1(),response.get2()));
            } catch (Exception e) {
                return Optional.empty();
            }

        },()->{

                var response = iteratorKey.next();

            return new Pair<Double,E>(response.get1(),response.get2());
            
        });
    }

    @Override
    public <E> EventHistory<E> fromIterators(Iterator<Double> times, Iterator<E> content) {
        return this.genericEventHistory((counter,pastEvent)->{
            if(times.hasNext()&&content.hasNext()){
                return Optional.of(new Pair<Double,E>(times.next(),content.next()));
            }
            return Optional.empty();
        }, ()->new Pair<Double,E>(times.next(),content.next()));
    }

    @Override
    public <E> EventHistory<E> fromListAndDelta(List<E> content, double initial, double delta) {
        return this.genericEventHistory((counter,pastEvent)->{
            int index = content.indexOf(pastEvent.get2());
            if(index+1<content.size()){
                return Optional.of(new Pair<Double,E>((index+1)*delta+initial,content.get(index+1)));
            }
            return Optional.empty();
        }, ()->new Pair<Double,E>(initial, content.get(0)));
    }

    @Override
    public <E> EventHistory<E> fromRandomTimesAndSupplier(Supplier<E> content, int size) {
        return this.genericEventHistory((counter,pastEvent)->{
            if(counter+1<size){
                return Optional.of(new Pair<Double,E>(Math.random()-pastEvent.get1(),content.get()));
            }
            return Optional.empty();
        }, ()->new Pair<Double,E>(Math.random(),content.get()));
    }

    @Override
    public EventHistory<String> fromFile(String file) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(file)));
        return this.genericEventHistory((counter,pastEvent)->{
            try {
                return this.getOneLineFromFile(file,br);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return Optional.empty();
            }
        }, ()->{
            try {
                return this.getOneLineFromFile(file,br).get();
            } catch (IOException e) {
                return null;
            }
        });
    }

    private Optional<Pair<Double,String>> getOneLineFromFile(String File,BufferedReader br) throws IOException{
        
        String line;
        if((line=br.readLine())!=null){
            String[] arr=line.split(":",2);
            return Optional.of(new Pair<Double,String>(Double.parseDouble(arr[0]),arr[1]));
        }
        return Optional.empty();
    }
    
}
