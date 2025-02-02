package com.samsung.android.biometrics.app.setting.knox;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.display.DisplayManager;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.EnterpriseDeviceManager;
import com.samsung.android.knox.EnterpriseKnoxManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.container.KnoxContainerManager;
import com.samsung.android.knox.devicesecurity.PasswordPolicy;

/* loaded from: classes.dex */
public final class KnoxUtils {
    public static float adjustBiometricViewWeightsForPatternPortrait() {
        int i = Resources.getSystem().getDisplayMetrics().densityDpi;
        if (i == 420) {
            return 0.4f;
        }
        if (i == 450) {
            return 0.42f;
        }
        if (i == 480) {
            return 0.43f;
        }
        if (i == 510) {
            return 0.46f;
        }
        if (i != 540) {
            return i != 720 ? 0.5f : 0.55f;
        }
        return 0.49f;
    }

    private static int getAdminUid(Context context, int i) {
        try {
            return context.getPackageManager().getPackageUidAsUser(SemPersonaManager.getAdminComponentName(i).getPackageName(), i);
        } catch (Exception e) {
            Log.e("KKG::KnoxUtils", "Error fetching admin uid " + e.getMessage());
            return i;
        }
    }

    public static int getDisplayRotation(Context context) {
        int rotation = ((DisplayManager) context.getSystemService("display")).getDisplay(0).getRotation();
        if (rotation == 0) {
            return 0;
        }
        if (rotation == 1) {
            return 1;
        }
        if (rotation != 2) {
            return rotation != 3 ? -1 : 3;
        }
        return 2;
    }

    public static int getNavigationBarSize(Context context) {
        int identifier = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (identifier > 0) {
            return context.getResources().getDimensionPixelSize(identifier);
        }
        return 0;
    }

    public static PasswordPolicy getPasswordPolicy(Context context, int i) {
        if (!SemPersonaManager.isKnoxId(i)) {
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
            if (enterpriseDeviceManager != null) {
                return enterpriseDeviceManager.getPasswordPolicy();
            }
            return null;
        }
        KnoxContainerManager knoxContainerManager = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, new ContextInfo(getAdminUid(context, i), i));
        if (knoxContainerManager != null) {
            return knoxContainerManager.getPasswordPolicy();
        }
        return null;
    }

    public static int isChangeRequested(Context context, int i) {
        PasswordPolicy passwordPolicy;
        if (SemPersonaManager.isKnoxId(i)) {
            passwordPolicy = EnterpriseKnoxManager.getInstance().getKnoxContainerManager(context, new ContextInfo(getAdminUid(context, i), i)).getPasswordPolicy();
        } else {
            EnterpriseDeviceManager enterpriseDeviceManager = EnterpriseDeviceManager.getInstance(context);
            passwordPolicy = enterpriseDeviceManager != null ? enterpriseDeviceManager.getPasswordPolicy() : null;
        }
        if (passwordPolicy != null) {
            return passwordPolicy.isChangeRequested();
        }
        return 0;
    }

    public static boolean isFoldableProduct() {
        String trim = SystemProperties.get("ro.product.device", "NONE").trim();
        if (trim.startsWith("winner") || trim.startsWith("f2") || trim.startsWith("FSG") || trim.startsWith("SCG")) {
            return true;
        }
        return SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_FOLDABLE_TYPE_FOLD");
    }

    public static boolean isMultifactorEnabledForWork(Context context, int i) {
        if (!SemPersonaManager.isKnoxId(i)) {
            return false;
        }
        int intForUser = Settings.Secure.getIntForUser(context.getContentResolver(), "knox_finger_print_plus", 0, i);
        Log.d("KKG::KnoxUtils", "get two factor value : " + i + " : " + intForUser);
        return intForUser == 1;
    }

    public static boolean isTablet() {
        String str = SemSystemProperties.get("ro.build.characteristics");
        return str != null && str.contains("tablet");
    }
}
