package androidx.compose.ui.platform;

import android.content.Context;
import android.graphics.Canvas;
import android.view.ViewGroup;
import androidx.compose.ui.graphics.AndroidCanvas;
import androidx.compose.ui.graphics.AndroidCanvas_androidKt;
import com.android.systemui.R;

/* loaded from: classes.dex */
public class DrawChildContainer extends ViewGroup {
    public boolean isDrawing;

    public DrawChildContainer(Context context) {
        super(context);
        setClipChildren(false);
        setTag(R.id.hide_in_inspector_tag, Boolean.TRUE);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchDraw(Canvas canvas) {
        int childCount = super.getChildCount();
        for (int i = 0; i < childCount; i++) {
            if (((ViewLayer) getChildAt(i)).isInvalidated) {
                this.isDrawing = true;
                try {
                    super.dispatchDraw(canvas);
                    return;
                } finally {
                    this.isDrawing = false;
                }
            }
        }
    }

    public final void drawChild$ui_release(androidx.compose.ui.graphics.Canvas canvas, ViewLayer viewLayer, long j) {
        Canvas canvas2 = AndroidCanvas_androidKt.EmptyCanvas;
        super.drawChild(((AndroidCanvas) canvas).internalCanvas, viewLayer, j);
    }

    @Override // android.view.ViewGroup
    public final int getChildCount() {
        if (this.isDrawing) {
            return super.getChildCount();
        }
        return 0;
    }

    @Override // android.view.View
    public final void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View, android.view.ViewParent
    public final void requestLayout() {
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
    }
}
