package es1;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class FlattenerFactoryImpl implements FlattenerFactory {

    private <I, O> Flattener<I, O> generic(BiFunction<List<I>, List<I>, List<O>> f, boolean combine,
            boolean vectorSum) {
        return new Flattener<I, O>() {

            

            @Override
            public List<O> flatten(List<List<I>> list) {
                List<O> toRet = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    if (combine || vectorSum) {
                        if (i + 1 < list.size()) {
                            toRet.addAll(f.apply(list.get(i), list.get(i + 1)));
                            i++;
                        } else {
                            toRet.addAll(f.apply(list.get(i), null));
                        }
                    } else {
                        toRet.addAll(f.apply(list.get(i), null));
                    }
                }

                

                return toRet;
            }

        };
    }

    /**
     * @return a Flattener that turns each inner list in its sum
     *         e.g.: [[e1,e2,e3],[e4,e5,e6],[e7,e8]] -->
     *         (e1+e2+e3),(e4+e5+e6),(e7+e8)
     */
    public Flattener<Integer, Integer> sumEach() {
        return this.generic((lst1, lst2) -> {
            int sum = 0;
            for (int i : lst1) {
                sum += i;
            }

            return List.of(sum);
        }, false, false);
    }

    /**
     * @return a generic Flattener that appends all inner lists
     *         e.g.: [[e1,e2,e3],[e4,e5,e6],[e7,e8]] --> [e1,e2,e3,e4,e5,e6,e7,e8]
     */
    public <X> Flattener<X, X> flattenAll() {
        return this.generic((lst, lst2) -> {
            return lst;
        }, true, false);
    }

    /**
     * @return a Flattener that concats all strings of pairs of inner lists
     *         e.g.: [[s1,s2,s3],[s4],[s5,s6],[s7],[s8]] --> [s1+s2+s3+s4, s5+s6+s7,
     *         s8]
     */
    public Flattener<String, String> concatPairs() {
        return this.generic((lst1, lst2) -> {
            String sum = "";
            for (String i : lst1) {
                sum += i;
            }

            if (lst2 != null) {
                for (String string : lst2) {
                    sum += string;
                }
            }

            return List.of(sum);
        }, false, true);
    }

    /**
     * @return a generic Flattener that turns each list into a single element of the
     *         output
     *         e.g.: [[s1,s2,s3],[s4],[s5,s6]] --> [f([s1,s2,s3]), f([s4]),
     *         f([s5,s6])]
     */
    public <I, O> Flattener<I, O> each(Function<List<I>, O> mapper) {
        return this.generic((lst1, lst2) -> {
            return List.of(mapper.apply(lst1));
        }, false, false);
    }

    /**
     * @return a Flattener that implements algebraic sum of vectors of integers
     *         You can assume all inner lists have same size
     *         e.g.: [[s1,s2],[s3,s4],[s5,s6],[s7,s8]] --> [s1+s3+s5+s7,
     *         s2+s4+s6+s8]
     */
    public Flattener<Integer, Integer> sumVectors() {
        return this.generic((lst1, lst2) -> {
            List<Integer> toRet = new ArrayList<>();

            if (lst1.get(0) instanceof Integer) {
                if (lst1.get(0) instanceof Integer && lst2 != null) {
                    for (int i = 0; i < lst1.size(); i++) {
                        toRet.add((Integer) lst1.get(i) + (Integer) lst2.get(i));
                    }
                } else {
                    for (int i = 0; i < lst1.size(); i++) {
                        toRet.add((Integer) lst1.get(i));
                    }
                }
            }

            return toRet;
        }, false, true);
    }
}
