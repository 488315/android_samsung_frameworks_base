package com.samsung.android.sume.core.message;

import android.util.Log;

import com.samsung.android.sume.core.Def;

import java.util.Map;

public class Event extends Message {
    private static final String TAG = Def.tagOf((Class<?>) Event.class);

    Event(int code) {
        super(990, code);
    }

    Event(Message message) {
        super(message);
    }

    public static Event of(int code) {
        return new Event(code);
    }

    public static Event of(int code, String message) {
        return (Event) new Event(code).put("message", message);
    }

    public static Event of(int code, Exception exception) {
        return (Event) new Event(code).setException(exception);
    }

    public static Event of(int code, String key, Object data) {
        return (Event) new Event(code).put(key, data);
    }

    public static Event of(int code, Map<String, Object> data) {
        return (Event) new Event(code).put(data);
    }

    public static Event of(Response response) {
        Event event = new Event(response);
        if (response.getBufferList() != null && !response.getBufferList().isEmpty()) {
            Log.d(TAG, "response contains buffer-list, set it into event");
            event.put("buffer-list", response.getBufferList());
        }
        return event;
    }

    public static Event of(Message message) {
        return new Event(message);
    }
}
