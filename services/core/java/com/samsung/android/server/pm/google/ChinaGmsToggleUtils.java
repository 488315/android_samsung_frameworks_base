package com.samsung.android.server.pm.google;

import android.app.ActivityThread;
import android.content.Context;
import android.content.pm.UserInfo;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.UserManager;
import android.provider.Settings;
import android.util.Log;
import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Predicate;

/* loaded from: classes2.dex */
public class ChinaGmsToggleUtils {
    public static final String[] GMS_PACKAGES = {"com.google.android.gms", "com.google.android.configupdater", "com.google.android.syncadapters.calendar"};
    public final Context mContext;

    public ChinaGmsToggleUtils(Context context) {
        this.mContext = context;
    }

    public void setGmsEnabledSetting(int i) {
        UserManager userManager = (UserManager) this.mContext.getSystemService(UserManager.class);
        if (userManager == null) {
            return;
        }
        int gmsEnabledState = getGmsEnabledState();
        if (i == -1) {
            Iterator it = userManager.getUsers().iterator();
            while (it.hasNext()) {
                setEnabledStateForGmsPackages(gmsEnabledState, ((UserInfo) it.next()).id);
            }
            return;
        }
        setEnabledStateForGmsPackages(gmsEnabledState, i);
    }

    public void setGmsEnabledPackage(String str, int[] iArr) {
        int gmsEnabledState = getGmsEnabledState();
        for (int i : iArr) {
            try {
                setApplicationEnabledSettingAsUser(str, gmsEnabledState, i);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("PackageManager", "Fail to enable " + str);
            }
        }
    }

    public final int getGmsEnabledState() {
        return Settings.Global.getInt(this.mContext.getContentResolver(), "google_core_control", 0) == 1 ? 1 : 2;
    }

    public final void setEnabledStateForGmsPackages(int i, int i2) {
        for (String str : GMS_PACKAGES) {
            try {
                setApplicationEnabledSettingAsUser(str, i, i2);
            } catch (Exception e) {
                e.printStackTrace();
                Log.d("PackageManager", "Fail to enable " + str);
            }
        }
    }

    public void setApplicationEnabledSettingAsUser(String str, int i, int i2) {
        ActivityThread.getPackageManager().setApplicationEnabledSetting(str, i, 0, i2, this.mContext.getOpPackageName());
    }

    public void registerContentObserverForGoogleControlCore(Handler handler) {
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("google_core_control"), true, new ContentObserver(handler) { // from class: com.samsung.android.server.pm.google.ChinaGmsToggleUtils.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                ChinaGmsToggleUtils.this.setGmsEnabledSetting(-1);
            }
        }, -1);
    }

    public static boolean isGMSPackage(final String str) {
        return Arrays.stream(GMS_PACKAGES).anyMatch(new Predicate() { // from class: com.samsung.android.server.pm.google.ChinaGmsToggleUtils$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean lambda$isGMSPackage$0;
                lambda$isGMSPackage$0 = ChinaGmsToggleUtils.lambda$isGMSPackage$0(str, (String) obj);
                return lambda$isGMSPackage$0;
            }
        });
    }

    public static /* synthetic */ boolean lambda$isGMSPackage$0(String str, String str2) {
        return str2.equals(str);
    }
}
