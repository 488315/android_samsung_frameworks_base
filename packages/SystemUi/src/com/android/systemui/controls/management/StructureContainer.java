package com.android.systemui.controls.management;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class StructureContainer {
    public final ControlsModel model;
    public final CharSequence structureName;

    public StructureContainer(CharSequence charSequence, ControlsModel controlsModel) {
        this.structureName = charSequence;
        this.model = controlsModel;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StructureContainer)) {
            return false;
        }
        StructureContainer structureContainer = (StructureContainer) obj;
        return Intrinsics.areEqual(this.structureName, structureContainer.structureName) && Intrinsics.areEqual(this.model, structureContainer.model);
    }

    public final int hashCode() {
        return this.model.hashCode() + (this.structureName.hashCode() * 31);
    }

    public final String toString() {
        return "StructureContainer(structureName=" + ((Object) this.structureName) + ", model=" + this.model + ")";
    }
}
