package com.android.wifitrackerlib;

import android.content.Context;
import android.util.ArrayMap;
import com.samsung.android.wifitrackerlib.LogUtils;
import com.samsung.android.wifitrackerlib.SemWifiEntryFilter;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ScanResultUpdater {
    public final Clock mClock;
    public final long mMaxScanAgeMillis;
    public final SemWifiEntryFilter mSemFilter;
    public final LogUtils mLog = new LogUtils();
    public final Map mScanResultsBySsidAndBssid = new ArrayMap();
    public final Object mLock = new Object();

    public ScanResultUpdater(Clock clock, long j) {
        this.mMaxScanAgeMillis = j;
        this.mClock = clock;
    }

    public final List getScanResults() {
        ArrayList arrayList;
        synchronized (this.mLock) {
            arrayList = new ArrayList(this.mScanResultsBySsidAndBssid.values());
        }
        return arrayList;
    }

    public ScanResultUpdater(Clock clock, long j, Context context) {
        this.mMaxScanAgeMillis = j;
        this.mClock = clock;
        this.mSemFilter = new SemWifiEntryFilter(context);
    }
}
