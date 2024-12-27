package com.android.server.knox;

import android.content.Context;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Log;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knox.analytics.KnoxAnalyticsData;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BasicContainerAnalytics {
    public final Context context;
    public final IKnoxAnalyticsContainerImpl ifKnoxAnalyticsContainer;
    public static final boolean DEBUG = "eng".equals(SystemProperties.get("ro.build.type"));
    public static final int CONTAINER_DO = 4;
    public static final int CONTAINER_WPCOD = 8;

    public BasicContainerAnalytics(Context context, IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl) {
        this.ifKnoxAnalyticsContainer = iKnoxAnalyticsContainerImpl;
        this.context = context;
    }

    public final int getContainerType(int i) {
        if (!this.ifKnoxAnalyticsContainer.isLoggingAllowedForUser(i)) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(i, "userId = ", " is not an enterprise user.", "BasicContainerAnalytics");
            return -1;
        }
        boolean z = false;
        int i2 = SemPersonaManager.isKnoxId(i) ? 0 : -1;
        if (SemPersonaManager.isDoEnabled(0)) {
            i2 = CONTAINER_DO;
        }
        try {
            boolean z2 = true;
            boolean z3 = Settings.Secure.getInt(this.context.getContentResolver(), "user_setup_complete", 0) != 0;
            if ("true".equals(SystemProperties.get("ro.organization_owned")) && !SemPersonaManager.isDoEnabled(0)) {
                z = true;
            }
            if (z || !SemPersonaManager.isKnoxId(i) || z3) {
                z2 = z;
            }
            return (z2 && SemPersonaManager.isKnoxId(i)) ? CONTAINER_WPCOD : i2;
        } catch (Exception e) {
            e.printStackTrace();
            return i2;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void logActivityChange(int i, int i2, String str) {
        IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl = this.ifKnoxAnalyticsContainer;
        if (iKnoxAnalyticsContainerImpl.isLoggingAllowedForUser(i)) {
            Bundle bundle = new Bundle();
            bundle.putInt("cTp", getContainerType(i2));
            bundle.putString("pN", str);
            bundle.putInt("OToE", i == 0 ? 0 : 1);
            bundle.putString("pV", IKnoxAnalyticsContainerImpl.getPackageInfo(i2, str).versionName);
            String profileOwnerPackage = iKnoxAnalyticsContainerImpl.getProfileOwnerPackage(i2);
            String deviceOwnerPackage = IKnoxAnalyticsContainerImpl.getDeviceOwnerPackage();
            boolean hasKnoxPermission = profileOwnerPackage != null ? IKnoxAnalyticsContainerImpl.hasKnoxPermission(profileOwnerPackage) : 1;
            boolean hasKnoxPermission2 = deviceOwnerPackage != null ? IKnoxAnalyticsContainerImpl.hasKnoxPermission(deviceOwnerPackage) : 1;
            Log.d("IFKnoxAnalyticsContainer", "[" + profileOwnerPackage + "] = " + hasKnoxPermission + " , [" + deviceOwnerPackage + "] : " + deviceOwnerPackage);
            bundle.putInt("cM", hasKnoxPermission & hasKnoxPermission2);
            logEvent(bundle, "ACTIVITY_STAMP");
        }
    }

    /* JADX WARN: Type inference failed for: r3v4, types: [java.io.Serializable, java.lang.String[]] */
    public final void logEvent(Bundle bundle, String str) {
        IKnoxAnalyticsContainerImpl iKnoxAnalyticsContainerImpl = this.ifKnoxAnalyticsContainer;
        if (iKnoxAnalyticsContainerImpl.personaManagerService.getAppSeparationId() > 0) {
            return;
        }
        KnoxAnalyticsData knoxAnalyticsData = new KnoxAnalyticsData("KNOX_CONTAINER", 6, str);
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
        iKnoxAnalyticsContainerImpl.getClass();
        IKnoxAnalyticsContainerImpl.sendAnalyticsLog(knoxAnalyticsData);
        if (DEBUG) {
            StringBuilder m = Preconditions$$ExternalSyntheticOutline0.m(str, " / ");
            m.append(knoxAnalyticsData.toString());
            Log.d("BasicContainerAnalytics", m.toString());
        }
    }

    public final void logWorkAppUsgae(int i, int i2, long j, String str) {
        if (this.ifKnoxAnalyticsContainer.isLoggingAllowedForUser(i)) {
            Bundle bundle = new Bundle();
            bundle.putInt("cTp", getContainerType(i));
            bundle.putString("pN", str);
            bundle.putLong("usgT", j);
            bundle.putLong("cnt", i2);
            logEvent(bundle, "WORKAPP_USAGE");
        }
    }
}
