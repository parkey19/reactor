package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SynchronousSink;

import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;

@Slf4j
public class Reactor2 {

    public static void main(String[] args) {

        Mono<Object> mempty = Mono.empty();
        Flux<Object> empty = Flux.empty();

        mempty.subscribe(new Subscriber<Object>() {
            private Subscription subscription;

            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("Subscriber.onSubscribe");
                this.subscription = s;
                this.subscription.request(1 ); //Publisher에 데이터 요청
            }

            @Override
            public void onNext(Object i) {
                System.out.println("Subscriber.onNext:" + i);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("Subscriber.onError" +t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("Subscriber.onComplete");
            }
        });

        // null을 값으로 받으면 값이 없는 Mono
        Mono<Integer> seq1 = Mono.justOrEmpty(null); // complete 신호

        seq1.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("justOrEmpty subscribe");
                this.subscription = s;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("justOrEmpty onNext");
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("justOrEmpty.onError" +t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("justOrEmpty.onComplete");
            }
        });


        Mono<Integer> seq2 = Mono.justOrEmpty(1); // next(1) 신호- complete 신호

        seq2.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("just subscribe");
                this.subscription = s;
                this.subscription.request(1);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("just onNext :" +integer);
                this.subscription.request(1);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("just.onError" +t.getMessage());
            }

            @Override
            public void onComplete() {
                System.out.println("just.onComplete");
            }
        });

        // Optional을 값으로 받음
        Mono<Integer> seq3 = Mono.justOrEmpty(Optional.empty()); // complete 신호

        Mono<Integer> seq4 = Mono.justOrEmpty(Optional.of(1)); // next(1) 신호 - complete 신호

        Flux<Integer> seq = Flux.range(11, 5);
        seq.subscribe(System.out::println);

        //
        Consumer<SynchronousSink<Integer>> randGen = new Consumer<SynchronousSink<Integer>>() {
            private int emitCount = 0;
            private Random rand = new Random();

            @Override
            public void accept(SynchronousSink<Integer> sink) {
                emitCount++;
                int data = rand.nextInt(100) + 1; // 1~100 사이 임의 정수
                log.info("Generator sink next " + data);
                sink.next(data); // 임의 정수 데이터 발생
                if (emitCount == 10) { // 10개 데이터를 발생했으면
                    log.info("Generator sink complete");
                    sink.complete(); // 완료 신호 발생
                }
            }
        };

        Flux<Integer> generate = Flux.generate(randGen);

        generate.subscribe(new BaseSubscriber<Integer>() {
            private int receiveCount = 0;
            @Override
            protected void hookOnSubscribe(Subscription subscription) {
                log.info("Subscriber#onSubscribe");
                log.info("Subscriber request first 3 items");
                request(3);
            }

            @Override
            protected void hookOnNext(Integer value) {
                log.info("Subscriber#onNext: " + value);
                receiveCount++;
                if (receiveCount % 3 == 0) {
                    log.info("Subscriber request next 3 items");
                    request(3);
                }
            }

            @Override
            protected void hookOnComplete() {
                log.info("Subscriber#onComplete");
            }
        });

        Flux<String> flux = Flux.generate(
                () -> { // Callable<S> stateSupplier
                    return 0;
                },
                (state, sink) -> { // BiFunction<S, SynchronousSink<T>, S> generator
                    sink.next("3 x " + state + " = " + 3 * state);
                    if (state == 10) {
                        sink.complete();
                    }
                    return state + 1;
                });

        flux.subscribe(s -> System.out.println(s));

        Flux<String> flux2 = Flux.generate(

                () -> 1,

                (state, sink) -> {

                    sink.next("Q: 3 * " + state);

                    sink.next("A: " + (3 * state)); // More than one call to onNext 에러!

                    if (state == 10) {

                        sink.complete();

                    }

                    return state + 1;

                });
        flux2.subscribe(s -> System.out.println(s));
    }
}
