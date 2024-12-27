package com.samsung.android.server.audio;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Binder;
import android.util.Log;

import com.samsung.android.server.audio.utils.AudioUtils;

import java.util.Arrays;
import java.util.List;

public final class PackageListHelper {
    public static AppCategorizer sCategorizer;
    public static PackageListHelper sInstance;
    public final List mAllowedPackageList;
    public final List mRestrictedPackageList;

    public PackageListHelper(Context context) {
        sCategorizer = new AppCategorizer(AudioSettingsHelper.getInstance(context));
        this.mAllowedPackageList =
                Arrays.asList(
                        Resources.getSystem()
                                .getStringArray(R.array.config_longPressOnPowerDurationSettings));
        this.mRestrictedPackageList = Arrays.asList(Resources.getSystem().getStringArray(17236291));
    }

    public static void addPackage(Context context, String str) {
        if (context.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") == 0) {
            try {
                ApplicationInfo applicationInfo =
                        context.getPackageManager().getApplicationInfo(str, 128);
                sCategorizer.putPackage(applicationInfo.uid, applicationInfo.packageName);
            } catch (PackageManager.NameNotFoundException unused) {
            }
        } else {
            Log.w(
                    "PackageListHelper",
                    "MODIFY_PHONE_STATE Permission Denial from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid());
        }
    }

    public static void destroy() {
        sInstance = null;
        AudioSettingsHelper.destroy();
    }

    public static void removePackageForName(Context context, String str) {
        if (context.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") != 0) {
            Log.w(
                    "PackageListHelper",
                    "Audio Settings Permission Denial: removePackageForName from pid="
                            + Binder.getCallingPid()
                            + ", uid="
                            + Binder.getCallingUid());
            return;
        }
        AppCategorizer appCategorizer = sCategorizer;
        int uidForPackage = AudioUtils.getUidForPackage(context, str);
        synchronized (appCategorizer.appList) {
            appCategorizer.appList.remove(Integer.valueOf(uidForPackage));
            AudioSettingsHelper audioSettingsHelper = appCategorizer.mSettingsHelper;
            audioSettingsHelper.getClass();
            audioSettingsHelper.remove("selectedpkg", "_uid='" + uidForPackage + "'");
        }
    }
}
