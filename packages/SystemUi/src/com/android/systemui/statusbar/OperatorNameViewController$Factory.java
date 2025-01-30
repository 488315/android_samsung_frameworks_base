package com.android.systemui.statusbar;

import android.telephony.TelephonyManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.plugins.DarkIconDispatcher;
import com.android.systemui.statusbar.connectivity.NetworkController;
import com.android.systemui.tuner.TunerService;
import com.android.systemui.util.CarrierConfigTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class OperatorNameViewController$Factory {
    public final DarkIconDispatcher mDarkIconDispatcher;

    public OperatorNameViewController$Factory(DarkIconDispatcher darkIconDispatcher, NetworkController networkController, TunerService tunerService, TelephonyManager telephonyManager, KeyguardUpdateMonitor keyguardUpdateMonitor, CarrierConfigTracker carrierConfigTracker) {
        this.mDarkIconDispatcher = darkIconDispatcher;
    }
}
