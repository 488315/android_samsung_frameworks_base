package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.qs.SecQSPanelControllerBase;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.ConfigurationState;
import java.util.ArrayList;
import java.util.Arrays;

/* loaded from: classes2.dex */
public class LargeTileBar extends BarItemImpl implements TileHostable {
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public LinearLayout mTileContainer;
    public final ArrayList mTiles;

    public LargeTileBar(Context context) {
        super(context);
        this.mTiles = new ArrayList();
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
    }

    @Override // com.android.systemui.qs.bar.TileHostable
    public void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        if (this.mBarRootView != null) {
            this.mTileContainer.addView(tileRecord.tileView);
            ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
            if (coloredBGHelper != null) {
                coloredBGHelper.addBarBackground(tileRecord.tileView, false);
            }
            updateHeightMargins();
        }
        if (this.mTiles.isEmpty() || this.mShowing) {
            return;
        }
        showBar(true);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public void destroy() {
        this.mCallback = null;
        removeAllTiles();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_height);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_large_tile_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public void inflateViews(ViewGroup viewGroup) {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_large_tile_bar, viewGroup, false);
        this.mBarRootView = viewInflate;
        if (viewInflate != null) {
            this.mTileContainer = (LinearLayout) viewInflate.findViewById(R.id.large_tile_container);
            this.mBarRootView.setVisibility(this.mShowing ? 0 : 8);
        }
        this.mTiles.forEach(new LargeTileBar$$ExternalSyntheticLambda0(this, 2));
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_large_tile_bar, (ViewGroup) null);
        this.mClonedBarView = viewInflate;
        LinearLayout linearLayout = (LinearLayout) viewInflate.findViewById(R.id.large_tile_container);
        createTilesViewAndDistribute(linearLayout, this.mTiles, (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class), Boolean.TRUE);
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            linearLayout.getChildAt(i).setImportantForAccessibility(2);
        }
        updateLayout(this.mClonedBarView, linearLayout);
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

    @Override // com.android.systemui.qs.bar.TileHostable
    public final void removeAllTiles() {
        if (this.mBarRootView != null) {
            this.mTiles.forEach(new LargeTileBar$$ExternalSyntheticLambda0(this, 0));
            if (this.mBGColorHelper != null) {
                this.mTiles.forEach(new LargeTileBar$$ExternalSyntheticLambda0(this, 1));
            }
            this.mTileContainer.removeAllViews();
        }
        this.mTiles.clear();
        if (this.mShowing) {
            showBar(false);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void removeCloneTileBG() {
        View view = this.mClonedBarView;
        if (view == null) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.large_tile_container);
        if (this.mBGColorHelper == null || viewGroup == null) {
            return;
        }
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            this.mBGColorHelper.removeFromBarBackground(viewGroup.getChildAt(i));
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        this.mTiles.forEach(new LargeTileBar$$ExternalSyntheticLambda0(this, 3));
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public void updateHeightMargins() {
        updateLayout(this.mTileContainer);
    }

    public final void updateLayout(LinearLayout linearLayout) {
        if (this.mBarRootView == null) {
            return;
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_between_margin);
        for (int i = 0; i < linearLayout.getChildCount() - 1; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getChildAt(i).getLayoutParams();
            layoutParams.setMarginEnd(dimensionPixelSize);
            linearLayout.getChildAt(i).setLayoutParams(layoutParams);
        }
    }

    public void updateLayout(View view, LinearLayout linearLayout) {
        updateLayout(linearLayout);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onUiModeChanged() {
    }
}
