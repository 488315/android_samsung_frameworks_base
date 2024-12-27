package com.android.systemui.statusbar.connectivity;

import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileStatusTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
