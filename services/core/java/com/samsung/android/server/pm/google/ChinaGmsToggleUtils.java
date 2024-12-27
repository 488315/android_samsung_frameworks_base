package com.samsung.android.server.pm.google;

import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class ChinaGmsToggleUtils {
    public static final String[] GMS_PACKAGES = {
        "com.google.android.gms",
        "com.google.android.configupdater",
        "com.google.android.syncadapters.calendar"
    };
    public final Context mContext;

    public ChinaGmsToggleUtils(Context context) {
        this.mContext = context;
    }

    public void setApplicationEnabledSettingAsUser(String str, int i, int i2) throws Exception {
        ActivityThread.getPackageManager()
                .setApplicationEnabledSetting(str, i, 0, i2, this.mContext.getOpPackageName());
    }

    public final void setEnabledStateForGmsPackages(int i, int i2) {
        String[] strArr = GMS_PACKAGES;
        for (int i3 = 0; i3 < 3; i3++) {
            String str = strArr[i3];
            try {
                setApplicationEnabledSettingAsUser(str, i, i2);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("PackageManager", "Fail to enable " + str);
            }
        }
    }

    public final void setGmsEnabledSetting(int i) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return;
        }
        int i2 =
                Settings.Global.getInt(this.mContext.getContentResolver(), "google_core_control", 0)
                                != 1
                        ? 2
                        : 1;
        if (i != -1) {
            setEnabledStateForGmsPackages(i2, i);
            return;
        }
        Iterator it = userManager.getUsers().iterator();
        while (it.hasNext()) {
            setEnabledStateForGmsPackages(i2, ((UserInfo) it.next()).id);
        }
    }
}
