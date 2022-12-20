package com.example.productservice.connection;

import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.Edge;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

@Component
public class CursorUtil {

    public static ConnectionCursor createCursorWith(Long id) {
        return new DefaultConnectionCursor(
                Base64.getEncoder().encodeToString(id.toString().getBytes(StandardCharsets.UTF_8))
        );
    }

    public static long decode(String cursor) {
        String cursorStr = new String(Base64.getDecoder().decode(String.valueOf(cursor)));
        return Long.parseLong(cursorStr);
    }

    public static <T> ConnectionCursor getFirstCursorFrom(List<Edge<T>> edges) {
        return edges.isEmpty() ? null : edges.get(0).getCursor();
    }

    public static <T> ConnectionCursor getLastCursorFrom(List<Edge<T>> edges) {
        return edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
    }
}
