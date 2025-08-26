package com.samsung.android.sdk.routines.automationservice.data;

import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ActionStatus {
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

    public /* synthetic */ ActionStatus(long j, boolean z, String str, ParameterValues parameterValues, DefaultConstructorMarker defaultConstructorMarker) {
        this(j, z, str, parameterValues);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ActionStatus)) {
            return false;
        }
        ActionStatus actionStatus = (ActionStatus) obj;
        return this.instanceId == actionStatus.instanceId && this.isEnabled == actionStatus.isEnabled && Intrinsics.areEqual(this.tag, actionStatus.tag) && Intrinsics.areEqual(this.parameterValues, actionStatus.parameterValues);
    }

    public final int hashCode() {
        return this.parameterValues.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(TransitionData$$ExternalSyntheticOutline0.m(Long.hashCode(this.instanceId) * 31, 31, this.isEnabled), 31, this.tag);
    }

    public final String toString() {
        return "ActionStatus(instanceId=" + this.instanceId + ", isEnabled=" + this.isEnabled + ", tag=" + this.tag + ", parameterValues=" + this.parameterValues + ')';
    }

    private ActionStatus(long j, boolean z, String str, ParameterValues parameterValues) {
        this.instanceId = j;
        this.isEnabled = z;
        this.tag = str;
        this.parameterValues = parameterValues;
    }
}
