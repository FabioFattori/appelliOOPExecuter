package a03b.e1;

import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class LazyTreeFactoryImpl implements LazyTreeFactory {

    @Override
    public <X> LazyTree<X> constantInfinite(X value) {
        return this.cons(Optional.of(value), () -> constantInfinite(value), () -> constantInfinite(value));
    }

    private <X> LazyTree<X> empty() {
        return cons(Optional.empty(), null, null);
    }

    @Override
    public <X> LazyTree<X> fromMap(X root, Map<X, Pair<X, X>> map) {
        var p = map.get(root);
        return this.cons(Optional.of(root), () -> (p != null) ? fromMap(map.get(root).getX(), map) : empty(),
                () -> (p != null) ? fromMap(map.get(root).getY(), map) : empty());
    }

    @Override
    public <X> LazyTree<X> cons(Optional<X> root, Supplier<LazyTree<X>> leftSupp, Supplier<LazyTree<X>> rightSupp) {
        return new LazyTree<X>() {

            @Override
            public boolean hasRoot() {
                return (root.isPresent());
            }

            @Override
            public X root() {
                return root.get();
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
        return this.cons(Optional.of(root), () -> fromTwoIterations(leftOp.apply(root), leftOp, rightOp),
                () -> fromTwoIterations(rightOp.apply(root), leftOp, rightOp));
    }

    @Override
    public <X> LazyTree<X> fromTreeWithBound(LazyTree<X> tree, int bound) {
        return this.cons((bound>0)?Optional.of(tree.root()):Optional.empty(),()->(bound>0)?fromTreeWithBound(tree.left(), bound-1):empty(), ()->(bound>0)?fromTreeWithBound(tree.right(), bound-1):empty());
    }

}
