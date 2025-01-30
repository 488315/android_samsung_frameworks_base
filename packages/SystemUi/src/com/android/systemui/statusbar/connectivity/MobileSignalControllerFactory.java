package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import android.os.Looper;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.MobileStatusTracker;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.util.CarrierConfigTracker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class MobileSignalControllerFactory {
    public final CallbackHandler callbackHandler;
    public final CarrierConfigTracker carrierConfigTracker;
    public final Context context;
    public final MobileMappingsProxy mobileMappings;

    public MobileSignalControllerFactory(Context context, CallbackHandler callbackHandler, CarrierConfigTracker carrierConfigTracker, MobileMappingsProxy mobileMappingsProxy) {
        this.context = context;
        this.callbackHandler = callbackHandler;
        this.carrierConfigTracker = carrierConfigTracker;
        this.mobileMappings = mobileMappingsProxy;
    }

    public final MobileSignalController createMobileSignalController(MobileMappings.Config config, boolean z, TelephonyManager telephonyManager, NetworkControllerImpl networkControllerImpl, SubscriptionInfo subscriptionInfo, MobileStatusTracker.SubscriptionDefaults subscriptionDefaults, Looper looper) {
        return new MobileSignalController(this.context, config, z, telephonyManager, this.callbackHandler, networkControllerImpl, this.mobileMappings, subscriptionInfo, subscriptionDefaults, looper, this.carrierConfigTracker, new MobileStatusTrackerFactory(telephonyManager, looper, subscriptionInfo, subscriptionDefaults));
    }
}
