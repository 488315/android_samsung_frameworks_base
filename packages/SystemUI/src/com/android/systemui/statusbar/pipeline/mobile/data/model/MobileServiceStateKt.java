package com.android.systemui.statusbar.pipeline.mobile.data.model;

import android.telephony.TelephonyDisplayInfo;

public abstract class MobileServiceStateKt {
    public static final MobileServiceState DEFAULT_SERVICE_STATE = new MobileServiceState(0, false, 0, 0, 0, 0, new TelephonyDisplayInfo(13, 2), null, 128, null);
}
