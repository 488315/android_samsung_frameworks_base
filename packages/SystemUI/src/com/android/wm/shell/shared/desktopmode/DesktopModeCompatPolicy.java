package com.android.wm.shell.shared.desktopmode;

import android.R;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.window.DesktopModeFlags;
import com.android.wm.shell.desktopmode.common.DefaultHomePackageSupplier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public final class DesktopModeCompatPolicy {
    public final HashSet allowFullScreenTransparentPackages;
    public final Context context;
    public DefaultHomePackageSupplier defaultHomePackageSupplier;
    public final Map packageInfoCache = new LinkedHashMap();
    public final String systemUiPackage;

    public DesktopModeCompatPolicy(Context context) throws Resources.NotFoundException {
        this.context = context;
        this.systemUiPackage = context.getResources().getString(R.string.config_systemUi);
        String[] stringArray = context.getResources().getStringArray(R.array.carrier_properties);
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(stringArray.length));
        ArraysKt___ArraysKt.toCollection(hashSet, stringArray);
        this.allowFullScreenTransparentPackages = hashSet;
    }

    public static boolean hasPlatformSignature(TaskInfo taskInfo) {
        ApplicationInfo applicationInfo;
        if (!DesktopModeFlags.ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE.isTrue()) {
            return !DesktopModeFlags.ENABLE_MODALS_FULLSCREEN_WITH_PERMISSIONS.isTrue();
        }
        ActivityInfo activityInfo = taskInfo.topActivityInfo;
        if (activityInfo == null || (applicationInfo = activityInfo.applicationInfo) == null) {
            return false;
        }
        return applicationInfo.isSignedWithPlatformKey();
    }

    public static boolean isTransparentTask(int i, boolean z) {
        return z && i > 0;
    }

    public final String getDefaultHomePackage() {
        String str;
        DefaultHomePackageSupplier defaultHomePackageSupplier = this.defaultHomePackageSupplier;
        if (defaultHomePackageSupplier != null && (str = (String) defaultHomePackageSupplier.get()) != null) {
            return str;
        }
        ComponentName homeActivities = this.context.getPackageManager().getHomeActivities(new ArrayList());
        if (homeActivities != null) {
            return homeActivities.getPackageName();
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:? A[ADDED_TO_REGION, RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0031  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00d4  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x00f4  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x013a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean isTopActivityExemptFromDesktopWindowing(TaskInfo taskInfo) {
        String packageName;
        boolean z;
        ActivityInfo activityInfo;
        Bundle bundle;
        boolean zBooleanValue;
        boolean z2;
        PackageInfo packageInfoAsUser;
        String[] strArr;
        int activityType;
        ComponentName componentName = taskInfo.baseActivity;
        if (componentName == null || (packageName = componentName.getPackageName()) == null || !DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODALS_POLICY.isTrue() || taskInfo.isTopActivityNoDisplay) {
            return false;
        }
        if (packageName.equals(this.systemUiPackage)) {
            if (!isTransparentTask(taskInfo.numActivities, taskInfo.isActivityStackTransparent)) {
                if (!(getDefaultHomePackage() == null || packageName.equals(getDefaultHomePackage())) || ((activityType = taskInfo.getActivityType()) != 2 && activityType != 3)) {
                    if (isTransparentTask(taskInfo.numActivities, taskInfo.isActivityStackTransparent) && (packageName.equals(getDefaultHomePackage()) || ((!packageName.startsWith("com.samsung.") && !packageName.startsWith("com.sec.")) || this.allowFullScreenTransparentPackages.contains(packageName) || (!taskInfo.hasNoTopWindow && !taskInfo.isFullSizeWindow)))) {
                        int i = taskInfo.userId;
                        if (!DesktopModeFlags.ENABLE_MODALS_FULLSCREEN_WITH_PERMISSIONS.isTrue()) {
                            zBooleanValue = !DesktopModeFlags.ENABLE_MODALS_FULLSCREEN_WITH_PLATFORM_SIGNATURE.isTrue();
                        } else if (this.allowFullScreenTransparentPackages.contains(packageName)) {
                            zBooleanValue = true;
                        } else {
                            String str = i + "@" + packageName;
                            LinkedHashMap linkedHashMap = (LinkedHashMap) this.packageInfoCache;
                            Object objValueOf = linkedHashMap.get(str);
                            if (objValueOf == null) {
                                try {
                                    packageInfoAsUser = this.context.getPackageManager().getPackageInfoAsUser(packageName, 4096, i);
                                } catch (PackageManager.NameNotFoundException unused) {
                                }
                                if (packageInfoAsUser == null || (strArr = packageInfoAsUser.requestedPermissions) == null) {
                                    z2 = false;
                                    objValueOf = Boolean.valueOf(z2);
                                    linkedHashMap.put(str, objValueOf);
                                } else {
                                    if (ArraysKt___ArraysKt.indexOf(strArr, "android.permission.SYSTEM_ALERT_WINDOW") >= 0) {
                                        z2 = true;
                                    }
                                    objValueOf = Boolean.valueOf(z2);
                                    linkedHashMap.put(str, objValueOf);
                                }
                            }
                            zBooleanValue = ((Boolean) objValueOf).booleanValue();
                        }
                        if (!zBooleanValue && !hasPlatformSignature(taskInfo)) {
                        }
                    } else {
                        if (isTransparentTask(taskInfo.numActivities, taskInfo.isActivityStackTransparent) && !hasPlatformSignature(taskInfo)) {
                            return false;
                        }
                        int i2 = taskInfo.resizeMode;
                        if (i2 != 0 && i2 != 10) {
                            return false;
                        }
                        try {
                            ComponentName componentName2 = taskInfo.baseActivity;
                            activityInfo = componentName2 != null ? this.context.getPackageManager().getActivityInfo(componentName2, 128) : null;
                        } catch (PackageManager.NameNotFoundException unused2) {
                        }
                        if (activityInfo == null || (bundle = activityInfo.metaData) == null) {
                            z = false;
                            if (!z || taskInfo.numActivities != 1) {
                            }
                        } else {
                            if (!bundle.getBoolean("com.samsung.android.multiwindow.nonresizeable")) {
                                if (bundle.getBoolean("com.samsung.android.multiwindow.ignore.nonresizable.setting")) {
                                }
                                return !z ? false : false;
                            }
                            z = true;
                            if (!z) {
                            }
                        }
                    }
                    return true;
                }
            }
        }
        return true;
    }

    public final boolean shouldDisableDesktopEntryPoints(TaskInfo taskInfo) {
        ComponentName componentName = taskInfo.baseActivity;
        String packageName = componentName != null ? componentName.getPackageName() : null;
        int i = taskInfo.numActivities;
        boolean z = taskInfo.isTopActivityNoDisplay;
        boolean z2 = taskInfo.isActivityStackTransparent;
        if (z || Intrinsics.areEqual(packageName, this.systemUiPackage) || getDefaultHomePackage() == null) {
            return true;
        }
        return (packageName != null && packageName.equals(getDefaultHomePackage())) || isTransparentTask(i, z2);
    }
}
