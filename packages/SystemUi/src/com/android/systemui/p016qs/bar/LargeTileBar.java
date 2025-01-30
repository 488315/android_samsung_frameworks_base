package com.android.systemui.p016qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.p016qs.SecQSPanelControllerBase;
import com.android.systemui.util.ConfigurationState;
import java.util.ArrayList;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class LargeTileBar extends BarItemImpl implements TileHostable {
    public final ConfigurationState mLastConfigurationState;
    public int mOrientation;
    public View mTileContainer;
    public final ArrayList mTiles;

    public LargeTileBar(Context context) {
        super(context);
        this.mTiles = new ArrayList();
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        this.mContext = context;
    }

    @Override // com.android.systemui.p016qs.bar.TileHostable
    public final void addTile(SecQSPanelControllerBase.TileRecord tileRecord) {
        this.mTiles.add(tileRecord);
        if (this.mBarRootView != null) {
            ((ViewGroup) this.mTileContainer).addView(tileRecord.tileView);
            updateHeightMargins();
        }
        if (this.mShowing) {
            return;
        }
        showBar(true);
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        removeAllTiles();
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final int getBarHeight() {
        return this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_height);
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_large_tile_bar;
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_large_tile_bar, viewGroup, false);
        this.mBarRootView = inflate;
        this.mTileContainer = inflate.findViewById(R.id.large_tile_container);
        ArrayList arrayList = this.mTiles;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((ViewGroup) this.mTileContainer).addView(((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tileView);
        }
        updateHeightMargins();
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return true;
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
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

    @Override // com.android.systemui.p016qs.bar.TileHostable
    public final void removeAllTiles() {
        View view = this.mBarRootView;
        ArrayList arrayList = this.mTiles;
        if (view != null) {
            int size = arrayList.size();
            for (int i = 0; i < size; i++) {
                ((ViewGroup) this.mTileContainer).removeView(((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tileView);
                ((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tile.setListening(this, false);
            }
        }
        arrayList.clear();
        if (this.mShowing) {
            showBar(false);
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        ArrayList arrayList = this.mTiles;
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            ((SecQSPanelControllerBase.TileRecord) arrayList.get(i)).tile.setListening(this, this.mListening);
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void setUnderneathQqs(boolean z) {
        this.mIsUnderneathQqs = z;
        updateHeightMargins();
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public void updateHeightMargins() {
        if (this.mBarRootView == null) {
            return;
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.large_tile_temp_between_margin);
        for (int i = 0; i < ((ViewGroup) this.mTileContainer).getChildCount() - 1; i++) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) ((ViewGroup) this.mTileContainer).getChildAt(i).getLayoutParams();
            layoutParams.setMarginEnd(dimensionPixelSize);
            ((ViewGroup) this.mTileContainer).getChildAt(i).setLayoutParams(layoutParams);
        }
    }

    @Override // com.android.systemui.p016qs.bar.BarItemImpl
    public final void onUiModeChanged() {
    }
}
