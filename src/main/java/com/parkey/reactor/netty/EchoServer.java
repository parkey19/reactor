package com.parkey.reactor.netty;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LineBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.netty.DisposableServer;
import reactor.netty.tcp.TcpServer;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class EchoServer {
    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        DisposableServer server = TcpServer.create()
                .port(9999) // 서버가 사용할 포트
                .doOnConnection(conn -> { // 클라이언트 연결시 호출
                    // conn: reactor.netty.Connection
                    conn.addHandler(new LineBasedFrameDecoder(1024));
                    conn.addHandler(new ChannelHandlerAdapter() {
                        @Override
                        public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
                            log.info("client added");
                        }

                        @Override
                        public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
                            log.info("client removed");
                        }

                        @Override
                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
                                throws Exception {
                            log.warn("exception {}", cause.toString());
                            ctx.close();
                        }
                    });
                    conn.onReadIdle(10_000, () -> {
                        log.warn("client read timeout");
                        conn.dispose();
                    });
                })
                .handle((in, out) -> // 연결된 커넥션에 대한 IN/OUT 처리
                        // reactor.netty (NettyInbound, NettyOutbound)
                        in.receive() // 데이터 읽기 선언, ByteBufFlux 리턴
                                .asString()  // 문자열로 변환 선언, Flux<String> 리턴
                                .flatMap(msg -> {
                                            log.debug("doOnNext: [{}]", msg);
                                            if (msg.equals("exit")) {
                                                return out.withConnection(conn -> conn.dispose());
                                            } else if (msg.equals("SHUTDOWN")) {
                                                latch.countDown();
                                                return out;
                                            } else {
                                                return out.sendString(Mono.just("echo: " + msg + "\r\n"));
                                            }
                                        }
                                )
                )
                .bind() // Mono<DisposableServer> 리턴
                .block(); // 서버 실행 및 DisposableServer 리턴

        latch.await();

        log.info("dispose server");
        server.disposeNow(); // 서버 종료
    }
}
