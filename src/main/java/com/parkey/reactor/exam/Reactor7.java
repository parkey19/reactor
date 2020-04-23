package com.parkey.reactor.exam;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.util.Random;
import java.util.stream.IntStream;

import static java.lang.Thread.sleep;

@Slf4j
public class Reactor7 {
    public static void main(String[] args) {
//        Flux.range(1, 20)
//                .parallel(2) // 작업을 레일로 나누기만 함
//                .runOn(Schedulers.newParallel("PAR", 2))  // 각 레일을 병렬로 실행
//                .map(x -> {
//                    int sleepTime = nextSleepTime(x % 2 == 0 ? 50 : 100, x % 2 == 0 ? 150 : 300);
//                    log.info("map1 {}, sleepTime {}", x, sleepTime);
//                    try {
//                        sleep(sleepTime);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    return String.format("%02d", x);
//
//                })
//                .subscribe(i -> log.info("next {}", i) );
                // nextSleepTime은 인자로 받은 두 정수 값 범위에 해당하는 임의의 값을 생성한다고 가정

        Flux.range(1, 20)
                .parallel(4)
                .runOn(Schedulers.newParallel("PAR", 2), 2) // 레일에 미리 채울 값으로 2 사용
                .subscribe(x -> log.info("next {}", x));
    }

    private static int nextSleepTime(int i1, int i2) {
        Random random = new Random();
        return random.nextInt(i2) + i1;
    }
}
