package com.samsung.android.sdk.scs.ai.visual.c2pa;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SignatureInfo {
    private final String certSerialNumber;
    private final String issuer;
    private final String time;

    public SignatureInfo(String str, String str2, String str3) {
        this.issuer = str;
        this.certSerialNumber = str2;
        this.time = str3;
    }

    public static /* synthetic */ SignatureInfo copy$default(SignatureInfo signatureInfo, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = signatureInfo.issuer;
        }
        if ((i & 2) != 0) {
            str2 = signatureInfo.certSerialNumber;
        }
        if ((i & 4) != 0) {
            str3 = signatureInfo.time;
        }
        return signatureInfo.copy(str, str2, str3);
    }

    public final String component1() {
        return this.issuer;
    }

    public final String component2() {
        return this.certSerialNumber;
    }

    public final String component3() {
        return this.time;
    }

    public final SignatureInfo copy(String str, String str2, String str3) {
        return new SignatureInfo(str, str2, str3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SignatureInfo)) {
            return false;
        }
        SignatureInfo signatureInfo = (SignatureInfo) obj;
        return Intrinsics.areEqual(this.issuer, signatureInfo.issuer) && Intrinsics.areEqual(this.certSerialNumber, signatureInfo.certSerialNumber) && Intrinsics.areEqual(this.time, signatureInfo.time);
    }

    public final String getCertSerialNumber() {
        return this.certSerialNumber;
    }

    public final String getIssuer() {
        return this.issuer;
    }

    public final String getTime() {
        return this.time;
    }

    public int hashCode() {
        String str = this.issuer;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.certSerialNumber;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.time;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("SignatureInfo(issuer=");
        sb.append(this.issuer);
        sb.append(", certSerialNumber=");
        sb.append(this.certSerialNumber);
        sb.append(", time=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.time, ')');
    }
}
