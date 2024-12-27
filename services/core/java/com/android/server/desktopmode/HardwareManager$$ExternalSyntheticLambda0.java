package com.android.server.desktopmode;

import com.samsung.android.desktopmode.DesktopModeFeature;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HardwareManager$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ HardwareManager f$0;

    public /* synthetic */ HardwareManager$$ExternalSyntheticLambda0(
            HardwareManager hardwareManager) {
        this.f$0 = hardwareManager;
    }

    @Override // java.lang.Runnable
    public final void run() {
        HardwareManager hardwareManager = this.f$0;
        int size = hardwareManager.mDisplays.size();
        for (int i = 0; i < size; i++) {
            if (HardwareManager.isSupportedDisplayType(
                    ((DisplayInfo) hardwareManager.mDisplays.valueAt(i)).mType)) {
                return;
            }
        }
        if (DesktopModeFeature.DEBUG) {
            Log.w(
                    "[DMS]HardwareManager",
                    "checkExternalDisplayConnectedLocked(), Reconnection time out!");
        }
        if (Utils.readFile(-1, "/sys/class/dp_sec/dex") != 0) {
            hardwareManager.mHandler.postDelayed(
                    new HardwareManager$$ExternalSyntheticLambda0(hardwareManager), 2000L);
            return;
        }
        hardwareManager.mIsExternalDisplayConnected = false;
        hardwareManager.updateDockStatusLocked();
        ((StateManager) hardwareManager.mStateManager).setExternalDisplayConnected(false, null);
    }
}
