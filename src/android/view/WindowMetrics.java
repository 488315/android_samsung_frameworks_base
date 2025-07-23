package android.view;

import android.graphics.Rect;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public final class WindowMetrics {
    private final Rect mBounds;
    private final float mDensity;
    private WindowInsets mWindowInsets;
    private Supplier<WindowInsets> mWindowInsetsSupplier;

    @Deprecated
    public WindowMetrics(Rect rect, WindowInsets windowInsets) {
        this(rect, windowInsets, 1.0f);
    }

    public WindowMetrics(Rect rect, WindowInsets windowInsets, float f) {
        this.mBounds = rect;
        this.mWindowInsets = windowInsets;
        this.mDensity = f;
    }

    public WindowMetrics(Rect rect, Supplier<WindowInsets> supplier, float f) {
        this.mBounds = rect;
        this.mWindowInsetsSupplier = supplier;
        this.mDensity = f;
    }

    public Rect getBounds() {
        return this.mBounds;
    }

    public WindowInsets getWindowInsets() {
        WindowInsets windowInsets = this.mWindowInsets;
        if (windowInsets != null) {
            return windowInsets;
        }
        WindowInsets windowInsets2 = this.mWindowInsetsSupplier.get();
        this.mWindowInsets = windowInsets2;
        return windowInsets2;
    }

    public float getDensity() {
        return this.mDensity;
    }

    public String toString() {
        return "WindowMetrics:{bounds=" + this.mBounds + ", windowInsets=" + this.mWindowInsets + ", density=" + this.mDensity + "}";
    }
}
