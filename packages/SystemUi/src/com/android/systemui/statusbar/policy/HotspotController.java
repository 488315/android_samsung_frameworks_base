package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public interface HotspotController extends CallbackController, Dumpable {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
        void onHotspotChanged(int i, boolean z);

        void onHotspotPrepared();

        void onUpdateConnectedDevices();

        default void onHotspotAvailabilityChanged(boolean z) {
        }
    }
}
