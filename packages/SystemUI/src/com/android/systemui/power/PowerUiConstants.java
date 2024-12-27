package com.android.systemui.power;

import android.content.ComponentName;
import android.net.Uri;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PowerUiConstants {
    public static final String DC_PACKAGE_NAME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static final Uri SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI = Uri.parse("content://com.samsung.android.sm/VerifyForcedAppStandby");
    public static final ComponentName TURN_OFF_PSM_COMPONENT_NAME = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.external.receiver.TurnOffPsmNotiReceiver");
}
