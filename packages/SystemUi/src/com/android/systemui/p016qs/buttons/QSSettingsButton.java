package com.android.systemui.p016qs.buttons;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.doze.AODUi$$ExternalSyntheticLambda0;
import com.android.systemui.p016qs.SecQSPanelResourcePicker;
import com.android.systemui.p016qs.buttons.QSButtonsContainer;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.policy.DeviceProvisionedController;
import com.android.systemui.statusbar.policy.DeviceProvisionedControllerImpl;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.TouchDelegateUtil;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class QSSettingsButton extends RelativeLayout implements QSButtonsContainer.CloseTooltipWindow {
    public final ActivityStarter mActivityStarter;
    public final Context mContext;
    public final DeviceProvisionedController mDeviceProvisionedController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public ImageButton mSettingsButton;
    public View mSettingsButtonBadge;
    public View mSettingsContainer;
    public final QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    public QSSettingsButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.get(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_settings;
        this.mActivityStarter = (ActivityStarter) Dependency.get(ActivityStarter.class);
        this.mDeviceProvisionedController = (DeviceProvisionedController) Dependency.get(DeviceProvisionedController.class);
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTouchTargetArea();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mSettingsButton = (ImageButton) findViewById(R.id.settings_button);
        this.mSettingsContainer = findViewById(R.id.settings_button_container);
        this.mSettingsButton.semSetHoverPopupType(0);
        ImageButton imageButton = this.mSettingsButton;
        ShadowDelegateUtil shadowDelegateUtil = ShadowDelegateUtil.INSTANCE;
        Drawable drawable = this.mContext.getDrawable(R.drawable.tw_ic_ab_setting_mtrl);
        float dimension = this.mContext.getResources().getDimension(R.dimen.shadow_radius);
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size);
        shadowDelegateUtil.getClass();
        imageButton.setImageDrawable(ShadowDelegateUtil.createShadowDrawable(drawable, dimension, 0.2f, dimensionPixelSize));
        updateTouchTargetArea();
        this.mSettingsButtonBadge = this.mSettingsContainer.findViewById(R.id.settings_button_badge);
        this.mSettingsButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSSettingsButton.1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                if (!((DeviceProvisionedControllerImpl) QSSettingsButton.this.mDeviceProvisionedController).isCurrentUserSetup()) {
                    QSSettingsButton.this.mActivityStarter.postQSRunnableDismissingKeyguard(new AODUi$$ExternalSyntheticLambda0());
                    return;
                }
                QSSettingsButton qSSettingsButton = QSSettingsButton.this;
                qSSettingsButton.getClass();
                Log.d("QSSettingsButton", "Click settings button.");
                qSSettingsButton.mActivityStarter.startActivity(new Intent("android.settings.SETTINGS"), true, true);
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.sCurrentScreenID, "QPPE1003");
            }
        });
        TouchDelegateUtil touchDelegateUtil = TouchDelegateUtil.INSTANCE;
        View view = this.mSettingsContainer;
        ImageButton imageButton2 = this.mSettingsButton;
        touchDelegateUtil.getClass();
        TouchDelegateUtil.expandTouchAreaAsParent(view, imageButton2);
        this.mSettingsButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSSettingsButton.2
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view2) {
                if (QSSettingsButton.this.mTipWindow.isTooltipShown()) {
                    return true;
                }
                QSSettingsButton qSSettingsButton = QSSettingsButton.this;
                qSSettingsButton.mTipWindow.showToolTip(view2, qSSettingsButton.mToolTipString);
                ((QSButtonsContainer) QSSettingsButton.this.getParent()).mCloseTooltipWindow = QSSettingsButton.this;
                return true;
            }
        });
    }

    public final void updateTouchTargetArea() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        Context context = this.mContext;
        secQSPanelResourcePicker.getClass();
        layoutParams.width = SecQSPanelResourcePicker.getButtonsWidth(context);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
