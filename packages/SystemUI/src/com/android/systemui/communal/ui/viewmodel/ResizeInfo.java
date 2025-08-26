package com.android.systemui.communal.ui.viewmodel;

/* loaded from: classes2.dex */
public final class ResizeInfo {
    public final DragHandle fromHandle;
    public final boolean isExpanding;
    public final int spans;

    public ResizeInfo(int i, DragHandle dragHandle) {
        this.spans = i;
        this.fromHandle = dragHandle;
        this.isExpanding = i > 0;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ResizeInfo)) {
            return false;
        }
        ResizeInfo resizeInfo = (ResizeInfo) obj;
        return this.spans == resizeInfo.spans && this.fromHandle == resizeInfo.fromHandle;
    }

    public final int hashCode() {
        return this.fromHandle.hashCode() + (Integer.hashCode(this.spans) * 31);
    }

    public final String toString() {
        return "ResizeInfo(spans=" + this.spans + ", fromHandle=" + this.fromHandle + ")";
    }
}
