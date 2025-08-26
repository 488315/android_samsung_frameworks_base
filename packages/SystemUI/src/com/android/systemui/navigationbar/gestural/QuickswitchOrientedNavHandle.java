package com.android.systemui.navigationbar.gestural;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.BasicRune;
import com.android.systemui.R;

/* loaded from: classes2.dex */
public class QuickswitchOrientedNavHandle extends NavigationHandle {
    public int mDeltaRotation;
    public final ImageView mHandleView;
    public final Rect mHomeHandleRect;
    public final RectF mTmpBoundsRectF;
    public final int mWidth;

    public QuickswitchOrientedNavHandle(Context context) {
        super(context);
        this.mTmpBoundsRectF = new RectF();
        this.mHomeHandleRect = new Rect();
        new Rect();
        this.mWidth = context.getResources().getDimensionPixelSize(R.dimen.navigation_home_handle_width);
        if (BasicRune.NAVBAR_ENABLED) {
            ImageView imageView = new ImageView(context);
            this.mHandleView = imageView;
            addView(imageView);
        }
    }

    public final RectF computeHomeHandleBounds() {
        float width;
        float f;
        float width2;
        float height;
        float f2;
        float f3 = this.mRadius * 2.0f;
        int i = getLocationOnScreen()[1];
        int i2 = this.mDeltaRotation;
        if (i2 == 1) {
            float f4 = this.mBottom;
            width = f4 + f3;
            int i3 = this.mWidth;
            float height2 = ((getHeight() / 2.0f) - (i3 / 2.0f)) - (i / 2.0f);
            f = i3 + height2;
            width2 = f4;
            height = height2;
        } else {
            if (i2 != 3) {
                float f5 = this.mRadius * 2.0f;
                width2 = (getWidth() / 2.0f) - (this.mWidth / 2.0f);
                height = (getHeight() - this.mBottom) - f5;
                width = (this.mWidth / 2.0f) + (getWidth() / 2.0f);
                f2 = f5 + height;
                this.mTmpBoundsRectF.set(width2, height, width, f2);
                return this.mTmpBoundsRectF;
            }
            width = getWidth() - this.mBottom;
            int i4 = this.mWidth;
            height = ((getHeight() / 2.0f) - (i4 / 2.0f)) - (i / 2.0f);
            f = i4 + height;
            width2 = width - f3;
        }
        f2 = f;
        this.mTmpBoundsRectF.set(width2, height, width, f2);
        return this.mTmpBoundsRectF;
    }

    @Override // com.android.systemui.navigationbar.gestural.NavigationHandle, android.view.View
    public final void onDraw(Canvas canvas) {
        if (BasicRune.NAVBAR_GESTURE) {
            return;
        }
        RectF rectFComputeHomeHandleBounds = computeHomeHandleBounds();
        float f = this.mRadius;
        canvas.drawRoundRect(rectFComputeHomeHandleBounds, f, f, this.mPaint);
    }

    @Override // com.android.systemui.navigationbar.gestural.NavigationHandle, com.android.systemui.navigationbar.views.buttons.ButtonInterface
    public final void setImageDrawable(Drawable drawable) {
        if (BasicRune.NAVBAR_GESTURE) {
            this.mHintDrawable = (GestureHintDrawable) drawable;
            int dimension = (int) this.mContext.getResources().getDimension(R.dimen.samsung_hint_bottom_padding);
            int i = this.mDeltaRotation;
            if (i == 0 || i == 2) {
                this.mHintDrawable.setLayerGravity(0, 80);
                this.mHintDrawable.setLayerGravity(1, 80);
                this.mHintDrawable.setLayerInset(0, 0, dimension, 0, dimension);
                this.mHintDrawable.setLayerInset(1, 0, dimension, 0, dimension);
            } else if (i == 1) {
                this.mHintDrawable.setLayerGravity(0, 3);
                this.mHintDrawable.setLayerGravity(1, 3);
                this.mHintDrawable.setLayerInset(0, dimension, 0, 0, 0);
                this.mHintDrawable.setLayerInset(1, dimension, 0, 0, 0);
            } else if (i == 3) {
                this.mHintDrawable.setLayerGravity(0, 5);
                this.mHintDrawable.setLayerGravity(1, 5);
                this.mHintDrawable.setLayerInset(0, 0, 0, dimension, 0);
                this.mHintDrawable.setLayerInset(1, 0, 0, dimension, 0);
            }
            this.mHintDrawable.setDarkIntensity(this.mDarkIntensity);
            this.mHandleView.setBackground(this.mHintDrawable);
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mHandleView.getLayoutParams();
            layoutParams.height = this.mHomeHandleRect.width();
            layoutParams.width = this.mContext.getResources().getDimensionPixelSize(R.dimen.samsung_hint_view_height);
            layoutParams.gravity = 16;
            updateViewLayout(this.mHandleView, layoutParams);
        }
    }
}
