package android.app;

import android.content.pm.IPackageManager;
import android.permission.IPermissionManager;

/* loaded from: classes.dex */
public class AppGlobals {
    public static Application getInitialApplication() {
        return ActivityThread.currentApplication();
    }

    public static String getInitialPackage() {
        return ActivityThread.currentPackageName();
    }

    public static IPackageManager getPackageManager() {
        return ActivityThread.getPackageManager();
    }

    public static IPermissionManager getPermissionManager() {
        return ActivityThread.getPermissionManager();
    }

    public static int getIntCoreSetting(String str, int i) {
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        return currentActivityThread != null ? currentActivityThread.getIntCoreSetting(str, i) : i;
    }

    public static float getFloatCoreSetting(String str, float f) {
        ActivityThread currentActivityThread = ActivityThread.currentActivityThread();
        return currentActivityThread != null ? currentActivityThread.getFloatCoreSetting(str, f) : f;
    }
}
