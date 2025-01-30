package com.samsung.android.localeoverlaymanager;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Debug;
import android.os.LocaleList;
import android.util.Log;
import com.android.internal.app.LocalePicker;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class Utils {
    public static final String TAG = "LOM" + Utils.class.getSimpleName();
    public static int sCurrentUserId = 0;

    public static int getCurrentUserId() {
        return sCurrentUserId;
    }

    public static void setCurrentUserId(int i) {
        LogWriter.logDebugInfoAndLogcat(TAG, "setCurrentUserId() called with: userId = [" + i + "]");
        sCurrentUserId = i;
    }

    public static String getPathForUser(String str, int i) {
        return String.format(str, Integer.valueOf(i));
    }

    public static void deleteFile(File file) {
        if (file != null) {
            try {
                Files.delete(file.toPath());
            } catch (IOException unused) {
                Log.e(TAG, Debug.getCaller() + ": Unable to delete file - " + file);
            }
        }
    }

    public static void deleteFilesInDir(File file) {
        Log.d(TAG, "deleteFilesInDir() called with: dir = [" + file + "]");
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            for (File file2 : listFiles) {
                try {
                    Files.delete(file2.toPath());
                } catch (IOException unused) {
                    Log.e(TAG, Debug.getCaller() + ": Unable to delete file - " + file);
                }
            }
        }
    }

    public static void deleteDisabledLocaleOverlays(List list) {
        Log.i(TAG, "deleteDisabledLocaleOverlays");
        File file = new File("/data/overlays/current_locale_apks/files/");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            File file2 = new File(file, ((String) it.next()) + ".apk");
            if (file2.exists()) {
                Log.i(TAG, "Deleting Overlay: " + file2.getName());
                deleteFile(file2);
            }
        }
    }

    public static Set getSystemLocales() {
        LocaleList locales = LocalePicker.getLocales();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < locales.size(); i++) {
            String language = locales.get(i).getLanguage();
            if (language.length() == 3) {
                Log.i(TAG, "updateOverlays: trying to get ISO_639_1 mapping for locale: " + language);
                language = (String) OverlayConstants.ISO_639_2_TO_639_1_MAPPING.get(language);
            }
            if (language != null) {
                hashSet.add(language);
            }
        }
        return hashSet;
    }

    public static Set getLocalesListAsSet(LocaleList localeList) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < localeList.size(); i++) {
            String language = localeList.get(i).getLanguage();
            if (language.length() == 3) {
                Log.i(TAG, "localeChanged: trying to get ISO_639_1 mapping for locale: " + language);
                language = (String) OverlayConstants.ISO_639_2_TO_639_1_MAPPING.get(language);
            }
            if (language != null) {
                hashSet.add(language);
            }
        }
        return hashSet;
    }

    public static boolean isValidPackage(Context context, String str) {
        try {
            return context.getPackageManager().getPackageUidAsUser(str, PackageManager.PackageInfoFlags.of(0L), 0) >= 0;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }
}
