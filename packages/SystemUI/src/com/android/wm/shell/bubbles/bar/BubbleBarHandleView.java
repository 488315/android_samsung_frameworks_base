package com.android.wm.shell.bubbles.bar;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.core.animation.IntProperty;
import com.android.systemui.R;

/* loaded from: classes3.dex */
public class BubbleBarHandleView extends View {
    public static final AnonymousClass1 HANDLE_COLOR = new IntProperty("handleColor") { // from class: com.android.wm.shell.bubbles.bar.BubbleBarHandleView.1
        @Override // android.util.Property
        public final Object get(Object obj) {
            return Integer.valueOf(((BubbleBarHandleView) obj).mHandlePaint.getColor());
        }

        @Override // androidx.core.animation.IntProperty
        public final void setValue(Object obj, int i) {
            BubbleBarHandleView bubbleBarHandleView = (BubbleBarHandleView) obj;
            AnonymousClass1 anonymousClass1 = BubbleBarHandleView.HANDLE_COLOR;
            bubbleBarHandleView.mHandlePaint.setColor(i);
            bubbleBarHandleView.invalidate();
        }
    };
    public final ArgbEvaluator mArgbEvaluator;
    public ObjectAnimator mColorChangeAnim;
    public float mCurrentHandleHeight;
    public float mCurrentHandleWidth;
    public final int mHandleDarkColor;
    public final float mHandleHeight;
    public final int mHandleLightColor;
    final Paint mHandlePaint;
    public final float mHandleWidth;
    public int mRegionSamplerColor;

    public BubbleBarHandleView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float width = getWidth();
        float f = this.mCurrentHandleWidth;
        float f2 = (width - f) / 2.0f;
        float f3 = f2 + f;
        float f4 = this.mCurrentHandleHeight;
        float height = (int) ((getHeight() / 2.0f) - (f4 / 2.0f));
        float f5 = f4 / 2.0f;
        canvas.drawRoundRect(f2, height, f3, height + f4, f5, f5, this.mHandlePaint);
    }

    public BubbleBarHandleView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleBarHandleView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public BubbleBarHandleView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        Paint paint = new Paint();
        this.mHandlePaint = paint;
        this.mArgbEvaluator = ArgbEvaluator.getInstance();
        paint.setFlags(1);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0);
        float dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_handle_height);
        this.mHandleHeight = dimensionPixelSize;
        float dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.bubble_bar_expanded_view_caption_width);
        this.mHandleWidth = dimensionPixelSize2;
        this.mHandleLightColor = getContext().getColor(R.color.bubble_bar_expanded_view_handle_light);
        this.mHandleDarkColor = getContext().getColor(R.color.bubble_bar_expanded_view_handle_dark);
        this.mCurrentHandleHeight = dimensionPixelSize;
        this.mCurrentHandleWidth = dimensionPixelSize2;
        setContentDescription(getResources().getString(R.string.handle_text));
    }
}
