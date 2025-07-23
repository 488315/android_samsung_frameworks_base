package com.android.systemui.statusbar.notification.stack.shared.model;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ShadeScrollState {
    public final boolean isScrolledToTop;
    public final int maxScrollPosition;
    public final int scrollPosition;

    public ShadeScrollState() {
        this(false, 0, 0, 7, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ShadeScrollState)) {
            return false;
        }
        ShadeScrollState shadeScrollState = (ShadeScrollState) obj;
        return this.isScrolledToTop == shadeScrollState.isScrolledToTop && this.scrollPosition == shadeScrollState.scrollPosition && this.maxScrollPosition == shadeScrollState.maxScrollPosition;
    }

    public final int hashCode() {
        return Integer.hashCode(this.maxScrollPosition) + ReorderTile$$ExternalSyntheticOutline0.m(this.scrollPosition, Boolean.hashCode(this.isScrolledToTop) * 31, 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ShadeScrollState(isScrolledToTop=");
        sb.append(this.isScrolledToTop);
        sb.append(", scrollPosition=");
        sb.append(this.scrollPosition);
        sb.append(", maxScrollPosition=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.maxScrollPosition, ")", sb);
    }

    public ShadeScrollState(boolean z, int i, int i2) {
        this.isScrolledToTop = z;
        this.scrollPosition = i;
        this.maxScrollPosition = i2;
    }

    public /* synthetic */ ShadeScrollState(boolean z, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? true : z, (i3 & 2) != 0 ? 0 : i, (i3 & 4) != 0 ? 0 : i2);
    }
}
