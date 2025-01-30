package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
