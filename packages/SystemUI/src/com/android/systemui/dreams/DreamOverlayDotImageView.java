package com.android.systemui.dreams;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import com.android.systemui.res.R$styleable;
import com.android.systemui.statusbar.AlphaOptimizedImageView;

/* loaded from: classes2.dex */
public class DreamOverlayDotImageView extends AlphaOptimizedImageView {
    public final int mDotColor;

    public DreamOverlayDotImageView(Context context) {
        this(context, null);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        setImageDrawable(new DotDrawable(this.mDotColor));
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public DreamOverlayDotImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray typedArrayObtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DreamOverlayDotImageView, 0, 0);
        try {
            this.mDotColor = typedArrayObtainStyledAttributes.getColor(0, -1);
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public class DotDrawable extends Drawable {
        public Bitmap mDotBitmap;
        public final int mDotColor;
        public final Paint mPaint = new Paint(1);
        public final Rect mBounds = new Rect();

        public DotDrawable(int i) {
            this.mDotColor = i;
        }

        @Override // android.graphics.drawable.Drawable
        public final void draw(Canvas canvas) {
            if (this.mBounds.isEmpty()) {
                return;
            }
            if (this.mDotBitmap == null) {
                int iWidth = this.mBounds.width();
                int iHeight = this.mBounds.height();
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iWidth, iHeight, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                Paint paint = new Paint(1);
                paint.setColor(this.mDotColor);
                canvas2.drawCircle(iWidth / 2.0f, iHeight / 2.0f, Math.min(iWidth, iHeight) / 2.0f, paint);
                this.mDotBitmap = bitmapCreateBitmap;
            }
            canvas.drawBitmap(this.mDotBitmap, (Rect) null, this.mBounds, this.mPaint);
        }

        @Override // android.graphics.drawable.Drawable
        public final int getOpacity() {
            return 0;
        }

        @Override // android.graphics.drawable.Drawable
        public final void onBoundsChange(Rect rect) {
            super.onBoundsChange(rect);
            this.mBounds.set(rect.left, rect.top, rect.right, rect.bottom);
            this.mDotBitmap = null;
        }

        @Override // android.graphics.drawable.Drawable
        public final void setAlpha(int i) {
        }

        @Override // android.graphics.drawable.Drawable
        public final void setColorFilter(ColorFilter colorFilter) {
        }
    }
}
