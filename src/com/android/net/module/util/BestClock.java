package com.android.net.module.util;

import android.util.Log;
import java.time.Clock;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Arrays;

/* loaded from: classes6.dex */
public final class BestClock extends Clock {
    private static final String TAG = "BestClock";
    private final Clock[] mClocks;
    private final ZoneId mZone;

    public BestClock(ZoneId zoneId, Clock... clockArr) {
        this.mZone = zoneId;
        this.mClocks = clockArr;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public long millis() {
        Clock[] clockArr = this.mClocks;
        int length = clockArr.length;
        for (int i = 0; i < length; i++) {
            try {
                return clockArr[i].millis();
            } catch (DateTimeException e) {
                Log.w(TAG, e.toString());
            }
        }
        throw new DateTimeException("No clocks in " + Arrays.toString(this.mClocks) + " were able to provide time");
    }

    @Override // java.time.Clock
    public ZoneId getZone() {
        return this.mZone;
    }

    @Override // java.time.Clock, java.time.InstantSource
    public Clock withZone(ZoneId zoneId) {
        return new BestClock(zoneId, this.mClocks);
    }

    @Override // java.time.Clock, java.time.InstantSource
    public Instant instant() {
        return Instant.ofEpochMilli(millis());
    }
}
