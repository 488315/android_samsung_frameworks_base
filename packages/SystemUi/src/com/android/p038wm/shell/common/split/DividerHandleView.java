package com.android.p038wm.shell.common.split;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import com.android.p038wm.shell.C3767R;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class DividerHandleView extends View {
    public final int mCircleDiameter;
    public final int mCircleMargin;
    public int mCurrentHeight;
    public int mCurrentWidth;
    public final int mHandleType;
    public final int mHeight;
    public final int mHorizontalHandlerTopMargin;
    public final boolean mIsHorizontalDivision;
    public final Paint mPaint;
    public final int mWidth;

    static {
        Class<Integer> cls = Integer.class;
        new Property(cls, "width") { // from class: com.android.wm.shell.common.split.DividerHandleView.1
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Integer.valueOf(((DividerHandleView) obj).mCurrentWidth);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerHandleView dividerHandleView = (DividerHandleView) obj;
                dividerHandleView.mCurrentWidth = ((Integer) obj2).intValue();
                dividerHandleView.invalidate();
            }
        };
        new Property(cls, "height") { // from class: com.android.wm.shell.common.split.DividerHandleView.2
            @Override // android.util.Property
            public final Object get(Object obj) {
                return Integer.valueOf(((DividerHandleView) obj).mCurrentHeight);
            }

            @Override // android.util.Property
            public final void set(Object obj, Object obj2) {
                DividerHandleView dividerHandleView = (DividerHandleView) obj;
                dividerHandleView.mCurrentHeight = ((Integer) obj2).intValue();
                dividerHandleView.invalidate();
            }
        };
    }

    public DividerHandleView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        Paint paint = new Paint();
        this.mPaint = paint;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, C3767R.styleable.DividerHandleView, 0, 0);
        try {
            int i = obtainStyledAttributes.getInt(0, 0);
            this.mHandleType = i;
            boolean z = true;
            if (i != 0) {
                z = obtainStyledAttributes.getBoolean(1, true);
            } else if (getResources().getConfiguration().orientation != 1) {
                z = false;
            }
            this.mIsHorizontalDivision = z;
            obtainStyledAttributes.recycle();
            if (!CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER || i != 2) {
                this.mWidth = getResources().getDimensionPixelSize(R.dimen.split_divider_handle_width);
                this.mHeight = getResources().getDimensionPixelSize(R.dimen.split_divider_handle_height);
            } else if (z) {
                this.mWidth = getResources().getDimensionPixelSize(R.dimen.cell_divider_handle_width);
                this.mHeight = getResources().getDimensionPixelSize(R.dimen.cell_divider_handle_height);
            } else {
                this.mWidth = getResources().getDimensionPixelSize(R.dimen.cell_divider_handle_height);
                this.mHeight = getResources().getDimensionPixelSize(R.dimen.cell_divider_handle_width);
            }
            paint.setColor(getResources().getColor(R.color.split_divider_handle_circle));
            this.mCircleMargin = getResources().getDimensionPixelSize(R.dimen.split_divider_handle_circle_margin);
            this.mCircleDiameter = Math.min(this.mWidth, this.mHeight);
            if (CoreRune.MW_MULTI_SPLIT_DIVIDER) {
                this.mHorizontalHandlerTopMargin = z ? getResources().getDimensionPixelSize(R.dimen.multi_split_docked_divider_horizontal_handler_margin_top) : 0;
            }
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    @Override // android.view.View
    public final boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.View
    public final void onDraw(Canvas canvas) {
        int width = getWidth();
        float f = (width - r1) / 2.0f;
        float f2 = this.mCircleDiameter + f;
        int height = getHeight();
        int i = this.mCircleDiameter;
        float f3 = this.mHorizontalHandlerTopMargin + ((height - i) / 2.0f);
        int i2 = this.mHandleType;
        if (i2 == 0 || i2 == 1) {
            float f4 = i;
            float f5 = f4 + f3;
            if (this.mIsHorizontalDivision) {
                float f6 = f - this.mCircleMargin;
                canvas.drawOval(f6 - f4, f3, f6, f5, this.mPaint);
                canvas.drawOval(f, f3, f2, f5, this.mPaint);
                float f7 = f2 + this.mCircleMargin;
                canvas.drawOval(f7, f3, f7 + this.mCircleDiameter, f5, this.mPaint);
                return;
            }
            float f8 = f3 - this.mCircleMargin;
            canvas.drawOval(f, f8 - f4, f2, f8, this.mPaint);
            canvas.drawOval(f, f3, f2, f5, this.mPaint);
            float f9 = f5 + this.mCircleMargin;
            canvas.drawOval(f, f9, f2, f9 + this.mCircleDiameter, this.mPaint);
            return;
        }
        if (CoreRune.MW_MULTI_SPLIT_CELL_DIVIDER && i2 == 2) {
            float width2 = getWidth() / 2;
            float height2 = getHeight() / 2;
            if (!this.mIsHorizontalDivision) {
                float f10 = height2 - (this.mCircleMargin / 2);
                canvas.drawOval(f, f10 - this.mCircleDiameter, f2, f10, this.mPaint);
                float f11 = height2 + (this.mCircleMargin / 2);
                canvas.drawOval(f, f11, f2, f11 + this.mCircleDiameter, this.mPaint);
                return;
            }
            float f12 = width2 - (this.mCircleMargin / 2);
            float f13 = this.mCircleDiameter;
            canvas.drawOval(f12 - f13, f3, f12, f3 + f13, this.mPaint);
            float f14 = width2 + (this.mCircleMargin / 2);
            float f15 = this.mCircleDiameter;
            canvas.drawOval(f14, f3, f14 + f15, f3 + f15, this.mPaint);
        }
    }
}
