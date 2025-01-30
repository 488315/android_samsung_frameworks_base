package com.android.systemui.shade.carrier;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NetworkNameInfo {
    public final String dataSpn;
    public final boolean hasVoWifiPLMN;
    public final String plmn;
    public final boolean showPlmn;
    public final boolean showSpn;
    public final String spn;

    public NetworkNameInfo(boolean z, String str, String str2, boolean z2, String str3, boolean z3) {
        this.showSpn = z;
        this.spn = str;
        this.dataSpn = str2;
        this.showPlmn = z2;
        this.plmn = str3;
        this.hasVoWifiPLMN = z3;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NetworkNameInfo)) {
            return false;
        }
        NetworkNameInfo networkNameInfo = (NetworkNameInfo) obj;
        return this.showSpn == networkNameInfo.showSpn && Intrinsics.areEqual(this.spn, networkNameInfo.spn) && Intrinsics.areEqual(this.dataSpn, networkNameInfo.dataSpn) && this.showPlmn == networkNameInfo.showPlmn && Intrinsics.areEqual(this.plmn, networkNameInfo.plmn) && this.hasVoWifiPLMN == networkNameInfo.hasVoWifiPLMN;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        boolean z = this.showSpn;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        int i2 = i * 31;
        String str = this.spn;
        int hashCode = (i2 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.dataSpn;
        int hashCode2 = (hashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        boolean z2 = this.showPlmn;
        int i3 = z2;
        if (z2 != 0) {
            i3 = 1;
        }
        int i4 = (hashCode2 + i3) * 31;
        String str3 = this.plmn;
        int hashCode3 = (i4 + (str3 != null ? str3.hashCode() : 0)) * 31;
        boolean z3 = this.hasVoWifiPLMN;
        return hashCode3 + (z3 ? 1 : z3 ? 1 : 0);
    }

    public final String toString() {
        return "NetworkNameInfo(showSpn=" + this.showSpn + ", spn=" + this.spn + ", dataSpn=" + this.dataSpn + ", showPlmn=" + this.showPlmn + ", plmn=" + this.plmn + ", hasVoWifiPLMN=" + this.hasVoWifiPLMN + ")";
    }
}
