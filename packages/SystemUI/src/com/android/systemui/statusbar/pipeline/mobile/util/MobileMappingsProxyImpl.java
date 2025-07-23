package com.android.systemui.statusbar.pipeline.mobile.util;

import com.android.settingslib.SignalIcon$MobileIconGroup;
import com.android.settingslib.mobile.MobileMappings;
import com.android.settingslib.mobile.TelephonyIcons;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileMappingsProxyImpl implements MobileMappingsProxy {
    public final SignalIcon$MobileIconGroup getDefaultIcons(MobileMappings.Config config) {
        return !config.showAtLeast3G ? TelephonyIcons.G : TelephonyIcons.THREE_G;
    }
}
