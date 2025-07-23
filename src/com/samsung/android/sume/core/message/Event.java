package com.samsung.android.sume.core.message;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import java.util.Map;

/* loaded from: classes6.dex */
public class Event extends Message {
    private static final String TAG = Def.tagOf((Class<?>) Event.class);

    Event(int i) {
        super(990, i);
    }

    Event(Message message) {
        super(message);
    }

    public static Event of(int i) {
        return new Event(i);
    }

    public static Event of(int i, String str) {
        return (Event) new Event(i).put("message", str);
    }

    public static Event of(int i, Exception exc) {
        return (Event) new Event(i).setException(exc);
    }

    public static Event of(int i, String str, Object obj) {
        return (Event) new Event(i).put(str, obj);
    }

    public static Event of(int i, Map<String, Object> map) {
        return (Event) new Event(i).put(map);
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
