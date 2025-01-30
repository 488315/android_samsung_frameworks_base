package com.android.wifitrackerlib;

import com.android.systemui.statusbar.connectivity.AccessPointControllerImpl;
import com.android.wifitrackerlib.WifiPickerTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiPickerTracker$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WifiPickerTracker.WifiPickerTrackerCallback f$0;

    public /* synthetic */ WifiPickerTracker$$ExternalSyntheticLambda6(WifiPickerTracker.WifiPickerTrackerCallback wifiPickerTrackerCallback, int i) {
        this.$r8$classId = i;
        this.f$0 = wifiPickerTrackerCallback;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.getClass();
                break;
            case 1:
                ((AccessPointControllerImpl) this.f$0).scanForAccessPoints();
                break;
            default:
                this.f$0.getClass();
                break;
        }
    }
}
