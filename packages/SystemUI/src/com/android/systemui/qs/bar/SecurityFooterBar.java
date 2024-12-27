package com.android.systemui.qs.bar;

import android.content.Context;
import android.content.res.Configuration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.android.systemui.R;
import com.android.systemui.qs.QSSecurityFooter;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;

public final class SecurityFooterBar extends BarItemImpl {
    public final QSSecurityFooter mSecurityFooter;

    public SecurityFooterBar(Context context, QSSecurityFooter qSSecurityFooter) {
        super(context);
        this.mContext = context;
        this.mSecurityFooter = qSSecurityFooter;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void destroy() {
        this.mCallback = null;
        this.mSecurityFooter.mVisibilityChangedListener = null;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final int getBarLayout() {
        return R.layout.sec_security_footer_bar;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onConfigChanged(Configuration configuration) {
        QSSecurityFooter qSSecurityFooter = this.mSecurityFooter;
        qSSecurityFooter.updateTextMaxWidth();
        qSSecurityFooter.mHandler.sendEmptyMessage(1);
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void onFinishInflate() {
        QSSecurityFooter qSSecurityFooter = this.mSecurityFooter;
        showBar(qSSecurityFooter.mIsVisible);
        ViewGroup viewGroup = (ViewGroup) this.mBarRootView.findViewById(R.id.security_footer_container);
        if (viewGroup != null) {
            viewGroup.addView(qSSecurityFooter.getView());
        }
        qSSecurityFooter.mVisibilityChangedListener = this;
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void setListening(boolean z) {
        if (this.mListening == z) {
            return;
        }
        this.mListening = z;
        QSSecurityFooter qSSecurityFooter = this.mSecurityFooter;
        if (!z) {
            ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).removeCallback(qSSecurityFooter.mCallback);
        } else {
            ((SecurityControllerImpl) qSSecurityFooter.mSecurityController).addCallback(qSSecurityFooter.mCallback);
            qSSecurityFooter.mHandler.sendEmptyMessage(1);
        }
    }

    @Override // com.android.systemui.qs.bar.BarItemImpl
    public final void updateHeightMargins() {
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.bar_bottom_margin_security_footer);
        LinearLayout linearLayout = (LinearLayout) this.mSecurityFooter.getView();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) linearLayout.getLayoutParams();
        layoutParams.bottomMargin = dimensionPixelSize;
        layoutParams.topMargin = dimensionPixelSize;
        linearLayout.setLayoutParams(layoutParams);
    }
}
