package com.android.systemui.power;

import android.net.Uri;
import com.samsung.android.feature.SemFloatingFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PowerUiConstants {
    public static final String DC_PACKAGE_NAME = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_SMARTMANAGER_CONFIG_PACKAGE_NAME");
    public static final Uri SMART_MGR_VERIFY_FORCED_APP_STANDBY_URI = Uri.parse("content://com.samsung.android.sm/VerifyForcedAppStandby");
}
