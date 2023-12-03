package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class AcceptorFactoryAdvancedImpl implements AcceptorFactory{

    private <E,R> Acceptor<E,R> createAcceptor(BiPredicate<E,List<E>> checkAccept,Function<List<E>,Optional<R>> endGiver){
        return new Acceptor<E,R>() {
            private List<E> acceptedElements= new ArrayList<>();

            @Override
            public boolean accept(E e) {
                boolean response = checkAccept.test(e,acceptedElements);
                if(response){
                    acceptedElements.add(e);
                }
                return response;
            }

            @Override
            public Optional<R> end() {
                return endGiver.apply(acceptedElements);
            }
            
        };
    }

    @Override
    public Acceptor<String, Integer> countEmptyStringsOnAnySequence() {
        return this.createAcceptor((string,lstAccepted)->(string instanceof String),(lstAccepted)->{
            int counter = 0;
            for (int i = 0; i < lstAccepted.size(); i++) {
                if(((String)lstAccepted.get(i)).isEmpty()){
                    counter++;
                }
            }
            return Optional.of(counter);
        } );
    }

    @Override
    public Acceptor<Integer, String> showAsStringOnlyOnIncreasingSequences() {
        return this.createAcceptor((value,lst)->{
            if(lst.isEmpty()){
                return true;
            }
            if(lst.get(lst.size()-1)>=value){
                return false;
            }
            return true;
        }, (lst)->{
            if(lst.size()<3){
                return Optional.empty();
            }
            return Optional.of(lst.get(0) +":"+ lst.get(1) +":"+ lst.get(2));
        });
    }

    @Override
    public Acceptor<Integer, Integer> sumElementsOnlyInTriples() {
        return this.createAcceptor((value,lst)->{
            if(lst.size()+1<=3){
                return true;
            }else{
                lst.add(value);
                return false;
            }
        }, (lst)->{
            if(lst.size()!=3){
                return Optional.empty();
            }
            return Optional.of(lst.get(0) + lst.get(1) + lst.get(2));
        });
    }

    @Override
    public <E, O1, O2> Acceptor<E, Pair<O1, O2>> acceptBoth(Acceptor<E, O1> a1, Acceptor<E, O2> a2) {
        return this.createAcceptor((value,lst)->
            a1.accept((E)value)&&a2.accept((E)value)
        , (lst)->{
            Optional<O1> end1 = a1.end();
            Optional<O2> end2 = a2.end();

            if(end1.isPresent()&&end2.isPresent()){
                return Optional.of(new Pair<O1,O2>(a1.end().get(), a2.end().get()));
            }
            return Optional.empty();
        });
    }

    @Override
    public <E, O, S> Acceptor<E, O> generalised(S initial, BiFunction<E, S, Optional<S>> stateFun,
            Function<S, Optional<O>> outputFun) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'generalised'");
    }
    
}
