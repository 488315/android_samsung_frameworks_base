package com.android.systemui.statusbar.connectivity;

import android.content.Context;
import com.android.systemui.statusbar.pipeline.mobile.util.MobileMappingsProxy;
import com.android.systemui.util.CarrierConfigTracker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
}
