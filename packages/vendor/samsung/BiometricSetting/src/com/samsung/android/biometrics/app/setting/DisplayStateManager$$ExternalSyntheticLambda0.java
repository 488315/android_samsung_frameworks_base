package com.samsung.android.biometrics.app.setting;

import com.samsung.android.biometrics.app.setting.fingerprint.HbmListener;

import java.util.ArrayList;
import java.util.Iterator;

public final /* synthetic */ class DisplayStateManager$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ DisplayStateManager f$0;

    public /* synthetic */ DisplayStateManager$$ExternalSyntheticLambda0(
            DisplayStateManager displayStateManager, int i) {
        this.$r8$classId = i;
        this.f$0 = displayStateManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        DisplayStateManager displayStateManager = this.f$0;
        switch (i) {
            case 0:
                Iterator it = ((ArrayList) displayStateManager.mHbmListeners).iterator();
                while (it.hasNext()) {
                    ((HbmListener) it.next())
                            .onHbmChanged(displayStateManager.mVirtualHbmNode.get());
                }
                break;
            default:
                displayStateManager.releaseRefreshRateForSeamlessMode();
                break;
        }
    }
}
