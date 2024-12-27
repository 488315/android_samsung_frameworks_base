package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

public final class ReorderWrapper extends StructureElementWrapper {
    public final CharSequence displayName;

    public ReorderWrapper(CharSequence charSequence) {
        super(null);
        this.displayName = charSequence;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof ReorderWrapper) && Intrinsics.areEqual(this.displayName, ((ReorderWrapper) obj).displayName);
    }

    public final int hashCode() {
        return this.displayName.hashCode();
    }

    public final String toString() {
        return "ReorderWrapper(displayName=" + ((Object) this.displayName) + ")";
    }
}
