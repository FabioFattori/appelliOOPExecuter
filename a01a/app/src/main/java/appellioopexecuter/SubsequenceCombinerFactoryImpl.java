package appellioopexecuter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SubsequenceCombinerFactoryImpl implements SubsequenceCombinerFactory {

    @Override
    public SubsequenceCombiner<Integer, Integer> tripletsToSum() {
        return new SubsequenceCombiner<Integer, Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                final List<Integer> toRet = new ArrayList<>();
                try {
                    for (int i = 0; i < list.size(); i += 3) {
                        int sum = list.get(i);
                        if (i + 1 <= list.size() - 1) {
                            sum += list.get(i + 1);
                        }
                        if (i + 2 <= list.size() - 1) {
                            sum += list.get(i + 2);
                        }
                        toRet.add(sum);
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }

                return toRet;
            }

        };
    }

    @Override
    public <X> SubsequenceCombiner<X, List<X>> tripletsToList() {
        return new SubsequenceCombiner<X, List<X>>() {

            @Override
            public List<List<X>> combine(List<X> list) {
                final List<List<X>> toRet = new ArrayList<>();
                try {
                    for (int i = 0; i < list.size(); i += 3) {

                        toRet.add(new ArrayList<>());

                        toRet.get(toRet.size() - 1).add(list.get(i));
                        if (i + 1 <= list.size() - 1) {
                            toRet.get(toRet.size() - 1).add(list.get(i + 1));
                        }
                        
                        if (i + 2 <= list.size() - 1) {
                        toRet.get(toRet.size() - 1).add(list.get(i + 2));
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }

                return toRet;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, Integer> countUntilZero() {
        return new SubsequenceCombiner<Integer, Integer>() {

            @Override
            public List<Integer> combine(List<Integer> list) {
                final List<Integer> toRet = new ArrayList<>();
                try {
                    int counter = 0;
                    for (Integer integer : list) {
                        if (integer != 0) {
                            counter++;
                        } else {
                            toRet.add(counter);
                            counter = 0;

                        }
                    }
                    if(counter != 0) {
                        toRet.add(counter);
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }

                return toRet;
            }

        };
    }

    @Override
    public <X, Y> SubsequenceCombiner<X, Y> singleReplacer(Function<X, Y> function) {
        return new SubsequenceCombiner<X, Y>() {

            @Override
            public List<Y> combine(List<X> list) {
                final List<Y> toRet = new ArrayList<>();
                try {
                    for (int i = 0; i < list.size(); i++) {

                        toRet.add(function.apply(list.get(i)));
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }

                return toRet;
            }

        };
    }

    @Override
    public SubsequenceCombiner<Integer, List<Integer>> cumulateToList(int threshold) {
        return new SubsequenceCombiner<Integer, List<Integer>>() {

            @Override
            public List<List<Integer>> combine(List<Integer> list) {
                final List<List<Integer>> toRet = new ArrayList<>();
                try {
                    int sum = 0;
                    toRet.add(new ArrayList<>());
                    for (Integer integer : list) {
                        sum += integer;

                        toRet.get(toRet.size() - 1).add(integer);

                        if (sum >= threshold) {
                            sum = 0;
                            toRet.add(new ArrayList<>());
                        }
                    }
                } catch (Exception e) {
                    throw new IllegalStateException();
                }

                return toRet;
            }

        };
    }

}
