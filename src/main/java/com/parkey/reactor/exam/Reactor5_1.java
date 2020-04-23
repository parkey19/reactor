package com.parkey.reactor.exam;

import reactor.core.publisher.Flux;

public class Reactor5_1 {
    public static void main(String[] args) {
        Flux<Integer> seq = Flux.just(1, 2, 3)
                .map(i -> {
                    if (i < 3) return i;
                    else throw new IllegalStateException("force");
                })
                .retryWhen(errorsFlux -> errorsFlux.take(2)); // 2개의 데이터 발생


        seq.subscribe(
                System.out::println,
                err -> System.err.println("에러 발생: " + err),
                () -> System.out.println("compelte")
        );

        Flux<Integer> seq2 = Flux.just(1, 2, 3)
                .map(i -> {
                    if (i < 3) return i;
                    else throw new IllegalStateException("force");
                })
                .retryWhen(errorsFlux -> errorsFlux.zipWith(Flux.range(1, 3),
                        (error, index) -> {
                            if (index < 3) return index;
                            else throw new RuntimeException("companion error"); //
                        })
                );



        seq2.subscribe(
                System.out::println,
                err ->System.err.println("에러 발생: "+err),
                ()->System.out.println("compelte"));
    }

}
