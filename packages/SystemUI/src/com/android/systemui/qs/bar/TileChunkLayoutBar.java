package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.keyguard.StrongAuthPopup$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSPanelHost;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.SecTileChunkLayout;
import com.android.systemui.qs.animator.QsAnimatorState;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.util.ConfigurationState;
import com.android.systemui.util.SettingsHelper;
import java.util.Arrays;

public final class TileChunkLayoutBar extends BarItemImpl {
    public static final float BUTTON_WIDTH_MAX_RATIO;
    public static final float BUTTON_WIDTH_MIN_RATIO;
    public int mCollapsedRow;
    public int mContainerCollapsedHeight;
    public int mContainerExpandedHeight;
    public int mHeight;
    public boolean mIsExpanded;
    public final ConfigurationState mLastConfigurationState;
    public boolean mLocalListening;
    public QSPanelHost mPanelHost;
    public final SecQSPanelResourcePicker mResourcePicker;
    public View mScrollIndicatorClickContainer;
    private SettingsHelper.OnChangedCallback mSettingListener;
    public final Uri[] mSettingsValueList;
    public SecTileChunkLayout mTileLayout;
    public View mTileLayoutBackground;
    public ViewGroup mTileLayoutContainer;
    public final TileChunkLayoutBar$$ExternalSyntheticLambda0 mTilesChangedCallback;

    static {
        boolean z = QpRune.QUICK_TABLET;
        BUTTON_WIDTH_MAX_RATIO = z ? 1.41f : 1.5f;
        BUTTON_WIDTH_MIN_RATIO = z ? 0.62f : 0.75f;
    }

