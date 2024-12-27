package com.samsung.android.knox.analytics.model;

import java.nio.charset.StandardCharsets;
import org.json.JSONArray;
import org.json.JSONException;

/* loaded from: classes6.dex */
public class EventList extends JSONArray {
    private int mTotalEventsCount;

    public EventList() {
        this.mTotalEventsCount = 0;
    }

    public EventList(byte[] content) throws JSONException {
        super(new String(content));
    }

    public void put(Event e) {
        put(e.toString());
        this.mTotalEventsCount += e.getBulk();
    }

    public byte[] toByteArray() {
        return super.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int getTotalEventsCount() {
        return this.mTotalEventsCount;
    }
}
