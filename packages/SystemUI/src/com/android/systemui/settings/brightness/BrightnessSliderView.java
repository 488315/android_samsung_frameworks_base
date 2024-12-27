package com.android.systemui.settings.brightness;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewParent;
import android.widget.FrameLayout;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import java.util.Collections;
import java.util.function.Supplier;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public class BrightnessSliderView extends FrameLayout {
    public BrightnessSliderController$$ExternalSyntheticLambda0 mListener;
    public Gefingerpoken mOnInterceptListener;
    public Drawable mProgressDrawable;
    public float mScale;
    public final SecBrightnessSliderView mSecBrightnessSliderView;
    public ToggleSeekBar mSlider;
    public final Rect mSystemGestureExclusionRect;

    public BrightnessSliderView(Context context) {
        this(context, null);
    }

    public final void applySliderScale() {
        Drawable drawable = this.mProgressDrawable;
        if (drawable != null) {
            Rect bounds = drawable.getBounds();
            int intrinsicHeight = (int) (this.mProgressDrawable.getIntrinsicHeight() * this.mScale);
            int intrinsicHeight2 = (this.mProgressDrawable.getIntrinsicHeight() - intrinsicHeight) / 2;
            this.mProgressDrawable.setBounds(bounds.left, intrinsicHeight2, bounds.right, intrinsicHeight + intrinsicHeight2);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final boolean dispatchTouchEvent(MotionEvent motionEvent) {
        BrightnessSliderController$$ExternalSyntheticLambda0 brightnessSliderController$$ExternalSyntheticLambda0 = this.mListener;
        if (brightnessSliderController$$ExternalSyntheticLambda0 != null) {
            brightnessSliderController$$ExternalSyntheticLambda0.f$0.mirrorTouchEvent(motionEvent);
        }
        return super.dispatchTouchEvent(motionEvent);
    }

    public float getSliderScaleY() {
        return this.mScale;
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ToggleSeekBar toggleSeekBar = (ToggleSeekBar) requireViewById(R.id.slider);
        this.mSlider = toggleSeekBar;
        toggleSeekBar.mAccessibilityLabel = getContentDescription().toString();
        try {
            this.mProgressDrawable = ((LayerDrawable) ((DrawableWrapper) ((LayerDrawable) this.mSlider.getProgressDrawable()).findDrawableByLayerId(android.R.id.progress)).getDrawable()).findDrawableByLayerId(R.id.slider_foreground);
        } catch (Exception unused) {
        }
        SecBrightnessSliderView secBrightnessSliderView = this.mSecBrightnessSliderView;
        if (secBrightnessSliderView != null) {
            secBrightnessSliderView.updateSliderResources();
        }
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        Gefingerpoken gefingerpoken = this.mOnInterceptListener;
        return gefingerpoken != null ? gefingerpoken.onInterceptTouchEvent(motionEvent) : super.onInterceptTouchEvent(motionEvent);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        applySliderScale();
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.notification_side_paddings);
        this.mSystemGestureExclusionRect.set(-dimensionPixelSize, 0, (i3 - i) + dimensionPixelSize, i4 - i2);
        setSystemGestureExclusionRects(Collections.singletonList(this.mSystemGestureExclusionRect));
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public final void requestDisallowInterceptTouchEvent(boolean z) {
        ViewParent viewParent = ((FrameLayout) this).mParent;
        if (viewParent != null) {
            viewParent.requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setSliderScaleY(float f) {
        if (f != this.mScale) {
            this.mScale = f;
            applySliderScale();
        }
    }

    public BrightnessSliderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mScale = 1.0f;
        this.mSystemGestureExclusionRect = new Rect();
        this.mSecBrightnessSliderView = new SecBrightnessSliderView(new Supplier() { // from class: com.android.systemui.settings.brightness.BrightnessSliderView$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return BrightnessSliderView.this.mSlider;
            }
        });
    }
}
