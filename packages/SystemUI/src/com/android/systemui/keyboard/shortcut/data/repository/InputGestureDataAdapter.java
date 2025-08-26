package com.android.systemui.keyboard.shortcut.data.repository;

import android.app.role.RoleManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.hardware.input.AppLaunchData;
import android.util.Log;
import androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0;
import com.android.internal.app.ResolverActivity;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;

/* loaded from: classes2.dex */
public final class InputGestureDataAdapter {
    public final Context context;
    public final InputGestureMaps inputGestureMaps;
    public final UserTracker userTracker;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public InputGestureDataAdapter(UserTracker userTracker, InputGestureMaps inputGestureMaps, Context context) {
        this.userTracker = userTracker;
        this.inputGestureMaps = inputGestureMaps;
        this.context = context;
    }

    public final Intent buildIntentFromComponentName(ComponentName componentName) throws PackageManager.NameNotFoundException {
        try {
            getUserContext().getPackageManager().getActivityInfo(componentName, 794624);
            Intent intent = new Intent("android.intent.action.MAIN");
            intent.addCategory("android.intent.category.LAUNCHER");
            intent.setComponent(componentName);
            return intent;
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("InputGestureDataUtils", "Unable to find activity info for componentName: " + componentName);
            return null;
        }
    }

    public final Intent fetchIntentFromAppLaunchData(AppLaunchData appLaunchData) {
        if (appLaunchData instanceof AppLaunchData.CategoryData) {
            return Intent.makeMainSelectorActivity("android.intent.action.MAIN", ((AppLaunchData.CategoryData) appLaunchData).getCategory());
        }
        if (!(appLaunchData instanceof AppLaunchData.RoleData)) {
            if (appLaunchData instanceof AppLaunchData.ComponentData) {
                AppLaunchData.ComponentData componentData = (AppLaunchData.ComponentData) appLaunchData;
                String packageName = componentData.getPackageName();
                String className = componentData.getClassName();
                Intent intentBuildIntentFromComponentName = buildIntentFromComponentName(new ComponentName(packageName, className));
                if (intentBuildIntentFromComponentName != null) {
                    return intentBuildIntentFromComponentName;
                }
                Intent intentBuildIntentFromComponentName2 = buildIntentFromComponentName(new ComponentName(getUserContext().getPackageManager().canonicalToCurrentPackageNames(new String[]{packageName})[0], className));
                if (intentBuildIntentFromComponentName2 != null) {
                    return intentBuildIntentFromComponentName2;
                }
            }
            return null;
        }
        String role = ((AppLaunchData.RoleData) appLaunchData).getRole();
        PackageManager packageManager = getUserContext().getPackageManager();
        Object systemService = getUserContext().getSystemService((Class<Object>) RoleManager.class);
        systemService.getClass();
        RoleManager roleManager = (RoleManager) systemService;
        if (!roleManager.isRoleAvailable(role)) {
            Log.w("InputGestureDataUtils", "Role " + role + " is not available.");
            return null;
        }
        String defaultApplication = roleManager.getDefaultApplication(role);
        if (defaultApplication != null) {
            Intent launchIntentForPackage = packageManager.getLaunchIntentForPackage(defaultApplication);
            if (launchIntentForPackage != null) {
                return launchIntentForPackage;
            }
            MotionLayout$$ExternalSyntheticOutline0.m("No launch intent for role ", role, "InputGestureDataUtils");
            return null;
        }
        Log.w("InputGestureDataUtils", "No default application for role " + role + ", user= " + getUserContext().getUser());
        return null;
    }

    public final Context getUserContext() {
        UserTracker userTracker = this.userTracker;
        return ((UserTrackerImpl) userTracker).createCurrentUserContext(((UserTrackerImpl) userTracker).getUserContext());
    }

    public final ActivityInfo resolveSingleMatchingActivityFrom(Intent intent) {
        ActivityInfo activityInfoResolveActivityInfo = intent.resolveActivityInfo(getUserContext().getPackageManager(), 65536);
        if (activityInfoResolveActivityInfo == null || StringsKt__StringsJVMKt.equals(Reflection.getOrCreateKotlinClass(ResolverActivity.class).getQualifiedName(), activityInfoResolveActivityInfo.name, false)) {
            return null;
        }
        return activityInfoResolveActivityInfo;
    }
}
