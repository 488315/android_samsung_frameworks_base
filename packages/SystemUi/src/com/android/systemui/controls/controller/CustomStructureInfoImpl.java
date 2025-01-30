package com.android.systemui.controls.controller;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class CustomStructureInfoImpl {
    public boolean active;

    public CustomStructureInfoImpl(boolean z) {
        this.active = z;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof CustomStructureInfoImpl) && this.active == ((CustomStructureInfoImpl) obj).active;
    }

    public final int hashCode() {
        boolean z = this.active;
        if (z) {
            return 1;
        }
        return z ? 1 : 0;
    }

    public final String toString() {
        return "CustomStructureInfoImpl(active=" + this.active + ")";
    }
}
