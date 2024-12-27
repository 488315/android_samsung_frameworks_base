package com.android.systemui.flags;

import com.android.systemui.CoreStartable;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class FeatureFlagsReleaseStartable implements CoreStartable {
    public FeatureFlagsReleaseStartable(DumpManager dumpManager, final FeatureFlags featureFlags) {
        dumpManager.registerCriticalDumpable("SysUIFlags", new Dumpable() { // from class: com.android.systemui.flags.FeatureFlagsReleaseStartable.1
            @Override // com.android.systemui.Dumpable
            public final void dump(PrintWriter printWriter, String[] strArr) {
                ((FeatureFlagsClassicRelease) FeatureFlags.this).dump(printWriter, strArr);
            }
        });
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
