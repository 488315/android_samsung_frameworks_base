package com.android.server.twilight;

import android.text.format.DateFormat;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TwilightState {
    public final long mSunriseTimeMillis;
    public final long mSunsetTimeMillis;

    public TwilightState(long j, long j2) {
        this.mSunriseTimeMillis = j;
        this.mSunsetTimeMillis = j2;
    }

    public final boolean equals(Object obj) {
        TwilightState twilightState;
        return (obj instanceof TwilightState)
                && (twilightState = (TwilightState) obj) != null
                && this.mSunriseTimeMillis == twilightState.mSunriseTimeMillis
                && this.mSunsetTimeMillis == twilightState.mSunsetTimeMillis;
    }

    public final int hashCode() {
        return Long.hashCode(this.mSunsetTimeMillis) ^ Long.hashCode(this.mSunriseTimeMillis);
    }

    public final boolean isNight() {
        long currentTimeMillis = System.currentTimeMillis();
        return currentTimeMillis >= this.mSunsetTimeMillis
                && currentTimeMillis < this.mSunriseTimeMillis;
    }

    public final String toString() {
        return "TwilightState { sunrise="
                + ((Object) DateFormat.format("MM-dd HH:mm", this.mSunriseTimeMillis))
                + " sunset="
                + ((Object) DateFormat.format("MM-dd HH:mm", this.mSunsetTimeMillis))
                + " }";
    }
}
