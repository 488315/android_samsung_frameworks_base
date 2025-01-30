package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.SemUserInfo;
import android.os.FileUtils;
import android.os.UserManager;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class PreferenceUtils {
    public static final String TAG = "PreferenceUtils";

    public static SharedPreferences getPreferences(Context context) {
        return getPreferences(Utils.getCurrentUserId(), context);
    }

    public static SharedPreferences getPreferences(int i, Context context) {
        boolean z;
        String str = TAG;
        Log.i(str, "getPreferences() called with: userId = [" + i + "], context: " + context);
        File file = new File(Utils.getPathForUser("/data/overlays/current_locale_apks/locale_preferences_%s/", i));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(str, "getPreferences: Unable to create dir - " + file);
            } else {
                FileUtils.setPermissions(file, 509, -1, 1000);
            }
        }
        File file2 = new File(file, "locale_overlay_preferences.xml");
        if (!file2.exists()) {
            try {
                z = file2.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't create " + file2 + " " + e.getMessage());
                z = false;
            }
            if (!z) {
                Log.e(TAG, "getPreferences: Unable to create file - " + file2);
            } else {
                FileUtils.setPermissions(file2, 509, -1, 1000);
            }
        }
        return context.createDeviceProtectedStorageContext().getSharedPreferences(file2, 0);
    }

    public static Set getLocalesForUser(int i) {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get(getLocaleFileUriForUser(i)));
            Log.i(TAG, "getLocalesForUser(): userId = [" + i + "], locales = [" + list + "]");
        } catch (IOException e) {
            Log.e(TAG, "Couldn't read locales for " + i + " " + e.getMessage());
        }
        if (list != null) {
            return new HashSet(list);
        }
        return new HashSet();
    }

    public static void setLocalesForUser(int i, Set set) {
        try {
            Files.write(Paths.get(getLocaleFileUriForUser(i)), set, new OpenOption[0]);
        } catch (IOException e) {
            Log.e(TAG, "Couldn't create for user " + i + " " + e.getMessage());
        }
    }

    public static URI getLocaleFileUriForUser(int i) {
        boolean z;
        String str = TAG;
        Log.i(str, "getLocaleFileUriForUser() called with: userId = [" + i + "]");
        File file = new File(Utils.getPathForUser("/data/overlays/current_locale_apks/locale_preferences_%s/", i));
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e(str, "getLocaleFileUriForUser: Unable to create dir - " + file);
            } else {
                FileUtils.setPermissions(file, 509, -1, 1000);
            }
        }
        File file2 = new File(file, "current_locale_overlays");
        if (!file2.exists() && i == Utils.getCurrentUserId()) {
            try {
                z = file2.createNewFile();
            } catch (IOException e) {
                Log.e(TAG, "Couldn't create " + file2 + " " + e.getMessage());
                z = false;
            }
            if (!z) {
                Log.e(TAG, "getLocaleFileUriForUser: Unable to create file - " + file2);
            } else {
                Log.i(TAG, "getLocaleFileUriForUser: Setting permissions for file: " + file2);
                FileUtils.setPermissions(file2, 509, -1, 1000);
            }
        }
        return file2.toURI();
    }

    public static Set getLocalesForAllUsers(Context context) {
        List<SemUserInfo> semGetUsers = ((UserManager) context.getSystemService("user")).semGetUsers();
        HashSet hashSet = new HashSet();
        Log.i(TAG, "getLocalesForAllUsers: UserInfos - " + semGetUsers);
        for (SemUserInfo semUserInfo : semGetUsers) {
            Set localesForUser = getLocalesForUser(semUserInfo.getUserHandle().semGetIdentifier());
            Log.i(TAG, "getLocalesForAllUsers: UserId - " + semUserInfo.getUserHandle().semGetIdentifier() + " has locales - " + localesForUser);
            hashSet.addAll(localesForUser);
        }
        return hashSet;
    }
}
