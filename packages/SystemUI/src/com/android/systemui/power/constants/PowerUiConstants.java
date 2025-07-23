package com.android.systemui.power.constants;

import android.content.ComponentName;
import android.net.Uri;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class PowerUiConstants {
    public static final String DC_PACKAGE_NAME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static final Uri SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI = Uri.parse("content://com.samsung.android.sm/VerifyForcedAppStandby");
    public static final ComponentName TURN_OFF_PSM_COMPONENT_NAME = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.external.receiver.TurnOffPsmNotiReceiver");
}
