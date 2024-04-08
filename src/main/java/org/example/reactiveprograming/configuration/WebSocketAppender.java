package org.example.reactiveprograming.configuration;

import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;


@Data
@EqualsAndHashCode(callSuper = true)
@Slf4j
public class WebSocketAppender extends AppenderBase<ILoggingEvent> {
    // encoder is required. And it has to have legal getter/setter methods.
    private PatternLayoutEncoder encoder;

    @Override
    protected void append(ILoggingEvent iLoggingEvent) {
        // Use encoder to encode logs.
        byte[] data = this.encoder.encode(iLoggingEvent);
        String message = new String(data, StandardCharsets.UTF_8);
        try {
//            var response = WebClient.create().post().uri("http://localhost:8081/web/text").body(
//                    Mono.just(message), String.class
//            ).exchange();
//            System.out.println(response.subscribe(System.out::println));
            LogChannel.push(message);
            System.out.println("Thread-" + Thread.currentThread().getName() + " " + message);
            //System.out.println(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
