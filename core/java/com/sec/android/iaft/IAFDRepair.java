package com.sec.android.iaft;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;

class IAFDRepair {
    public static final int EXP_REPAIRMODE_ONEACTIVITY = 2;
    public static final int EXP_REPAIRMODE_ONEKEY = 1;
    public static final int EXP_REPAIRMODE_ONLYTIPS = 3;
    public static final int EXP_REPAIR_CANNOT = 0;
    public static final int EXP_REPAIR_ISOLABLE = 2;
    public static final int EXP_REPAIR_PILE = 1;

    IAFDRepair() {}

    public boolean repairHandle(Context ct, Bundle bundle) {
        boolean result = false;
        int exptype = bundle.getInt("type");
        String pkgName = bundle.getString(SmLib_IafdConstant.KEY_PACKAGE_NAME);
        try {
            result = IAFDHotfix.hotfix(ct, exptype, pkgName);
            if (!result) {
                switch (exptype) {
                    case 19:
                        result = repair_WebView(ct, exptype, pkgName);
                        break;
                    case 27:
                        result = repair_allfile(ct, exptype, pkgName);
                        break;
                    case 34:
                        result = repair_NoEnoughSpace(ct, exptype, pkgName);
                        break;
                    case 35:
                        int dualuid = bundle.getInt("dualUserId");
                        result = repair_NoSettingsProvidersForDual(ct, dualuid);
                        break;
                }
            }
        } catch (Exception e) {
        }
        return result;
    }

    private boolean repair_allfile(Context mContext, int expType, String pkgName) {
        try {
            Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
            intent.setFlags(268435456);
            intent.setData(Uri.parse("package:" + pkgName));
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean repair_WebView(Context mContext, int expType, String pkgName) {
        return false;
    }

    private boolean repair_NoEnoughSpace(Context mContext, int expType, String pkgName) {
        try {
            Intent intent = new Intent("com.sec.android.app.myfiles.RUN_STORAGE_ANALYSIS");
            intent.setFlags(268435456);
            mContext.startActivity(intent);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean repair_NoSettingsProvidersForDual(Context mContext, int dualuid) {
        try {
            Runtime.getRuntime()
                    .exec(
                            "pm install-existing --user "
                                    + String.valueOf(dualuid)
                                    + " com.android.providers.settings");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
