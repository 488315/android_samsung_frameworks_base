package com.android.systemui.cover;

import android.hardware.display.VirtualDisplay;
import android.util.Log;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class CoverScreenManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CoverScreenManager f$0;

    public /* synthetic */ CoverScreenManager$$ExternalSyntheticLambda0(CoverScreenManager coverScreenManager, int i) {
        this.$r8$classId = i;
        this.f$0 = coverScreenManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CoverScreenManager coverScreenManager = this.f$0;
                synchronized (coverScreenManager) {
                    VirtualDisplay virtualDisplay = coverScreenManager.mVirtualDisplay;
                    if (virtualDisplay != null) {
                        coverScreenManager.startCoverHomeActivity(virtualDisplay.getDisplay());
                    }
                }
                return;
            default:
                CoverScreenManager coverScreenManager2 = this.f$0;
                coverScreenManager2.getClass();
                Log.d("CoverScreenManager", "addPluginListener() PluginFaceWidget is connected");
                if (coverScreenManager2.mIsAttached) {
                    coverScreenManager2.requestPluginConnection(coverScreenManager2.mCoverState);
                    return;
                }
                return;
        }
    }
}
