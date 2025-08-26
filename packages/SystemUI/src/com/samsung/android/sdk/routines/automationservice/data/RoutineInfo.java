package com.samsung.android.sdk.routines.automationservice.data;

import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class RoutineInfo {
    public static final Companion Companion = new Companion(null);
    public final String id;
    public final String name;
    public final AutomationService.SystemRoutineType type;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public /* synthetic */ RoutineInfo(String str, String str2, AutomationService.SystemRoutineType systemRoutineType, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, systemRoutineType);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof RoutineInfo)) {
            return false;
        }
        RoutineInfo routineInfo = (RoutineInfo) obj;
        return Intrinsics.areEqual(this.name, routineInfo.name) && Intrinsics.areEqual(this.id, routineInfo.id) && this.type == routineInfo.type;
    }

    public final int hashCode() {
        return this.type.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.name.hashCode() * 31, 31, this.id);
    }

    public final String toString() {
        return "RoutineInfo(name=" + this.name + ", id=" + this.id + ", type=" + this.type + ')';
    }

    private RoutineInfo(String str, String str2, AutomationService.SystemRoutineType systemRoutineType) {
        this.name = str;
        this.id = str2;
        this.type = systemRoutineType;
    }
}
