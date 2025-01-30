package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.util.ConfigurationState;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class PagedTileLayoutBar extends BarItemImpl {
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public View mPageIndicator;
    public View mTileLayoutContainer;

    public PagedTileLayoutBar(Context context) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        this.mContext = context;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return getDimensionPixelSize(R.dimen.paged_tile_bar_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_paged_tile_layout_bar;
    }

    public final int getDimensionPixelSize(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_paged_tile_layout_bar, viewGroup, false);
        this.mBarRootView = inflate;
        this.mTileLayoutContainer = inflate.findViewById(R.id.tile_layout_container);
        this.mPageIndicator = this.mBarRootView.findViewById(R.id.panel_page_indicator);
        updateHeightMargins();
        this.mTileLayoutContainer.setBackground(this.mContext.getDrawable(R.drawable.sec_large_button_ripple_background));
        if (this.mShowing) {
            return;
        }
        showBar(false);
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
            updateHeightMargins();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void showBar(boolean z) {
        super.showBar(z);
        if (this.mBarRootView == null) {
            Log.w(this.TAG, "mBarRootView is null... " + z);
            this.mShowing = z;
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        int dimensionPixelSize = getDimensionPixelSize(R.dimen.bar_top_margin);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        layoutParams.topMargin = dimensionPixelSize;
        this.mBarRootView.setLayoutParams(layoutParams);
        int dimensionPixelSize2 = getDimensionPixelSize(R.dimen.paged_tile_layout_side_padding);
        this.mTileLayoutContainer.setPadding(dimensionPixelSize2, getDimensionPixelSize(R.dimen.paged_tile_bar_top_padding), dimensionPixelSize2, getDimensionPixelSize(R.dimen.paged_tile_bar_indicator_bottom_margin));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mPageIndicator.getLayoutParams();
        layoutParams2.topMargin = getDimensionPixelSize(R.dimen.paged_tile_bar_indicator_top_margin);
        this.mPageIndicator.setLayoutParams(layoutParams2);
    }
}
