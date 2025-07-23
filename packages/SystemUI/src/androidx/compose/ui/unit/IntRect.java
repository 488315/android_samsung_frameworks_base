package androidx.compose.ui.unit;

import androidx.activity.BackEventCompat$$ExternalSyntheticOutline0;
import androidx.compose.ui.unit.IntOffset;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class IntRect {
    public static final Companion Companion = new Companion(null);
    public static final IntRect Zero = new IntRect(0, 0, 0, 0);
    public final int bottom;
    public final int left;
    public final int right;
    public final int top;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public IntRect(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
    }

    /* renamed from: contains--gyyYBs, reason: not valid java name */
    public final boolean m855containsgyyYBs(long j) {
        int i;
        IntOffset.Companion companion = IntOffset.Companion;
        int i2 = (int) (j >> 32);
        return i2 >= this.left && i2 < this.right && (i = (int) (j & 4294967295L)) >= this.top && i < this.bottom;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof IntRect)) {
            return false;
        }
        IntRect intRect = (IntRect) obj;
        return this.left == intRect.left && this.top == intRect.top && this.right == intRect.right && this.bottom == intRect.bottom;
    }

    /* renamed from: getCenter-nOcc-ac, reason: not valid java name */
    public final long m856getCenternOccac() {
        long height = (((getHeight() / 2) + this.top) & 4294967295L) | (((getWidth() / 2) + this.left) << 32);
        IntOffset.Companion companion = IntOffset.Companion;
        return height;
    }

    public final int getHeight() {
        return this.bottom - this.top;
    }

    /* renamed from: getTopLeft-nOcc-ac, reason: not valid java name */
    public final long m857getTopLeftnOccac() {
        long j = (this.left << 32) | (this.top & 4294967295L);
        IntOffset.Companion companion = IntOffset.Companion;
        return j;
    }

    public final int getWidth() {
        return this.right - this.left;
    }

    public final int hashCode() {
        return Integer.hashCode(this.bottom) + ReorderTile$$ExternalSyntheticOutline0.m(this.right, ReorderTile$$ExternalSyntheticOutline0.m(this.top, Integer.hashCode(this.left) * 31, 31), 31);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("IntRect.fromLTRB(");
        sb.append(this.left);
        sb.append(", ");
        sb.append(this.top);
        sb.append(", ");
        sb.append(this.right);
        sb.append(", ");
        return BackEventCompat$$ExternalSyntheticOutline0.m(sb, this.bottom, ')');
    }
}
