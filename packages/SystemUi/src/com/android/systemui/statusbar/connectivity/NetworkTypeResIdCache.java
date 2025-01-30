package com.android.systemui.statusbar.connectivity;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileIconCarrierIdOverrides;
import com.android.settingslib.mobile.MobileIconCarrierIdOverridesImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
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
