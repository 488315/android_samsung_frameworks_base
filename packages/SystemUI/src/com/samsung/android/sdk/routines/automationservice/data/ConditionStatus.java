package com.samsung.android.sdk.routines.automationservice.data;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ConditionStatus {
    public static final Companion Companion = new Companion(null);
    public final long instanceId;
    public final boolean isEnabled;
    public final ParameterValues parameterValues;
    public final String tag;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ ConditionStatus(long j, boolean z, String str, ParameterValues parameterValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, z, str, parameterValues);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ConditionStatus)) {
            return false;
        }
        ConditionStatus conditionStatus = (ConditionStatus) obj;
        return this.instanceId == conditionStatus.instanceId && this.isEnabled == conditionStatus.isEnabled && Intrinsics.areEqual(this.tag, conditionStatus.tag) && Intrinsics.areEqual(this.parameterValues, conditionStatus.parameterValues);
    }

    public final int hashCode() {
        return this.parameterValues.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Long.hashCode(this.instanceId) * 31, 31, this.isEnabled), 31, this.tag);
    }

    public final String toString() {
        return "ConditionStatus(instanceId=" + this.instanceId + ", isEnabled=" + this.isEnabled + ", tag=" + this.tag + ", parameterValues=" + this.parameterValues + ')';
    }

    private ConditionStatus(long j, boolean z, String str, ParameterValues parameterValues) {
        this.instanceId = j;
        this.isEnabled = z;
        this.tag = str;
        this.parameterValues = parameterValues;
    }
}
