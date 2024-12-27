package com.android.systemui.shade.carrier;

import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

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

    public final int hashCode() {
        int hashCode = Boolean.hashCode(this.showSpn) * 31;
        String str = this.spn;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.dataSpn;
        int m = TransitionData$$ExternalSyntheticOutline0.m((hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31, 31, this.showPlmn);
        String str3 = this.plmn;
        return Boolean.hashCode(this.hasVoWifiPLMN) + ((m + (str3 != null ? str3.hashCode() : 0)) * 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NetworkNameInfo(showSpn=");
        sb.append(this.showSpn);
        sb.append(", spn=");
        sb.append(this.spn);
        sb.append(", dataSpn=");
        sb.append(this.dataSpn);
        sb.append(", showPlmn=");
        sb.append(this.showPlmn);
        sb.append(", plmn=");
        sb.append(this.plmn);
        sb.append(", hasVoWifiPLMN=");
        return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m(sb, this.hasVoWifiPLMN, ")");
    }
}
