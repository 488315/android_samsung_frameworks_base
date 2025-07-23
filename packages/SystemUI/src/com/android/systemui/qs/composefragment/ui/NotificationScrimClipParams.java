package com.android.systemui.qs.composefragment.ui;

import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class NotificationScrimClipParams {
    public final int bottom;
    public final int leftInset;
    public final int radius;
    public final int rightInset;
    public final int top;

    public NotificationScrimClipParams() {
        this(0, 0, 0, 0, 0, 31, null);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof NotificationScrimClipParams)) {
            return false;
        }
        NotificationScrimClipParams notificationScrimClipParams = (NotificationScrimClipParams) obj;
        return this.top == notificationScrimClipParams.top && this.bottom == notificationScrimClipParams.bottom && this.leftInset == notificationScrimClipParams.leftInset && this.rightInset == notificationScrimClipParams.rightInset && this.radius == notificationScrimClipParams.radius;
    }

    public final int hashCode() {
        return Integer.hashCode(this.radius) + ReorderTile$$ExternalSyntheticOutline0.m(this.rightInset, ReorderTile$$ExternalSyntheticOutline0.m(this.leftInset, ReorderTile$$ExternalSyntheticOutline0.m(this.bottom, Integer.hashCode(this.top) * 31, 31), 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NotificationScrimClipParams(top=");
        sb.append(this.top);
        sb.append(", bottom=");
        sb.append(this.bottom);
        sb.append(", leftInset=");
        sb.append(this.leftInset);
        sb.append(", rightInset=");
        sb.append(this.rightInset);
        sb.append(", radius=");
        return ReorderTile$$ExternalSyntheticOutline0.m(this.radius, ")", sb);
    }

    public NotificationScrimClipParams(int i, int i2, int i3, int i4, int i5) {
        this.top = i;
        this.bottom = i2;
        this.leftInset = i3;
        this.rightInset = i4;
        this.radius = i5;
    }

    public /* synthetic */ NotificationScrimClipParams(int i, int i2, int i3, int i4, int i5, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this((i6 & 1) != 0 ? 0 : i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? 0 : i5);
    }
}
