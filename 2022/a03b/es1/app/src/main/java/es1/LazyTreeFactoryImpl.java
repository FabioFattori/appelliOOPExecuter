package es1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import com.google.common.base.Function;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    private <X> LazyTree<X> genericThree(Supplier<Optional<X>> getRoot, Function<X, X> getLeft, Function<X, X> getRight,
            boolean isNotEmpty) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return isNotEmpty;
            }

            @Override
            public X root() {

                return getRoot.get().orElse(null);

            }

            @Override
            public LazyTree<X> left() {
                return genericThree(
                        () -> (getLeft.apply(this.root()) == null) ? Optional.empty()
                                : Optional.of(getLeft.apply(this.root())),
                        getLeft, getRight, (getLeft.apply(this.root()) == null) ? false : true);
            }

            @Override
            public LazyTree<X> right() {

                return genericThree(
                        () -> (getRight.apply(this.root()) == null) ? Optional.empty()
                                : Optional.of(getRight.apply(this.root())),
                        getLeft, getRight, (getRight.apply(this.root()) == null) ? false : true);
            }

        };
    }

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return this.genericThree(() -> Optional.of(value), (X) -> value, (X) -> value, true);
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        return this.genericThree(() -> Optional.of(root), (key) -> (map.get(key) != null) ? map.get(key).getX() : null,
                (key) -> (map.get(key) != null) ? map.get(key).getY() : null, true);
    }

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return (this.root() != null);
            }

            @Override
            public X root() {
                return root.orElse(null);
            }

            @Override
            public LazyTree<X> left() {
                return leftSupp.get();
            }

            @Override
            public LazyTree<X> right() {
                return rightSupp.get();
            }

        };
    }

    @Override
    public <X> LazyTree<X> fromTwoIterations(X root, UnaryOperator<X> leftOp, UnaryOperator<X> rightOp) {
        return this.genericThree(() -> Optional.of(root), (X) -> leftOp.apply(X), (X) -> rightOp.apply(X), true);
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        
        return tree;
    }

}
