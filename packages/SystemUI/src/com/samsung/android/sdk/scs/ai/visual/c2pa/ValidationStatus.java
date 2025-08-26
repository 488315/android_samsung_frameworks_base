package com.samsung.android.sdk.scs.ai.visual.c2pa;

import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ValidationStatus {
    private final String code;
    private final String explanation;
    private final String url;

    public ValidationStatus(String str, String str2, String str3) {
        this.code = str;
        this.url = str2;
        this.explanation = str3;
    }

    public static /* synthetic */ ValidationStatus copy$default(ValidationStatus validationStatus, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = validationStatus.code;
        }
        if ((i & 2) != 0) {
            str2 = validationStatus.url;
        }
        if ((i & 4) != 0) {
            str3 = validationStatus.explanation;
        }
        return validationStatus.copy(str, str2, str3);
    }

    public final String component1() {
        return this.code;
    }

    public final String component2() {
        return this.url;
    }

    public final String component3() {
        return this.explanation;
    }

    public final ValidationStatus copy(String str, String str2, String str3) {
        return new ValidationStatus(str, str2, str3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ValidationStatus)) {
            return false;
        }
        ValidationStatus validationStatus = (ValidationStatus) obj;
        return Intrinsics.areEqual(this.code, validationStatus.code) && Intrinsics.areEqual(this.url, validationStatus.url) && Intrinsics.areEqual(this.explanation, validationStatus.explanation);
    }

    public final String getCode() {
        return this.code;
    }

    public final String getExplanation() {
        return this.explanation;
    }

    public final String getUrl() {
        return this.url;
    }

    public int hashCode() {
        String str = this.code;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.url;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.explanation;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ValidationStatus(code=");
        sb.append(this.code);
        sb.append(", url=");
        sb.append(this.url);
        sb.append(", explanation=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.explanation, ')');
    }
}
