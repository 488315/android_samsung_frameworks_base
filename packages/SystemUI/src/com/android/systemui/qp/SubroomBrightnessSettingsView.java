package com.android.systemui.qp;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.provider.Settings;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.airbnb.lottie.LottieAnimationView;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.settings.brightness.BrightnessAnimationIcon;
import com.android.systemui.util.SettingsHelper;

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

    public SubroomBrightnessSettingsView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        int[] intArray = context.getResources().getIntArray(17236323);
        this.mBrightnessLevels = intArray;
        this.SEEK_BAR_MAX_VALUE = intArray[intArray.length - 1];
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mBrightness = Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_SUBSCREEN_BRIGHTNESS, 73, -2);
        RecyclerView$$ExternalSyntheticOutline0.m(this.mBrightness, "SubroomBrightnessSettingsView", new StringBuilder("onAttachedToWindow() mBrightness: "));
        this.mSeekBar.setProgress(this.mBrightness);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        ImageView imageView = this.mMoreIcon;
        if (imageView != null) {
            imageView.setContentDescription(this.mContext.getString(R.string.sec_brightness_detail_content_description));
            this.mMoreIcon.setColorFilter(this.mContext.getColor(R.color.subroom_qp_seekbar_icon_color), PorterDuff.Mode.SRC_IN);
        }
        this.mSunIcon.init(this.mContext);
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSeekBar;
        if (subScreenBrightnessToggleSeekBar != null) {
            subScreenBrightnessToggleSeekBar.setContentDescription(this.mContext.getString(R.string.subscreen_brightness_button_talkback_label));
            this.mSeekBar.setProgressDrawable(this.mContext.getDrawable(this.mIsSliderWarning ? R.drawable.sec_brightness_progress_warning_drawable : R.drawable.subroom_seekbar_background));
        }
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = (SubScreenBrightnessToggleSeekBar) findViewById(R.id.subroom_brightness_seekbar);
        this.mSeekBar = subScreenBrightnessToggleSeekBar;
        subScreenBrightnessToggleSeekBar.setMax(this.SEEK_BAR_MAX_VALUE);
        this.mSeekBar.setContentDescription(this.mContext.getString(R.string.subscreen_brightness_button_talkback_label));
        if (QpRune.QUICK_SUBSCREEN_PANEL) {
            BrightnessAnimationIcon brightnessAnimationIcon = new BrightnessAnimationIcon((LottieAnimationView) findViewById(R.id.subscreen_brightness_icon));
            this.mSunIcon = brightnessAnimationIcon;
            brightnessAnimationIcon.init(this.mContext);
            ImageView imageView = (ImageView) findViewById(R.id.brightness_panel_more_icon);
            this.mMoreIcon = imageView;
            if (imageView != null) {
                imageView.setColorFilter(this.mContext.getColor(R.color.subroom_qp_seekbar_icon_color), PorterDuff.Mode.SRC_IN);
            }
            int floor = (int) Math.floor((this.mSeekBar.getMax() * this.mContext.getResources().getInteger(R.integer.sec_brightness_slider_warning_percent)) / 100.0d);
            this.mDualSeekBarThreshold = floor;
            if (floor <= this.mSeekBar.getProgress()) {
                this.mSunIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
            }
        }
    }

    public final void setDualSeekBarResources(boolean z, boolean z2) {
        SubScreenBrightnessToggleSeekBar subScreenBrightnessToggleSeekBar = this.mSeekBar;
        if (subScreenBrightnessToggleSeekBar == null) {
            return;
        }
        if (z != this.mIsSliderWarning) {
            this.mIsSliderWarning = z;
            subScreenBrightnessToggleSeekBar.setProgressDrawable(this.mContext.getDrawable(z ? R.drawable.sec_brightness_progress_warning_drawable : z2 ? R.drawable.subroom_seekbar_background_detail : R.drawable.subroom_seekbar_background));
        }
        this.mSunIcon.play(this.mSeekBar.getProgress(), this.mSeekBar.getMax());
    }
}
