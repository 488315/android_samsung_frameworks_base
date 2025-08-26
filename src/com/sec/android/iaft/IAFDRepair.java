package com.sec.android.iaft;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import java.io.IOException;

/* loaded from: classes6.dex */
class IAFDRepair {
    public static final int EXP_REPAIRMODE_ONEACTIVITY = 2;
    public static final int EXP_REPAIRMODE_ONEKEY = 1;
    public static final int EXP_REPAIRMODE_ONLYTIPS = 3;
    public static final int EXP_REPAIR_CANNOT = 0;
    public static final int EXP_REPAIR_ISOLABLE = 2;
    public static final int EXP_REPAIR_PILE = 1;

    private boolean repair_WebView(Context context, int i, String str) {
        return false;
    }

    IAFDRepair() {
    }

    public boolean repairHandle(Context context, Bundle bundle) {
        int i = bundle.getInt("type");
        String string = bundle.getString(SmLib_IafdConstant.KEY_PACKAGE_NAME);
        try {
            boolean zHotfix = IAFDHotfix.hotfix(context, i, string);
            if (!zHotfix) {
                try {
                    if (i == 19) {
                        return repair_WebView(context, i, string);
                    }
                    if (i == 27) {
                        return repair_allfile(context, i, string);
                    }
                    if (i == 34) {
                        return repair_NoEnoughSpace(context, i, string);
                    }
                    if (i == 35) {
                        return repair_NoSettingsProvidersForDual(context, bundle.getInt("dualUserId"));
                    }
                } catch (Exception unused) {
                    return zHotfix;
                }
            }
            return zHotfix;
        } catch (Exception unused2) {
            return false;
        }
    }

    private boolean repair_allfile(Context context, int i, String str) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setFlags(268435456);
            intent.setData(Uri.parse("package:" + str));
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean repair_NoEnoughSpace(Context context, int i, String str) {
        try {
            Intent intent = new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS");
            intent.setFlags(268435456);
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean repair_NoSettingsProvidersForDual(Context context, int i) throws IOException {
        try {
            Runtime.getRuntime().exec("pm install-existing --user " + String.valueOf(i) + " com.android.providers.settings");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
