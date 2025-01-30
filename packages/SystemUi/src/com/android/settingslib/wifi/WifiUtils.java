package com.android.settingslib.wifi;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.os.SystemClock;
import android.util.ArraySet;
import androidx.core.widget.NestedScrollView$$ExternalSyntheticOutline0;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class WifiUtils {
    static final String ACTION_WIFI_DIALOG = "com.android.settings.WIFI_DIALOG";
    static final String EXTRA_CHOSEN_WIFI_ENTRY_KEY = "key_chosen_wifientry_key";
    static final String EXTRA_CONNECT_FOR_CALLER = "connect_for_caller";
    public static final int[] WIFI_PIE = {R.drawable.ic_voice_search_api_holo_light, R.drawable.ic_voice_search_api_material, R.drawable.ic_volume, R.drawable.ic_volume_bluetooth_ad2p, R.drawable.ic_volume_bluetooth_in_call};
    public static final int[] NO_INTERNET_WIFI_PIE = {com.android.systemui.R.drawable.ic_no_internet_wifi_signal_0, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_1, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_2, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_3, com.android.systemui.R.drawable.ic_no_internet_wifi_signal_4};

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class InternetIconInjector {
        public final Context mContext;

        public InternetIconInjector(Context context) {
            this.mContext = context;
        }

        public final Drawable getIcon(int i, boolean z) {
            if (i < 0) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("Wi-Fi level is out of range! level:", i, "WifiUtils");
                i = 0;
            } else if (i >= 5) {
                NestedScrollView$$ExternalSyntheticOutline0.m34m("Wi-Fi level is out of range! level:", i, "WifiUtils");
                i = 4;
            }
            return this.mContext.getDrawable(z ? WifiUtils.NO_INTERNET_WIFI_PIE[i] : WifiUtils.WIFI_PIE[i]);
        }
    }

    public static String getVisibilityStatus(AccessPoint accessPoint) {
        String str;
        StringBuilder sb;
        WifiInfo wifiInfo = accessPoint.mInfo;
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        StringBuilder sb4 = new StringBuilder();
        StringBuilder sb5 = new StringBuilder();
        if (!accessPoint.isActive() || wifiInfo == null) {
            str = null;
        } else {
            str = wifiInfo.getBSSID();
            if (str != null) {
                sb2.append(" ");
                sb2.append(str);
            }
            sb2.append(" standard = ");
            sb2.append(wifiInfo.getWifiStandard());
            sb2.append(" rssi=");
            sb2.append(wifiInfo.getRssi());
            sb2.append("  score=");
            sb2.append(wifiInfo.getScore());
            if (accessPoint.mSpeed != 0) {
                sb2.append(" speed=");
                sb2.append(AccessPoint.getSpeedLabel(accessPoint.mSpeed, accessPoint.mContext));
            }
            sb2.append(String.format(" tx=%.1f,", Double.valueOf(wifiInfo.getSuccessfulTxPacketsPerSecond())));
            sb2.append(String.format("%.1f,", Double.valueOf(wifiInfo.getRetriedTxPacketsPerSecond())));
            sb2.append(String.format("%.1f ", Double.valueOf(wifiInfo.getLostTxPacketsPerSecond())));
            sb2.append(String.format("rx=%.1f", Double.valueOf(wifiInfo.getSuccessfulRxPacketsPerSecond())));
        }
        long elapsedRealtime = SystemClock.elapsedRealtime();
        ArraySet arraySet = new ArraySet();
        synchronized (accessPoint.mLock) {
            arraySet.addAll((Collection) accessPoint.mScanResults);
            arraySet.addAll((Collection) accessPoint.mExtraScanResults);
        }
        Iterator it = arraySet.iterator();
        int i = 0;
        int i2 = 0;
        int i3 = -127;
        int i4 = -127;
        int i5 = -127;
        int i6 = 0;
        while (true) {
            sb = sb2;
            if (!it.hasNext()) {
                break;
            }
            ScanResult scanResult = (ScanResult) it.next();
            if (scanResult == null) {
                sb2 = sb;
            } else {
                int i7 = scanResult.frequency;
                Iterator it2 = it;
                if (i7 >= 4900 && i7 <= 5900) {
                    i6++;
                    int i8 = scanResult.level;
                    if (i8 > i4) {
                        i4 = i8;
                    }
                    if (i6 <= 4) {
                        sb4.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                    }
                } else if (i7 >= 2400 && i7 <= 2500) {
                    i++;
                    int i9 = scanResult.level;
                    if (i9 > i3) {
                        i3 = i9;
                    }
                    if (i <= 4) {
                        sb3.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                    }
                } else if (i7 >= 58320 && i7 <= 70200) {
                    i2++;
                    int i10 = scanResult.level;
                    if (i10 > i5) {
                        i5 = i10;
                    }
                    if (i2 <= 4) {
                        sb5.append(verboseScanResultSummary(accessPoint, scanResult, str, elapsedRealtime));
                    }
                }
                sb2 = sb;
                it = it2;
            }
        }
        sb.append(" [");
        if (i > 0) {
            sb.append("(");
            sb.append(i);
            sb.append(")");
            if (i > 4) {
                sb.append("max=");
                sb.append(i3);
                sb.append(",");
            }
            sb.append(sb3.toString());
        }
        sb.append(";");
        if (i6 > 0) {
            sb.append("(");
            sb.append(i6);
            sb.append(")");
            if (i6 > 4) {
                sb.append("max=");
                sb.append(i4);
                sb.append(",");
            }
            sb.append(sb4.toString());
        }
        sb.append(";");
        if (i2 > 0) {
            sb.append("(");
            sb.append(i2);
            sb.append(")");
            if (i2 > 4) {
                sb.append("max=");
                sb.append(i5);
                sb.append(",");
            }
            sb.append(sb5.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public static String verboseScanResultSummary(AccessPoint accessPoint, ScanResult scanResult, String str, long j) {
        StringBuilder sb = new StringBuilder(" \n{");
        sb.append(scanResult.BSSID);
        if (scanResult.BSSID.equals(str)) {
            sb.append("*");
        }
        sb.append("=");
        sb.append(scanResult.frequency);
        sb.append(",");
        sb.append(scanResult.level);
        TimestampedScoredNetwork timestampedScoredNetwork = (TimestampedScoredNetwork) ((HashMap) accessPoint.mScoredNetworkCache).get(scanResult.BSSID);
        int calculateBadge = timestampedScoredNetwork == null ? 0 : timestampedScoredNetwork.mScore.calculateBadge(scanResult.level);
        if (calculateBadge != 0) {
            sb.append(",");
            sb.append(AccessPoint.getSpeedLabel(calculateBadge, accessPoint.mContext));
        }
        int i = ((int) (j - (scanResult.timestamp / 1000))) / 1000;
        sb.append(",");
        sb.append(i);
        sb.append("s}");
        return sb.toString();
    }
}
