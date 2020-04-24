package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.logging.Level;

@Slf4j
public class Reactor10 {
    public static void main(String[] args) {
        Flux.just(1,2,3,4,5,6)
                .log(null, Level.FINE)
                .map(x -> x*2)
                .subscribe(x->log.info("next:{}", x));

        System.out.println("----------------------------------------");

        Flux.just(1, 2, 4, -1, 5, 6)
                .map(x -> x + 1)
                .checkpoint("MAP1")
                .map(x -> 10 / x) // 원본 데이터가 -1인 경우 x는 0이 되어 익셉션이 발생
                .checkpoint("MAP2")
                .subscribe(
                        x -> System.out.println("next: " + x),
                        err -> err.printStackTrace());
    }
}
