package com.android.server.notification.sec.runestone;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class CollectionContract$Notification$Log {
    public int cancelReason;
    public long canceledTimeMs;
    public String category;
    public String channelId;
    public long enqueuedTimeMs;
    public long firstExpandedTimeMs;
    public long firstShownTimeMs;
    public int id;
    public String pkg;
    public String tag;

    public final String toString() {
        StringBuilder sb = new StringBuilder("RunestoneNotiLog[pkg=");
        sb.append(this.pkg);
        sb.append(", id=");
        sb.append(this.id);
        sb.append(", cancelReason=");
        sb.append(this.cancelReason);
        sb.append(", category=");
        sb.append(this.category);
        sb.append(", channelId=");
        sb.append(this.channelId);
        sb.append(", tag= ");
        sb.append(this.tag);
        sb.append(", enqueuedTimeMs=");
        sb.append(this.enqueuedTimeMs);
        sb.append(", canceledTimeMs=");
        sb.append(this.canceledTimeMs);
        sb.append(", firstExpandedTimeMs=");
        sb.append(this.firstExpandedTimeMs);
        sb.append(", firstShownTimeMs=");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.firstShownTimeMs, "]");
    }
}
