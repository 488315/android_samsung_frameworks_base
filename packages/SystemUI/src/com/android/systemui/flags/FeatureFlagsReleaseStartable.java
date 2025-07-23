package com.android.systemui.flags;

import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class FeatureFlagsReleaseStartable implements CoreStartable {
    public FeatureFlagsReleaseStartable(DumpManager dumpManager, final FeatureFlags featureFlags) {
        dumpManager.registerCriticalDumpable("SysUIFlags", new Dumpable() { // from class: com.android.systemui.flags.FeatureFlagsReleaseStartable.1
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                FeatureFlags.this.dump(printWriter, strArr);
            }
        });
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
