package com.samsung.android.localeoverlaymanager;

import android.os.Debug;
import android.os.LocaleList;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.app.LocalePicker;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.RCPManagerService$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class Utils {
    public static int sCurrentUserId;

    public static void deleteFile(File file) {
        try {
            Files.delete(file.toPath());
        } catch (IOException unused) {
            Log.e("LOMUtils", Debug.getCaller() + ": Unable to delete file - " + file);
        }
    }

    public static Set getLocalesListAsSet(LocaleList localeList) {
        HashSet hashSet = new HashSet();
        for (int i = 0; i < localeList.size(); i++) {
            String language = localeList.get(i).getLanguage();
            if (language.length() == 3) {
                Log.i(
                        "LOMUtils",
                        "localeChanged: trying to get ISO_639_1 mapping for locale: "
                                .concat(language));
                language =
                        (String)
                                ((HashMap) OverlayConstants.ISO_639_2_TO_639_1_MAPPING)
                                        .get(language);
            }
            if (language != null) {
                hashSet.add(language);
            }
        }
        handleNewLocaleCodes(hashSet);
        return hashSet;
    }

    public static Set getSystemLocales() {
        LocaleList locales = LocalePicker.getLocales();
        HashSet hashSet = new HashSet();
        for (int i = 0; i < locales.size(); i++) {
            String language = locales.get(i).getLanguage();
            if (language.length() == 3) {
                Log.i(
                        "LOMUtils",
                        "updateOverlays: trying to get ISO_639_1 mapping for locale: "
                                .concat(language));
                language =
                        (String)
                                ((HashMap) OverlayConstants.ISO_639_2_TO_639_1_MAPPING)
                                        .get(language);
            }
            if (language != null) {
                hashSet.add(language);
            }
        }
        handleNewLocaleCodes(hashSet);
        return hashSet;
    }

    public static void handleNewLocaleCodes(Set set) {
        for (String str : ((HashMap) OverlayConstants.SPECIAL_LOCALE_CODES_EQUIVALENTS).keySet()) {
            if (set.remove(str)) {
                StringBuilder m =
                        DumpUtils$$ExternalSyntheticOutline0.m("replacing code: ", str, "with : ");
                Map map = OverlayConstants.SPECIAL_LOCALE_CODES_EQUIVALENTS;
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(
                        m, (String) ((HashMap) map).get(str), "LOMUtils");
                set.add((String) ((HashMap) map).get(str));
            }
        }
    }

    public static String readCSCVersion() {
        String str = "";
        String str2 = SystemProperties.get("ril.official_cscver", "");
        if (!TextUtils.isEmpty(str2)) {
            return str2;
        }
        Log.i("LOMUtils", "reading CSC Version from file");
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(
                            new InputStreamReader(
                                    new FileInputStream("system/CSCVersion.txt"),
                                    Charset.defaultCharset()));
            try {
                String readLine = bufferedReader.readLine();
                if (readLine != null && !readLine.isEmpty()) {
                    str = readLine.trim();
                }
                bufferedReader.close();
            } finally {
            }
        } catch (Exception e) {
            RCPManagerService$$ExternalSyntheticOutline0.m(
                    e, new StringBuilder("readTextFromFile failed: "), "LOMUtils");
        }
        return str;
    }

    public static void setCurrentUserId(int i) {
        LogWriter.logDebugInfoAndLogcat(
                "LOMUtils", "setCurrentUserId() called with: userId = [" + i + "]");
        sCurrentUserId = i;
    }
}
