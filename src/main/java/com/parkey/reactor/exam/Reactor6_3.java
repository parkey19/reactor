package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class Reactor6_3 {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 6)
                .log()
                .subscribeOn(Schedulers.newElastic("SUB"))
                .map(i -> {
                    log.info("map1: " + i + " --> " + (i + 20));
                    return i + 20;
                })
                .map(i -> {
                    log.info("mapBySub: " + i + " --> " + (i + 100));
                    return i + 100;
                })
                .publishOn(Schedulers.newElastic("PUB1"), 2)
                .map(i -> {
                    log.info("mapByPub1: " + i + " --> " + (i + 1000));
                    return i + 1000;
                })
                .publishOn(Schedulers.newElastic("PUB2"), 2)
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        log.info("hookOnSubscribe");
                        request(1);
                    }
                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("hookOnNext: " + value);
                        request(1);
                    }
                    @Override
                    protected void hookOnComplete() {
                        log.info("hookOnComplete");
                        latch.countDown();
                    }
                });

        latch.await();
    }
}
