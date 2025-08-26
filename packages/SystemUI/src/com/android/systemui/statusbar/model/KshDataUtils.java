package com.android.systemui.statusbar.model;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.hardware.display.DisplayManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.Display;
import com.android.systemui.R;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes3.dex */
public class KshDataUtils {
    public static final Uri HOME_CONTENT_URI = Uri.parse("content://com.sec.android.app.launcher.settings");
    public final HashMap mAppsIcon;
    public final HashMap mAppsLabel;
    public final Context mContext;
    public final HashMap mDefaultIcons;
    public final int[] mModifierList;

    public KshDataUtils(Context context) {
        HashMap map = new HashMap();
        this.mAppsIcon = map;
        HashMap map2 = new HashMap();
        this.mAppsLabel = map2;
        this.mDefaultIcons = new HashMap();
        this.mModifierList = new int[]{65536, 4096, 2, 1, 4, 8, 16};
        this.mContext = context;
        map.put("android.intent.category.APP_BROWSER", Integer.valueOf(R.drawable.btkeyboard_no_default_internet));
        map.put("android.intent.category.APP_CALENDAR", Integer.valueOf(R.drawable.btkeyboard_no_default_calendar));
        map.put("android.intent.category.APP_CONTACTS", Integer.valueOf(R.drawable.btkeyboard_no_default_contact));
        map.put("android.intent.category.APP_EMAIL", Integer.valueOf(R.drawable.btkeyboard_no_default_email));
        map.put("android.intent.category.APP_MAPS", Integer.valueOf(R.drawable.btkeyboard_no_default_map));
        map.put("android.intent.category.APP_MESSAGING", Integer.valueOf(R.drawable.btkeyboard_no_default_msg));
        map.put("android.intent.category.APP_MUSIC", Integer.valueOf(R.drawable.btkeyboard_no_default_music));
        map2.put("android.intent.category.APP_BROWSER", context.getString(R.string.ksh_group_applications_browser));
        map2.put("android.intent.category.APP_CALENDAR", context.getString(R.string.ksh_group_applications_calendar));
        map2.put("android.intent.category.APP_CONTACTS", context.getString(R.string.ksh_group_applications_contacts));
        map2.put("android.intent.category.APP_EMAIL", context.getString(R.string.ksh_group_applications_email));
        map2.put("android.intent.category.APP_MAPS", context.getString(R.string.ksh_group_applications_maps));
        map2.put("android.intent.category.APP_MESSAGING", context.getString(R.string.ksh_group_applications_messages));
        map2.put("android.intent.category.APP_MUSIC", context.getString(R.string.ksh_group_applications_music));
    }

    public final String getAppLabel(String str) {
        if (str.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
            return this.mContext.getString(R.string.ksh_group_applications_finder);
        }
        try {
            PackageManager packageManager = this.mContext.getPackageManager();
            return (String) packageManager.getApplicationInfo(str, 0).loadLabel(packageManager);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.w("KshDataUtils", "getAppLabel : " + str + " not found, failed to get label");
            return "";
        }
    }

