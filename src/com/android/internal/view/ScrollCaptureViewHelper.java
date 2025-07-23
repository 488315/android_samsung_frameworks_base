package com.android.internal.view;

import android.graphics.Rect;
import android.os.CancellationSignal;
import android.view.View;
import android.view.ViewGroup;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
public interface ScrollCaptureViewHelper<V extends View> {
    public static final int DOWN = 1;
    public static final int UP = -1;

    boolean onAcceptSession(V v);

    void onPrepareForEnd(V v);

    void onPrepareForStart(V v, Rect rect);

    void onScrollRequested(V v, Rect rect, Rect rect2, CancellationSignal cancellationSignal, Consumer<ScrollResult> consumer);

    public static class ScrollResult {
        public Rect availableArea;
        public Rect requestedArea;
        public int scrollDelta;

        public String toString() {
            return "ScrollResult{requestedArea=" + this.requestedArea + ", availableArea=" + this.availableArea + ", scrollDelta=" + this.scrollDelta + '}';
        }
    }

    default Rect onComputeScrollBounds(V v) {
        Rect rect = new Rect(0, 0, v.getWidth(), v.getHeight());
        if ((v instanceof ViewGroup) && ((ViewGroup) v).getClipToPadding()) {
            rect.inset(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
        }
        return rect;
    }
}
