package com.samsung.android.wifitrackerlib;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class WifiScoredNetwork {
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