    public final Icon getIconForPackageName(String str) {
        try {
            try {
                ApplicationInfo applicationInfo = this.mContext.getPackageManager().getApplicationInfo(str, 0);
                if (applicationInfo != null) {
                    return Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon);
                }
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("KshDataUtils", str.concat(" not found, failed to get app icon"));
            }
            return null;
        } catch (Throwable unused2) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00f2  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Pair getPackageInfoForSetting(String str) {
        boolean zEquals;
        String string = Settings.System.getString(this.mContext.getContentResolver(), str);
        Icon iconCreateWithBitmap = null;
        if (TextUtils.isEmpty(string)) {
            return new Pair("", null);
        }
        switch (str) {
            case "app_shortcuts_command_a":
                zEquals = "android.app.role.ASSISTANT".equals(string);
                break;
            case "app_shortcuts_command_d":
                zEquals = "android.app.role.HOME".equals(string);
                break;
            case "app_shortcuts_command_h":
                zEquals = "android.app.role.HOME".equals(string);
                break;
            case "app_shortcuts_command_i":
                zEquals = KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(string);
                break;
            default:
                zEquals = false;
                break;
        }
        if (zEquals) {
            return new Pair("", null);
        }
        if (string.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
            String appLabel = getAppLabel(string);
            try {
                try {
                    int identifier = this.mContext.getPackageManager().getResourcesForApplication("com.sec.android.app.launcher").getIdentifier("finder_search_icon", "mipmap", "com.sec.android.app.launcher");
                    if (identifier != 0) {
                        iconCreateWithBitmap = Icon.createWithResource("com.sec.android.app.launcher", identifier);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("KshDataUtils", "com.sec.android.app.launcher not found, failed to get app icon");
                }
            } catch (Throwable unused2) {
            }
            return new Pair(appLabel, iconCreateWithBitmap);
        }
        if (!string.contains("android.intent.category.")) {
            if (!string.contains("android.app.role.")) {
                return new Pair(getAppLabel(string), getIconForPackageName(string));
            }
            List roleHolders = ((RoleManager) this.mContext.getSystemService(RoleManager.class)).getRoleHolders(string);
            String str2 = roleHolders.isEmpty() ? "" : (String) roleHolders.get(0);
            return new Pair(getAppLabel(str2), getIconForPackageName(str2));
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(string);
        ResolveInfo resolveInfoResolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 65536);
        if (resolveInfoResolveActivity != null) {
            if ("android.intent.category.APP_CALENDAR".equals(string)) {
                Drawable drawableSemGetApplicationIconForIconTray = this.mContext.getPackageManager().semGetApplicationIconForIconTray(resolveInfoResolveActivity.activityInfo.applicationInfo, 1);
                if (drawableSemGetApplicationIconForIconTray != null) {
                    if (drawableSemGetApplicationIconForIconTray instanceof BitmapDrawable) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawableSemGetApplicationIconForIconTray;
                        if (bitmapDrawable.getBitmap() != null) {
                            iconCreateWithBitmap = Icon.createWithBitmap(bitmapDrawable.getBitmap());
                        } else {
                            Bitmap bitmapCreateBitmap = (drawableSemGetApplicationIconForIconTray.getIntrinsicWidth() <= 0 || drawableSemGetApplicationIconForIconTray.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(drawableSemGetApplicationIconForIconTray.getIntrinsicWidth(), drawableSemGetApplicationIconForIconTray.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                            Canvas canvas = new Canvas(bitmapCreateBitmap);
                            drawableSemGetApplicationIconForIconTray.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                            drawableSemGetApplicationIconForIconTray.draw(canvas);
                            iconCreateWithBitmap = Icon.createWithBitmap(bitmapCreateBitmap);
                        }
                    }
                }
                return new Pair(getAppLabel(resolveInfoResolveActivity.activityInfo.applicationInfo.packageName), iconCreateWithBitmap);
            }
            if (!"com.android.internal.app.ResolverActivity".equals(resolveInfoResolveActivity.activityInfo.name)) {
                ApplicationInfo applicationInfo = resolveInfoResolveActivity.activityInfo.applicationInfo;
                return new Pair(getAppLabel(resolveInfoResolveActivity.activityInfo.applicationInfo.packageName), Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon));
            }
            if (this.mAppsIcon.containsKey(string)) {
                Icon iconCreateWithResource = Icon.createWithResource(this.mContext, ((Integer) this.mAppsIcon.get(string)).intValue());
                if (iconCreateWithResource != null) {
                    iconCreateWithResource.setTint(this.mContext.getColor(R.color.ksh_no_default_app_item_color));
                    this.mDefaultIcons.put(iconCreateWithResource, Boolean.TRUE);
                }
                return new Pair((String) this.mAppsLabel.get(string), iconCreateWithResource);
            }
        }
        return new Pair("", null);
    }

    public final boolean isDexDisplay() throws PackageManager.NameNotFoundException {
        boolean z;
        PackageInfo packageInfo;
        Bundle bundleCall;
        try {
            DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
            int displayId = this.mContext.getDisplayId();
            companion.getClass();
            boolean zInDesktopWindowing = DesktopStateImpl.Companion.inDesktopWindowing(displayId);
            Context context = this.mContext;
            int displayId2 = context.getDisplay().getDisplayId();
            if (displayId2 == 0) {
                z = false;
            } else {
                for (Display display : ((DisplayManager) context.getSystemService("display")).getDisplays()) {
                    if (display.getDisplayId() == displayId2 && (display.getFlags() & 131072) != 0) {
                        z = true;
                        break;
                    }
                }
                z = false;
            }
            Context context2 = this.mContext;
            try {
                packageInfo = context2.getPackageManager().getPackageInfo("com.sec.android.app.launcher", 0);
            } catch (PackageManager.NameNotFoundException unused) {
                Log.e("KshDataUtils", "Package not found: com.sec.android.app.launcher");
                packageInfo = null;
            }
            return zInDesktopWindowing || z || ((packageInfo == null || (bundleCall = context2.getContentResolver().call(HOME_CONTENT_URI, "get_internal_Dex_status", "internal_dex_status", (Bundle) null)) == null) ? false : bundleCall.getBoolean("internal_dex_status", false));
        } catch (IllegalArgumentException e) {
            Log.w("KshDataUtils", "isDexDisplay exception : " + e);
            return false;
        }
    }
}
