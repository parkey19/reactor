package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Reactor6_2 {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.range(1, 6)
                .publishOn(Schedulers.newElastic("PUB1"), 2)
                .map(i -> {
                    log.info("map 1: {} + 10", i);
                    return i + 10;
                })
                .publishOn(Schedulers.newElastic("PUB2"))
                .map(i -> {
                    log.info("map 2: {} + 10", i);
                    return i + 10;
                })
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        log.info("hookOnSubscribe");
                        requestUnbounded();
                    }
                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("hookOnNext: " + value);
                    }

                    @Override
                    protected void hookOnComplete() {
                        log.info("hookOnComplete");
                        latch.countDown();
                    }
                });
    }
}
