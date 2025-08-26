package com.android.systemui.statusbar.connectivity;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NetworkTypeResIdCache {
    public int cachedResId;
    public boolean isOverridden;
    public Integer lastCarrierId;
    public SignalIcon$MobileIconGroup lastIconGroup;
    public final MobileIconCarrierIdOverrides overrides;

    /* JADX WARN: Multi-variable type inference failed */
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
