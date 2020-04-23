package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Reactor6_1 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 6)
                .map(i -> {
                    log.info("map 1: {} + 10", i);
                    return i + 10;

                })
                .publishOn(Schedulers.newElastic("PUB"), 2)
                .map(i -> { // publishOn에서 지정한 PUB 스케줄러가 실행
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
                        log.info("hookOnNext: " + value); // publishOn에서 지정한 스케줄러가 실행
                    }
                    @Override
                    protected void hookOnComplete() {
                        log.info("hookOnComplete"); // publishOn에서 지정한 스케줄러가 실행
                        latch.countDown();
                    }
                });

        latch.await();
    }
}
