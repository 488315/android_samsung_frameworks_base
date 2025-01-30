package com.android.systemui.statusbar.model;

import android.app.role.RoleManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import com.android.systemui.R;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        boolean equals = str.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity");
        Context context = this.mContext;
        if (equals) {
            return context.getString(R.string.ksh_group_applications_search);
        }
        try {
            PackageManager packageManager = context.getPackageManager();
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

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final Pair getPackageInfoForSetting(String str) {
        char c;
        Context context = this.mContext;
        String string = Settings.System.getString(context.getContentResolver(), str);
        Icon icon = null;
        if (TextUtils.isEmpty(string)) {
            return new Pair("", null);
        }
        switch (str.hashCode()) {
            case -638905219:
                if (str.equals("app_shortcuts_command_a")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -638905216:
                if (str.equals("app_shortcuts_command_d")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -638905212:
                if (str.equals("app_shortcuts_command_h")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -638905211:
                if (str.equals("app_shortcuts_command_i")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0 ? c != 1 ? c != 2 ? c != 3 ? false : KnoxVpnPolicyConstants.ANDROID_SETTINGS_PKG.equals(string) : "android.app.role.HOME".equals(string) : "android.app.role.HOME".equals(string) : "android.app.role.ASSISTANT".equals(string)) {
            return new Pair("", null);
        }
        if (string.equals("com.sec.android.app.launcher/com.sec.android.app.launcher.search.SearchActivity")) {
            String appLabel = getAppLabel(string);
            try {
                try {
                    int identifier = context.getPackageManager().getResourcesForApplication("com.sec.android.app.launcher").getIdentifier("finder_search_icon", "mipmap", "com.sec.android.app.launcher");
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
            List roleHolders = ((RoleManager) context.getSystemService(RoleManager.class)).getRoleHolders(string);
            String str2 = roleHolders.isEmpty() ? "" : (String) roleHolders.get(0);
            return new Pair(getAppLabel(str2), getIconForPackageName(str2));
        }
        Intent intent = new Intent("android.intent.action.MAIN");
        intent.addCategory(string);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, 65536);
        if (resolveActivity != null) {
            if (!"com.android.internal.app.ResolverActivity".equals(resolveActivity.activityInfo.name)) {
                ApplicationInfo applicationInfo = resolveActivity.activityInfo.applicationInfo;
                return new Pair(getAppLabel(resolveActivity.activityInfo.applicationInfo.packageName), Icon.createWithResource(applicationInfo.packageName, applicationInfo.icon));
            }
            HashMap hashMap = this.mAppsIcon;
            if (hashMap.containsKey(string)) {
                Icon createWithResource = Icon.createWithResource(context, ((Integer) hashMap.get(string)).intValue());
                if (createWithResource != null) {
                    createWithResource.setTint(context.getColor(R.color.ksh_no_default_app_item_color));
                    this.mDefaultIcons.put(createWithResource, Boolean.TRUE);
                }
                return new Pair((String) this.mAppsLabel.get(string), createWithResource);
            }
        }
        return new Pair("", null);
    }
}
