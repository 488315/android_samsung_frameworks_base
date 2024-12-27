package com.android.systemui.controls.management.model;

import com.android.systemui.controls.controller.ControlInfo$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public final int hashCode() {
        return Boolean.hashCode(this.needChunk) + ControlInfo$$ExternalSyntheticOutline0.m((this.controlsModel.hashCode() + (this.structureName.hashCode() * 31)) * 31, 31, this.displayName);
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
