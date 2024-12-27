package com.android.systemui.util.leak;

import com.android.systemui.dump.DumpManager;

public class LeakModule {
    public LeakDetector providesLeakDetector(DumpManager dumpManager, TrackedCollections trackedCollections) {
        return new LeakDetector(null, null, null, dumpManager);
    }
}
