package com.android.server.biometrics.sensors.fingerprint;

import android.text.TextUtils;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.server.accessibility.magnification.MagnificationConnectionManager$$ExternalSyntheticOutline0;
import com.android.server.biometrics.SemBioAnalyticsManager;
import java.util.function.BiFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class SemFpProviderEx {
    public final SemBioAnalyticsManager mAnalyticsManager;
    public final BiFunction mIntResultRequestProvider;
    public final BiFunction mStringResultRequestProvider;
    public final SparseArray mSensorInfos = new SparseArray(1);
    public final SparseArray mSdkVersions = new SparseArray(1);
    public final SparseIntArray mSecurityLevels = new SparseIntArray(1);

    public SemFpProviderEx(BiFunction biFunction, BiFunction biFunction2, SemBioAnalyticsManager semBioAnalyticsManager) {
        this.mStringResultRequestProvider = biFunction;
        this.mIntResultRequestProvider = biFunction2;
        this.mAnalyticsManager = semBioAnalyticsManager;
    }

    public final String getDaemonSdkVersion(int i) {
        String str = (String) this.mSdkVersions.get(i);
        if (TextUtils.isEmpty(str)) {
            str = (String) this.mStringResultRequestProvider.apply(Integer.valueOf(i), 4);
            this.mSdkVersions.put(i, str);
        }
        return TextUtils.emptyIfNull(str);
    }

    public final void updateCacheDataOfHAL(int i) {
        String trim;
        getDaemonSdkVersion(i);
        String str = (String) this.mStringResultRequestProvider.apply(Integer.valueOf(i), 5);
        this.mSensorInfos.put(i, str.replace("\n", ", "));
        TextUtils.emptyIfNull(str);
        int intValue = ((Integer) this.mIntResultRequestProvider.apply(Integer.valueOf(i), 30)).intValue();
        if (intValue <= 0) {
            intValue = 1;
        }
        this.mSecurityLevels.put(i, intValue);
        String str2 = (String) this.mSensorInfos.get(i);
        if (str2 != null) {
            try {
                int indexOf = str2.indexOf("UID : ");
                trim = indexOf >= 0 ? str2.substring(indexOf + 6).split("\\n")[0].trim() : "";
            } catch (Exception e) {
                MagnificationConnectionManager$$ExternalSyntheticOutline0.m(e, new StringBuilder("dispatchHalInfoToAnalytics: "), "FingerprintService");
                return;
            }
        } else {
            trim = null;
        }
        SemBioAnalyticsManager semBioAnalyticsManager = this.mAnalyticsManager;
        String str3 = (String) this.mSdkVersions.get(i);
        semBioAnalyticsManager.getClass();
        if (!TextUtils.isEmpty(str3)) {
            semBioAnalyticsManager.fpInsertLog(2, "FPDA", str3);
        }
        if (TextUtils.isEmpty(trim)) {
            return;
        }
        semBioAnalyticsManager.fpInsertLog(2, "FPDS", trim);
    }
}
