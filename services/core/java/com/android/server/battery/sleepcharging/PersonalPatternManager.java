package com.android.server.battery.sleepcharging;

import android.content.Context;

public final class PersonalPatternManager {
    public Context mContext;

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
