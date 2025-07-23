package com.samsung.android.media;

/* loaded from: classes6.dex */
public class SemQuramDngTimeZone {
    public static final int eInvalidOffset = -901;
    public static final int eMaxOffsetHours = 15;
    public static final int eMaxOffsetMinutes = 900;
    public static final int eMinOffsetHours = -15;
    public static final int eMinOffsetMinutes = -900;
    public int mOffsetMinutes;

    public boolean isValid() {
        return this.mOffsetMinutes != -901;
    }

    public int getHour() {
        return this.mOffsetMinutes / 60;
    }

    public int getMinute() {
        return this.mOffsetMinutes;
    }
}
