package com.android.systemui.controls.controller;

import android.content.ComponentName;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

public final class StructureInfo {
    public static final Companion Companion = new Companion(null);
    public static final StructureInfo EMPTY_STRUCTURE = new StructureInfo(new ComponentName("", ""), "", new ArrayList(), false, 8, null);
    public boolean active;
    public final ComponentName componentName;
    public final List controls;
    public final CharSequence structure;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StructureInfo(ComponentName componentName, CharSequence charSequence, List<ControlInfo> list, boolean z) {
        this.componentName = componentName;
        this.structure = charSequence;
        this.controls = list;
        this.active = z;
    }

    public static StructureInfo copy$default(StructureInfo structureInfo, List list) {
        return new StructureInfo(structureInfo.componentName, structureInfo.structure, list, structureInfo.active);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StructureInfo)) {
            return false;
        }
        StructureInfo structureInfo = (StructureInfo) obj;
        return Intrinsics.areEqual(this.componentName, structureInfo.componentName) && Intrinsics.areEqual(this.structure, structureInfo.structure) && Intrinsics.areEqual(this.controls, structureInfo.controls) && this.active == structureInfo.active;
    }

    public final int hashCode() {
        return Boolean.hashCode(this.active) + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.controls, ControlInfo$$ExternalSyntheticOutline0.m(this.componentName.hashCode() * 31, 31, this.structure), 31);
    }

    public final String toString() {
        ComponentName componentName = this.componentName;
        CharSequence charSequence = this.structure;
        return "StructureInfo{" + componentName + ", structure = " + ((Object) charSequence) + ", active = " + this.active + ", controls = " + this.controls + "}";
    }

    public /* synthetic */ StructureInfo(ComponentName componentName, CharSequence charSequence, List list, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(componentName, charSequence, list, (i & 8) != 0 ? false : z);
    }
}
