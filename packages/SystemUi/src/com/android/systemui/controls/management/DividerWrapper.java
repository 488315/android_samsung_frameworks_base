package com.android.systemui.controls.management;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DividerWrapper extends ElementWrapper {
    public boolean showDivider;
    public boolean showNone;

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public DividerWrapper() {
        this(r2, r2, 3, null);
        boolean z = false;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DividerWrapper)) {
            return false;
        }
        DividerWrapper dividerWrapper = (DividerWrapper) obj;
        return this.showNone == dividerWrapper.showNone && this.showDivider == dividerWrapper.showDivider;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v1, types: [int] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    public final int hashCode() {
        boolean z = this.showNone;
        ?? r0 = z;
        if (z) {
            r0 = 1;
        }
        int i = r0 * 31;
        boolean z2 = this.showDivider;
        return i + (z2 ? 1 : z2 ? 1 : 0);
    }

    public final String toString() {
        return "DividerWrapper(showNone=" + this.showNone + ", showDivider=" + this.showDivider + ")";
    }

    public /* synthetic */ DividerWrapper(boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2);
    }

    public DividerWrapper(boolean z, boolean z2) {
        super(null);
        this.showNone = z;
        this.showDivider = z2;
    }
}
