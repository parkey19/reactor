package com.parkey.reactor.exam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Map;

public class Reactor8 {
    public static void main(String[] args) {
        Flux.just(1, 2, 3, 4)
                .collectMap(x -> x % 2)
                .subscribe(map -> System.out.println(map)); // {0=4, 1=3}

        System.out.println("--------------------------------------");

        Mono<Map<Integer, Collection<Integer>>> oddEvenList =
                Flux.just(1, 2, 3, 4)
                        .collectMultimap(x -> x % 2);
        oddEvenList.subscribe(map -> System.out.println(map)); //{0=[2, 4], 1=[1, 3]}

        System.out.println("--------------------------------------");

        Mono<Long> countMono = Flux.just(1, 2, 3, 4).count();
        countMono.subscribe(System.out::println);

        System.out.println("--------------------------------------");

        Mono<Integer> mulMono = Flux.just(1, 2, 3, 4).reduce((acc, ele) -> acc * ele);
        mulMono.subscribe(sum -> System.out.println("sum : " + sum)); //24

        System.out.println("--------------------------------------");
        Mono<String> strMono = Flux.just(1, 2, 3, 4)
                .reduce("", (str, ele) -> str + "-" + ele.toString());
        strMono.subscribe(System.out::println); // -1-2-3-4 출력

        System.out.println("--------------------------------------");
        //중간 결과 값 리턴
        Flux<Integer> seq = Flux.just(1, 2, 3, 4).scan((acc, x) -> acc * x);
        seq.subscribe(System.out::println);

        System.out.println("--------------------------------------");
        Mono<Boolean> all = Flux.just(1, 2, 3, 4).all(x -> x > 2);
        all.subscribe(b -> System.out.println("all: " + b)); // false

        System.out.println("--------------------------------------");
        Mono<Boolean> any = Flux.just(1, 2, 3, 4).any(x -> {
            System.out.println("x :" + x);
            return x > 2;
        });
        any.subscribe(b -> System.out.println("any: " + b)); // true

        System.out.println("--------------------------------------");
        Mono<Boolean> hasElements = Flux.just(1, 2, 3, 4).log().hasElements();
        hasElements.subscribe(b -> System.out.println("hasElements: " + b)); //1 true

        Mono<Boolean> hasElement = Flux.just(1, 2, 3, 4).log().hasElement(3);
        hasElement.subscribe(b -> System.out.println("hasElement: " + b)); //1,2,3 true
    }

}
