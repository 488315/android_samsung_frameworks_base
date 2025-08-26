package com.android.systemui.dextouchpad.util;

import android.os.Debug;
import android.os.FactoryTest;
import android.os.SemSystemProperties;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;

/* loaded from: classes2.dex */
public class Features {
    public static final boolean DEBUG;
    public static final boolean IS_FACTORY_BINARY;
    public static final boolean IS_SPEN_INBOX_MODEL;
    public static final boolean IS_SUPPORT_SPEN;
    public static final boolean IS_SUPPORT_TABLET;
    public static final boolean IS_SUPPORT_WINNER;

    static {
        DEBUG = Debug.semIsProductDev() || SemSystemProperties.getInt("ro.debuggable", 0) != 0 || Log.isLoggable("DMS", 3);
        IS_FACTORY_BINARY = FactoryTest.isFactoryMode();
        IS_SUPPORT_TABLET = SemSystemProperties.get("ro.build.characteristics").contains("tablet") || (SemSystemProperties.getInt("ro.debuggable", 0) != 0 && SemSystemProperties.getBoolean("persist.service.dex.tablet", false));
        IS_SUPPORT_WINNER = SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD") && !SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_HALF_FOLDED_MODE");
        int i = SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_FRAMEWORK_CONFIG_SPEN_VERSION", -1);
        boolean z = i > 0;
        IS_SUPPORT_SPEN = z;
        IS_SPEN_INBOX_MODEL = z && i % 10 == 5;
    }

    private Features() {
    }
}
