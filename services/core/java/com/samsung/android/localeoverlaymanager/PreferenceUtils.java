package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.FileUtils;
import android.util.Log;

import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PreferenceUtils {
    public static URI getLocaleFileUriForUser(int i) {
        boolean z;
        Log.i("PreferenceUtils", "getLocaleFileUriForUser() called with: userId = [" + i + "]");
        File file =
                new File(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "/data/overlays/current_locale_apks/locale_preferences_", "/"));
        if (!file.exists()) {
            if (file.mkdirs()) {
                FileUtils.setPermissions(file, 509, -1, 1000);
            } else {
                Log.e("PreferenceUtils", "getLocaleFileUriForUser: Unable to create dir - " + file);
            }
        }
        File file2 = new File(file, "current_locale_overlays");
        if (!file2.exists() && i == Utils.sCurrentUserId) {
            try {
                z = file2.createNewFile();
            } catch (IOException e) {
                Log.e("PreferenceUtils", "Couldn't create " + file2 + " " + e.getMessage());
                z = false;
            }
            if (z) {
                Log.i(
                        "PreferenceUtils",
                        "getLocaleFileUriForUser: Setting permissions for file: " + file2);
                FileUtils.setPermissions(file2, 509, -1, 1000);
            } else {
                Log.e(
                        "PreferenceUtils",
                        "getLocaleFileUriForUser: Unable to create file - " + file2);
            }
        }
        return file2.toURI();
    }

    public static Set getLocalesForUser(int i) {
        List<String> list = null;
        try {
            list = Files.readAllLines(Paths.get(getLocaleFileUriForUser(i)));
            Log.i(
                    "PreferenceUtils",
                    "getLocalesForUser(): userId = [" + i + "], locales = [" + list + "]");
        } catch (IOException e) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Couldn't read locales for ", " ");
            m.append(e.getMessage());
            Log.e("PreferenceUtils", m.toString());
        }
        return list != null ? new HashSet(list) : new HashSet();
    }

    public static SharedPreferences getPreferences(Context context) {
        boolean z;
        int i = Utils.sCurrentUserId;
        Log.i(
                "PreferenceUtils",
                "getPreferences() called with: userId = [" + i + "], context: " + context);
        File file =
                new File(
                        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                                i, "/data/overlays/current_locale_apks/locale_preferences_", "/"));
        if (!file.exists()) {
            if (file.mkdirs()) {
                FileUtils.setPermissions(file, 509, -1, 1000);
            } else {
                Log.e("PreferenceUtils", "getPreferences: Unable to create dir - " + file);
            }
        }
        File file2 = new File(file, "locale_overlay_preferences.xml");
        if (!file2.exists()) {
            try {
                z = file2.createNewFile();
            } catch (IOException e) {
                Log.e("PreferenceUtils", "Couldn't create " + file2 + " " + e.getMessage());
                z = false;
            }
            if (z) {
                FileUtils.setPermissions(file2, 509, -1, 1000);
            } else {
                Log.e("PreferenceUtils", "getPreferences: Unable to create file - " + file2);
            }
        }
        return context.createDeviceProtectedStorageContext().getSharedPreferences(file2, 0);
    }

    public static void setLocalesForUser(int i, Set set) {
        try {
            Files.write(Paths.get(getLocaleFileUriForUser(i)), set, new OpenOption[0]);
        } catch (IOException e) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Couldn't create for user ", " ");
            m.append(e.getMessage());
            Log.e("PreferenceUtils", m.toString());
        }
    }
}
