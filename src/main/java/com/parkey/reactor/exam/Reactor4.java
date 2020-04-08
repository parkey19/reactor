package com.parkey.reactor.exam;

import reactor.core.publisher.Flux;

import java.time.Duration;

public class Reactor4 {
    public static void main(String[] args) {
        Flux.just("a", "bc", "def", "wxyz")
             .map(str -> str.length()) // 문자열을 Integer 값으로 1-1 변환
             .subscribe(len -> System.out.println(len));
        System.out.println("==============================================");
        Flux<Integer> seq = Flux.just(1, 2, 3)
                .flatMap(i -> Flux.range(1, i)); // Integer를 Flux<Integer>로 1-N 변환
        seq.subscribe(System.out::println);
        System.out.println("==============================================");
        Flux.range(1, 10)
                .filter(num -> num % 2 == 0)
                .subscribe(x -> System.out.print(x + " -> "));
        System.out.println("==============================================");
        Flux<String> tick1 = Flux.interval(Duration.ofSeconds(1)).map(tick -> tick + "초틱");
        Flux<String> tick2 = Flux.interval(Duration.ofMillis(700)).map(tick -> tick + "밀리초틱");
        tick1.mergeWith(tick2).subscribe(System.out::println);
        try {
            Thread.sleep(10000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
