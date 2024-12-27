package com.android.server.wm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Slog;

import com.samsung.android.server.packagefeature.PackageFeatureUserChange;
import com.samsung.android.server.util.SafetySystemService;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public abstract class PackagesChange {
    public static final List sAllPackagesChange = new ArrayList();
    public static final List sAllPackagesChangeAsTask = new ArrayList();
    public final ActivityTaskManagerService mAtmService;
    public PackageManager mPackageManager;
    public PackageFeatureUserChange[] mUserChanges;
    public final String mControllerName = getClass().getSimpleName();
    public final ConcurrentHashMap mCachedHomeActivities = new ConcurrentHashMap();

    public PackagesChange(ActivityTaskManagerService activityTaskManagerService) {
        this.mAtmService = activityTaskManagerService;
        ((ArrayList) sAllPackagesChange).add(this);
    }

    public static void updateAllValueToTask(Task task) {
        Iterator it = ((ArrayList) sAllPackagesChangeAsTask).iterator();
        while (it.hasNext()) {
            DisplayCutoutController displayCutoutController = (DisplayCutoutController) it.next();
            displayCutoutController.getClass();
            ComponentName componentName = task.realActivity;
            String packageName = componentName != null ? componentName.getPackageName() : null;
            task.mCutoutPolicy =
                    packageName != null
                            ? displayCutoutController.getPolicy(task.mUserId, packageName)
                            : 0;
        }
    }

    public void dump(PrintWriter printWriter, String str) {}

    public final List getLauncherActivities(int i, String str) {
        Context context;
        PackageManager packageManager;
        if (this.mPackageManager == null) {
            SafetySystemService.Manager manager = SafetySystemService.LazyHolder.sSingleton;
            synchronized (manager) {
                context = manager.mSystemContext;
            }
            if (context == null) {
                Slog.w(
                        "SafetySystemService",
                        "PackageManager".concat(" should be called after system ready."));
                packageManager = null;
            } else {
                packageManager = context.getPackageManager();
            }
            this.mPackageManager = packageManager;
        }
        if (this.mPackageManager == null) {
            return Collections.emptyList();
        }
        return this.mPackageManager.queryIntentActivitiesAsUser(
                new Intent("android.intent.action.MAIN")
                        .addCategory("android.intent.category.LAUNCHER")
                        .setPackage(str),
                786432,
                i);
    }
}
