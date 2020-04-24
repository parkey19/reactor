package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
public class Reactor9 {
    public static void main(String[] args) {
//        Flux<Flux<Integer>> windowSeq =
//                Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
//                        .window(4); // 4개 간격으로 4개씩 새로운 Flux로 묶음
//        windowSeq.subscribe(seq -> { // seq는 Flux<Integer>
//            Mono<List<Integer>> monoList = seq.collectList();
//            monoList.subscribe(list -> log.info("window: {}", list));
//        });

        Flux<Flux<Integer>> windowSeq =
                Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
                        .window(4, 5); // 3개 간격마다 4개씩 새로운 Flux로 묶음
        windowSeq.subscribe(seq -> { // seq는 Flux<Integer>
            Mono<List<Integer>> monoList = seq.collectList();
            monoList.subscribe(list -> log.info("window: {}", list));
        });

        System.out.println("---------------------------------------");

        Flux.just(1,1,2,3,3,4)
                .windowUntil(x -> x % 2 == 0)
                .subscribe(seq -> {
                    seq.collectList().subscribe(lst -> log.info("window: {}", lst));
                });

        System.out.println("---------------------------------------");

        Flux.just(1,1,2,4,3,3,4,6,8,9,10)
                .log()
                .windowWhile(x -> x % 2 == 0) // 짝수인 동안
                .subscribe(seq -> {
                    seq.collectList().subscribe(lst -> log.info("window: {}", lst));
                });
        System.out.println("---------------------------------------");
        Flux<List<Integer>> bufferSeq = Flux.just(1, 2, 3, 4, 5, 6, 7, 8, 9, 10).buffer(4);
        bufferSeq.subscribe(list -> log.info("window: {}", list));
    }
}
