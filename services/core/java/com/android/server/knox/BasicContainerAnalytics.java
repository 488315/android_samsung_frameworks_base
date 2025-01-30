package com.android.server.knox;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.app.SemDualAppManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;
import com.samsung.android.knox.analytics.util.KnoxAnalyticsDataConverter;

/* loaded from: classes2.dex */
public class BasicContainerAnalytics {
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public final String TAG = "BasicContainerAnalytics";
    public final Context context;
    public final IKnoxAnalyticsContainer ifKnoxAnalyticsContainer;

    public BasicContainerAnalytics(IKnoxAnalyticsContainer iKnoxAnalyticsContainer, Context context) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainer;
        this.context = context;
    }

    public int getContainerType(int i) {
        if (!SemPersonaManager.isSecureFolderId(i) && !SemDualAppManager.isDualAppId(i)) {
            boolean z = false;
            if (i != 0 || SemPersonaManager.isDoEnabled(0)) {
                int i2 = SemPersonaManager.isKnoxId(i) ? 0 : -1;
                if (this.ifKnoxAnalyticsContainer.isLegacyClId(i)) {
                    i2 = 2;
                }
                if (i == 0 && this.ifKnoxAnalyticsContainer.isDoEnabled(0)) {
                    i2 = 4;
                }
                try {
                    boolean isUserSetupCompleted = isUserSetupCompleted(this.context);
                    boolean z2 = true;
                    if (this.ifKnoxAnalyticsContainer.isOrganizationOwned() && !this.ifKnoxAnalyticsContainer.isDoEnabled(0)) {
                        z = true;
                    }
                    if (z || !this.ifKnoxAnalyticsContainer.isKnoxId(i) || isUserSetupCompleted) {
                        z2 = z;
                    }
                    if (!z2) {
                        return i2;
                    }
                    if (this.ifKnoxAnalyticsContainer.isKnoxId(i)) {
                        return 8;
                    }
                    return i2;
                } catch (Exception e) {
                    e.printStackTrace();
                    return i2;
                }
            }
        }
        Log.d("BasicContainerAnalytics", "userId = " + i + " is not an enterprise user.");
        return -1;
    }

    public void logEventAccountChanged(int i, String str, int i2) {
        if (i != 0) {
            try {
                if (!this.ifKnoxAnalyticsContainer.isSecureFolderId(i) && this.ifKnoxAnalyticsContainer.isKnoxId(i)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("cTp", getContainerType(i));
                    bundle.putString(KnoxAnalyticsDataConverter.EVENT, "LOGIN_ACCOUNTS_CHANGED");
                    bundle.putString("accTy", str);
                    bundle.putInt("add", i2);
                    logEvent(bundle, "LOGIN_ACCOUNTS_CHANGED");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void logWorkAppUsgae(int i, String str, long j, int i2) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i));
        bundle.putString("pN", str);
        bundle.putLong("usgT", j);
        bundle.putLong("cnt", i2);
        logEvent(bundle, "WORKAPP_USAGE");
    }

    public void logActivityChange(int i, int i2, String str) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i2));
        bundle.putString("pN", str);
        bundle.putInt("OToE", i == 0 ? 0 : 1);
        bundle.putString("pV", this.ifKnoxAnalyticsContainer.getPackageInfo(str, i2).versionName);
        bundle.putInt("cM", this.ifKnoxAnalyticsContainer.isPremiumLicenseActivated(i2) ? 1 : 0);
        logEvent(bundle, "ACTIVITY_STAMP");
    }

    public void logPackageChanged(int i, String str, int i2) {
        String str2;
        if (SemPersonaManager.isSystemApp(this.context, str)) {
            return;
        }
        try {
            str2 = this.ifKnoxAnalyticsContainer.getInstallerPackageName(str);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            str2 = null;
        }
        Bundle bundle = new Bundle();
        if (str2 != null && !str2.isEmpty()) {
            bundle.putString("instN", str2);
        }
        int isIMPackage = i2 == 1 ? this.ifKnoxAnalyticsContainer.isIMPackage(str, i) : 0;
        bundle.putInt("cTp", getContainerType(i));
        bundle.putString("pN", str);
        bundle.putInt("add", i2);
        bundle.putInt("ime", isIMPackage);
        logEvent(bundle, "PACKAGE_CHANGED");
    }

    public void logWorkModeOn(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i));
        logEvent(bundle, "WORK_MODE_ON");
    }

    public void logWorkModeOff(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i));
        logEvent(bundle, "WORK_MODE_OFF");
    }

    public void logMoveToKnox(int i, boolean z) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i));
        bundle.putInt("move", z ? 1 : 0);
        logEvent(bundle, "MOVE_TO_KNOX_FILE");
    }

    public void logWorkProfileAdded(int i, String str) {
        try {
            Bundle bundle = new Bundle();
            bundle.putInt("cTp", getContainerType(i));
            if (str == null) {
                str = ((DevicePolicyManager) this.context.getSystemService("device_policy")).getProfileOwnerAsUser(new UserHandle(i)).getPackageName();
            }
            bundle.putString("pN", str);
            bundle.putString("pV", this.ifKnoxAnalyticsContainer.getPackageInfo(str, i).versionName);
            logEvent(bundle, "WORK_PROFILE_CREATED");
        } catch (Exception e) {
            Log.d("BasicContainerAnalytics", "WORK_PROFILE_CREATED KA failed : " + e);
        }
    }

    public void logWorkProfileRemoved(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("cTp", getContainerType(i));
        logEvent(bundle, "WORK_PROFILE_REMOVED");
    }

    public void logDeviceOwnerChanged(String str) {
        if (this.ifKnoxAnalyticsContainer.isDoEnabled(0)) {
            Bundle bundle = new Bundle();
            bundle.putInt("cTp", getContainerType(0));
            bundle.putString("pN", str);
            logEvent(bundle, "WORK_PROFILE_CREATED");
            return;
        }
        Bundle bundle2 = new Bundle();
        bundle2.putInt("cTp", getContainerType(0));
        logEvent(bundle2, "WORK_PROFILE_REMOVED");
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.Serializable, java.lang.String[]] */
    public void logEvent(Bundle bundle, String str) {
        if (bundle == null || this.ifKnoxAnalyticsContainer.getSeparatedAppsUserId() <= 0) {
            KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_CONTAINER", 5, str);
            if (bundle != null) {
                for (String str2 : bundle.keySet()) {
                    Object obj = bundle.get(str2);
                    if (obj instanceof Integer) {
                        knoxAnalyticsData.setProperty(str2, (Integer) obj);
                    } else if (obj instanceof String) {
                        knoxAnalyticsData.setProperty(str2, (String) obj);
                    } else if (obj instanceof Long) {
                        knoxAnalyticsData.setProperty(str2, (Long) obj);
                    } else if (obj instanceof String[]) {
                        knoxAnalyticsData.setProperty(str2, (String[]) obj);
                    }
                }
            }
            this.ifKnoxAnalyticsContainer.sendAnalyticsLog(knoxAnalyticsData);
            if (DEBUG) {
                Log.d("BasicContainerAnalytics", str + " / " + knoxAnalyticsData.toString());
            }
        }
    }

    public final boolean isUserSetupCompleted(Context context) {
        return Settings.Secure.getInt(context.getContentResolver(), "user_setup_complete", 0) != 0;
    }

    public void logProfilePolicyRestriction(String str, int i, int i2) {
        String str2;
        int containerType = getContainerType(i2);
        try {
            str2 = ((DevicePolicyManager) this.context.getSystemService("device_policy")).getProfileOwnerAsUser(new UserHandle(i2)).getPackageName();
        } catch (Exception e) {
            e.printStackTrace();
            str2 = null;
        }
        Bundle bundle = new Bundle();
        bundle.putString("rN", str);
        bundle.putInt("bV", i);
        bundle.putInt("cTp", containerType);
        bundle.putString("pN", str2);
        logEvent(bundle, "PROFILE_POLICY_RESTRICTION");
    }
}
