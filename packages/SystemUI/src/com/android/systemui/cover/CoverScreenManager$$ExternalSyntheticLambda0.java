package com.android.systemui.cover;

import android.hardware.display.VirtualDisplay;
import android.util.Log;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
                coverScreenManager.getClass();
                Log.d("CoverScreenManager", "addPluginListener() PluginFaceWidget is connected");
                if (coverScreenManager.mIsAttached) {
                    coverScreenManager.requestPluginConnection(coverScreenManager.mCoverState);
                    return;
                }
                return;
            default:
                CoverScreenManager coverScreenManager2 = this.f$0;
                synchronized (coverScreenManager2) {
                    try {
                        VirtualDisplay virtualDisplay = coverScreenManager2.mVirtualDisplay;
                        if (virtualDisplay != null) {
                            coverScreenManager2.startCoverHomeActivity(virtualDisplay.getDisplay());
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
        }
    }
}
