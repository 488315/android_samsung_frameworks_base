package com.android.wm.shell.shared.desktopmode;

import android.R;
import android.app.TaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.window.DesktopModeFlags;
import com.android.wm.shell.desktopmode.common.DefaultHomePackageSupplier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.MapsKt__MapsJVMKt;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopModeCompatPolicy {
    public final HashSet allowFullScreenTransparentPackages;
    public final Context context;
    public DefaultHomePackageSupplier defaultHomePackageSupplier;
    public final Map packageInfoCache = new LinkedHashMap();
    public final String systemUiPackage;

    public DesktopModeCompatPolicy(Context context) {
        this.context = context;
        this.systemUiPackage = context.getResources().getString(R.string.config_systemUi);
        HashSet hashSet = new HashSet(MapsKt__MapsJVMKt.mapCapacity(2));
        ArraysKt___ArraysKt.toCollection(hashSet, new String[]{"com.google.android.cellbroadcastreceiver", "com.samsung.android.mtp"});
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

    /* JADX WARN: Code restructure failed: missing block: B:12:0x002e, code lost:
    
        if (isTransparentTask(r9.numActivities, r9.isActivityStackTransparent) != false) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c2, code lost:
    
        if (hasPlatformSignature(r9) == false) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00f4, code lost:
    
        if (r0.getBoolean("com.samsung.android.multiwindow.ignore.nonresizable.setting") != false) goto L68;
     */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00fc  */
    /* JADX WARN: Removed duplicated region for block: B:77:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isTopActivityExemptFromDesktopWindowing(android.app.TaskInfo r9) {
        /*
            Method dump skipped, instructions count: 288
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(android.app.TaskInfo):boolean");
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
