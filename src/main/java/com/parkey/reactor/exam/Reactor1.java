package com.parkey.reactor.exam;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Reactor1 {
    public static void main(String[] args) {
//        Flux.just(1, 2, 3)
//                .doOnNext(i -> System.out.println("doOnNext: " + i))
//                .subscribe(i -> System.out.println("Received: " + i));
//        Flux<Integer> seq = Flux.just(1, 2, 3)
//                .doOnNext(i -> System.out.println("doOnNext: " + i));
//        System.out.println("시퀀스 생성");
//
//        seq.subscribe(i -> System.out.println("Received: " + i));

        //구독을 할 때마다 매번 새로운 요청을 서버에 전송하고 결과를 받는다.
        Flux<Integer> seq2 = Flux.just(1, 2, 3, 4);
//        seq2.subscribe(v -> System.out.println("구독1:" + v));
//        seq2.subscribe(v -> System.out.println("구독2:" + v));

        seq2.subscribe(new Subscriber<Integer>() {
            private Subscription subscription;
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("Subscriber.onSubscribe");
                this.subscription = s;
//                this.subscription.request(5 ); //Publisher에 데이터 요청
            }

            @Override
            public void onNext(Integer i) {
                System.out.println("Subscriber.onNext:" + i);
//                this.subscription.request(1);
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

//        Mono<Object> mempty = Mono.empty();
//        Flux<Object> empty = Flux.empty();
//        mempty.subscribe(new Subscriber<Object>() {
//            private Subscription subscription;
//
//            @Override
//            public void onSubscribe(Subscription s) {
//                System.out.println("Subscriber.onSubscribe");
//                this.subscription = s;
//                this.subscription.request(1 ); //Publisher에 데이터 요청
//            }
//
//            @Override
//            public void onNext(Object i) {
//                System.out.println("Subscriber.onNext:" + i);
////                this.subscription.request(1);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                System.out.println("Subscriber.onError" +t.getMessage());
//            }
//
//            @Override
//            public void onComplete() {
//                System.out.println("Subscriber.onComplete");
//            }
//        });

    }

}
