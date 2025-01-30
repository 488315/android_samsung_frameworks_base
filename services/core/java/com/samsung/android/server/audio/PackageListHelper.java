package com.samsung.android.server.audio;

import android.R;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Binder;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class PackageListHelper {
    public static AppCategorizer sCategorizer;
    public static PackageListHelper sInstance;
    public final List mAllowedPackageList;
    public final List mRestrictedPackageList;

    public PackageListHelper(Context context) {
        sCategorizer = new AppCategorizer(AudioSettingsHelper.getInstance(context));
        this.mAllowedPackageList = Arrays.asList(Resources.getSystem().getStringArray(R.array.config_mainBuiltInDisplayCutoutSideOverride));
        this.mRestrictedPackageList = Arrays.asList(Resources.getSystem().getStringArray(17236281));
    }

    public static PackageListHelper getInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PackageListHelper(context);
        }
        return sInstance;
    }

    public static void destroy() {
        sInstance = null;
        AudioSettingsHelper.destroy();
    }

    public void initPackageList(Context context) {
        Log.d("PackageListHelper", "initPackageList");
        for (int i = 0; i < this.mAllowedPackageList.size(); i++) {
            int uidForPackage = AudioUtils.getUidForPackage(context, (String) this.mAllowedPackageList.get(i));
            if (uidForPackage != 0) {
                sCategorizer.putPackage(uidForPackage, (String) this.mAllowedPackageList.get(i));
            }
        }
    }

    public void removePackageForName(Context context, String str) {
        if (checkAudioSettingsPermission(context, "removePackageForName")) {
            sCategorizer.removePackage(AudioUtils.getUidForPackage(context, str));
        }
    }

    public boolean isAlreadyInDB(String str) {
        if (isRestrictedPackage(str)) {
            return true;
        }
        return sCategorizer.checkExist(str);
    }

    public boolean isInAllowedList(String str) {
        for (int i = 0; i < this.mAllowedPackageList.size(); i++) {
            if (TextUtils.equals(str, (CharSequence) this.mAllowedPackageList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public boolean isRestrictedPackage(String str) {
        for (int i = 0; i < this.mRestrictedPackageList.size(); i++) {
            if (TextUtils.equals(str, (CharSequence) this.mRestrictedPackageList.get(i))) {
                return true;
            }
        }
        return false;
    }

    public String[] getSelectedAppList() {
        return sCategorizer.getSelectedPackages();
    }

    public void addPackage(Context context, int i, String str) {
        if (context.checkCallingOrSelfPermission("android.permission.MODIFY_PHONE_STATE") != 0) {
            Log.w("PackageListHelper", "MODIFY_PHONE_STATE Permission Denial from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
            return;
        }
        try {
            ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(str, 128);
            sCategorizer.putPackage(applicationInfo.uid, applicationInfo.packageName);
        } catch (PackageManager.NameNotFoundException unused) {
        }
    }

    public boolean checkAudioSettingsPermission(Context context, String str) {
        if (context.checkCallingOrSelfPermission("android.permission.MODIFY_AUDIO_SETTINGS") == 0) {
            return true;
        }
        Log.w("PackageListHelper", "Audio Settings Permission Denial: " + str + " from pid=" + Binder.getCallingPid() + ", uid=" + Binder.getCallingUid());
        return false;
    }
}
