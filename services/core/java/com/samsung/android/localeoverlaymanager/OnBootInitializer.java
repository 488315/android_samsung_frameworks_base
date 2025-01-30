package com.samsung.android.localeoverlaymanager;

import android.app.LocaleManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.SemUserInfo;
import android.os.Build;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.Log;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;

/* loaded from: classes2.dex */
public class OnBootInitializer {
    public static final String TAG = "OnBootInitializer";
    public LocaleOverlayManager mManager;

    public OnBootInitializer(LocaleOverlayManager localeOverlayManager) {
        this.mManager = localeOverlayManager;
    }

    public boolean updateOverlays(Set set, Context context, boolean z) {
        SharedPreferences preferences = PreferenceUtils.getPreferences(context);
        String string = preferences.getString("old_build_number", null);
        String str = Build.VERSION.INCREMENTAL;
        String str2 = TAG;
        Log.i(str2, "updateOverlays: oldBuildNum: " + string + ", curBuildNum: " + str + ", isSafeMode: " + z);
        if (!str.equals(string) || z) {
            this.mManager.setPackageUpdateTask(true);
            Set systemLocales = Utils.getSystemLocales();
            LogWriter.logDebugInfoAndLogcat(str2, "updateOverlays: Performing FOTA/safeMode Update. systemLocales = [" + systemLocales + "], oldBuildNum = [" + string + "], curBuildNum = [" + str + "], isSafeMode = [" + z + "]");
            if (string == null) {
                String substring = SystemProperties.get("persist.sys.locale", "en-US").substring(0, 2);
                systemLocales.add(substring);
                LogWriter.logDebugInfoAndLogcat(str2, "updateOverlays: Adding default locale to systemLocales. default locale = " + substring + ", systemLocales = " + systemLocales);
            }
            PreferenceUtils.setLocalesForUser(Utils.getCurrentUserId(), systemLocales);
            preferences.edit().putBoolean("locale_in_progress", true).commit();
            this.mManager.disableUnRequiredLocaleOverlays(systemLocales);
            Utils.deleteFilesInDir(new File("/data/overlays/current_locale_apks/files/"));
            this.mManager.installLocalesForPackages(set, systemLocales);
            preferences.edit().putString("old_build_number", str).commit();
            return true;
        }
        if (preferences.getBoolean("locale_in_progress", false)) {
            Log.i(str2, "updateOverlays: Performing incomplete locale change");
            this.mManager.setPackageUpdateTask(true);
            this.mManager.installLocalesForPackages(set);
            return true;
        }
        String string2 = preferences.getString("app_in_progress", "None");
        if (!"None".equals(string2)) {
            Log.i(str2, "updateOverlays: Performing incomplete app update");
            this.mManager.setPackageUpdateTask(true);
            this.mManager.setUpdatedPackage(string2);
            this.mManager.installLocalesForPackages(Collections.singleton(string2));
            return true;
        }
        Set systemLocales2 = Utils.getSystemLocales();
        Set localesForUser = PreferenceUtils.getLocalesForUser(Utils.getCurrentUserId());
        systemLocales2.removeAll(localesForUser);
        if (!systemLocales2.isEmpty()) {
            Log.i(str2, "updateOverlays: Installing locales for cases like setup skipped in FDM");
            preferences.edit().putBoolean("locale_in_progress", true).commit();
            localesForUser.addAll(systemLocales2);
            PreferenceUtils.setLocalesForUser(Utils.getCurrentUserId(), localesForUser);
            this.mManager.installLocales(systemLocales2, null);
            return true;
        }
        return !this.mManager.ensureOverlaysEnabled(new ArrayList(localesForUser));
    }

    public void cleanupOverlayDir(final Context context) {
        if (context == null) {
            Log.e(TAG, "cleanupOverlayDir called with null context. skip cleanup.");
            return;
        }
        List semGetUsers = ((UserManager) context.getSystemService("user")).semGetUsers();
        final HashSet hashSet = new HashSet();
        Log.i(TAG, "getLocalesForAllUsers: UserInfos - " + semGetUsers);
        Iterator it = semGetUsers.iterator();
        while (it.hasNext()) {
            int semGetIdentifier = ((SemUserInfo) it.next()).getUserHandle().semGetIdentifier();
            Set localesForUser = PreferenceUtils.getLocalesForUser(semGetIdentifier);
            String str = TAG;
            Log.i(str, "getLocalesForAllUsers: UserId - " + semGetIdentifier + " has locales - " + localesForUser);
            if (localesForUser.isEmpty()) {
                Log.e(str, "cleanupOverlayDir: initialization of new user is not done yet. Skip cleanup. UserId: " + semGetIdentifier);
                return;
            }
            hashSet.addAll(localesForUser);
        }
        OverlayConstants.ISO_639_2_TO_639_1_MAPPING.forEach(new BiConsumer() { // from class: com.samsung.android.localeoverlaymanager.OnBootInitializer$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                OnBootInitializer.lambda$cleanupOverlayDir$0(hashSet, (String) obj, (String) obj2);
            }
        });
        String str2 = TAG;
        Log.e(str2, "Languages to skip cleanup " + hashSet);
        final LocaleManager localeManager = (LocaleManager) context.getSystemService(LocaleManager.class);
        File[] listFiles = new File("/data/overlays/current_locale_apks/files/").listFiles(new FileFilter() { // from class: com.samsung.android.localeoverlaymanager.OnBootInitializer$$ExternalSyntheticLambda1
            @Override // java.io.FileFilter
            public final boolean accept(File file) {
                boolean lambda$cleanupOverlayDir$1;
                lambda$cleanupOverlayDir$1 = OnBootInitializer.lambda$cleanupOverlayDir$1(hashSet, context, localeManager, file);
                return lambda$cleanupOverlayDir$1;
            }
        });
        if (listFiles == null || listFiles.length == 0) {
            return;
        }
        Log.i(str2, "Cleaning up files " + Arrays.toString(listFiles));
        for (File file : listFiles) {
            Log.i(TAG, "Cleaning up " + file.getName());
            Utils.deleteFile(file);
        }
    }

    public static /* synthetic */ void lambda$cleanupOverlayDir$0(Set set, String str, String str2) {
        if (set.remove(str)) {
            Log.i(TAG, "replacing code: " + str + " with " + str2);
            set.add(str2);
        }
    }

    public static /* synthetic */ boolean lambda$cleanupOverlayDir$1(Set set, Context context, LocaleManager localeManager, File file) {
        boolean z;
        String substring;
        int lastIndexOf;
        String absolutePath = file.getAbsolutePath();
        Iterator it = set.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (absolutePath.endsWith(((String) it.next()) + ".apk")) {
                z = true;
                break;
            }
        }
        int lastIndexOf2 = absolutePath.lastIndexOf(46);
        if (lastIndexOf2 > 0 && (lastIndexOf = (substring = absolutePath.substring(41, lastIndexOf2)).lastIndexOf(46)) > 0) {
            String substring2 = substring.substring(0, lastIndexOf);
            if (Utils.isValidPackage(context, substring2)) {
                String substring3 = substring.substring(substring2.length() + 1);
                Set localesListAsSet = Utils.getLocalesListAsSet(localeManager.getApplicationLocales(substring2));
                if (localesListAsSet != null && localesListAsSet.contains(substring3)) {
                    z = true;
                }
            }
        }
        return !z;
    }
}
