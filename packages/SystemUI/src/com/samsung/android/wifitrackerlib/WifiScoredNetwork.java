package com.samsung.android.wifitrackerlib;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
