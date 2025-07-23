package androidx.window.core;

import android.graphics.Rect;
import androidx.compose.runtime.external.kotlinx.collections.immutable.internal.ListImplementation$$ExternalSyntheticOutline0;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Bounds {
    public static final Companion Companion = new Companion(null);
    public static final Bounds EMPTY_BOUNDS = new Bounds(0, 0, 0, 0);
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

    public Bounds(int i, int i2, int i3, int i4) {
        this.left = i;
        this.top = i2;
        this.right = i3;
        this.bottom = i4;
        if (i > i3) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i, i3, "Left must be less than or equal to right, left: ", ", right: ").toString());
        }
        if (i2 > i4) {
            throw new IllegalArgumentException(ListImplementation$$ExternalSyntheticOutline0.m(i2, i4, "top must be less than or equal to bottom, top: ", ", bottom: ").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Bounds.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        Bounds bounds = (Bounds) obj;
        return this.left == bounds.left && this.top == bounds.top && this.right == bounds.right && this.bottom == bounds.bottom;
    }

    public final int getHeight() {
        return this.bottom - this.top;
    }

    public final int getWidth() {
        return this.right - this.left;
    }

    public final int hashCode() {
        return (((((this.left * 31) + this.top) * 31) + this.right) * 31) + this.bottom;
    }

    public final Rect toRect() {
        return new Rect(this.left, this.top, this.right, this.bottom);
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("Bounds { [");
        sb.append(this.left);
        sb.append(',');
        sb.append(this.top);
        sb.append(',');
        sb.append(this.right);
        sb.append(',');
        return ReorderTile$$ExternalSyntheticOutline0.m(this.bottom, "] }", sb);
    }

    public Bounds(Rect rect) {
        this(rect.left, rect.top, rect.right, rect.bottom);
    }
}
