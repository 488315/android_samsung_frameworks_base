package com.android.systemui.qs.buttons;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;

public class QSSettingsButton extends RelativeLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ImageButton mSettingsButton;
    public View mSettingsContainer;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    public QSSettingsButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_settings;
        this.mActivityStarter = (ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class);
        this.mDeviceProvisionedController = (DeviceProvisionedController) Dependency.sDependency.getDependencyInner(DeviceProvisionedController.class);
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTouchTargetArea$4();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSettingsButton = (ImageButton) findViewById(R.id.settings_button);
        this.mSettingsContainer = findViewById(R.id.settings_button_container);
        this.mSettingsButton.semSetHoverPopupType(0);
        this.mSettingsButton.setOnHoverListener(new QSSettingsButton$$ExternalSyntheticLambda0());
        this.mSettingsButton.setImageDrawable(ShadowDelegateUtil.INSTANCE.createShadowDrawable(this.mContext.getDrawable(R.drawable.tw_ic_ab_setting_mtrl), this.mContext.getResources().getDimension(R.dimen.shadow_radius), 0.2f, this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size)));
        updateTouchTargetArea$4();
        this.mSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSSettingsButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSSettingsButton qSSettingsButton = QSSettingsButton.this;
                if (!((DeviceProvisionedControllerImpl) qSSettingsButton.mDeviceProvisionedController).isCurrentUserSetup()) {
                    qSSettingsButton.mActivityStarter.postQSRunnableDismissingKeyguard(new QSSettingsButton$$ExternalSyntheticLambda4());
                    return;
                }
                Log.d("QSSettingsButton", "Click settings button.");
                qSSettingsButton.mActivityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, true);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_SETTING_BUTTON);
            }
        });
        this.mSettingsContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.buttons.QSSettingsButton$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return QSSettingsButton.this.mSettingsButton.onTouchEvent(motionEvent);
            }
        });
        this.mSettingsButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSSettingsButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) {
                QSSettingsButton qSSettingsButton = QSSettingsButton.this;
                if (qSSettingsButton.mTipWindow.isTooltipShown()) {
                    return true;
                }
                qSSettingsButton.mTipWindow.showToolTip(view, qSSettingsButton.mToolTipString);
                ((QSButtonsContainer) qSSettingsButton.getParent()).mCloseTooltipWindow = qSSettingsButton;
                return true;
            }
        });
    }

    public final void updateTouchTargetArea$4() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getButtonsWidth(this.mContext);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
