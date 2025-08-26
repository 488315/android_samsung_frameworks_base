package com.android.systemui.qs.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.FactoryTest;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Slog;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.globalactions.GlobalActionsComponent;
import com.android.systemui.globalactions.presentation.view.SamsungGlobalActionsDialog;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewUtil;

/* loaded from: classes2.dex */
public class QSPowerButton extends FrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final InterestingConfigChanges configChanges = new InterestingConfigChanges(268435456);
    public final Context mContext;
    public ImageButton mPowerButton;
    public final SecQSPanelResourcePicker mResourcePicker;
    public QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    public QSPowerButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        configChanges.applyNewConfig(context.getResources());
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_power_off;
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configChanges.applyNewConfig(this.mContext.getResources())) {
            this.mTipWindow = QSTooltipWindow.getInstance(this.mContext);
        }
        updateTouchTargetArea$3();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        ImageButton imageButton = (ImageButton) findViewById(R.id.power_button);
        this.mPowerButton = imageButton;
        imageButton.semSetHoverPopupType(0);
        this.mPowerButton.setOnHoverListener(new QSPowerButton$$ExternalSyntheticLambda0());
        this.mPowerButton.setImageDrawable(ShadowDelegateUtil.INSTANCE.createShadowDrawable(this.mContext.getDrawable(R.drawable.tw_ic_ab_power_mtrl), this.mContext.getResources().getDimension(R.dimen.shadow_radius), 0.2f, this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size)));
        updateTouchTargetArea$3();
        this.mPowerButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSPowerButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                QSPowerButton qSPowerButton = this.f$0;
                InterestingConfigChanges interestingConfigChanges = QSPowerButton.configChanges;
                qSPowerButton.getClass();
                Log.d("QSPowerButton", "!@[Shutdown] Click power off button.");
                if (FactoryTest.isLongPressOnPowerOffEnabled() || FactoryTest.isAutomaticTestMode(qSPowerButton.mContext)) {
                    Slog.d("QSPowerButton", "!@long press power shutdown by power icon of quickpanel");
                    ((GlobalActionsComponent) Dependency.sDependency.getDependencyInner(GlobalActionsComponent.class)).shutdown();
                } else {
                    SamsungGlobalActionsDialog.showGlobalActionsfromQuickPanel();
                }
                SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_POWER_OFF_BUTTON);
            }
        });
        findViewById(R.id.power_button_container).setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.buttons.QSPowerButton$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.mPowerButton.onTouchEvent(motionEvent);
            }
        });
        this.mPowerButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSPowerButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) throws Resources.NotFoundException {
                QSPowerButton qSPowerButton = this.f$0;
                if (qSPowerButton.mTipWindow.isTooltipShown()) {
                    return true;
                }
                qSPowerButton.mTipWindow.showToolTip(view, qSPowerButton.mToolTipString);
                QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) ViewUtil.findParentOfType(qSPowerButton, QSButtonsContainer.class);
                if (qSButtonsContainer == null) {
                    return true;
                }
                qSButtonsContainer.mCloseTooltipWindow = qSPowerButton;
                return true;
            }
        });
    }

    public final void updateTouchTargetArea$3() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getButtonsWidth(this.mContext);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
