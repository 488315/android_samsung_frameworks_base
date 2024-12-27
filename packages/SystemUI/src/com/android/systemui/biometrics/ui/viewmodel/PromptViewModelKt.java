package com.android.systemui.biometrics.ui.viewmodel;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.systemui.biometrics.Utils;
import com.android.systemui.biometrics.domain.model.BiometricPromptRequest;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public abstract class PromptViewModelKt {
    public static final ApplicationInfo getApplicationInfoForLogo(BiometricPromptRequest.Biometric biometric, Context context, ComponentName componentName) {
        String str;
        if (componentName != null) {
            str = componentName.getPackageName();
        } else {
            boolean z = biometric.allowBackgroundAuthentication;
            String str2 = biometric.opPackageName;
            if (!z) {
                int i = Utils.$r8$clinit;
                if (context.checkCallingOrSelfPermission("android.permission.USE_BIOMETRIC_INTERNAL") != 0 || !"android".equals(str2)) {
                    str = null;
                }
            }
            str = str2;
        }
        if (str == null) {
            MotionLayout$$ExternalSyntheticOutline0.m("Cannot find application info for ", biometric.opPackageName, "PromptViewModel");
            return null;
        }
        try {
            return context.getPackageManager().getApplicationInfo(str, 4194816);
        } catch (PackageManager.NameNotFoundException e) {
            Log.w("PromptViewModel", "Cannot find application info for " + context.getOpPackageName(), e);
            return null;
        }
    }

    public static final ComponentName getComponentNameForLogo(BiometricPromptRequest.Biometric biometric, ActivityTaskManager activityTaskManager) {
        boolean z = true;
        ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.firstOrNull(activityTaskManager.getTasks(1));
        ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
        ComponentName componentName2 = biometric.componentNameForConfirmDeviceCredentialActivity;
        if (componentName2 != null) {
            return componentName2;
        }
        String packageName = componentName != null ? componentName.getPackageName() : null;
        boolean z2 = packageName instanceof String;
        String str = biometric.opPackageName;
        if (z2 && str != null) {
            z = packageName.contentEquals(str);
        } else if (z2 && (str instanceof String)) {
            z = Intrinsics.areEqual(packageName, str);
        } else if (packageName != str) {
            if (packageName != null && str != null && packageName.length() == str.length()) {
                int length = packageName.length();
                for (int i = 0; i < length; i++) {
                    if (packageName.charAt(i) == str.charAt(i)) {
                    }
                }
            }
            z = false;
            break;
        }
        if (z) {
            return componentName;
        }
        Log.w("PromptViewModel", "Top activity " + componentName + " is not the client " + str);
        return null;
    }
}
