package com.android.server.audio;

import com.android.server.utils.EventLogger;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AudioServiceEvents$SoundDoseEvent extends EventLogger.Event {
    public final int mEventType;
    public final float mFloatValue;
    public final long mLongValue;

    public AudioServiceEvents$SoundDoseEvent(int i, long j, float f) {
        this.mEventType = i;
        this.mFloatValue = f;
        this.mLongValue = j;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        float f = this.mFloatValue;
        int i = this.mEventType;
        if (i == 0) {
            return String.format("momentary exposure MEL=%.2f", Float.valueOf(f));
        }
        if (i == 1) {
            return String.format(Locale.US, "dose update CSD=%.1f%% total duration=%d", Float.valueOf(f * 100.0f), Long.valueOf(this.mLongValue));
        }
        if (i == 2) {
            return "CSD reached 500%";
        }
        if (i == 3) {
            return "CSD accumulating: RS2 entered";
        }
        if (i == 4) {
            return "CSD lowering volume to RS1";
        }
        return "FIXME invalid event type:" + i;
    }
}
