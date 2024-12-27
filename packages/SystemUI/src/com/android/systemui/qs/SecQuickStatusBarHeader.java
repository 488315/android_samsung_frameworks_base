package com.android.systemui.qs;

import android.content.Context;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.DisplayCutout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowInsets;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.android.systemui.Dependency;
import com.android.systemui.DualToneHandler;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.QSClockHeaderView;

public class SecQuickStatusBarHeader extends FrameLayout {
    public QSClockHeaderView mClockView;
    public int mCutOutHeight;
    public View mDateButtonContainer;
    public TextView mDateView;
    public boolean mExpanded;
    public SecQuickQSPanel mHeaderQsPanel;
    public final StringBuilder mLogBuilder;
    public int mNavBarHeight;
    public View mPrivacyChip;
    public boolean mQsDisabled;
    public final SecQSPanelResourcePicker mResourcePicker;
    public View mView;

    public SecQuickStatusBarHeader(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mCutOutHeight = 0;
        this.mNavBarHeight = 0;
        this.mLogBuilder = new StringBuilder();
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        new DualToneHandler(new ContextThemeWrapper(context, R.style.Theme_SystemUI_QuickSettings_Header));
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
            updateContentsPadding();
        }
        return super.onApplyWindowInsets(windowInsets);
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateContentsPadding();
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
        this.mClockView.setTextColor(((FrameLayout) this).mContext.getColor(R.color.qs_status_bar_clock_color));
        this.mDateView.setTextColor(((FrameLayout) this).mContext.getColor(R.color.qs_status_bar_clock_color));
        updateContentsPadding();
    }

    @Override // android.view.ViewGroup
    public final boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        this.mLogBuilder.setLength(0);
        StringBuilder sb = this.mLogBuilder;
        sb.append("mExpanded: ");
        sb.append(this.mExpanded);
        return super.onInterceptTouchEvent(motionEvent);
    }

    public final void updateContentsPadding() {
        Context context = getContext();
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mDateButtonContainer.getLayoutParams();
        layoutParams.topMargin = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_top);
        layoutParams.bottomMargin = context.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_margin_bottom);
        this.mDateButtonContainer.setLayoutParams(layoutParams);
        int dateButtonContainerPadding = this.mResourcePicker.resourcePickHelper.getTargetPicker().getDateButtonContainerPadding(context);
        int notificationSidePadding = this.mResourcePicker.resourcePickHelper.getTargetPicker().getNotificationSidePadding(context, false);
        View view = this.mDateButtonContainer;
        view.setPaddingRelative(notificationSidePadding, view.getPaddingTop(), dateButtonContainerPadding, this.mDateButtonContainer.getPaddingBottom());
        View view2 = this.mView;
        view2.setPadding(view2.getPaddingLeft(), this.mView.getPaddingTop(), this.mView.getPaddingRight(), this.mView.getPaddingBottom());
    }
}
