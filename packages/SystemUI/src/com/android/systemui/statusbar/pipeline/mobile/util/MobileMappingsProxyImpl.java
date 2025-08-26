package com.android.systemui.statusbar.pipeline.mobile.util;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;

/* loaded from: classes3.dex */
public final class MobileMappingsProxyImpl implements MobileMappingsProxy {
    public final SignalIcon$MobileIconGroup getDefaultIcons(MobileMappings.Config config) {
        return !config.showAtLeast3G ? TelephonyIcons.G : TelephonyIcons.THREE_G;
    }
}
