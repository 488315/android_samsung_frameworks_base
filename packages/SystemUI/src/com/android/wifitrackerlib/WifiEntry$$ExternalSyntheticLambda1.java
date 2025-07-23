package com.android.wifitrackerlib;

import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import java.net.InetAddress;
import java.util.Comparator;
import java.util.function.Function;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda1 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i;
        r2 = 0;
        r2 = 0;
        int i2 = 0;
        switch (this.$r8$classId) {
            case 0:
                Comparator comparator = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 1:
                Comparator comparator2 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(!((WifiEntry) obj).isSaved());
            case 2:
                Comparator comparator3 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(!((WifiEntry) obj).isSuggestion());
            case 3:
                WifiEntry wifiEntry = (WifiEntry) obj;
                SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
                if (semWifiEntryFlags.networkScoringUiEnabled && (i = wifiEntry.mSpeed) >= 20 && semWifiEntryFlags.networkType != 2) {
                    i2 = i;
                }
                return Integer.valueOf(-i2);
            case 4:
                Comparator comparator4 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Integer.valueOf(-((WifiEntry) obj).getLevel());
            case 5:
                Comparator comparator5 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Integer.valueOf(((WifiEntry) obj).getSecurity());
            case 6:
                Comparator comparator6 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 7:
                return ((InetAddress) obj).getHostAddress();
            case 8:
                return ((WifiEntry) obj).getTitle();
            case 9:
                Comparator comparator7 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 10:
                Comparator comparator8 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
            case 11:
                return Integer.valueOf(-((WifiEntry) obj).mRssi);
            case 12:
                Comparator comparator9 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 13:
                Comparator comparator10 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
            case 14:
                return Integer.valueOf(-((WifiEntry) obj).mFrequency);
            default:
                Comparator comparator11 = WifiEntry.WIFI_PICKER_COMPARATOR;
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
        }
    }
}
