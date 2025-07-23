package com.android.systemui.util.leak;

import com.android.systemui.dump.DumpManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class LeakModule {
    public LeakDetector providesLeakDetector(DumpManager dumpManager, TrackedCollections trackedCollections) {
        return new LeakDetector(null, null, null, dumpManager);
    }
}
