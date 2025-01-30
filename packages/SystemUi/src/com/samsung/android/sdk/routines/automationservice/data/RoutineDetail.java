package com.samsung.android.sdk.routines.automationservice.data;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
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
        return this.actions.hashCode() + ((this.conditions.hashCode() + (this.info.hashCode() * 31)) * 31);
    }

    public final String toString() {
        return "RoutineDetail(info=" + this.info + ", conditions=" + this.conditions + ", actions=" + this.actions + ')';
    }
}
