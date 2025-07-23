package android.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.android.internal.R;
import com.android.internal.widget.ScrollBarUtils;

/* loaded from: classes5.dex */
public class ScrollBarDrawable extends Drawable implements Drawable.Callback {
    private boolean mAlwaysDrawHorizontalTrack;
    private boolean mAlwaysDrawVerticalTrack;
    private boolean mBoundsChanged;
    private ColorFilter mColorFilter;
    private int mExtent;
    private boolean mHasSetAlpha;
    private boolean mHasSetColorFilter;
    private Drawable mHorizontalThumb;
    private Drawable mHorizontalTrack;
    private boolean mMutated;
    private int mOffset;
    private int mRange;
    private boolean mRangeChanged;
    private View mSemParent;
    private boolean mVertical;
    private Drawable mVerticalThumb;
    private Drawable mVerticalTrack;
    private int mAlpha = 255;
    private final Rect mClickableThumbRect = new Rect();

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    public ScrollBarDrawable() {
    }

    public ScrollBarDrawable(View view) {
        this.mSemParent = view;
        view.getContext().obtainStyledAttributes(R.styleable.Theme).recycle();
    }

    public void setAlwaysDrawHorizontalTrack(boolean z) {
        this.mAlwaysDrawHorizontalTrack = z;
    }

    public void setAlwaysDrawVerticalTrack(boolean z) {
        this.mAlwaysDrawVerticalTrack = z;
    }

    public boolean getAlwaysDrawVerticalTrack() {
        return this.mAlwaysDrawVerticalTrack;
    }

    public boolean getAlwaysDrawHorizontalTrack() {
        return this.mAlwaysDrawHorizontalTrack;
    }

    public void setParameters(int i, int i2, int i3, boolean z) {
        if (this.mVertical != z) {
            this.mVertical = z;
            this.mBoundsChanged = true;
        }
        if (this.mRange == i && this.mOffset == i2 && this.mExtent == i3) {
            return;
        }
        this.mRange = i;
        this.mOffset = i2;
        this.mExtent = i3;
        this.mRangeChanged = true;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        boolean z2;
        boolean z3 = this.mVertical;
        int i = this.mExtent;
        int i2 = this.mRange;
        if (i <= 0 || i2 <= i) {
            z = z3 ? this.mAlwaysDrawVerticalTrack : this.mAlwaysDrawHorizontalTrack;
            z2 = false;
        } else {
            z = true;
            z2 = true;
        }
        Rect bounds = getBounds();
        if (canvas.quickReject(bounds.left, bounds.top, bounds.right, bounds.bottom)) {
            return;
        }
        if (z) {
            drawTrack(canvas, bounds, z3);
        }
        if (z2) {
            int height = z3 ? bounds.height() : bounds.width();
            int thumbLength = ScrollBarUtils.getThumbLength(height, z3 ? bounds.width() : bounds.height(), i, i2);
            drawThumb(canvas, bounds, ScrollBarUtils.getThumbOffset(height, thumbLength, i, i2, this.mOffset), thumbLength, z3);
        }
    }

    @Override // android.graphics.drawable.Drawable
    protected void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        this.mBoundsChanged = true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        Drawable drawable = this.mVerticalTrack;
        if (drawable != null && drawable.isStateful()) {
            return true;
        }
        Drawable drawable2 = this.mVerticalThumb;
        if (drawable2 != null && drawable2.isStateful()) {
            return true;
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null && drawable3.isStateful()) {
            return true;
        }
        Drawable drawable4 = this.mHorizontalThumb;
        return (drawable4 != null && drawable4.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    protected boolean onStateChange(int[] iArr) {
        boolean onStateChange = super.onStateChange(iArr);
        Drawable drawable = this.mVerticalTrack;
        if (drawable != null) {
            onStateChange |= drawable.setState(iArr);
        }
        Drawable drawable2 = this.mVerticalThumb;
        if (drawable2 != null) {
            onStateChange |= drawable2.setState(iArr);
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            onStateChange |= drawable3.setState(iArr);
        }
        Drawable drawable4 = this.mHorizontalThumb;
        return drawable4 != null ? drawable4.setState(iArr) | onStateChange : onStateChange;
    }

    private void drawTrack(Canvas canvas, Rect rect, boolean z) {
        Drawable drawable;
        if (z) {
            drawable = this.mVerticalTrack;
        } else {
            drawable = this.mHorizontalTrack;
        }
        if (drawable != null) {
            if (this.mBoundsChanged) {
                drawable.setBounds(rect);
            }
            drawable.draw(canvas);
        }
    }

    private void drawThumb(Canvas canvas, Rect rect, int i, int i2, boolean z) {
        Rect rect2 = this.mClickableThumbRect;
        boolean z2 = this.mRangeChanged || this.mBoundsChanged;
        if (z) {
            Drawable drawable = this.mVerticalThumb;
            if (drawable != null) {
                if (z2) {
                    drawable.setBounds(rect.left, rect.top + i, rect.right, rect.top + i + i2);
                    rect2.set(drawable.getBounds());
                    View view = this.mSemParent;
                    if (view != null) {
                        view.mSemVerticalScrollbarRect.set(rect2);
                    }
                }
                drawable.draw(canvas);
                return;
            }
            return;
        }
        Drawable drawable2 = this.mHorizontalThumb;
        if (drawable2 != null) {
            if (z2) {
                drawable2.setBounds(rect.left + i, rect.top, rect.left + i + i2, rect.bottom);
                rect2.set(drawable2.getBounds());
                View view2 = this.mSemParent;
                if (view2 != null) {
                    view2.mSemHorizontalScrollbarRect.set(rect2);
                }
            }
            drawable2.draw(canvas);
        }
    }

    public void setVerticalThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.mVerticalThumb;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mVerticalThumb = drawable;
    }

