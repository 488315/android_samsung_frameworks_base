package com.android.systemui.statusbar.pipeline.carrier;

import android.telephony.TelephonyManager;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MobileSignalUtil {
    public final CommonUtil commonUtil;
    public final boolean isVoiceCapable;

    public MobileSignalUtil(CommonUtil commonUtil, SystemPropertiesWrapper systemPropertiesWrapper, TelephonyManager telephonyManager) {
        this.commonUtil = commonUtil;
        this.isVoiceCapable = telephonyManager.isVoiceCapable();
    }
}
