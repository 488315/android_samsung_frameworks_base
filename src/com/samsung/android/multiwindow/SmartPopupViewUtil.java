package com.samsung.android.multiwindow;

import android.content.Context;
import android.provider.Settings;
import com.android.internal.util.CollectionUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes6.dex */
public class SmartPopupViewUtil {
    public static final String ACTION_DEMO_RESET_STARTED = "com.samsung.sea.rm.DEMO_RESET_STARTED";
    public static final String ACTION_SOFT_RESET = "com.samsung.intent.action.SETTINGS_SOFT_RESET";
    public static final String INTENT_LELINK_CONNECTION_CHANGED = "com.samsung.intent.action.LELINK_CAST_CONNECTION_CHANGED";
    public static final String INTENT_SEM_WIFI_DISPLAY_SOURCE_STATE = "com.samsung.intent.action.WIFI_DISPLAY_SOURCE_STATE";
    private static final String PACKAGE_SEPARATOR = ":";
    public static final int WIFI_DISPLAY_STATE_CONNECTED = 2;
    public static final int WIFI_DISPLAY_STATE_CONNECTING = 1;

    public static void putPackageStrListToDB(Context context, List<String> list) {
        putPackageListStrToDB(context, packageListToStr(list));
    }

    public static List<String> getPackageStrListFromDB(Context context) {
        return packageStrToList(getPackageListStrFromDB(context));
    }

    private static void putPackageListStrToDB(Context context, String str) {
        Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.PACKAGES_IN_SMART_POP_UP_VIEW, str, -2);
    }

    private static String getPackageListStrFromDB(Context context) {
        return Settings.Secure.getStringForUser(context.getContentResolver(), Settings.Secure.PACKAGES_IN_SMART_POP_UP_VIEW, -2);
    }

    private static String packageListToStr(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String str : CollectionUtils.emptyIfNull(list)) {
            if (str != null && !str.equals("")) {
                sb.append(str);
                sb.append(":");
            }
        }
        return sb.toString();
    }

    private static List<String> packageStrToList(String str) {
        if (str == null) {
            return new ArrayList();
        }
        return new ArrayList(Arrays.asList(str.split(":")));
    }

    public static void resetPackageListStr(Context context) {
        Settings.Secure.putStringForUser(context.getContentResolver(), Settings.Secure.PACKAGES_IN_SMART_POP_UP_VIEW, "", -2);
    }

    public static boolean isShopDemo(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "shopdemo", 0) == 1;
    }
}
