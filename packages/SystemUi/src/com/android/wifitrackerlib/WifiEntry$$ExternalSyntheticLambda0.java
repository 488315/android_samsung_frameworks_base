package com.android.wifitrackerlib;

import com.samsung.android.wifitrackerlib.SemWifiEntryFlags;
import java.net.InetAddress;
import java.util.function.Function;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class WifiEntry$$ExternalSyntheticLambda0 implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        int i;
        r1 = 0;
        r1 = 0;
        int i2 = 0;
        switch (this.$r8$classId) {
            case 0:
                return ((InetAddress) obj).getHostAddress();
            case 1:
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 2:
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 3:
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
            case 4:
                return Integer.valueOf(-((WifiEntry) obj).mRssi);
            case 5:
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            case 6:
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
            case 7:
                return Integer.valueOf(-((WifiEntry) obj).mFrequency);
            case 8:
                return ((WifiEntry) obj).getTitle();
            case 9:
                return Boolean.valueOf(!(((WifiEntry) obj).mRssi != -127));
            case 10:
                return Boolean.valueOf(!((WifiEntry) obj).isSaved());
            case 11:
                return Boolean.valueOf(!((WifiEntry) obj).isSuggestion());
            case 12:
                WifiEntry wifiEntry = (WifiEntry) obj;
                SemWifiEntryFlags semWifiEntryFlags = wifiEntry.mSemFlags;
                if (semWifiEntryFlags.networkScoringUiEnabled && (i = wifiEntry.mSpeed) >= 20 && semWifiEntryFlags.networkType != 2) {
                    i2 = i;
                }
                return Integer.valueOf(-i2);
            case 13:
                return Integer.valueOf(-((WifiEntry) obj).mLevel);
            case 14:
                return ((WifiEntry) obj).getTitle();
            case 15:
                return Integer.valueOf(((WifiEntry) obj).getSecurity());
            case 16:
                return Boolean.valueOf(((WifiEntry) obj).getConnectedState() != 2);
            default:
                return ((WifiEntry) obj).getTitle();
        }
    }
}
