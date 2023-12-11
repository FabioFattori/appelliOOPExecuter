package a01b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

public class MathematicalFunctionsFactoryImpl implements MathematicalFunctionsFactory {

    private <A, B> MathematicalFunction<A, B> createGenericFunction(Predicate<A> isInDomain,
            Function<A, Optional<B>> applyF) {
        return new MathematicalFunction<A, B>() {

            @Override
            public Optional<B> apply(A a) {
                if (inDomain(a)) {
                    return applyF.apply(a);
                }
                return Optional.empty();
            }

            @Override
            public boolean inDomain(A a) {
                return isInDomain.test(a);
            }

            @Override
            public <C> MathematicalFunction<A, C> composeWith(MathematicalFunction<B, C> f) {
                return createGenericFunction(isInDomain, (a) -> (f.apply(this.apply(a).get())));
            }

            @Override
            public MathematicalFunction<A, B> withUpdatedValue(A domainValue, B codomainValue) {
                return createGenericFunction((a) -> (a.equals(domainValue)) ? true : isInDomain.test(a),
                        (a) -> (a.equals(domainValue)) ? Optional.of(codomainValue) : applyF.apply(a));
            }

            @Override
            public MathematicalFunction<A, B> restrict(Set<A> subDomain) {
                // TODO Auto-generated method stub
                throw new UnsupportedOperationException("Unimplemented method 'restrict'");
            }

        };
    }

    @Override
    public <A, B> MathematicalFunction<A, B> constant(Predicate<A> domainPredicate, B value) {
        return this.createGenericFunction(domainPredicate, (a) -> Optional.of(value));
    }

    @Override
    public <A, B> MathematicalFunction<A, A> identity(Predicate<A> domainPredicate) {
        return this.createGenericFunction(domainPredicate, (a) -> Optional.of(a));
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromFunction(Predicate<A> domainPredicate, Function<A, B> function) {
        return this.createGenericFunction(domainPredicate, (a) -> Optional.of(function.apply(a)));
    }

    @Override
    public <A, B> MathematicalFunction<A, B> fromMap(Map<A, B> map) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'fromMap'");
    }

}
