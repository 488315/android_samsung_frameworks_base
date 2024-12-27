package com.android.systemui.statusbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.systemui.res.R$styleable;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
@RemoteViews.RemoteView
/* loaded from: classes2.dex */
public class AnimatedImageView extends ImageView {
    public boolean mAllowAnimation;
    public AnimationDrawable mAnim;
    public boolean mAttached;
    public int mDrawableId;
    public final boolean mHasOverlappingRendering;

    public AnimatedImageView(Context context) {
        this(context, null);
    }

    @Override // android.widget.ImageView, android.view.View
    public boolean hasOverlappingRendering() {
        return this.mHasOverlappingRendering;
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttached = true;
        updateAnim();
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        AnimationDrawable animationDrawable = this.mAnim;
        if (animationDrawable != null) {
            animationDrawable.stop();
        }
        this.mAttached = false;
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mAnim != null) {
            if (isShown() && this.mAllowAnimation) {
                this.mAnim.start();
            } else {
                this.mAnim.stop();
            }
        }
    }

    @Override // android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        if (drawable == null) {
            this.mDrawableId = 0;
        } else if (this.mDrawableId == drawable.hashCode()) {
            return;
        } else {
            this.mDrawableId = drawable.hashCode();
        }
        super.setImageDrawable(drawable);
        updateAnim();
    }

    @Override // android.widget.ImageView
    public final void setImageResource(int i) {
        if (this.mDrawableId == i) {
            return;
        }
        this.mDrawableId = i;
        super.setImageResource(i);
        updateAnim();
    }

    public final void updateAnim() {
        AnimationDrawable animationDrawable;
        Drawable drawable = getDrawable();
        if (this.mAttached && (animationDrawable = this.mAnim) != null) {
            animationDrawable.stop();
        }
        if (!(drawable instanceof AnimationDrawable)) {
            this.mAnim = null;
            return;
        }
        this.mAnim = (AnimationDrawable) drawable;
        if (isShown() && this.mAllowAnimation) {
            this.mAnim.start();
        }
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mAllowAnimation = true;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.AnimatedImageView, 0, 0);
        try {
            this.mHasOverlappingRendering = obtainStyledAttributes.getBoolean(0, true);
        } finally {
            obtainStyledAttributes.recycle();
        }
    }
}
