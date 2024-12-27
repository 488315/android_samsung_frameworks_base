package com.android.systemui;

import android.os.Build;
import android.os.UserManager;
import com.android.internal.app.AppLockPolicy;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class Rune extends BaseRune {
    public static final boolean SYSUI_APPLOCK;
    public static final boolean SYSUI_BINDER_CALL_MONITOR;
    public static final boolean SYSUI_CHINA_FEATURE;
    public static final boolean SYSUI_MULTI_SIM = DeviceType.isMultiSimSupported();
    public static final boolean SYSUI_MULTI_USER = UserManager.supportsMultipleUsers();
    public static final boolean SYSUI_TEST_FOR_PLANK;
    public static final boolean SYSUI_UI_THREAD_MONITOR;

    static {
        boolean z = true;
        SYSUI_TEST_FOR_PLANK = Build.IS_ENG || Build.IS_USERDEBUG;
        boolean z2 = DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_MID || DeviceType.getDebugLevel() == DeviceType.DEBUG_LEVEL_HIGH || !DeviceType.isShipBuild();
        SYSUI_UI_THREAD_MONITOR = z2;
        if (BinderCallMonitorConstants.STRICT_MODE_ENABLED || (!z2 && !DeviceType.isEngOrUTBinary() && DeviceType.getRpCount() != 0)) {
            z = false;
        }
        SYSUI_BINDER_CALL_MONITOR = z;
        SYSUI_APPLOCK = AppLockPolicy.isSupportAppLock();
        SYSUI_CHINA_FEATURE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false);
    }

    public static void runIf(Runnable runnable, boolean z) {
        if (z) {
            runnable.run();
        }
    }

    public static void runIf(int i, Consumer consumer) {
        consumer.accept(Integer.valueOf(i));
    }
}
