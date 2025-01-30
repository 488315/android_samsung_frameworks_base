package com.samsung.android.sdk.routines.automationservice.data;

import androidx.picker.model.AppInfo$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.routines.automationservice.interfaces.AutomationService;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class RoutineInfo {
    public static final Companion Companion = new Companion(null);

    /* renamed from: id */
    public final String f630id;
    public final String name;
    public final AutomationService.SystemRoutineType type;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
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
        return Intrinsics.areEqual(this.name, routineInfo.name) && Intrinsics.areEqual(this.f630id, routineInfo.f630id) && this.type == routineInfo.type;
    }

    public final int hashCode() {
        return this.type.hashCode() + AppInfo$$ExternalSyntheticOutline0.m41m(this.f630id, this.name.hashCode() * 31, 31);
    }

    public final String toString() {
        return "RoutineInfo(name=" + this.name + ", id=" + this.f630id + ", type=" + this.type + ')';
    }

    private RoutineInfo(String str, String str2, AutomationService.SystemRoutineType systemRoutineType) {
        this.name = str;
        this.f630id = str2;
        this.type = systemRoutineType;
    }
}
