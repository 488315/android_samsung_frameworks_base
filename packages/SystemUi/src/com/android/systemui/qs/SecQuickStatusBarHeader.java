package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.DualToneHandler;
import com.android.systemui.FontSizeUtils;
import com.android.systemui.QpRune;
import com.android.systemui.R;
import com.android.systemui.log.SecTouchLogHelper;
import com.android.systemui.shade.SecPanelExpansionStateNotifier;
import com.android.systemui.shade.ShadeHeaderController;
import com.android.systemui.statusbar.policy.QSClockHeaderView;
import java.util.ArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class SecQuickStatusBarHeader extends FrameLayout {
    public QSClockHeaderView mClockView;
    public int mCutOutHeight;
    public View mDateButtonContainer;
    public TextView mDateView;
    public final DualToneHandler mDualToneHandler;
    public boolean mExpanded;
    public SecQuickQSPanel mHeaderQsPanel;
    public final StringBuilder mLogBuilder;
    public int mNavBarHeight;
    public final SecPanelExpansionStateNotifier mPanelExpansionStateNotifier;
    public View mPrivacyChip;
    public boolean mQsDisabled;
    public final SecQSPanelResourcePicker mResourcePicker;
    public final ShadeHeaderController mShadeHeaderController;
    public final SecTouchLogHelper mTouchLogHelper;
    public View mView;

    public SecQuickStatusBarHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCutOutHeight = 0;
        this.mNavBarHeight = 0;
        this.mTouchLogHelper = new SecTouchLogHelper();
        this.mLogBuilder = new StringBuilder();
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mPanelExpansionStateNotifier = (SecPanelExpansionStateNotifier) Dependency.get(SecPanelExpansionStateNotifier.class);
        this.mShadeHeaderController = (ShadeHeaderController) Dependency.get(ShadeHeaderController.class);
        this.mDualToneHandler = new DualToneHandler(new ContextThemeWrapper(context, 2132018544));
    }

    @Override // android.view.View
    public final WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        int safeInsetTop;
        DisplayCutout displayCutout = windowInsets.getDisplayCutout();
        int navBarHeight = this.mResourcePicker.getNavBarHeight(getContext());
        int i = 0;
        if (displayCutout != null && (safeInsetTop = displayCutout.getSafeInsetTop() - displayCutout.getSafeInsetBottom()) >= 0) {
            i = safeInsetTop;
        }
        if (this.mCutOutHeight != i || navBarHeight != this.mNavBarHeight) {
            this.mNavBarHeight = navBarHeight;
            this.mCutOutHeight = i;
            updateResources();
            updateContentsPadding();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateResources();
        updateContentsPadding();
        FontSizeUtils.updateFontSize(this.mClockView, R.dimen.sec_status_bar_clock_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mDateView, R.dimen.sec_status_bar_clock_size, 0.8f, 1.3f);
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mDateButtonContainer = findViewById(R.id.quick_qs_date_buttons);
        this.mHeaderQsPanel = (SecQuickQSPanel) findViewById(R.id.quick_qs_panel);
        this.mClockView = (QSClockHeaderView) findViewById(R.id.header_clock);
        this.mDateView = (TextView) findViewById(R.id.header_date);
        this.mView = findViewById(R.id.header);
        this.mPrivacyChip = findViewById(R.id.privacy_chip);
        SecQuickQSPanel secQuickQSPanel = this.mHeaderQsPanel;
        if (secQuickQSPanel != null) {
            secQuickQSPanel.setDescendantFocusability(262144);
            this.mHeaderQsPanel.setFocusable(false);
        }
        updateResources();
        this.mClockView.setTextColor(((FrameLayout) this).mContext.getColor(R.color.qs_status_bar_clock_color));
        this.mDateView.setTextColor(((FrameLayout) this).mContext.getColor(R.color.qs_status_bar_clock_color));
        FontSizeUtils.updateFontSize(this.mClockView, R.dimen.sec_status_bar_clock_size, 0.8f, 1.3f);
        FontSizeUtils.updateFontSize(this.mDateView, R.dimen.sec_status_bar_clock_size, 0.8f, 1.3f);
        updateContentsPadding();
    }

    @Override // android.view.View
    public final boolean onHoverEvent(MotionEvent motionEvent) {
        return super.onHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptHoverEvent(MotionEvent motionEvent) {
        return super.onInterceptHoverEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int i = this.mPanelExpansionStateNotifier.mModel.panelOpenState;
        boolean z = i == 2 || i == 1;
        this.mLogBuilder.setLength(0);
        StringBuilder sb = this.mLogBuilder;
        sb.append("mExpanded: ");
        sb.append(this.mExpanded);
        StringBuilder sb2 = this.mLogBuilder;
        sb2.append(", beAbleToListen: ");
        sb2.append(z);
        this.mTouchLogHelper.printOnInterceptTouchEventLog(motionEvent, "SecQuickStatusBarHeaderView", this.mLogBuilder.toString());
        if (z) {
            return super.onInterceptTouchEvent(motionEvent);
        }
        return true;
    }

    @Override // android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updateResources();
    }

    public final void updateContentsPadding() {
        int i = getContext().getResources().getConfiguration().orientation;
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = getContext();
        secQSPanelResourcePicker.getClass();
        int panelSidePadding = SecQSPanelResourcePicker.getPanelSidePadding(context);
        View view = this.mDateButtonContainer;
        view.setPaddingRelative(panelSidePadding, view.getPaddingTop(), panelSidePadding, this.mDateButtonContainer.getPaddingBottom());
        View view2 = this.mView;
        view2.setPadding(view2.getPaddingLeft(), QpRune.QUICK_TABLET_TOP_MARGIN ? this.mShadeHeaderController.getViewHeight() - this.mView.getResources().getDimensionPixelSize(R.dimen.shade_header_bottom_margin_tablet) : this.mView.getPaddingTop(), this.mView.getPaddingRight(), this.mView.getPaddingBottom());
    }

    public final void updateResources() {
        new ArrayList().add(new Rect(0, 0, 0, 0));
        this.mDualToneHandler.getSingleColor(0.0f);
    }
}
