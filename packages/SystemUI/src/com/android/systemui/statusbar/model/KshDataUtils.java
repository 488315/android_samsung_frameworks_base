package com.android.systemui.statusbar.model;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.systemui.R;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KshDataUtils {
    public final HashMap mAppsIcon;
    public final HashMap mAppsLabel;
    public final Context mContext;
    public final HashMap mDefaultIcons;
    public final int[] mModifierList;

    public KshDataUtils(Context context) {
        HashMap hashMap = new HashMap();
        this.mAppsIcon = hashMap;
        HashMap hashMap2 = new HashMap();
        this.mAppsLabel = hashMap2;
        this.mDefaultIcons = new HashMap();
        this.mModifierList = new int[]{65536, 4096, 2, 1, 4, 8, 16};
        this.mContext = context;
        hashMap.put("android.intent.category.APP_BROWSER", Integer.valueOf(R.drawable.btkeyboard_no_default_internet));
        hashMap.put("android.intent.category.APP_CALENDAR", Integer.valueOf(R.drawable.btkeyboard_no_default_calendar));
        hashMap.put("android.intent.category.APP_CONTACTS", Integer.valueOf(R.drawable.btkeyboard_no_default_contact));
        hashMap.put("android.intent.category.APP_EMAIL", Integer.valueOf(R.drawable.btkeyboard_no_default_email));
        hashMap.put("android.intent.category.APP_MAPS", Integer.valueOf(R.drawable.btkeyboard_no_default_map));
        hashMap.put("android.intent.category.APP_MESSAGING", Integer.valueOf(R.drawable.btkeyboard_no_default_msg));
        hashMap.put("android.intent.category.APP_MUSIC", Integer.valueOf(R.drawable.btkeyboard_no_default_music));
        hashMap2.put("android.intent.category.APP_BROWSER", context.getString(R.string.ksh_group_applications_browser));
        hashMap2.put("android.intent.category.APP_CALENDAR", context.getString(R.string.ksh_group_applications_calendar));
        hashMap2.put("android.intent.category.APP_CONTACTS", context.getString(R.string.ksh_group_applications_contacts));
        hashMap2.put("android.intent.category.APP_EMAIL", context.getString(R.string.ksh_group_applications_email));
        hashMap2.put("android.intent.category.APP_MAPS", context.getString(R.string.ksh_group_applications_maps));
        hashMap2.put("android.intent.category.APP_MESSAGING", context.getString(R.string.ksh_group_applications_messages));
        hashMap2.put("android.intent.category.APP_MUSIC", context.getString(R.string.ksh_group_applications_music));
    }

    public final String getAppLabel(String str) {
        if (str.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
            return this.mContext.getString(R.string.ksh_group_applications_search);
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
                return null;
            } catch (PackageManager.NameNotFoundException unused) {
                Log.w("KshDataUtils", str.concat(" not found, failed to get app icon"));
                return null;
            }
        } catch (Throwable unused2) {
            return null;
        }
    }

    public final Pair getPackageInfoForSetting(String str) {
        boolean equals;
        String string = Settings.System.getString(this.mContext.getContentResolver(), str);
        Icon icon = null;
        if (TextUtils.isEmpty(string)) {
            return new Pair("", null);
        }
        switch (str) {
            case "app_shortcuts_command_a":
                equals = "android.app.role.ASSISTANT".equals(string);
                break;
            case "app_shortcuts_command_d":
                equals = "android.app.role.HOME".equals(string);
                break;
            case "app_shortcuts_command_h":
                equals = "android.app.role.HOME".equals(string);
                break;
            case "app_shortcuts_command_i":
                equals = KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(string);
                break;
            default:
                equals = false;
                break;
        }
        if (equals) {
            return new Pair("", null);
        }
        if (string.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
            String appLabel = getAppLabel(string);
            try {
                try {
                    int identifier = this.mContext.getPackageManager().getResourcesForApplication("com.sec.android.app.launcher").getIdentifier("finder_search_icon", "mipmap", "com.sec.android.app.launcher");
                    if (identifier != 0) {
                        icon = Icon.createWithResource("com.sec.android.app.launcher", identifier);
                    }
                } catch (PackageManager.NameNotFoundException unused) {
                    Log.e("KshDataUtils", "com.sec.android.app.launcher not found, failed to get app icon");
                }
            } catch (Throwable unused2) {
            }
            return new Pair(appLabel, icon);
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
        ResolveInfo resolveActivity = this.mContext.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            if ("android.intent.category.APP_CALENDAR".equals(string)) {
                Drawable semGetApplicationIconForIconTray = this.mContext.getPackageManager().semGetApplicationIconForIconTray(resolveActivity.activityInfo.applicationInfo, 1);
                if (semGetApplicationIconForIconTray != null) {
                    if (semGetApplicationIconForIconTray instanceof BitmapDrawable) {
                        BitmapDrawable bitmapDrawable = (BitmapDrawable) semGetApplicationIconForIconTray;
                        if (bitmapDrawable.getBitmap() != null) {
                            icon = Icon.createWithBitmap(bitmapDrawable.getBitmap());
                        }
                    }
                    Bitmap createBitmap = (semGetApplicationIconForIconTray.getIntrinsicWidth() <= 0 || semGetApplicationIconForIconTray.getIntrinsicHeight() <= 0) ? Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888) : Bitmap.createBitmap(semGetApplicationIconForIconTray.getIntrinsicWidth(), semGetApplicationIconForIconTray.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(createBitmap);
                    semGetApplicationIconForIconTray.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    semGetApplicationIconForIconTray.draw(canvas);
                    icon = Icon.createWithBitmap(createBitmap);
                }
                return new Pair(getAppLabel(resolveActivity.activityInfo.applicationInfo.packageName), icon);
            }
            if (!"com.android.internal.app.ResolverActivity".equals(resolveActivity.activityInfo.name)) {
                ApplicationInfo applicationInfo = resolveActivity.activityInfo.applicationInfo;
                return new Pair(getAppLabel(resolveActivity.activityInfo.applicationInfo.packageName), Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon));
            }
            if (this.mAppsIcon.containsKey(string)) {
                Icon createWithResource = Icon.createWithResource(this.mContext, ((Integer) this.mAppsIcon.get(string)).intValue());
                if (createWithResource != null) {
                    createWithResource.setTint(this.mContext.getColor(R.color.ksh_no_default_app_item_color));
                    this.mDefaultIcons.put(createWithResource, Boolean.TRUE);
                }
                return new Pair((String) this.mAppsLabel.get(string), createWithResource);
            }
        }
        return new Pair("", null);
    }
}
