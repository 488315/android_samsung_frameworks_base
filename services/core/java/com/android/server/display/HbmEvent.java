package com.android.server.display;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HbmEvent {
    public final long mEndTimeMillis;
    public final long mStartTimeMillis;

    public HbmEvent(long j, long j2) {
        this.mStartTimeMillis = j;
        this.mEndTimeMillis = j2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("HbmEvent: {startTimeMillis:");
        long j = this.mStartTimeMillis;
        sb.append(j);
        sb.append(", endTimeMillis: ");
        long j2 = this.mEndTimeMillis;
        sb.append(j2);
        sb.append("}, total: ");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, (j2 - j) / 1000, "]");
    }
}
