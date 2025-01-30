package com.android.server.biometrics.sensors.fingerprint;

import android.text.TextUtils;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseIntArray;
import com.android.server.biometrics.SemBioAnalyticsManager;
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import java.io.PrintWriter;
import java.util.function.BiFunction;

/* loaded from: classes.dex */
public class SemFpProviderEx {
    public final SemBioAnalyticsManager mAnalyticsManager;
    public final BiFunction mIntResultRequestProvider;
    public final SparseArray mSdkVersions;
    public final SparseIntArray mSecurityLevels;
    public final SparseArray mSensorInfos;
    public final BiFunction mStringResultRequestProvider;

    public SemFpProviderEx(BiFunction biFunction, BiFunction biFunction2) {
        this(biFunction, biFunction2, SemBioAnalyticsManager.get());
    }

    public SemFpProviderEx(BiFunction biFunction, BiFunction biFunction2, SemBioAnalyticsManager semBioAnalyticsManager) {
        this.mSensorInfos = new SparseArray(1);
        this.mSdkVersions = new SparseArray(1);
        this.mSecurityLevels = new SparseIntArray(1);
        this.mStringResultRequestProvider = biFunction;
        this.mIntResultRequestProvider = biFunction2;
        this.mAnalyticsManager = semBioAnalyticsManager;
    }

    public void updateCacheDataOfHAL(int i) {
        getDaemonSdkVersion(i);
        getSensorInfo(i, false);
        setSecurityLevel(i);
        dispatchHalInfoToAnalytics(i);
    }

    public final void dispatchHalInfoToAnalytics(int i) {
        String trim;
        String str = (String) this.mSensorInfos.get(i);
        if (str != null) {
            try {
                int indexOf = str.indexOf("UID : ");
                trim = indexOf >= 0 ? str.substring(indexOf + 6).split("\\n")[0].trim() : "";
            } catch (Exception e) {
                Slog.w("FingerprintService", "dispatchHalInfoToAnalytics: " + e.getMessage());
                return;
            }
        } else {
            trim = null;
        }
        this.mAnalyticsManager.fpHalInfo((String) this.mSdkVersions.get(i), trim);
    }

    public String getSensorInfo(int i, boolean z) {
        String str;
        if (z) {
            str = (String) this.mSensorInfos.get(i);
        } else {
            String str2 = (String) this.mStringResultRequestProvider.apply(Integer.valueOf(i), 5);
            this.mSensorInfos.put(i, str2.replace(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE, ", "));
            str = str2;
        }
        return TextUtils.emptyIfNull(str);
    }

    public String getDaemonSdkVersion(int i) {
        String str = (String) this.mSdkVersions.get(i);
        if (TextUtils.isEmpty(str)) {
            str = (String) this.mStringResultRequestProvider.apply(Integer.valueOf(i), 4);
            this.mSdkVersions.put(i, str);
        }
        return TextUtils.emptyIfNull(str);
    }

    public int getSecurityLevel(int i) {
        return this.mSecurityLevels.get(i, 1);
    }

    public final void setSecurityLevel(int i) {
        int intValue = ((Integer) this.mIntResultRequestProvider.apply(Integer.valueOf(i), 30)).intValue();
        if (intValue <= 0) {
            intValue = 1;
        }
        this.mSecurityLevels.put(i, intValue);
    }

    public void dumpInternal(int i, PrintWriter printWriter) {
        printWriter.println();
        printWriter.println(" daemon version : " + ((String) this.mSdkVersions.get(i, "None")));
        printWriter.println(" sensor info : " + ((String) this.mSensorInfos.get(i, "None")));
        printWriter.println(" SL : " + this.mSecurityLevels.get(i, 0));
    }
}
