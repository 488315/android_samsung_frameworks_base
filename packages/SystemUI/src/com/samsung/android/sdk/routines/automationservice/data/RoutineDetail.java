package com.samsung.android.sdk.routines.automationservice.data;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class RoutineDetail {
    public final List actions;
    public final List conditions;
    public final RoutineInfo info;

    public RoutineDetail(RoutineInfo routineInfo, List<ConditionStatus> list, List<ActionStatus> list2) {
        this.info = routineInfo;
        this.conditions = list;
        this.actions = list2;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoutineDetail)) {
            return false;
        }
        RoutineDetail routineDetail = (RoutineDetail) obj;
        return Intrinsics.areEqual(this.info, routineDetail.info) && Intrinsics.areEqual(this.conditions, routineDetail.conditions) && Intrinsics.areEqual(this.actions, routineDetail.actions);
    }

    public final int hashCode() {
        return this.actions.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.conditions, this.info.hashCode() * 31, 31);
    }

    public final String toString() {
        return "RoutineDetail(info=" + this.info + ", conditions=" + this.conditions + ", actions=" + this.actions + ')';
    }
}
