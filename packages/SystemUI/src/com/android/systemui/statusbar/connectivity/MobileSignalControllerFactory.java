package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.util.CarrierConfigTracker;

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
}
