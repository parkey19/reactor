package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class Reactor6 {
    public static void main(String[] args) {
        Flux.range(1, 3)
                .map(i -> {
                    log.info("map {} to {}", i, i + 2);
                    return i + 2;
                })
                .flatMap(i -> {
                    log.info("flatMap {} to Flux.range({}, {})", i, 1, i);
                    return Flux.range(1, i);
                })
                .subscribe(i -> log.info("next " + i));
    }
}
