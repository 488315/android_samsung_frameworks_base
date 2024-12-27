package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
