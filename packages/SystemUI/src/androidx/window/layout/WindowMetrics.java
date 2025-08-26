package androidx.window.layout;

import android.graphics.Rect;
import androidx.core.view.WindowInsetsCompat;
import androidx.window.core.Bounds;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class WindowMetrics {
    public final Bounds _bounds;
    public final WindowInsetsCompat _windowInsetsCompat;
    public final float density;

    public WindowMetrics(Bounds bounds, WindowInsetsCompat windowInsetsCompat, float f) {
        this._bounds = bounds;
        this._windowInsetsCompat = windowInsetsCompat;
        this.density = f;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!WindowMetrics.class.equals(obj != null ? obj.getClass() : null)) {
            return false;
        }
        WindowMetrics windowMetrics = (WindowMetrics) obj;
        return Intrinsics.areEqual(this._bounds, windowMetrics._bounds) && Intrinsics.areEqual(this._windowInsetsCompat, windowMetrics._windowInsetsCompat) && this.density == windowMetrics.density;
    }

    public final int hashCode() {
        return this._windowInsetsCompat.hashCode() + (this._bounds.hashCode() * 31);
    }

    public final String toString() {
        return "WindowMetrics( bounds=" + this._bounds + ", windowInsetsCompat=" + this._windowInsetsCompat + ')';
    }

    public WindowMetrics(Rect rect, WindowInsetsCompat windowInsetsCompat, float f, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(rect, (i & 2) != 0 ? new WindowInsetsCompat.Builder().mImpl.build() : windowInsetsCompat, f);
    }

    public WindowMetrics(Rect rect, WindowInsetsCompat windowInsetsCompat, float f) {
        this(new Bounds(rect), windowInsetsCompat, f);
    }
}
