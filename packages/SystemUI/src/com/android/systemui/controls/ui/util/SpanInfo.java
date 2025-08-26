package com.android.systemui.controls.ui.util;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class SpanInfo {
    public int numberPerLine;
    public int span;
    public final int width;

    /* JADX WARN: Illegal instructions before constructor call */
    public SpanInfo() {
        int i = 0;
        this(i, i, 3, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpanInfo)) {
            return false;
        }
        SpanInfo spanInfo = (SpanInfo) obj;
        return this.width == spanInfo.width && this.numberPerLine == spanInfo.numberPerLine;
    }

    public final int hashCode() {
        return Integer.hashCode(this.numberPerLine) + (Integer.hashCode(this.width) * 31);
    }

    public final String toString() {
        return "SpanInfo(width=" + this.width + ", numberPerLine=" + this.numberPerLine + ")";
    }

    public SpanInfo(int i, int i2) {
        this.width = i;
        this.numberPerLine = i2;
        this.span = 1;
    }

    public /* synthetic */ SpanInfo(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? -1 : i, (i3 & 2) != 0 ? 1 : i2);
    }
}
