package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Resources;
import android.widget.LinearLayout;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.qs.QSSecurityFooter;
import com.android.systemui.shade.SecPanelSplitHelper;
import com.android.systemui.statusbar.phone.datausage.DataUsageLabelView;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class DataUsageBar extends BarItemImpl {
    public final DataUsageLabelView mDataUsageLabelView;
    public boolean mIsSecurityFooterVisible;
    public final QSSecurityFooter mSecurityFooter;

    public DataUsageBar(Context context, DataUsageLabelView dataUsageLabelView, QSSecurityFooter qSSecurityFooter) {
        super(context);
        this.mIsSecurityFooterVisible = false;
        this.mContext = context;
        this.mDataUsageLabelView = dataUsageLabelView;
        this.mSecurityFooter = qSSecurityFooter;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            this.mDataUsageLabelView.getClass();
            DataUsageLabelView.mVisibilityChangedListener = null;
        }
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
    public final void onFinishInflate() {
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            showBar(this.mDataUsageLabelView.mDataUsageVisibility && SecPanelSplitHelper.isEnabled());
            DataUsageLabelView.mVisibilityChangedListener = this;
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setExpanded(boolean z) {
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            showBar(z && SecPanelSplitHelper.isEnabled());
            QSSecurityFooter qSSecurityFooter = this.mSecurityFooter;
            if (qSSecurityFooter == null || !z || this.mIsSecurityFooterVisible == qSSecurityFooter.mIsVisible) {
                return;
            }
            this.mIsSecurityFooterVisible = qSSecurityFooter.mIsVisible;
            updateHeightMargins();
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        if (QpRune.QUICK_DATA_USAGE_LABEL) {
            Resources resources = this.mContext.getResources();
            int dimensionPixelSize = resources.getDimensionPixelSize(R.dimen.bar_bottom_margin_security_footer);
            LinearLayout linearLayout = (LinearLayout) this.mBarRootView;
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
            layoutParams.bottomMargin = dimensionPixelSize;
            if (this.mIsSecurityFooterVisible) {
                layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.data_usage_bar_top_margin_with_security_footer);
            } else {
                layoutParams.topMargin = dimensionPixelSize;
            }
            linearLayout.setLayoutParams(layoutParams);
        }
    }
}
