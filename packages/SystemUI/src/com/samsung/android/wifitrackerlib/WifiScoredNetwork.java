package com.samsung.android.wifitrackerlib;

/* loaded from: classes4.dex */
public class WifiScoredNetwork {
    public final String bssid;
    public final int[] levels;
    public final int networkType;

    public WifiScoredNetwork(String str, int i, int[] iArr) {
        this.bssid = str;
        this.networkType = i;
        this.levels = iArr;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("key:");
        sb.append(this.bssid);
        sb.append(", networkType:");
        sb.append(this.networkType);
        sb.append(", speed:[");
        for (int i : this.levels) {
            sb.append(i);
            sb.append(" ");
        }
        sb.append("]");
        return sb.toString();
    }
}
