package com.parkey.reactor.exam;

import lombok.AllArgsConstructor;
import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class TestDelay {
    public static void main(String[] args) {

        User user1 = new User(1,"1");
        User user2 = new User(1,"1");
        User user3 = new User(2,"2");
        User user4 = new User(3,"3");
        User user5 = new User(3,"3");

        List<User> users = Arrays.asList(user1, user2, user3, user4, user5);

        Flux.fromIterable(users)
                .collectMap(User::getId)




//                ) // concatMap으로 순서보장
//                .doOnNext(integerUserGroupedFlux -> {
//                    System.out.println(integerUserGroupedFlux.blockFirst().getName());
//                })
                .subscribe(integerListMap ->
                    integerListMap.forEach((integer, users1) -> System.out.println("key 1 : " + integer + " users2 : "+ users1)));
//                .flatMap(Function.identity())
//                .collectMultimap(User::getId, user -> List::new);

//        Mono<HashMap<Integer, List<User>>> map = mapMono.map(
//                integerCollectionMap -> {
//                    return getIntegerListHashMap(integerCollectionMap);
//                }
//
//        );
//        map
//                .subscribe(integerListMap ->
//                    integerListMap.forEach((integer, users1) -> System.out.println("key 1 : " + integer + " users2 : "+ users1))
//                );


//        mapMono
////                .flatMap(integerCollectionMap -> makeArray(integerCollectionMap))
//                .subscribe(integerListMap ->
//                    integerListMap.forEach((integer, users1) -> System.out.println("key 1 : " + integer + " users2 : "+ users1))
//                );







    }

    private static HashMap<Integer, List<User>> getIntegerListHashMap(Map<Integer, Collection<User>> integerCollectionMap) {
        HashMap<Integer, List<User>> integerListHashMap = new HashMap<>();
        integerCollectionMap.forEach((integer, users1) -> {
            System.out.println("dsafkjsakldj");
            ArrayList<User> objects = new ArrayList<>();
            objects.addAll(users1);
            integerListHashMap.put(integer, objects);
        });

        return integerListHashMap;
    }

//    Function<Mono<HashMap<Integer, Collection<User>>>, Mono<Map<Integer, List<User>>>> transMap =
//            m -> m.flatMap(map -> Stream.of(map).collect(toMap(Function.identity(), o -> {List users = new ArrayList<User>(); users.addAll((Collection) o); return users;}))
//                    );


//    private static Function<Map<Integer, List<User>>, Mono<?>> makeArray(Map<Integer, Collection<User>> integerCollectionMap) {
//        return Stream.of(integerCollectionMap)
//                .forEach((key, collection) -> integerCollectionMap.put(key, new ArrayList<>(collection)));
//
//
//
//    }

    @Data
    @AllArgsConstructor
    private static class User {
        private int id;
        private String name;

    }

}


