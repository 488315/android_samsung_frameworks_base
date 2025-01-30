package com.android.settingslib.widget;

import android.content.Context;
import android.graphics.drawable.AnimatedRotateDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AnimatedImageView extends ImageView {
    public AnimatedRotateDrawable mDrawable;

    public AnimatedImageView(Context context) {
        super(context);
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    @Override // android.view.View
    public final void onVisibilityChanged(View view, int i) {
        super.onVisibilityChanged(view, i);
        if (this.mDrawable != null) {
            getVisibility();
            this.mDrawable.stop();
        }
    }

    @Override // android.widget.ImageView
    public final void setImageDrawable(Drawable drawable) {
        super.setImageDrawable(drawable);
        updateDrawable();
    }

    @Override // android.widget.ImageView
    public final void setImageResource(int i) {
        super.setImageResource(i);
        updateDrawable();
    }

    public final void updateDrawable() {
        AnimatedRotateDrawable animatedRotateDrawable;
        if (isShown() && (animatedRotateDrawable = this.mDrawable) != null) {
            animatedRotateDrawable.stop();
        }
        AnimatedRotateDrawable drawable = getDrawable();
        if (!(drawable instanceof AnimatedRotateDrawable)) {
            this.mDrawable = null;
            return;
        }
        AnimatedRotateDrawable animatedRotateDrawable2 = drawable;
        this.mDrawable = animatedRotateDrawable2;
        animatedRotateDrawable2.setFramesCount(56);
        this.mDrawable.setFramesDuration(32);
        isShown();
    }

    public AnimatedImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }
}
