package com.android.systemui.statusbar.pipeline.carrier;

import android.telephony.TelephonyManager;

/* loaded from: classes3.dex */
public final class MobileSignalUtil {
    public final CommonUtil commonUtil;
    public final boolean isVoiceCapable;

    public MobileSignalUtil(CommonUtil commonUtil, SystemPropertiesWrapper systemPropertiesWrapper, TelephonyManager telephonyManager) {
        this.commonUtil = commonUtil;
        this.isVoiceCapable = telephonyManager.isVoiceCapable();
    }
}
