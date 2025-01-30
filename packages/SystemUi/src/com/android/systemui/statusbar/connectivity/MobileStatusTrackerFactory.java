package com.android.systemui.statusbar.connectivity;

import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileStatusTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileStatusTrackerFactory {
    public final MobileStatusTracker.SubscriptionDefaults defaults;
    public final SubscriptionInfo info;
    public final TelephonyManager phone;
    public final Looper receiverLooper;

    public MobileStatusTrackerFactory(TelephonyManager telephonyManager, Looper looper, SubscriptionInfo subscriptionInfo, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults) {
        this.phone = telephonyManager;
        this.receiverLooper = looper;
        this.info = subscriptionInfo;
        this.defaults = subscriptionDefaults;
    }
}
