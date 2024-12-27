package com.android.systemui.statusbar.connectivity;

import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileStatusTracker;

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
