package com.android.systemui.statusbar.connectivity;

import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileStatusTracker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
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
