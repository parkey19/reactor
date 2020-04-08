package com.parkey.reactor.exam;

import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;

public class Reactor3 {
    public static void main(String[] args) {

        Flux<Integer> flux = Flux.create( (FluxSink<Integer> sink) -> {
            sink.onRequest(request -> { // request는 Subscriber가 요청한 데이터 개수
                System.out.println("request : " + request);
                for (int i = 1; i <= request; i++) {
                    sink.next(i); // Flux.generate()의 경우와 달리 한 번에 한 개 이상의 next() 신호 발생 가능
                }
            });
        });

    }

}
