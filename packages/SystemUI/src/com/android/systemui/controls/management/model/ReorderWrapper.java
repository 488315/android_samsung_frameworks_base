package com.android.systemui.controls.management.model;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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
