package com.android.systemui.controls.controller;

import android.content.ComponentName;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureInfo {
    public static final Companion Companion = new Companion(null);
    public static final StructureInfo EMPTY_STRUCTURE = new StructureInfo(new ComponentName("", ""), "", new ArrayList());
    public final ComponentName componentName;
    public final List controls;
    public final CustomStructureInfoImpl customStructureInfo;
    public final CharSequence structure;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public StructureInfo(ComponentName componentName, CharSequence charSequence, List<ControlInfo> list, CustomStructureInfoImpl customStructureInfoImpl) {
        this.componentName = componentName;
        this.structure = charSequence;
        this.controls = list;
        this.customStructureInfo = customStructureInfoImpl;
    }

    public static StructureInfo copy$default(StructureInfo structureInfo, List list) {
        return new StructureInfo(structureInfo.componentName, structureInfo.structure, list, structureInfo.customStructureInfo);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StructureInfo)) {
            return false;
        }
        StructureInfo structureInfo = (StructureInfo) obj;
        return Intrinsics.areEqual(this.componentName, structureInfo.componentName) && Intrinsics.areEqual(this.structure, structureInfo.structure) && Intrinsics.areEqual(this.controls, structureInfo.controls) && Intrinsics.areEqual(this.customStructureInfo, structureInfo.customStructureInfo);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.controls.hashCode() + ((this.structure.hashCode() + (this.componentName.hashCode() * 31)) * 31)) * 31;
        boolean z = this.customStructureInfo.active;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "StructureInfo{" + this.componentName + ", structure = " + ((Object) this.structure) + ", active = " + this.customStructureInfo.active + ", controls = " + this.controls + "}";
    }

    public StructureInfo(ComponentName componentName, CharSequence charSequence, List<ControlInfo> list) {
        this(componentName, charSequence, list, new CustomStructureInfoImpl(false));
    }
}
