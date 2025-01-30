package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ControlWrapper extends StructureElementWrapper {
    public final AllControlsModel controlsModel;
    public final CharSequence displayName;
    public final boolean needChunk;
    public final CharSequence structureName;

    public /* synthetic */ ControlWrapper(CharSequence charSequence, AllControlsModel allControlsModel, CharSequence charSequence2, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(charSequence, allControlsModel, (i & 4) != 0 ? charSequence : charSequence2, (i & 8) != 0 ? true : z);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ControlWrapper)) {
            return false;
        }
        ControlWrapper controlWrapper = (ControlWrapper) obj;
        return Intrinsics.areEqual(this.structureName, controlWrapper.structureName) && Intrinsics.areEqual(this.controlsModel, controlWrapper.controlsModel) && Intrinsics.areEqual(this.displayName, controlWrapper.displayName) && this.needChunk == controlWrapper.needChunk;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final int hashCode() {
        int hashCode = (this.displayName.hashCode() + ((this.controlsModel.hashCode() + (this.structureName.hashCode() * 31)) * 31)) * 31;
        boolean z = this.needChunk;
        int i = z;
        if (z != 0) {
            i = 1;
        }
        return hashCode + i;
    }

    public final String toString() {
        return "ControlWrapper(structureName=" + ((Object) this.structureName) + ", controlsModel=" + this.controlsModel + ", displayName=" + ((Object) this.displayName) + ", needChunk=" + this.needChunk + ")";
    }

    public ControlWrapper(CharSequence charSequence, AllControlsModel allControlsModel, CharSequence charSequence2, boolean z) {
        super(null);
        this.structureName = charSequence;
        this.controlsModel = allControlsModel;
        this.displayName = charSequence2;
        this.needChunk = z;
    }
}
