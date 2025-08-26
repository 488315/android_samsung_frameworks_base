package com.samsung.android.sdk.routines.v3.data;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class TargetInstanceInfo {
    private final TargetInstanceInfoExtra extra;
    private long instanceId;
    private final String intentParam;
    private final String labelParam;
    private final String packageName;
    private final String tag;

    public TargetInstanceInfo(long j, String str, String str2, String str3, String str4, TargetInstanceInfoExtra targetInstanceInfoExtra) {
        this.instanceId = j;
        this.packageName = str;
        this.tag = str2;
        this.intentParam = str3;
        this.labelParam = str4;
        this.extra = targetInstanceInfoExtra;
    }

    public static /* synthetic */ TargetInstanceInfo copy$default(TargetInstanceInfo targetInstanceInfo, long j, String str, String str2, String str3, String str4, TargetInstanceInfoExtra targetInstanceInfoExtra, int i, Object obj) {
        if ((i & 1) != 0) {
            j = targetInstanceInfo.instanceId;
        }
        long j2 = j;
        if ((i & 2) != 0) {
            str = targetInstanceInfo.packageName;
        }
        String str5 = str;
        if ((i & 4) != 0) {
            str2 = targetInstanceInfo.tag;
        }
        String str6 = str2;
        if ((i & 8) != 0) {
            str3 = targetInstanceInfo.intentParam;
        }
        String str7 = str3;
        if ((i & 16) != 0) {
            str4 = targetInstanceInfo.labelParam;
        }
        String str8 = str4;
        if ((i & 32) != 0) {
            targetInstanceInfoExtra = targetInstanceInfo.extra;
        }
        return targetInstanceInfo.copy(j2, str5, str6, str7, str8, targetInstanceInfoExtra);
    }

    public final long component1() {
        return this.instanceId;
    }

    public final String component2() {
        return this.packageName;
    }

    public final String component3() {
        return this.tag;
    }

    public final String component4() {
        return this.intentParam;
    }

    public final String component5() {
        return this.labelParam;
    }

    public final TargetInstanceInfoExtra component6() {
        return this.extra;
    }

    public final TargetInstanceInfo copy(long j, String str, String str2, String str3, String str4, TargetInstanceInfoExtra targetInstanceInfoExtra) {
        return new TargetInstanceInfo(j, str, str2, str3, str4, targetInstanceInfoExtra);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TargetInstanceInfo)) {
            return false;
        }
        TargetInstanceInfo targetInstanceInfo = (TargetInstanceInfo) obj;
        return this.instanceId == targetInstanceInfo.instanceId && Intrinsics.areEqual(this.packageName, targetInstanceInfo.packageName) && Intrinsics.areEqual(this.tag, targetInstanceInfo.tag) && Intrinsics.areEqual(this.intentParam, targetInstanceInfo.intentParam) && Intrinsics.areEqual(this.labelParam, targetInstanceInfo.labelParam) && Intrinsics.areEqual(this.extra, targetInstanceInfo.extra);
    }

    public final TargetInstanceInfoExtra getExtra() {
        return this.extra;
    }

    public final long getInstanceId() {
        return this.instanceId;
    }

    public final String getIntentParam() {
        return this.intentParam;
    }

    public final String getLabelParam() {
        return this.labelParam;
    }

    public final String getPackageName() {
        return this.packageName;
    }

    public final String getTag() {
        return this.tag;
    }

    public int hashCode() {
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(Long.hashCode(this.instanceId) * 31, 31, this.packageName), 31, this.tag);
        String str = this.intentParam;
        int iHashCode = (iM + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.labelParam;
        return this.extra.hashCode() + ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31);
    }

    public final void setInstanceId(long j) {
        this.instanceId = j;
    }

    public String toString() {
        return "TargetInstanceInfo(instanceId=" + this.instanceId + ", packageName=" + this.packageName + ", tag=" + this.tag + ", intentParam=" + this.intentParam + ", labelParam=" + this.labelParam + ", extra=" + this.extra + ')';
    }

    public /* synthetic */ TargetInstanceInfo(long j, String str, String str2, String str3, String str4, TargetInstanceInfoExtra targetInstanceInfoExtra, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? -1L : j, str, str2, str3, (i & 16) != 0 ? null : str4, targetInstanceInfoExtra);
    }
}
