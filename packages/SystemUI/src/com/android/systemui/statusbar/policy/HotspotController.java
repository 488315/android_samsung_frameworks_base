package com.android.systemui.statusbar.policy;

import com.android.systemui.Dumpable;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface HotspotController extends CallbackController, Dumpable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
        void onHotspotChanged(int i, boolean z);

        void onHotspotPrepared();

        void onUpdateConnectedDevices();

        default void onHotspotAvailabilityChanged(boolean z) {
        }
    }
}
