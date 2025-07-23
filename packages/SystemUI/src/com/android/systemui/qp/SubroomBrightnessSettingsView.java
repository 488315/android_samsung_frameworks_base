package com.android.systemui.qp;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.keyguard.KeyguardSecSimPinViewController$$ExternalSyntheticOutline0;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.settings.brightness.BrightnessAnimationIcon;
import com.android.systemui.util.SettingsHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class SubroomBrightnessSettingsView extends LinearLayout {
    public final int SEEK_BAR_MAX_VALUE;
    public int mBrightness;
    public final int[] mBrightnessLevels;
    public final Context mContext;
    public int mDualSeekBarThreshold;
    public boolean mIsSliderWarning;
    public ImageView mMoreIcon;
    public SubScreenBrightnessToggleSeekBar mSeekBar;
    public BrightnessAnimationIcon mSunIcon;
    public ValueAnimator mThumbAnimator;
    public int mThumbThreshold;

    public SubroomBrightnessSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        int[] intArray = context.getResources().getIntArray(17236337);
        this.mBrightnessLevels = intArray;
        this.SEEK_BAR_MAX_VALUE = intArray[intArray.length - 1];
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        BrightnessAnimationIcon brightnessAnimationIcon;
        super.onAttachedToWindow();
        this.mBrightness = Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS, 73, -2);
        RecyclerView$$ExternalSyntheticOutline0.m(this.mBrightness, "SubroomBrightnessSettingsView", new StringBuilder("onAttachedToWindow() mBrightness: "));
        this.mSeekBar.setProgress(this.mBrightness);
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (brightnessAnimationIcon = this.mSunIcon) == null) {
            return;
        }
        brightnessAnimationIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        try {
            if (QpRune.QUICK_SUBSCREEN_PANEL) {
                ImageView imageView = this.mMoreIcon;
                if (imageView != null) {
                    imageView.setContentDescription(this.mContext.getString(R.string.sec_brightness_detail_content_description));
                    this.mMoreIcon.setColorFilter(this.mContext.getColor(R.color.subroom_qp_seekbar_icon_color), PorterDuff.Mode.SRC_IN);
                }
                BrightnessAnimationIcon brightnessAnimationIcon = this.mSunIcon;
                if (brightnessAnimationIcon != null) {
                    brightnessAnimationIcon.init(this.mContext);
                }
            }
            SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSeekBar;
            if (subScreenBrightnessToggleSeekBar != null) {
                subScreenBrightnessToggleSeekBar.setContentDescription(this.mContext.getString(R.string.subscreen_brightness_button_talkback_label));
                this.mSeekBar.setProgressDrawable(this.mContext.getDrawable(this.mIsSliderWarning ? R.drawable.sec_brightness_progress_warning_drawable : R.drawable.subroom_seekbar_background));
                if (this.mSeekBar.getThumb() != null) {
                    this.mSeekBar.getThumb().setColorFilter(this.mContext.getColor(R.color.tw_progress_color_thumb), PorterDuff.Mode.SRC_IN);
                }
            }
        } catch (Exception e) {
            KeyguardSecSimPinViewController$$ExternalSyntheticOutline0.m("Exception: ", e, "SubroomBrightnessSettingsView");
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        BrightnessAnimationIcon brightnessAnimationIcon;
        super.onFinishInflate();
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = (SubScreenBrightnessToggleSeekBar) findViewById(R.id.subroom_brightness_seekbar);
        this.mSeekBar = subScreenBrightnessToggleSeekBar;
        subScreenBrightnessToggleSeekBar.setMax(this.SEEK_BAR_MAX_VALUE);
        if (this.mSeekBar.getThumb() != null) {
            this.mSeekBar.getThumb().setAlpha(0);
        }
        this.mSeekBar.setContentDescription(this.mContext.getString(R.string.subscreen_brightness_button_talkback_label));
        boolean z = QpRune.QUICK_SUBSCREEN_PANEL;
        if (z) {
            BrightnessAnimationIcon brightnessAnimationIcon2 = new BrightnessAnimationIcon((LottieAnimationView) findViewById(R.id.subscreen_brightness_icon));
            this.mSunIcon = brightnessAnimationIcon2;
            brightnessAnimationIcon2.init(this.mContext);
            ImageView imageView = (ImageView) findViewById(R.id.brightness_panel_more_icon);
            this.mMoreIcon = imageView;
            if (imageView != null) {
                imageView.setColorFilter(this.mContext.getColor(R.color.subroom_qp_seekbar_icon_color), PorterDuff.Mode.SRC_IN);
            }
            int floor = (int) Math.floor((this.mSeekBar.getMax() * this.mContext.getResources().getInteger(R.integer.sec_brightness_slider_warning_percent)) / 100.0d);
            this.mDualSeekBarThreshold = floor;
            if (floor <= this.mSeekBar.getProgress() && z && (brightnessAnimationIcon = this.mSunIcon) != null) {
                brightnessAnimationIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
            }
        }
        if (this.mSeekBar.getThumb() != null) {
            ValueAnimator ofInt = ValueAnimator.ofInt(0, 255);
            this.mThumbAnimator = ofInt;
            ofInt.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.qp.SubroomBrightnessSettingsView$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    SubroomBrightnessSettingsView subroomBrightnessSettingsView = SubroomBrightnessSettingsView.this;
                    subroomBrightnessSettingsView.mThumbAnimator.setDuration(200L);
                    subroomBrightnessSettingsView.mSeekBar.getThumb().setAlpha(((Integer) valueAnimator.getAnimatedValue()).intValue());
                }
            });
        }
    }

    public final void setDualSeekBarResources(boolean z, boolean z2) {
        BrightnessAnimationIcon brightnessAnimationIcon;
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSeekBar;
        if (subScreenBrightnessToggleSeekBar == null) {
            return;
        }
        if (z != this.mIsSliderWarning) {
            this.mIsSliderWarning = z;
            subScreenBrightnessToggleSeekBar.setProgressDrawable(this.mContext.getDrawable(z2 ? R.drawable.subroom_seekbar_background_detail : R.drawable.subroom_seekbar_background));
        }
        if (!QpRune.QUICK_SUBSCREEN_PANEL || (brightnessAnimationIcon = this.mSunIcon) == null) {
            return;
        }
        brightnessAnimationIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
    }

    public final void setProgress(int i) {
        BrightnessAnimationIcon brightnessAnimationIcon;
        this.mSeekBar.setProgress(i);
        if (this.mDualSeekBarThreshold > i || !QpRune.QUICK_SUBSCREEN_PANEL || (brightnessAnimationIcon = this.mSunIcon) == null) {
            return;
        }
        brightnessAnimationIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
    }

    public final void setThumbScale(int i) {
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSeekBar;
        if (subScreenBrightnessToggleSeekBar == null || subScreenBrightnessToggleSeekBar.getThumb() == null) {
            return;
        }
        if (this.mSeekBar.getWidth() != 0) {
            this.mThumbThreshold = (this.mSeekBar.getMax() * this.mSeekBar.getHeight()) / this.mSeekBar.getWidth();
        }
        if (i < this.mThumbThreshold) {
            this.mSeekBar.getThumb().setLevel((i * 10000) / this.mThumbThreshold);
        } else {
            this.mSeekBar.getThumb().setLevel(10000);
        }
    }
}
