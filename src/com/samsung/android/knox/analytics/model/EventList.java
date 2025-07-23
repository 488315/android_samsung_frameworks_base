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

    public EventList(byte[] bArr) throws JSONException {
        super(new String(bArr));
    }

    public void put(Event event) {
        put(event.toString());
        this.mTotalEventsCount += event.getBulk();
    }

    public byte[] toByteArray() {
        return super.toString().getBytes(StandardCharsets.UTF_8);
    }

    public int getTotalEventsCount() {
        return this.mTotalEventsCount;
    }
}
