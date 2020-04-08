package com.parkey.reactor.exam;

import reactor.core.publisher.Flux;

import java.util.Random;

public class Reactor5 {
    public static void main(String[] args) {
        Flux.range(1, 10)
                .map(x -> {
                    if (x == 5) throw new RuntimeException("exception 5"); // 에러 발생
                    else return x;
                }).onErrorReturn(-1)
                .subscribe(
                        i -> System.out.println(i), // next 신호 처리
                        ex -> System.err.println(ex.getMessage()), // error 신호 처리
                        () -> System.out.println("complete") // complete 신호 처리
                );
        Random random = new Random();
        Flux<Integer> seq = Flux.range(1, 10)
                .map(x -> {
                    int rand = random.nextInt(8);
                    if (rand == 0) throw new IllegalArgumentException("illarg");
                    if (rand == 1) throw new IllegalStateException("illstate");
                    if (rand == 2) throw new RuntimeException("exception");
                    return x;
                })
                .onErrorResume(error -> {
                    if (error instanceof IllegalArgumentException) {
                        return Flux.just(21, 22);
                    }
                    if (error instanceof IllegalStateException) {
                        return Flux.just(31, 32);
                    }
                    return Flux.error(error);
                });

        seq.subscribe(System.out::println);

        Flux.range(1, 5)
                .map(input -> {
                    if (input < 4) return "num " + input;
                    throw new RuntimeException("boom");
                })
                .retry(1) // 에러 신호 발생시 1회 재시도
                .subscribe(System.out::println, System.err::println);
    }
}
