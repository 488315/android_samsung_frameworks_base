package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.provider.Settings;
import android.widget.LinearLayout;
import com.android.systemui.Dependency;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSSecurityFooter;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelView;
import com.android.systemui.util.SecQsUiDisplayModeInteractor;
import com.android.systemui.util.SettingsHelper;

/* loaded from: classes2.dex */
public class DataUsageBar extends BarItemImpl {
    public DataUsageLabelView mDataUsageLabelView;
    public boolean mIsLandScape;
    public boolean mIsSecurityFooterVisible;
    public final QSSecurityFooter mSecurityFooter;
    private final SettingsHelper.OnChangedCallback mSplitCallback;

    public DataUsageBar(Context context, QSSecurityFooter qSSecurityFooter) {
        super(context);
        this.mIsSecurityFooterVisible = false;
        this.mIsLandScape = false;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.qs.bar.DataUsageBar.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public final void onChanged(Uri uri) {
                if (uri != null && uri.equals(Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL))) {
                    DataUsageBar dataUsageBar = DataUsageBar.this;
                    DataUsageLabelView dataUsageLabelView = dataUsageBar.mDataUsageLabelView;
                    dataUsageBar.showBar(dataUsageLabelView != null && dataUsageLabelView.mDataUsageVisibility && SecPanelSplitHelper.isEnabled());
                }
            }
        };
        this.mSplitCallback = onChangedCallback;
        this.mContext = context;
        this.mSecurityFooter = qSSecurityFooter;
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(onChangedCallback, Settings.Secure.getUriFor(SettingsHelper.INDEX_SPLIT_QUICK_PANEL));
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        DataUsageLabelView dataUsageLabelView;
        this.mCallback = null;
        if (!QpRune.QUICK_DATA_USAGE_LABEL || (dataUsageLabelView = this.mDataUsageLabelView) == null) {
            return;
        }
        dataUsageLabelView.mVisibilityChangedListener = null;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_panel_split_data_usage_container;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final boolean isAvailable() {
        return QpRune.QUICK_DATA_USAGE_LABEL;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) throws Resources.NotFoundException {
        this.mIsLandScape = configuration.orientation == 2;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onFinishInflate() {
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            DataUsageLabelView dataUsageLabelView = (DataUsageLabelView) this.mBarRootView.findViewById(R.id.data_usage_label_view);
            this.mDataUsageLabelView = dataUsageLabelView;
            if (dataUsageLabelView != null) {
                showBar(dataUsageLabelView.mDataUsageVisibility && SecPanelSplitHelper.isEnabled());
                this.mDataUsageLabelView.mVisibilityChangedListener = this;
            }
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) throws Resources.NotFoundException {
        QSSecurityFooter qSSecurityFooter;
        if (!QpRune.QUICK_DATA_USAGE_LABEL || (qSSecurityFooter = this.mSecurityFooter) == null || !z || this.mIsSecurityFooterVisible == qSSecurityFooter.mIsVisible) {
            return;
        }
        this.mIsSecurityFooterVisible = qSSecurityFooter.mIsVisible;
        updateHeightMargins();
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() throws Resources.NotFoundException {
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            Resources resources = this.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bar_bottom_margin_security_footer);
            LinearLayout linearLayout = (LinearLayout) this.mBarRootView;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.bottomMargin = dimensionPixelSize;
            if (this.mIsSecurityFooterVisible) {
                layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.data_usage_bar_top_margin_with_security_footer);
            } else if (!this.mIsLandScape || ((SecQsUiDisplayModeInteractor) Dependency.sDependency.getDependencyInner(SecQsUiDisplayModeInteractor.class)).isTablet()) {
                layoutParams.topMargin = dimensionPixelSize;
            } else {
                layoutParams.topMargin = dimensionPixelSize - resources.getDimensionPixelSize(R.dimen.bar_top_margin);
            }
            linearLayout.setLayoutParams(layoutParams);
        }
    }
}
