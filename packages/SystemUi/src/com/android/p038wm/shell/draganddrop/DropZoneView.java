package com.android.p038wm.shell.draganddrop;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.FloatProperty;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.internal.policy.ScreenDecorationsUtils;
import com.android.systemui.R;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DropZoneView extends FrameLayout {
    public final float[] mContainerMargin;
    public final float mCornerRadius;
    public final int mMarginColor;
    public float mMarginPercent;
    public final MarginView mMarginView;
    public final Path mPath;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MarginView extends View {
        public MarginView(Context context) {
            super(context);
        }

        @Override // android.view.View
        public final void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            DropZoneView.this.mPath.reset();
            DropZoneView dropZoneView = DropZoneView.this;
            Path path = dropZoneView.mPath;
            float[] fArr = dropZoneView.mContainerMargin;
            float f = fArr[0];
            float f2 = dropZoneView.mMarginPercent;
            float f3 = f * f2;
            float f4 = f2 * fArr[1];
            float width = getWidth();
            DropZoneView dropZoneView2 = DropZoneView.this;
            float f5 = width - (dropZoneView2.mContainerMargin[2] * dropZoneView2.mMarginPercent);
            float height = getHeight();
            DropZoneView dropZoneView3 = DropZoneView.this;
            float f6 = height - (dropZoneView3.mContainerMargin[3] * dropZoneView3.mMarginPercent);
            dropZoneView3.getClass();
            DropZoneView.this.getClass();
            float f7 = f6 - 0.0f;
            DropZoneView dropZoneView4 = DropZoneView.this;
            float f8 = dropZoneView4.mCornerRadius;
            float f9 = dropZoneView4.mMarginPercent;
            path.addRoundRect(f3, f4, f5, f7, f8 * f9, f8 * f9, Path.Direction.CW);
            DropZoneView.this.mPath.setFillType(Path.FillType.INVERSE_EVEN_ODD);
            canvas.clipPath(DropZoneView.this.mPath);
            canvas.drawColor(DropZoneView.this.mMarginColor);
        }
    }

    static {
        new FloatProperty("insets") { // from class: com.android.wm.shell.draganddrop.DropZoneView.1
            @Override // android.util.Property
            public final Float get(Object obj) {
                return Float.valueOf(((DropZoneView) obj).mMarginPercent);
            }

            @Override // android.util.FloatProperty
            public final void setValue(Object obj, float f) {
                DropZoneView dropZoneView = (DropZoneView) obj;
                if (f != dropZoneView.mMarginPercent) {
                    dropZoneView.mMarginPercent = f;
                    dropZoneView.mMarginView.invalidate();
                }
            }
        };
    }

    public DropZoneView(Context context) {
        this(context, null);
    }

    public DropZoneView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DropZoneView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DropZoneView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mPath = new Path();
        float[] fArr = new float[4];
        this.mContainerMargin = fArr;
        fArr[0] = 0.0f;
        if (this.mMarginPercent > 0.0f) {
            this.mMarginView.invalidate();
        }
        this.mCornerRadius = ScreenDecorationsUtils.getWindowCornerRadius(context);
        this.mMarginColor = getResources().getColor(R.color.taskbar_background_dark);
        int color = getResources().getColor(android.R.color.system_accent1_500);
        Color.argb(1.0f, Color.red(color), Color.green(color), Color.blue(color));
        Color.argb(0.9f, 0.0f, 0.0f, 0.0f);
        setBackgroundDrawable(new ColorDrawable());
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.split_icon_size);
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        addView(imageView, new FrameLayout.LayoutParams(dimensionPixelSize, dimensionPixelSize, 17));
        imageView.setAlpha(0.0f);
        MarginView marginView = new MarginView(context);
        this.mMarginView = marginView;
        addView(marginView, new FrameLayout.LayoutParams(-1, -1));
    }
}
