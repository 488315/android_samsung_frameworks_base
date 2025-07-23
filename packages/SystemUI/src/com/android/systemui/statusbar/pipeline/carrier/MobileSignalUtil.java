package com.android.systemui.statusbar.pipeline.carrier;

import android.telephony.TelephonyManager;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MobileSignalUtil {
    public final CommonUtil commonUtil;
    public final boolean isVoiceCapable;

    public MobileSignalUtil(CommonUtil commonUtil, SystemPropertiesWrapper systemPropertiesWrapper, TelephonyManager telephonyManager) {
        this.commonUtil = commonUtil;
        this.isVoiceCapable = telephonyManager.isVoiceCapable();
    }
}
