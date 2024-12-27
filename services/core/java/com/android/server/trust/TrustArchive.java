package com.android.server.trust;

import android.content.ComponentName;
import android.os.SystemClock;

import java.util.ArrayDeque;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class TrustArchive {
    public ArrayDeque mEvents;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Event {
        public final ComponentName agent;
        public final long duration;
        public final long elapsedTimestamp = SystemClock.elapsedRealtime();
        public final int flags;
        public final boolean managingTrust;
        public final String message;
        public final int type;
        public final int userId;

        public Event(
                int i, int i2, ComponentName componentName, String str, long j, int i3, boolean z) {
            this.type = i;
            this.userId = i2;
            this.agent = componentName;
            this.message = str;
            this.duration = j;
            this.flags = i3;
            this.managingTrust = z;
        }
    }

    public final void addEvent(Event event) {
        if (this.mEvents.size() >= 200) {
            this.mEvents.removeFirst();
        }
        this.mEvents.addLast(event);
    }
}
