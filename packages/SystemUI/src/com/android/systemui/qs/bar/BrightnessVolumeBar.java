package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSTileHost;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.util.ConfigurationState;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BrightnessVolumeBar extends BarItemImpl implements TileHostable {
    public final BrightnessBar mBrightnessBar;
    public final List mBrightnessTileList;
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public final VolumeBar mVolumeBar;
    public final List mVolumeTileList;

    public BrightnessVolumeBar(Context context, BarFactory barFactory, QSTileHost qSTileHost) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        BarItemImpl createBarItem = barFactory.createBarItem(BarType.VOLUME);
        createBarItem.mIsOnCollapsedState = false;
        this.mVolumeBar = (VolumeBar) createBarItem;
        BarItemImpl createBarItem2 = barFactory.createBarItem(BarType.BRIGHTNESS);
        createBarItem2.mIsOnCollapsedState = false;
        this.mBrightnessBar = (BrightnessBar) createBarItem2;
        this.mVolumeTileList = createTileList(context, qSTileHost, R.string.sec_volume_bar_tiles_default);
        this.mBrightnessTileList = createTileList(context, qSTileHost, R.string.sec_brightness_bar_tiles_default);
    }

    public static List createTileList(Context context, QSTileHost qSTileHost, int i) {
        ArrayList arrayList = new ArrayList();
        for (String str : context.getResources().getString(i).split(",")) {
            String trim = str.trim();
            arrayList.add(qSTileHost.isSystemTile(trim) ? qSTileHost.changeOldOSTileNameToNewName(trim) : qSTileHost.getCustomTileSpecFromTileName(trim));
        }
        return arrayList;
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        if (((ArrayList) this.mBrightnessTileList).contains(tileRecord.tile.getTileSpec())) {
            this.mBrightnessBar.addTile(tileRecord);
            return;
        }
        if (((ArrayList) this.mVolumeTileList).contains(tileRecord.tile.getTileSpec())) {
            this.mVolumeBar.addTile(tileRecord);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mVolumeBar.destroy();
        this.mBrightnessBar.destroy();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_bar_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.qspanel_brightness_media_deivces_bar_layout;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarWidthWeight(Context context) {
        return (QpRune.QUICK_TABLET || context.getResources().getConfiguration().orientation != 2) ? 4 : 2;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qspanel_brightness_media_deivces_bar_layout, viewGroup, false);
        this.mBarRootView = inflate;
        inflate.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_no_ripple_background));
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(this.mBarRootView, false);
        }
        ViewGroup viewGroup2 = (ViewGroup) this.mBarRootView;
        VolumeBar volumeBar = this.mVolumeBar;
        volumeBar.inflateViews(viewGroup2);
        View view = volumeBar.mBarRootView;
        ((ViewGroup) this.mBarRootView).removeView(view);
        ((ViewGroup) this.mBarRootView).addView(view);
        ViewGroup viewGroup3 = (ViewGroup) this.mBarRootView;
        BrightnessBar brightnessBar = this.mBrightnessBar;
        brightnessBar.inflateViews(viewGroup3);
        View view2 = brightnessBar.mBarRootView;
        ((ViewGroup) this.mBarRootView).removeView(view2);
        ((ViewGroup) this.mBarRootView).addView(view2);
        showBar(true);
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.qspanel_brightness_media_deivces_bar_layout, (ViewGroup) null);
        this.mClonedBarView = inflate;
        inflate.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_no_ripple_background));
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(this.mClonedBarView, false);
        }
        VolumeBar volumeBar = this.mVolumeBar;
        volumeBar.makeCloneBar();
        BrightnessBar brightnessBar = this.mBrightnessBar;
        brightnessBar.makeCloneBar();
        View view = volumeBar.mClonedBarView;
        View view2 = brightnessBar.mClonedBarView;
        ((ViewGroup) this.mClonedBarView).addView(view);
        ((ViewGroup) this.mClonedBarView).addView(view2);
        this.mClonedBarView.setVisibility(0);
        updateLayout$3(this.mClonedBarView);
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
            this.mVolumeBar.updateHeightMargins();
            this.mBrightnessBar.onConfigChanged(configuration);
            updateHeightMargins();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onKnoxPolicyChanged() {
        if (this.mBarRootView == null) {
            return;
        }
        this.mBrightnessBar.updateVisibility$7();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
        if (this.mBarRootView == null) {
            return;
        }
        this.mVolumeBar.onUiModeChanged();
        this.mBrightnessBar.onUiModeChanged();
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void removeAllTiles() {
        this.mVolumeBar.removeAllTiles();
        this.mBrightnessBar.removeAllTiles();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        if (this.mBarRootView == null) {
            return;
        }
        this.mVolumeBar.setListening(z);
        this.mBrightnessBar.setListening(z);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateClonedBar() {
        this.mVolumeBar.updateClonedBar();
        this.mBrightnessBar.updateClonedBar();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        updateLayout$3(this.mBarRootView);
    }

    public final void updateLayout$3(View view) {
        if (view == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, getBarHeight());
        if (view.getLayoutParams() != null) {
            layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_slider_start_margin);
        view.setPaddingRelative(dimensionPixelSize, 0, dimensionPixelSize, 0);
        layoutParams.height = getBarHeight();
        if (QpRune.QUICK_TABLET || this.mOrientation != 2) {
            layoutParams.width = -1;
            layoutParams.setMarginEnd(0);
        } else {
            layoutParams.weight = 2.0f;
            layoutParams.width = 0;
        }
        view.setLayoutParams(layoutParams);
        if (view instanceof LinearLayout) {
            LinearLayout linearLayout = (LinearLayout) view;
            linearLayout.setOrientation(1);
            linearLayout.setGravity(17);
            int i = 0;
            while (i < linearLayout.getChildCount()) {
                View childAt = linearLayout.getChildAt(i);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) childAt.getLayoutParams();
                layoutParams2.topMargin = (i == 1 && this.mBrightnessBar.mShowing) ? this.mContext.getResources().getDimensionPixelSize(R.dimen.brightness_volume_between_margin) : 0;
                childAt.setLayoutParams(layoutParams2);
                i++;
            }
        }
    }
}
