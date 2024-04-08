package org.example.reactiveprograming.configuration;

import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@ServerEndpoint(value = "/channel/log")
@Slf4j
public class LogChannel {

    public static final ConcurrentMap<String, LogChannel> CHANNELS = new ConcurrentHashMap<>();

    private Session session;

    //@OnMessage(maxMessageSize = 1) // MaxMessage 1 byte
    @OnMessage()
    public void onMessage(String message) {
        log.debug("Receive Message: {}", message);
        try {
            this.session.close(new CloseReason(CloseReason.CloseCodes.TOO_BIG, "This endpoint does not accept client messages"));
        } catch (IOException e) {
            log.error("Connection close error: id={}, err={}", this.session.getId(), e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) {
        this.session = session;
        //this.session.setMaxIdleTimeout(0);
        CHANNELS.put(this.session.getId(), this);
        log.info("New client connection: id={}", this.session.getId());
    }

    @OnClose
    public void onClose(CloseReason closeReason) {
        log.info("Connection disconnected: id={}, err={}", this.session.getId(), closeReason);
        CHANNELS.remove(this.session.getId());
    }

    @OnError
    public void onError(Throwable throwable) throws IOException {
        log.info("Connection Error: id={}, err={}", this.session.getId(), throwable.getStackTrace());
        this.session.close(new CloseReason(CloseReason.CloseCodes.UNEXPECTED_CONDITION, throwable.getMessage()));
    }

    /**
     * Push messages to all clients
     *
     * @param message
     */
    public static void push(String message) {
        CHANNELS.values().forEach(endpoint -> {
            if (endpoint.session.isOpen()) {
                System.out.println("Push message to socket channel");
                System.out.println("Thread-" + Thread.currentThread().getName());
                endpoint.session.getAsyncRemote().sendText(message);
            }
        });
    }
}
