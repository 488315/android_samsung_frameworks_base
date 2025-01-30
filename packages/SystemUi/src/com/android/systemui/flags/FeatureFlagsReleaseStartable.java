package com.android.systemui.flags;

import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FeatureFlagsReleaseStartable implements CoreStartable {
    public FeatureFlagsReleaseStartable(DumpManager dumpManager, final FeatureFlags featureFlags) {
        dumpManager.registerCriticalDumpable("SysUIFlags", new Dumpable() { // from class: com.android.systemui.flags.FeatureFlagsReleaseStartable.1
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                ((FeatureFlagsRelease) FeatureFlags.this).dump(printWriter, strArr);
            }
        });
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