    public Drawable getVerticalTrackDrawable() {
        return this.mVerticalTrack;
    }

    public Drawable getVerticalThumbDrawable() {
        return this.mVerticalThumb;
    }

    public Drawable getHorizontalTrackDrawable() {
        return this.mHorizontalTrack;
    }

    public Drawable getHorizontalThumbDrawable() {
        return this.mHorizontalThumb;
    }

    public void setVerticalTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.mVerticalTrack;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mVerticalTrack = drawable;
    }

    public void setHorizontalThumbDrawable(Drawable drawable) {
        Drawable drawable2 = this.mHorizontalThumb;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mHorizontalThumb = drawable;
    }

    public void setHorizontalTrackDrawable(Drawable drawable) {
        Drawable drawable2 = this.mHorizontalTrack;
        if (drawable2 != null) {
            drawable2.setCallback(null);
        }
        propagateCurrentState(drawable);
        this.mHorizontalTrack = drawable;
    }

    private void propagateCurrentState(Drawable drawable) {
        if (drawable != null) {
            if (this.mMutated) {
                drawable.mutate();
            }
            drawable.setState(getState());
            drawable.setCallback(this);
            if (this.mHasSetAlpha) {
                drawable.setAlpha(this.mAlpha);
            }
            if (this.mHasSetColorFilter) {
                drawable.setColorFilter(this.mColorFilter);
            }
        }
    }

    public int getSize(boolean z) {
        if (z) {
            Drawable drawable = this.mVerticalTrack;
            if (drawable != null) {
                return drawable.getIntrinsicWidth();
            }
            Drawable drawable2 = this.mVerticalThumb;
            if (drawable2 != null) {
                return drawable2.getIntrinsicWidth();
            }
            return 0;
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            return drawable3.getIntrinsicHeight();
        }
        Drawable drawable4 = this.mHorizontalThumb;
        if (drawable4 != null) {
            return drawable4.getIntrinsicHeight();
        }
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public ScrollBarDrawable mutate() {
        if (!this.mMutated && super.mutate() == this) {
            Drawable drawable = this.mVerticalTrack;
            if (drawable != null) {
                drawable.mutate();
            }
            Drawable drawable2 = this.mVerticalThumb;
            if (drawable2 != null) {
                drawable2.mutate();
            }
            Drawable drawable3 = this.mHorizontalTrack;
            if (drawable3 != null) {
                drawable3.mutate();
            }
            Drawable drawable4 = this.mHorizontalThumb;
            if (drawable4 != null) {
                drawable4.mutate();
            }
            this.mMutated = true;
        }
        return this;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mAlpha = i;
        this.mHasSetAlpha = true;
        Drawable drawable = this.mVerticalTrack;
        if (drawable != null) {
            drawable.setAlpha(i);
        }
        Drawable drawable2 = this.mVerticalThumb;
        if (drawable2 != null) {
            drawable2.setAlpha(i);
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            drawable3.setAlpha(i);
        }
        Drawable drawable4 = this.mHorizontalThumb;
        if (drawable4 != null) {
            drawable4.setAlpha(i);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mAlpha;
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mColorFilter = colorFilter;
        this.mHasSetColorFilter = true;
        Drawable drawable = this.mVerticalTrack;
        if (drawable != null) {
            drawable.setColorFilter(colorFilter);
        }
        Drawable drawable2 = this.mVerticalThumb;
        if (drawable2 != null) {
            drawable2.setColorFilter(colorFilter);
        }
        Drawable drawable3 = this.mHorizontalTrack;
        if (drawable3 != null) {
            drawable3.setColorFilter(colorFilter);
        }
        Drawable drawable4 = this.mHorizontalThumb;
        if (drawable4 != null) {
            drawable4.setColorFilter(colorFilter);
        }
    }

    @Override // android.graphics.drawable.Drawable
    public ColorFilter getColorFilter() {
        return this.mColorFilter;
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("ScrollBarDrawable: range=");
        sb.append(this.mRange);
        sb.append(" offset=");
        sb.append(this.mOffset);
        sb.append(" extent=");
        sb.append(this.mExtent);
        sb.append(this.mVertical ? " V" : " H");
        return sb.toString();
    }
}
