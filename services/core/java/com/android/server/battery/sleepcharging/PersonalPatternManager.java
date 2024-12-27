package com.android.server.battery.sleepcharging;

import android.content.Context;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class PersonalPatternManager {
    public Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SleepPattern {
        public final long bedTimeMillis;
        public final float confidence;
        public final boolean isConfident;
        public final long wakeupTimeMillis;
        public final String weekType;

        public SleepPattern(String str, long j, long j2, float f, boolean z) {
            this.weekType = str;
            this.bedTimeMillis = j;
            this.wakeupTimeMillis = j2;
            this.confidence = f;
            this.isConfident = z;
        }
    }
}
