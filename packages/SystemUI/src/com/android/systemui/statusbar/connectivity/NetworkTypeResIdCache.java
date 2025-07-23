package com.android.systemui.statusbar.connectivity;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NetworkTypeResIdCache {
    public int cachedResId;
    public boolean isOverridden;
    public Integer lastCarrierId;
    public SignalIcon$MobileIconGroup lastIconGroup;
    public final MobileIconCarrierIdOverrides overrides;

    public NetworkTypeResIdCache() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    public final String toString() {
        return "networkTypeResIdCache={id=" + this.cachedResId + ", isOverridden=" + this.isOverridden + "}";
    }

    public NetworkTypeResIdCache(MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides) {
        this.overrides = mobileIconCarrierIdOverrides;
    }

    public /* synthetic */ NetworkTypeResIdCache(MobileIconCarrierIdOverrides mobileIconCarrierIdOverrides, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new MobileIconCarrierIdOverridesImpl() : mobileIconCarrierIdOverrides);
    }
}
