package com.android.systemui.charging;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.app.animation.Interpolators;
import com.android.settingslib.Utils;
import com.android.systemui.R;
import com.android.systemui.shared.recents.utilities.Utilities;
import com.android.systemui.surfaceeffects.ripple.RippleShader;
import com.android.systemui.surfaceeffects.ripple.RippleView;
import java.text.NumberFormat;
import java.util.Arrays;

public final class WirelessChargingLayout extends FrameLayout {
    public RippleView mRippleView;
    public RippleShader.SizeAtProgress[] mSizeAtProgressArray;

    public WirelessChargingLayout(Context context, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        super(context);
        init(context, i, i2, z, rippleShape);
    }

    public final void init(Context context, int i, int i2, boolean z, RippleShader.RippleShape rippleShape) {
        long j;
        boolean z2 = i != -1;
        FrameLayout.inflate(new ContextThemeWrapper(context, z ? R.style.ChargingAnim_DarkBackground : R.style.ChargingAnim_WallpaperBackground), R.layout.wireless_charging_layout, this);
        TextView textView = (TextView) findViewById(R.id.wireless_charging_percentage);
        if (i2 != -1) {
            textView.setText(NumberFormat.getPercentInstance().format(i2 / 100.0f));
            textView.setAlpha(0.0f);
        }
        long integer = context.getResources().getInteger(R.integer.wireless_charging_fade_offset);
        long integer2 = context.getResources().getInteger(R.integer.wireless_charging_fade_duration);
        float f = context.getResources().getFloat(R.dimen.wireless_charging_anim_battery_level_text_size_start);
        float f2 = context.getResources().getFloat(R.dimen.wireless_charging_anim_battery_level_text_size_end) * (z2 ? 0.75f : 1.0f);
        ObjectAnimator ofFloat = ObjectAnimator.ofFloat(textView, "textSize", f, f2);
        ofFloat.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        ObjectAnimator ofFloat2 = ObjectAnimator.ofFloat(textView, "alpha", 0.0f, 1.0f);
        Interpolator interpolator = Interpolators.LINEAR;
        ofFloat2.setInterpolator(interpolator);
        ofFloat2.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat2.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(textView, "alpha", 1.0f, 0.0f);
        ofFloat3.setDuration(integer2);
        ofFloat3.setInterpolator(interpolator);
        ofFloat3.setStartDelay(integer);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
        if (Utilities.isLargeScreen(context)) {
            j = integer;
        } else {
            ObjectAnimator ofArgb = ObjectAnimator.ofArgb(this, "backgroundColor", 0, 1275068416);
            j = integer;
            ofArgb.setDuration(300L);
            ofArgb.setInterpolator(interpolator);
            ObjectAnimator ofArgb2 = ObjectAnimator.ofArgb(this, "backgroundColor", 1275068416, 0);
            ofArgb2.setDuration(300L);
            ofArgb2.setInterpolator(interpolator);
            ofArgb2.setStartDelay((rippleShape == RippleShader.RippleShape.CIRCLE ? 1500L : 3000L) - 300);
            AnimatorSet animatorSet2 = new AnimatorSet();
            animatorSet2.playTogether(ofArgb, ofArgb2);
            animatorSet2.start();
        }
        RippleView rippleView = (RippleView) findViewById(R.id.wireless_charging_ripple);
        this.mRippleView = rippleView;
        rippleView.setupShader(rippleShape);
        int defaultColor = Utils.getColorAttr(android.R.attr.colorAccent, this.mRippleView.getContext()).getDefaultColor();
        RippleView rippleView2 = this.mRippleView;
        RippleShader.RippleShape rippleShape2 = rippleView2.rippleShape;
        if (rippleShape2 == null) {
            rippleShape2 = null;
        }
        if (rippleShape2 == RippleShader.RippleShape.ROUNDED_BOX) {
            rippleView2.duration = 3000L;
            RippleShader rippleShader = rippleView2.rippleShader;
            if (rippleShader == null) {
                rippleShader = null;
            }
            rippleShader.setFloatUniform("in_sparkle_strength", 0.22f);
            this.mRippleView.setColor(defaultColor, 102);
            RippleView rippleView3 = this.mRippleView;
            RippleShader rippleShader2 = rippleView3.rippleShader;
            RippleShader.FadeParams fadeParams = (rippleShader2 != null ? rippleShader2 : null).baseRingFadeParams;
            fadeParams.fadeInStart = 0.0f;
            fadeParams.fadeInEnd = 0.0f;
            fadeParams.fadeOutStart = 0.2f;
            fadeParams.fadeOutEnd = 0.47f;
            RippleShader.FadeParams fadeParams2 = (rippleShader2 != null ? rippleShader2 : null).sparkleRingFadeParams;
            fadeParams2.fadeInStart = 0.0f;
            fadeParams2.fadeInEnd = 0.0f;
            fadeParams2.fadeOutStart = 0.2f;
            fadeParams2.fadeOutEnd = 1.0f;
            RippleShader.FadeParams fadeParams3 = (rippleShader2 != null ? rippleShader2 : null).centerFillFadeParams;
            fadeParams3.fadeInStart = 0.0f;
            fadeParams3.fadeInEnd = 0.0f;
            fadeParams3.fadeOutStart = 0.0f;
            fadeParams3.fadeOutEnd = 0.2f;
            if (rippleShader2 == null) {
                rippleShader2 = null;
            }
            rippleShader2.getClass();
            RippleShader rippleShader3 = rippleView3.rippleShader;
            (rippleShader3 != null ? rippleShader3 : null).getClass();
        } else {
            rippleView2.duration = 1500L;
            rippleView2.setColor(defaultColor, 115);
        }
        this.mRippleView.addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() { // from class: com.android.systemui.charging.WirelessChargingLayout.1
            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewAttachedToWindow(View view) {
                WirelessChargingLayout.this.mRippleView.startRipple(null);
                WirelessChargingLayout.this.mRippleView.removeOnAttachStateChangeListener(this);
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public final void onViewDetachedFromWindow(View view) {
            }
        });
        if (!z2) {
            animatorSet.start();
            return;
        }
        TextView textView2 = (TextView) findViewById(R.id.reverse_wireless_charging_percentage);
        textView2.setVisibility(0);
        textView2.setText(NumberFormat.getPercentInstance().format(i / 100.0f));
        textView2.setAlpha(0.0f);
        ObjectAnimator ofFloat4 = ObjectAnimator.ofFloat(textView2, "textSize", f, f2);
        ofFloat4.setInterpolator(new PathInterpolator(0.0f, 0.0f, 0.0f, 1.0f));
        ofFloat4.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_scale_animation_duration));
        ObjectAnimator ofFloat5 = ObjectAnimator.ofFloat(textView2, "alpha", 0.0f, 1.0f);
        ofFloat5.setInterpolator(interpolator);
        ofFloat5.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat5.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat6 = ObjectAnimator.ofFloat(textView2, "alpha", 1.0f, 0.0f);
        ofFloat6.setDuration(integer2);
        ofFloat6.setInterpolator(interpolator);
        long j2 = j;
        ofFloat6.setStartDelay(j2);
        AnimatorSet animatorSet3 = new AnimatorSet();
        animatorSet3.playTogether(ofFloat4, ofFloat5, ofFloat6);
        ImageView imageView = (ImageView) findViewById(R.id.reverse_wireless_charging_icon);
        imageView.setVisibility(0);
        int round = Math.round(TypedValue.applyDimension(1, f2, getResources().getDisplayMetrics()));
        imageView.setPadding(round, 0, round, 0);
        ObjectAnimator ofFloat7 = ObjectAnimator.ofFloat(imageView, "alpha", 0.0f, 1.0f);
        ofFloat7.setInterpolator(interpolator);
        ofFloat7.setDuration(context.getResources().getInteger(R.integer.wireless_charging_battery_level_text_opacity_duration));
        ofFloat7.setStartDelay(context.getResources().getInteger(R.integer.wireless_charging_anim_opacity_offset));
        ObjectAnimator ofFloat8 = ObjectAnimator.ofFloat(imageView, "alpha", 1.0f, 0.0f);
        ofFloat8.setDuration(integer2);
        ofFloat8.setInterpolator(interpolator);
        ofFloat8.setStartDelay(j2);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.playTogether(ofFloat7, ofFloat8);
        animatorSet.start();
        animatorSet3.start();
        animatorSet4.start();
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.mRippleView != null) {
            int measuredWidth = getMeasuredWidth();
            int measuredHeight = getMeasuredHeight();
            float f = measuredWidth;
            float f2 = measuredHeight;
            this.mRippleView.setCenter(f * 0.5f, 0.5f * f2);
            RippleShader.RippleShape rippleShape = this.mRippleView.rippleShape;
            if (rippleShape == null) {
                rippleShape = null;
            }
            if (rippleShape == RippleShader.RippleShape.ROUNDED_BOX) {
                RippleShader.SizeAtProgress[] sizeAtProgressArr = this.mSizeAtProgressArray;
                if (sizeAtProgressArr == null) {
                    float max = Math.max(f, f2);
                    this.mSizeAtProgressArray = new RippleShader.SizeAtProgress[]{new RippleShader.SizeAtProgress(0.0f, 0.0f, 0.0f), new RippleShader.SizeAtProgress(0.3f, f * 0.4f, f2 * 0.4f), new RippleShader.SizeAtProgress(1.0f, max, max)};
                } else {
                    RippleShader.SizeAtProgress sizeAtProgress = sizeAtProgressArr[0];
                    sizeAtProgress.t = 0.0f;
                    sizeAtProgress.width = 0.0f;
                    sizeAtProgress.height = 0.0f;
                    RippleShader.SizeAtProgress sizeAtProgress2 = sizeAtProgressArr[1];
                    sizeAtProgress2.t = 0.3f;
                    sizeAtProgress2.width = f * 0.4f;
                    sizeAtProgress2.height = 0.4f * f2;
                    float max2 = Math.max(f, f2);
                    RippleShader.SizeAtProgress sizeAtProgress3 = this.mSizeAtProgressArray[2];
                    sizeAtProgress3.t = 1.0f;
                    sizeAtProgress3.width = max2;
                    sizeAtProgress3.height = max2;
                }
                RippleView rippleView = this.mRippleView;
                RippleShader.SizeAtProgress[] sizeAtProgressArr2 = this.mSizeAtProgressArray;
                RippleShader rippleShader = rippleView.rippleShader;
                (rippleShader != null ? rippleShader : null).rippleSize.setSizeAtProgresses((RippleShader.SizeAtProgress[]) Arrays.copyOf(sizeAtProgressArr2, sizeAtProgressArr2.length));
            } else {
                float max3 = Math.max(measuredWidth, measuredHeight);
                RippleShader rippleShader2 = this.mRippleView.rippleShader;
                RippleShader.RippleSize rippleSize = (rippleShader2 != null ? rippleShader2 : null).rippleSize;
                rippleSize.getClass();
                rippleSize.setSizeAtProgresses(rippleSize.initialSize, new RippleShader.SizeAtProgress(1.0f, max3, max3));
            }
        }
        super.onLayout(z, i, i2, i3, i4);
    }

    private WirelessChargingLayout(Context context) {
        super(context);
        init(context, -1, -1, false, RippleShader.RippleShape.CIRCLE);
    }

    private WirelessChargingLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context, -1, -1, false, RippleShader.RippleShape.CIRCLE);
    }
}
