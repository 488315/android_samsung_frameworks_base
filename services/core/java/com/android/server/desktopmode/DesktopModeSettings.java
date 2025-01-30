package com.android.server.desktopmode;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.ArraySet;
import android.util.IndentingPrintWriter;
import com.samsung.android.desktopmode.DesktopModeFeature;
import java.util.Set;

/* loaded from: classes2.dex */
public abstract class DesktopModeSettings {
    public static final Uri CONTENT_URI;
    public static final String DEX_MODE_DEFAULT_VALUE;
    public static final Set SETTINGS_GLOBAL_KEYS;
    public static final String TAG = "[DMS]" + DesktopModeSettings.class.getSimpleName();
    public static int sCurrentUserId;

    static {
        ArraySet arraySet = new ArraySet();
        SETTINGS_GLOBAL_KEYS = arraySet;
        arraySet.add("dock_usbpd_ids");
        arraySet.add("dock_version");
        CONTENT_URI = Uri.parse("content://com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
        DEX_MODE_DEFAULT_VALUE = DesktopModeFeature.SUPPORT_NEW_DEX ? "new" : "classic";
        sCurrentUserId = -10000;
    }

    public static void setCurrentUserId(int i) {
        sCurrentUserId = i;
    }

    public static boolean isGlobalKey(String str) {
        return SETTINGS_GLOBAL_KEYS.contains(str);
    }

    public static void applyGlobalSettings(ContentResolver contentResolver, int i) {
        for (String str : SETTINGS_GLOBAL_KEYS) {
            String settingsAsUser = getSettingsAsUser(contentResolver, str, (String) null, 0);
            if (settingsAsUser != null) {
                setSettingsAsUser(contentResolver, str, settingsAsUser, i);
            }
        }
    }

    public static Uri getUriAsUser(int i) {
        return Uri.parse("content://" + i + "@com.sec.android.desktopmode.uiservice.SettingsProvider/settings");
    }

    public static Uri getUri() {
        return CONTENT_URI;
    }

    public static Uri getUriFor(String str) {
        return Uri.withAppendedPath(CONTENT_URI, str);
    }

    public static Bundle getAllSettingsAsUser(ContentResolver contentResolver, int i) {
        try {
            return contentResolver.call(getUriAsUser(i), "getSettings", (String) null, (Bundle) null);
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to get settings", e);
            return null;
        }
    }

    public static Bundle getAllSettings(ContentResolver contentResolver) {
        return getAllSettingsAsUser(contentResolver, sCurrentUserId);
    }

    public static String getSettingsAsUser(ContentResolver contentResolver, String str, String str2, int i) {
        try {
            return getSettingsAsUserOrThrowException(contentResolver, str, str2, i);
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to get settings", e);
            return str2;
        }
    }

    public static String getSettingsAsUserOrThrowException(ContentResolver contentResolver, String str, String str2, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("def", str2);
        Bundle call = contentResolver.call(getUriAsUser(i), "getSettings", (String) null, bundle);
        return call != null ? call.getString(str) : str2;
    }

    public static boolean getSettingsAsUser(ContentResolver contentResolver, String str, boolean z, int i) {
        return Boolean.parseBoolean(getSettingsAsUser(contentResolver, str, Boolean.toString(z), i));
    }

    public static int getSettingsAsUser(ContentResolver contentResolver, String str, int i, int i2) {
        return Integer.parseInt(getSettingsAsUser(contentResolver, str, Integer.toString(i), i2));
    }

    public static float getSettingsAsUser(ContentResolver contentResolver, String str, float f, int i) {
        return Float.parseFloat(getSettingsAsUser(contentResolver, str, Float.toString(f), i));
    }

    public static String getSettings(ContentResolver contentResolver, String str, String str2) {
        return getSettingsAsUser(contentResolver, str, str2, sCurrentUserId);
    }

    public static boolean getSettings(ContentResolver contentResolver, String str, boolean z) {
        return getSettingsAsUser(contentResolver, str, z, sCurrentUserId);
    }

    public static int getSettings(ContentResolver contentResolver, String str, int i) {
        return getSettingsAsUser(contentResolver, str, i, sCurrentUserId);
    }

    public static float getSettings(ContentResolver contentResolver, String str, float f) {
        return getSettingsAsUser(contentResolver, str, f, sCurrentUserId);
    }

    public static String getSettingsOrThrowException(ContentResolver contentResolver, String str, String str2) {
        return getSettingsAsUserOrThrowException(contentResolver, str, str2, sCurrentUserId);
    }

    public static void setSettingsAsUserOrThrowException(ContentResolver contentResolver, String str, String str2, int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("key", str);
        bundle.putString("val", str2);
        contentResolver.call(getUriAsUser(i), "setSettings", (String) null, bundle);
    }

    public static void setSettingsAsUser(ContentResolver contentResolver, String str, String str2, int i) {
        try {
            setSettingsAsUserOrThrowException(contentResolver, str, str2, i);
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to set settings", e);
        }
    }

    public static void setSettingsAsUser(ContentResolver contentResolver, String str, boolean z, int i) {
        setSettingsAsUser(contentResolver, str, Boolean.toString(z), i);
    }

    public static void setSettingsAsUser(ContentResolver contentResolver, String str, int i, int i2) {
        setSettingsAsUser(contentResolver, str, Integer.toString(i), i2);
    }

    public static void setSettings(ContentResolver contentResolver, String str, String str2) {
        setSettingsAsUser(contentResolver, str, str2, sCurrentUserId);
    }

    public static void setSettings(ContentResolver contentResolver, String str, boolean z) {
        setSettingsAsUser(contentResolver, str, Boolean.toString(z), sCurrentUserId);
    }

    public static void setSettings(ContentResolver contentResolver, String str, int i) {
        setSettingsAsUser(contentResolver, str, Integer.toString(i), sCurrentUserId);
    }

    public static void setSettingsOrThrowException(ContentResolver contentResolver, String str, String str2) {
        setSettingsAsUserOrThrowException(contentResolver, str, str2, sCurrentUserId);
    }

    public static void setSettingsOrThrowException(ContentResolver contentResolver, String str, int i) {
        setSettingsAsUserOrThrowException(contentResolver, str, Integer.toString(i), sCurrentUserId);
    }

    public static boolean contains(ContentResolver contentResolver, String str) {
        return getSettings(contentResolver, str, (String) null) != null;
    }

    public static void deleteSettingsAsUserOrThrowException(ContentResolver contentResolver, String str, int i) {
        Bundle bundle = new Bundle(1);
        bundle.putString("key", str);
        contentResolver.call(getUriAsUser(i), "deleteSettings", (String) null, bundle);
    }

    public static void deleteSettingsAsUser(ContentResolver contentResolver, String str, int i) {
        try {
            deleteSettingsAsUserOrThrowException(contentResolver, str, i);
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to delete settings", e);
        }
    }

    public static void deleteSettingsOrThrowException(ContentResolver contentResolver, String str) {
        deleteSettingsAsUserOrThrowException(contentResolver, str, sCurrentUserId);
    }

    public static void deleteSettings(ContentResolver contentResolver, String str) {
        deleteSettingsAsUser(contentResolver, str, sCurrentUserId);
    }

    public static void deleteAllSettingsAsUserOrThrowException(ContentResolver contentResolver, int i) {
        contentResolver.call(getUriAsUser(i), "deleteSettings", (String) null, (Bundle) null);
    }

    public static void deleteAllSettingsOrThrowException(ContentResolver contentResolver) {
        deleteAllSettingsAsUserOrThrowException(contentResolver, sCurrentUserId);
    }

    public static void clearSettingsAsUser(ContentResolver contentResolver, int i) {
        try {
            contentResolver.call(getUriAsUser(i), "clearSettings", (String) null, (Bundle) null);
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to clear Dex settings", e);
        }
    }

    public static void dumpApp(IndentingPrintWriter indentingPrintWriter, ContentResolver contentResolver, int i) {
        try {
            Bundle call = contentResolver.call(getUriAsUser(i), "dumpApp", (String) null, (Bundle) null);
            if (call != null) {
                indentingPrintWriter.print(call.getString("dumpApp"));
            }
        } catch (IllegalArgumentException e) {
            Log.m9e(TAG, "Failed to dump", e);
        }
    }

    public static void dump(IndentingPrintWriter indentingPrintWriter, ContentResolver contentResolver, int i) {
        indentingPrintWriter.println("Current " + DesktopModeSettings.class.getSimpleName() + " state:");
        indentingPrintWriter.increaseIndent();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        indentingPrintWriter.println("Current user (" + i + ") settings=" + Utils.bundleToString(getAllSettingsAsUser(contentResolver, i)));
        if (i != 0) {
            indentingPrintWriter.println("System user (0) settings" + Utils.bundleToString(getAllSettingsAsUser(contentResolver, 0)));
        }
        Binder.restoreCallingIdentity(clearCallingIdentity);
        indentingPrintWriter.decreaseIndent();
    }
}