    public TileChunkLayoutBar(Context context, SecQSPanelResourcePicker secQSPanelResourcePicker) {
        super(context);
        this.mLastConfigurationState = new ConfigurationState(Arrays.asList(ConfigurationState.ConfigurationField.ORIENTATION, ConfigurationState.ConfigurationField.SCREEN_HEIGHT_DP, ConfigurationState.ConfigurationField.DISPLAY_DEVICE_TYPE));
        this.mTilesChangedCallback = new TileChunkLayoutBar$$ExternalSyntheticLambda0(this);
        this.mIsExpanded = false;
        this.mCollapsedRow = 2;
        this.mHeight = 0;
        this.mLocalListening = false;
        this.mSettingsValueList = new Uri[]{Settings.Global.getUriFor(SettingsHelper.INDEX_QS_BUTTON_GRID_POPUP), Settings.Global.getUriFor(SettingsHelper.INDEX_QS_BUTTON_GRID_TILE_WIDTH)};
        this.mSettingListener = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.TileChunkLayoutBar.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                TileChunkLayoutBar tileChunkLayoutBar = TileChunkLayoutBar.this;
                if (tileChunkLayoutBar.mTileLayout == null) {
                    return;
                }
                String str = "settingsHelper onChanged() Settings:" + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isQSButtonGridPopupEnabled();
                String str2 = tileChunkLayoutBar.TAG;
                Log.d(str2, str);
                if (((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isQSButtonGridPopupEnabled()) {
                    SecTileChunkLayout secTileChunkLayout = tileChunkLayoutBar.mTileLayout;
                    int qSButtonGridWidth = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).getQSButtonGridWidth();
                    float f = TileChunkLayoutBar.BUTTON_WIDTH_MAX_RATIO;
                    float f2 = TileChunkLayoutBar.BUTTON_WIDTH_MIN_RATIO;
                    float f3 = (f - f2) / 10.0f;
                    float min = Math.min(Math.max(Math.round((f - (qSButtonGridWidth * f3)) * 1000.0f) / 1000.0f, f2), f);
                    Log.d(str2, "QUICKSTAR_QS_TILE_LAYOUT_CUSTOM_MATRIX result[P:" + qSButtonGridWidth + ", R:" + min + "] dP:" + TileChunkLayoutBar.getDefaultProgress() + ", iR:" + f3 + ", cDR:" + (f - (TileChunkLayoutBar.getDefaultProgress() * f3)));
                    secTileChunkLayout.updateTileWidth(min);
                } else {
                    tileChunkLayoutBar.mTileLayout.updateTileWidth(1.0f);
                }
                tileChunkLayoutBar.calculateContainerHeight();
            }
        };
        this.mContext = context;
        this.mResourcePicker = secQSPanelResourcePicker;
    }

    public static int getDefaultProgress() {
        float f = BUTTON_WIDTH_MAX_RATIO;
        return Math.min(Math.max(Math.round((f - 1.0f) / ((f - BUTTON_WIDTH_MIN_RATIO) / 10.0f)), 0), 10);
    }

    public final void calculateContainerHeight() {
        this.mIsExpanded = false;
        if (this.mTileLayout == null) {
            this.mContainerCollapsedHeight = 0;
            this.mContainerExpandedHeight = 0;
        } else {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin);
            int dimensionPixelSize2 = this.mContext.getResources().getDimensionPixelSize(R.dimen.tile_chunk_layout_vertical_between_margin_expanded);
            SecTileChunkLayout secTileChunkLayout = this.mTileLayout;
            int i = secTileChunkLayout.rows;
            int i2 = (i <= 0 || this.mCollapsedRow <= i) ? this.mCollapsedRow : i;
            this.mCollapsedRow = i2;
            if (i2 == 0 && i > 0) {
                this.mCollapsedRow = 2;
                if (secTileChunkLayout.records.size() == 0) {
                    this.mCollapsedRow = 0;
                } else if (i < 2) {
                    this.mCollapsedRow = 1;
                }
            }
            StringBuilder m = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "calculateContainerHeight Expanded row: ", " Collapsed row: ");
            m.append(this.mCollapsedRow);
            Log.d(this.TAG, m.toString());
            Context context = this.mContext;
            SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
            int touchIconSize = secQSPanelResourcePicker.getTouchIconSize(context);
            int i3 = this.mCollapsedRow;
            this.mContainerCollapsedHeight = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.mContext, R.dimen.tile_chunk_layout_last_vertical_margin, ((i3 - 1) * dimensionPixelSize) + (touchIconSize * i3));
            this.mContainerExpandedHeight = StrongAuthPopup$$ExternalSyntheticOutline0.m(this.mContext, R.dimen.tile_chunk_layout_last_vertical_margin_expanded, ((i - 1) * dimensionPixelSize2) + (secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getTileExpandedHeight(this.mContext) * i));
        }
        setContainerHeight(this.mIsExpanded ? this.mContainerExpandedHeight : this.mContainerCollapsedHeight);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        QSPanelHost qSPanelHost = this.mPanelHost;
        if (qSPanelHost != null) {
            qSPanelHost.mCallbacks.remove(this.mTilesChangedCallback);
        }
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).unregisterCallback(this.mSettingListener);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_tile_chunk_layout_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void inflateViews(ViewGroup viewGroup) {
        View inflate = LayoutInflater.from(this.mContext).inflate(R.layout.sec_tile_chunk_layout_bar, viewGroup, false);
        this.mBarRootView = inflate;
        inflate.setClickable(true);
        this.mTileLayoutContainer = (ViewGroup) this.mBarRootView.findViewById(R.id.tile_layout_container);
        this.mTileLayoutBackground = this.mBarRootView.findViewById(R.id.tile_layout_background);
        this.mScrollIndicatorClickContainer = this.mBarRootView.findViewById(R.id.scroll_indicator_touch_container);
        updateHeightMargins();
        this.mTileLayoutBackground.setBackground(this.mContext.getDrawable(R.drawable.sec_tile_layout_background));
        ColoredBGHelper coloredBGHelper = this.mBGColorHelper;
        if (coloredBGHelper != null) {
            coloredBGHelper.addBarBackground(this.mTileLayoutBackground, true);
        }
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(this.mSettingListener, this.mSettingsValueList);
        if (this.mShowing) {
            return;
        }
        showBar(false);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isNeedToEdit() {
        return true;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void makeCloneBar() {
        this.mClonedBarView = new View(this.mContext);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        if (this.mBarRootView == null) {
            return;
        }
        ConfigurationState configurationState = this.mLastConfigurationState;
        if (configurationState.needToUpdate(configuration)) {
            updateHeightMargins();
            configurationState.update(configuration);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int orignBottomMargin() {
        return 0;
    }

    public final void setContainerHeight(int i) {
        ViewGroup viewGroup = this.mTileLayoutContainer;
        if (viewGroup == null) {
            return;
        }
        this.mHeight = i;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewGroup.getLayoutParams();
        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_tile_chunk_layout_margin_top);
        layoutParams.height = i;
        this.mTileLayoutContainer.setLayoutParams(layoutParams);
        int i2 = this.mHeight;
        int i3 = this.mContainerCollapsedHeight;
        float f = (i2 - i3) / (this.mContainerExpandedHeight - i3);
        if (this.mTileLayout == null) {
            return;
        }
        setIndicatorTouchHeight(f);
        if (f == 1.0f) {
            this.mIsExpanded = true;
            this.mTileLayout.setLabelVisibility(true);
            this.mTileLayout.updateFocusForTiles(this.mCollapsedRow, true);
            this.mScrollIndicatorClickContainer.setContentDescription(this.mContext.getResources().getString(R.string.collapse_chunk));
        } else if (f == 0.0f) {
            this.mIsExpanded = false;
            this.mTileLayout.updateFocusForTiles(this.mCollapsedRow, false);
            this.mTileLayout.setLabelVisibility(false);
            this.mScrollIndicatorClickContainer.setContentDescription(this.mContext.getResources().getString(R.string.expand_chunk));
        }
        setIndicatorTouchHeight(f);
        this.mTileLayout.setFraction(f);
    }

    public final void setIndicatorTouchHeight(float f) {
        if (this.mScrollIndicatorClickContainer == null) {
            return;
        }
        Context context = this.mContext;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        SecQSPanelResourceCommon.Companion.getClass();
        int dp = SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_tile_chunk_layout_indicator_touch_height, context);
        Context context2 = this.mContext;
        secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getClass();
        int dp2 = dp - ((int) ((dp - SecQSPanelResourceCommon.Companion.dp(R.dimen.sec_tile_chunk_layout_indicator_touch_height_expanded, context2)) * f));
        ViewGroup.LayoutParams layoutParams = this.mScrollIndicatorClickContainer.getLayoutParams();
        layoutParams.height = dp2;
        this.mScrollIndicatorClickContainer.setLayoutParams(layoutParams);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        this.mListening = z;
        if (this.mLocalListening == z) {
            return;
        }
        this.mLocalListening = z;
        if (z || !this.mIsExpanded) {
            return;
        }
        if (SecPanelSplitHelper.isEnabled() ? QsAnimatorState.qsExpanded : true) {
            setContainerHeight(this.mContainerCollapsedHeight);
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
            Log.w(this.TAG, "showBar(): mBarRootView is null... " + z);
            this.mShowing = z;
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        View view = this.mBarRootView;
        if (view == null) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = 0;
        layoutParams.width = -1;
        layoutParams.height = -2;
        this.mBarRootView.setLayoutParams(layoutParams);
        calculateContainerHeight();
    }
}
