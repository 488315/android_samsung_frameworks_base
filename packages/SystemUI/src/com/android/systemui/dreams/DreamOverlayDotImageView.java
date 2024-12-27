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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.DreamOverlayDotImageView, 0, 0);
        try {
            this.mDotColor = obtainStyledAttributes.getColor(0, -1);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class DotDrawable extends Drawable {
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
                int width = this.mBounds.width();
                int height = this.mBounds.height();
                Bitmap createBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                Canvas canvas2 = new Canvas(createBitmap);
                Paint paint = new Paint(1);
                paint.setColor(this.mDotColor);
                canvas2.drawCircle(width / 2.0f, height / 2.0f, Math.min(width, height) / 2.0f, paint);
                this.mDotBitmap = createBitmap;
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
