package com.android.server.desktopmode;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.util.ArraySet;

import com.samsung.android.desktopmode.DesktopModeFeature;

import java.util.Set;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class DesktopModeSettings {
    public static final Uri CONTENT_URI;
    public static final String DEX_MODE_DEFAULT_VALUE;
    public static final Set SETTINGS_GLOBAL_KEYS;
    public static int sCurrentUserId;

    static {
        ArraySet arraySet = new ArraySet();
        SETTINGS_GLOBAL_KEYS = arraySet;
        arraySet.add("dock_usbpd_ids");
        arraySet.add("dock_version");
        CONTENT_URI =
                Uri.parse(
                        "content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
        DEX_MODE_DEFAULT_VALUE = DesktopModeFeature.SUPPORT_NEW_DEX ? "new" : "classic";
        sCurrentUserId = -10000;
    }

    public static void deleteSettingsAsUser(ContentResolver contentResolver, String str, int i) {
        try {
            Bundle bundle = new Bundle(1);
            bundle.putString("key", str);
            contentResolver.call(getUriAsUser(i), "deleteSettings", (String) null, bundle);
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to delete settings", e);
        }
    }

    public static Bundle getAllSettingsAsUser(int i, ContentResolver contentResolver) {
        try {
            return contentResolver.call(
                    getUriAsUser(i), "getSettings", (String) null, (Bundle) null);
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to get settings", e);
            return null;
        }
    }

    public static int getSettingsAsUser(
            ContentResolver contentResolver, String str, int i, int i2) {
        return Integer.parseInt(getSettingsAsUser(contentResolver, str, Integer.toString(i), i2));
    }

    public static String getSettingsAsUser(
            ContentResolver contentResolver, String str, String str2, int i) {
        try {
            return getSettingsAsUserOrThrowException(contentResolver, str, str2, i);
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to get settings", e);
            return str2;
        }
    }

    public static boolean getSettingsAsUser(
            ContentResolver contentResolver, String str, boolean z, int i) {
        return Boolean.parseBoolean(
                getSettingsAsUser(contentResolver, str, Boolean.toString(z), i));
    }

    public static String getSettingsAsUserOrThrowException(
            ContentResolver contentResolver, String str, String str2, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("def", str2);
        Bundle call = contentResolver.call(getUriAsUser(i), "getSettings", (String) null, bundle);
        return call != null ? call.getString(str) : str2;
    }

    public static Uri getUriAsUser(int i) {
        return Uri.parse(
                "content://"
                        + i
                        + "@com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public static void setSettings(ContentResolver contentResolver, String str, int i) {
        setSettingsAsUser(contentResolver, str, Integer.toString(i), sCurrentUserId);
    }

    public static void setSettings(ContentResolver contentResolver, String str, String str2) {
        setSettingsAsUser(contentResolver, str, str2, sCurrentUserId);
    }

    public static void setSettings(ContentResolver contentResolver, String str, boolean z) {
        setSettingsAsUser(contentResolver, str, Boolean.toString(z), sCurrentUserId);
    }

    public static void setSettingsAsUser(
            ContentResolver contentResolver, String str, int i, int i2) {
        setSettingsAsUser(contentResolver, str, Integer.toString(i), i2);
    }

    public static void setSettingsAsUser(
            ContentResolver contentResolver, String str, String str2, int i) {
        try {
            setSettingsAsUserOrThrowException(contentResolver, str, str2, i);
        } catch (IllegalArgumentException e) {
            Log.e("[DMS]DesktopModeSettings", "Failed to set settings", e);
        }
    }

    public static void setSettingsAsUser(
            ContentResolver contentResolver, String str, boolean z, int i) {
        setSettingsAsUser(contentResolver, str, Boolean.toString(z), i);
    }

    public static void setSettingsAsUserOrThrowException(
            ContentResolver contentResolver, String str, String str2, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("val", str2);
        contentResolver.call(getUriAsUser(i), "setSettings", (String) null, bundle);
    }

    public static void setSettingsOrThrowException(
            ContentResolver contentResolver, String str, String str2) {
        setSettingsAsUserOrThrowException(contentResolver, str, str2, sCurrentUserId);
    }
}
