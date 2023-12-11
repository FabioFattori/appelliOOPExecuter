package a01a.e1;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class TreeFactoryImpl implements TreeFactory{

    private <E> Tree<E> createGenericTree(Supplier<E> getRoot,Supplier<List<Tree<E>>> getChildrens){
        return new Tree<E>() {

            @Override
            public E getRoot() {
                return getRoot.get();
            }

            @Override
            public List<Tree<E>> getChildren() {
                return getChildrens.get();
            }

            @Override
            public Set<E> getLeafs() {
                List<E> toRet=visitChildren(false);

                if(toRet.isEmpty()){
                    toRet.add(this.getRoot());
                }
                return Set.copyOf(toRet);
            }

            private List<E> visitChildren(boolean insertRoot){
                List<E> toRet=new ArrayList<>();
                for (Tree<E> children : getChildren()) {
                    if(children.getChildren().isEmpty()){
                        toRet.add(children.getRoot());
                    }else{
                        for (Tree<E> child : children.getChildren()) {
                            toRet.add(child.getRoot());
                        }
                    }

                    if(insertRoot && !toRet.contains(children.getRoot())){
                        toRet.add(children.getRoot());
                    }
                }
                return toRet;
            }

            

            @Override
            public Set<E> getAll() {
                List<E> toRet = visitChildren(true);
                if(!toRet.contains(this.getRoot())){
                    toRet.add(this.getRoot());
                }

                return Set.copyOf(toRet);
            }
            
        };
    }

    @Override
    public <E> Tree<E> singleValue(E root) {
        return this.createGenericTree(()->root,()->new ArrayList<>());
    }

    @Override
    public <E> Tree<E> twoChildren(E root, Tree<E> child1, Tree<E> child2) {
        return this.createGenericTree(()->root, ()->List.of(child1,child2));
    }

    @Override
    public <E> Tree<E> oneLevel(E root, List<E> children) {
        return this.createGenericTree(()->root, ()->{
            List<Tree<E>> toRet = new ArrayList<>();
            for (E e : children) {
                toRet.add(this.createGenericTree(()->e, ()->new ArrayList<>()));
            }
            return toRet;
        });
    }

    @Override
    public <E> Tree<E> chain(E root, List<E> list) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'chain'");
    }
    
}
