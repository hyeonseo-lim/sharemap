package com.map.sharemap;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class WebSocketAuthInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes
    ) {

        if (request instanceof org.springframework.http.server.ServletServerHttpRequest servletRequest) {

            HttpSession session = servletRequest.getServletRequest().getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                return false; // ❌ 로그인 없으면 차단
            }

            attributes.put("user", session.getAttribute("user"));
        }

        return true;
    }

    @Override
    public void afterHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Exception exception
    ) {
    }
}