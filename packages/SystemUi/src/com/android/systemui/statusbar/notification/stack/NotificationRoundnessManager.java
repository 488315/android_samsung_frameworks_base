package com.android.systemui.statusbar.notification.stack;

import com.android.keyguard.KeyguardClockSwitchController$$ExternalSyntheticOutline0;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.statusbar.notification.SourceType;
import java.io.PrintWriter;
import java.util.HashSet;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationRoundnessManager implements Dumpable {
    public HashSet mAnimatedChildren;
    public boolean mIsClearAllInProgress;
    public boolean mRoundForPulsingViews;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface SectionStateProvider {
    }

    static {
        SourceType.from("DismissAnimation");
    }

    public NotificationRoundnessManager(DumpManager dumpManager) {
        dumpManager.getClass();
        DumpManager.registerDumpable$default(dumpManager, "NotificationRoundnessManager", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        KeyguardClockSwitchController$$ExternalSyntheticOutline0.m65m(KeyguardClockSwitchController$$ExternalSyntheticOutline0.m64m(new StringBuilder("roundForPulsingViews="), this.mRoundForPulsingViews, printWriter, "isClearAllInProgress="), this.mIsClearAllInProgress, printWriter);
    }
}
