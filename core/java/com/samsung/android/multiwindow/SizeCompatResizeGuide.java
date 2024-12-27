package com.samsung.android.multiwindow;

import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;

import com.samsung.android.core.SizeCompatInfo;

import java.util.function.Consumer;

public abstract class SizeCompatResizeGuide extends FreeformResizeGuide {
    public static final int CTRL_TYPE_LEFT = 1;
    public static final int CTRL_TYPE_TOP = 4;
    public static final int CTRL_TYPE_UNDEFINED = 0;

    public abstract void adjustBounds(
            SizeCompatInfo sizeCompatInfo,
            int i,
            Rect rect,
            Rect rect2,
            boolean z,
            Consumer<Rect> consumer);

    public abstract void cancelAnimation(Rect rect, Rect rect2, Consumer<Rect> consumer);

    public SizeCompatResizeGuide(Context context, ComponentName componentName) {
        super(context, 0, componentName);
    }

    @Override // com.samsung.android.multiwindow.FreeformResizeGuide
    public SizeCompatResizeGuide asSizeCompatResizeGuide() {
        return this;
    }

    @Override // com.samsung.android.multiwindow.FreeformResizeGuide
    public void adjustMinMaxSize(Rect inOutBounds) {}

    @Override // com.samsung.android.multiwindow.FreeformResizeGuide
    public void handleResizeGesture(Rect bounds, int x, int y) {}
}
