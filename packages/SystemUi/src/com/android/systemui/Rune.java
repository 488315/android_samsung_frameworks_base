package com.android.systemui;

import android.os.Build;
import android.os.SystemProperties;
import android.os.UserManager;
import com.android.internal.app.AppLockPolicy;
import com.android.systemui.keyguard.KeyguardViewMediator$$ExternalSyntheticLambda6;
import com.android.systemui.uithreadmonitor.BinderCallMonitorConstants;
import com.android.systemui.util.DeviceType;
import com.samsung.android.feature.SemCscFeature;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class Rune extends BaseRune {
    public static final boolean SYSUI_APPLOCK;
    public static final boolean SYSUI_BINDER_CALL_MONITOR;
    public static final boolean SYSUI_CHINA_FEATURE;
    public static final boolean SYSUI_MULTI_SIM = DeviceType.isMultiSimSupported();
    public static final boolean SYSUI_MULTI_USER = UserManager.supportsMultipleUsers();
    public static final boolean SYSUI_TEST_FOR_PLANK;
    public static final boolean SYSUI_UI_THREAD_MONITOR;

    /* JADX WARN: Code restructure failed: missing block: B:22:0x0052, code lost:
    
        if (com.android.systemui.util.DeviceType.sRpCount == 0) goto L30;
     */
    static {
        boolean z = true;
        SYSUI_TEST_FOR_PLANK = Build.IS_ENG || Build.IS_USERDEBUG;
        boolean z2 = DeviceType.getDebugLevel() == 1 || DeviceType.getDebugLevel() == 2 || !DeviceType.isShipBuild();
        SYSUI_UI_THREAD_MONITOR = z2;
        if (!BinderCallMonitorConstants.STRICT_MODE_ENABLED) {
            if (!z2 && !DeviceType.isEngOrUTBinary()) {
                if (DeviceType.sRpCount == -2) {
                    DeviceType.sRpCount = SystemProperties.getInt("ro.boot.rp", -1);
                }
            }
            SYSUI_BINDER_CALL_MONITOR = z;
            SYSUI_APPLOCK = AppLockPolicy.isSupportAppLock();
            SYSUI_CHINA_FEATURE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false);
        }
        z = false;
        SYSUI_BINDER_CALL_MONITOR = z;
        SYSUI_APPLOCK = AppLockPolicy.isSupportAppLock();
        SYSUI_CHINA_FEATURE = SemCscFeature.getInstance().getBoolean("CscFeature_Common_SupportZProjectFunctionInGlobal", false);
    }

    public static void runIf(Runnable runnable, boolean z) {
        if (z) {
            runnable.run();
        }
    }

    public static void runIf(int i, KeyguardViewMediator$$ExternalSyntheticLambda6 keyguardViewMediator$$ExternalSyntheticLambda6) {
        keyguardViewMediator$$ExternalSyntheticLambda6.accept(Integer.valueOf(i));
    }
}
