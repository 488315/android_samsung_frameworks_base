package com.android.systemui.qs.buttons;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.settingslib.applications.InterestingConfigChanges;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.EdmMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.qs.SecQSPanelController;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.qs.buttons.QSButtonsContainer;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.util.ShadowDelegateUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.ViewUtil;

/* loaded from: classes2.dex */
public class QSEditButton extends FrameLayout implements QSButtonsContainer.CloseTooltipWindow {
    public static final InterestingConfigChanges configChanges = new InterestingConfigChanges(268435456);
    public final Context mContext;
    public ImageButton mEditButton;
    public View mEditContainer;
    public SecQSPanelController mQsPanelController;
    public final SecQSPanelResourcePicker mResourcePicker;
    public QSTooltipWindow mTipWindow;
    public final int mToolTipString;

    public QSEditButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        configChanges.applyNewConfig(context.getResources());
        this.mResourcePicker = (SecQSPanelResourcePicker) Dependency.sDependency.getDependencyInner(SecQSPanelResourcePicker.class);
        this.mTipWindow = QSTooltipWindow.getInstance(context);
        this.mToolTipString = R.string.tooltip_quick_settings_edit;
    }

    @Override // com.android.systemui.qs.buttons.QSButtonsContainer.CloseTooltipWindow
    public final void closeTooltip() {
        this.mTipWindow.hideToolTip();
    }

    public final boolean isSupportEditButton() {
        CustomSdkMonitor customSdkMonitor;
        if (this.mQsPanelController == null) {
            return false;
        }
        SecQSPanelResourceCommon secQSPanelResourceCommon = this.mResourcePicker.resourcePickHelper.getTargetPicker().common;
        return (secQSPanelResourceCommon.isEmergencyMode || (customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) secQSPanelResourceCommon.knoxStateMonitor$delegate.getValue())).mCustomSdkMonitor) == null || customSdkMonitor.mKnoxCustomQuickPanelEditMode == 0) ? false : true;
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (configChanges.applyNewConfig(this.mContext.getResources())) {
            this.mTipWindow = QSTooltipWindow.getInstance(this.mContext);
        }
        updateTouchTargetArea$2();
    }

    @Override // android.view.View
    public final void onFinishInflate() {
        super.onFinishInflate();
        this.mEditButton = (ImageButton) findViewById(R.id.edit_button);
        this.mEditContainer = findViewById(R.id.edit_button_container);
        this.mEditButton.semSetHoverPopupType(0);
        this.mEditButton.setOnHoverListener(new QSEditButton$$ExternalSyntheticLambda0());
        this.mEditButton.setImageDrawable(ShadowDelegateUtil.INSTANCE.createShadowDrawable(this.mContext.getDrawable(R.drawable.tw_ic_ab_edit_mtrl), this.mContext.getResources().getDimension(R.dimen.shadow_radius), 0.2f, this.mContext.getResources().getDimensionPixelSize(R.dimen.qs_button_size)));
        updateTouchTargetArea$2();
        this.mEditButton.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.qs.buttons.QSEditButton$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                final QSEditButton qSEditButton = this.f$0;
                InterestingConfigChanges interestingConfigChanges = QSEditButton.configChanges;
                EdmMonitor edmMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) Dependency.sDependency.getDependencyInner(KnoxStateMonitor.class))).mEdmMonitor;
                if (edmMonitor != null && !edmMonitor.mSettingsChangesAllowed) {
                    Context context = qSEditButton.mContext;
                    Toast.makeText(context, context.getString(R.string.quick_settings_it_policy_prevents, context.getString(R.string.qs_edit_title)), 0).show();
                } else if (qSEditButton.mQsPanelController != null) {
                    ((ActivityStarter) Dependency.sDependency.getDependencyInner(ActivityStarter.class)).postQSCustomizerRunnableDismissingKeyguard(new Runnable() { // from class: com.android.systemui.qs.buttons.QSEditButton$$ExternalSyntheticLambda4
                        @Override // java.lang.Runnable
                        public final void run() {
                            qSEditButton.mQsPanelController.showEdit();
                        }
                    });
                    SystemUIAnalytics.sendEventLog(SystemUIAnalytics.getCurrentScreenID(), SystemUIAnalytics.EID_EDIT_BUTTON);
                }
            }
        });
        this.mEditContainer.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.qs.buttons.QSEditButton$$ExternalSyntheticLambda2
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view, MotionEvent motionEvent) {
                return this.f$0.mEditButton.onTouchEvent(motionEvent);
            }
        });
        this.mEditButton.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.android.systemui.qs.buttons.QSEditButton$$ExternalSyntheticLambda3
            @Override // android.view.View.OnLongClickListener
            public final boolean onLongClick(View view) throws Resources.NotFoundException {
                QSEditButton qSEditButton = this.f$0;
                if (qSEditButton.mTipWindow.isTooltipShown()) {
                    return true;
                }
                qSEditButton.mTipWindow.showToolTip(view, qSEditButton.mToolTipString);
                QSButtonsContainer qSButtonsContainer = (QSButtonsContainer) ViewUtil.findParentOfType(qSEditButton, QSButtonsContainer.class);
                if (qSButtonsContainer == null) {
                    return true;
                }
                qSButtonsContainer.mCloseTooltipWindow = qSEditButton;
                return true;
            }
        });
    }

    public final void updateTouchTargetArea$2() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        SecQSPanelResourcePicker secQSPanelResourcePicker = this.mResourcePicker;
        layoutParams.width = secQSPanelResourcePicker.resourcePickHelper.getTargetPicker().getButtonsWidth(this.mContext);
        layoutParams.height = this.mContext.getResources().getDimensionPixelSize(R.dimen.sec_qs_buttons_container_height);
        setLayoutParams(layoutParams);
    }
}
