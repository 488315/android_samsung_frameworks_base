package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* loaded from: classes3.dex */
public interface HotspotController extends CallbackController, Dumpable {

    public interface Callback {
        void onHotspotChanged(int i, boolean z);

        void onHotspotPrepared();

        void onUpdateConnectedDevices();

        default void onHotspotAvailabilityChanged(boolean z) {
        }
    }
}
