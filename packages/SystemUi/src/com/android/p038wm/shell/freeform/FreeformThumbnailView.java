package com.android.p038wm.shell.freeform;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.android.systemui.R;
import com.samsung.android.util.InterpolatorUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class FreeformThumbnailView extends FrameLayout {
    public int mAirViewMargin;
    public Bitmap mBitmap;
    public final Context mContext;
    public final Point mDisplaySize;
    public int mHeight;
    public ImageView mImageView;
    public int mMargin;
    public int mMaxSize;
    public int mOrientation;
    public final Point mPivot;
    public final Rect mStableInsets;
    public FrameLayout mView;
    public int mWidth;

    public FreeformThumbnailView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStableInsets = new Rect();
        this.mDisplaySize = new Point();
        this.mPivot = new Point();
        this.mContext = context;
    }

    public final void computeInset() {
        FreeformContainerManager freeformContainerManager = FreeformContainerManager.getInstance(this.mContext);
        Rect rect = this.mStableInsets;
        freeformContainerManager.getClass();
        FreeformContainerManager.getStableInsets(rect);
        if (this.mOrientation != 1) {
            Rect rect2 = this.mStableInsets;
            int i = rect2.bottom;
            int i2 = this.mAirViewMargin;
            if (i < i2) {
                rect2.bottom = i2;
                return;
            }
            return;
        }
        Rect rect3 = this.mStableInsets;
        int i3 = rect3.left;
        int i4 = this.mAirViewMargin;
        if (i3 < i4) {
            rect3.left = i4;
        }
        if (rect3.right < i4) {
            rect3.right = i4;
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int i = configuration.orientation;
        if (i != this.mOrientation) {
            this.mOrientation = i;
            getDisplay().getRealSize(this.mDisplaySize);
            computeInset();
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mImageView = (ImageView) findViewById(R.id.freeform_thumbnail_img);
        this.mView = (FrameLayout) findViewById(R.id.freeform_thumbnail);
        this.mMaxSize = (int) getResources().getDimension(R.dimen.freeform_thumbnail_max_size);
        this.mMargin = (int) getResources().getDimension(R.dimen.freeform_thumbnail_margin);
        this.mAirViewMargin = (int) getResources().getDimension(R.dimen.freeform_thumbnail_air_view_margin);
        this.mContext.getDisplay().getRealSize(this.mDisplaySize);
        this.mOrientation = getResources().getConfiguration().orientation;
        computeInset();
    }

    public final void scheduleAnimation(final boolean z) {
        float f;
        float f2;
        float f3 = 0.0f;
        float f4 = 1.0f;
        if (z) {
            f2 = 0.8f;
            f = 1.0f;
        } else {
            f = 0.8f;
            f2 = 1.0f;
            f4 = 0.0f;
            f3 = 1.0f;
        }
        AlphaAnimation alphaAnimation = new AlphaAnimation(f3, f4);
        alphaAnimation.setInterpolator(new LinearInterpolator());
        alphaAnimation.setDuration(200L);
        Point point = this.mPivot;
        ScaleAnimation scaleAnimation = new ScaleAnimation(f2, f, f2, f, point.x, point.y);
        scaleAnimation.setInterpolator(z ? InterpolatorUtils.ONE_EASING : InterpolatorUtils.SINE_IN_OUT_33);
        scaleAnimation.setDuration(z ? 350L : 200L);
        AnimationSet animationSet = new AnimationSet(false);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(scaleAnimation);
        animationSet.setAnimationListener(new Animation.AnimationListener() { // from class: com.android.wm.shell.freeform.FreeformThumbnailView.1
            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationEnd(Animation animation) {
                if (z) {
                    return;
                }
                FreeformThumbnailView.this.setVisibility(8);
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationStart(Animation animation) {
                if (z) {
                    FreeformThumbnailView.this.setVisibility(0);
                }
            }

            @Override // android.view.animation.Animation.AnimationListener
            public final void onAnimationRepeat(Animation animation) {
            }
        });
        if (z) {
            setVisibility(0);
        }
        this.mView.startAnimation(animationSet);
    }
}
