package a03a.e1;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DecisionChainFactoryImpl implements DecisionChainFactory {

    private <A, B> DecisionChain<A, B> generic(Function<A, Optional<B>> elaborateInput,
            Function<A, DecisionChain<A, B>> getNext) {
        return new DecisionChain<A, B>() {

            @Override
            public Optional<B> result(A a) {
                return elaborateInput.apply(a);
            }

            @Override
            public DecisionChain<A, B> next(A a) {
                return getNext.apply(a);
            }

        };
    }

    @Override
    public <A, B> DecisionChain<A, B> oneResult(B b) {
        return this.generic(a -> Optional.of(b), null);
    }

    @Override
    public <A, B> DecisionChain<A, B> simpleTwoWay(Predicate<A> predicate, B positive, B negative) {
        return this.generic(a -> Optional.empty(),
                (a) -> (predicate.test(a)) ? this.oneResult(positive) : this.oneResult(negative));
    }

    @Override
    public <A, B> DecisionChain<A, B> enumerationLike(List<Pair<A, B>> mapList, B defaultReply) {
        return this.generic(a -> (a == mapList.get(0).get1()) ? Optional.of(mapList.get(0).get2()) : Optional.empty(),
                a -> (mapList.stream().filter(pair -> pair.get1() == a).count() != 0)
                        ? this.enumerationLike(mapList.stream().skip(1).collect(Collectors.toList()), defaultReply)
                        : this.oneResult(defaultReply));
    }

    @Override
    public <A, B> DecisionChain<A, B> twoWay(Predicate<A> predicate, DecisionChain<A, B> positive,
            DecisionChain<A, B> negative) {
        return this.generic(a -> Optional.empty(), a -> (predicate.test(a)) ? positive : negative);
    }

    @Override
    public <A, B> DecisionChain<A, B> switchChain(List<Pair<Predicate<A>, B>> cases, B defaultReply) {
        return this.generic((a) -> Optional.empty(),
                (a) -> (cases.isEmpty()) ? this.oneResult(defaultReply)
                        : (cases.get(0).get1().test(a)) ? this.oneResult(cases.get(0).get2())
                                : this.switchChain(cases.stream().skip(1).collect(Collectors.toList()), defaultReply));
    }

}
