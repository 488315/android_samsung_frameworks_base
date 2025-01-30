package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.DeviceState;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BrightnessMediaDevicesBar extends BarItemImpl {
    public final BrightnessBar mBrightnessBar;
    public final ConfigurationState mLastConfigurationState;
    public final MediaDevicesBar mMediaDevicesBar;
    public int mOrientation;

    public BrightnessMediaDevicesBar(Context context, BarFactory barFactory) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        BarItemImpl createBarItem = barFactory.createBarItem(BarType.BRIGHTNESS);
        createBarItem.mIsOnCollapsedState = true;
        this.mBrightnessBar = (BrightnessBar) createBarItem;
        BarItemImpl createBarItem2 = barFactory.createBarItem(BarType.MEDIA_DEVICES);
        createBarItem2.mIsOnCollapsedState = true;
        this.mMediaDevicesBar = (MediaDevicesBar) createBarItem2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mBrightnessBar.destroy();
        this.mMediaDevicesBar.destroy();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        int barHeight = this.mBrightnessBar.getBarHeight();
        int barHeight2 = this.mMediaDevicesBar.getBarHeight();
        return haveEnoughSpace() ? barHeight + barHeight2 : Math.max(barHeight, barHeight2);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qspanel_brightness_media_deivces_bar_layout;
    }

    public final boolean haveEnoughSpace() {
        ConfigurationState.ConfigurationField configurationField = ConfigurationState.ConfigurationField.ORIENTATION;
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.getValue(configurationField) == -100) {
            configurationState.update(this.mContext.getResources().getConfiguration());
        }
        return configurationState.getValue(configurationField) == 1 || QpRune.QUICK_TABLET;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qspanel_brightness_media_deivces_bar_layout, viewGroup, false);
        this.mBarRootView = inflate;
        BrightnessBar brightnessBar = this.mBrightnessBar;
        brightnessBar.inflateViews((ViewGroup) inflate);
        View view = brightnessBar.mBarRootView;
        ((ViewGroup) this.mBarRootView).removeView(view);
        ((ViewGroup) this.mBarRootView).addView(view);
        ViewGroup viewGroup2 = (ViewGroup) this.mBarRootView;
        MediaDevicesBar mediaDevicesBar = this.mMediaDevicesBar;
        mediaDevicesBar.inflateViews(viewGroup2);
        View view2 = mediaDevicesBar.mBarRootView;
        ((ViewGroup) this.mBarRootView).removeView(view2);
        ((ViewGroup) this.mBarRootView).addView(view2);
        showBar(true);
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return DeviceState.isMediaQuickControlBarAvailable(this.mContext);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        if (this.mBarRootView == null) {
            return;
        }
        int i = this.mContext.getResources().getConfiguration().orientation;
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration) || this.mOrientation != i) {
            this.mOrientation = i;
            configurationState.update(configuration);
            this.mMediaDevicesBar.onConfigChanged(configuration);
            this.mBrightnessBar.onConfigChanged(configuration);
            updateHeightMargins();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        if (this.mBarRootView == null) {
            return;
        }
        this.mBrightnessBar.onUiModeChanged();
        this.mMediaDevicesBar.onUiModeChanged();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setPosition(float f) {
        if (Float.isNaN(f) || f >= 0.1f) {
            return;
        }
        this.mBrightnessBar.setPosition(f);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        if (haveEnoughSpace()) {
            ((LinearLayout) this.mBarRootView).setOrientation(1);
            for (int i = 0; i < ((LinearLayout) this.mBarRootView).getChildCount(); i++) {
                View childAt = ((LinearLayout) this.mBarRootView).getChildAt(i);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams.weight = 0.0f;
                layoutParams.width = -1;
                layoutParams.setMarginStart(0);
                childAt.setLayoutParams(layoutParams);
            }
            return;
        }
        ((LinearLayout) this.mBarRootView).setOrientation(0);
        for (int i2 = 0; i2 < ((LinearLayout) this.mBarRootView).getChildCount(); i2++) {
            View childAt2 = ((LinearLayout) this.mBarRootView).getChildAt(i2);
            LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt2.getLayoutParams();
            layoutParams2.weight = 1.0f;
            layoutParams2.width = 0;
            if (i2 == 1 && this.mBrightnessBar.mShowing) {
                layoutParams2.setMarginStart(this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_between_margin));
            } else {
                layoutParams2.setMarginStart(0);
            }
            childAt2.setLayoutParams(layoutParams2);
        }
    }
}
