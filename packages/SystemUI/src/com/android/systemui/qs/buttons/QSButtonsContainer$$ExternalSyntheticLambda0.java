package com.android.systemui.qs.buttons;

import android.os.UserManager;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.panelresource.SecQSPanelResourceCommon;
import com.android.systemui.shade.SecPanelSplitHelper;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSButtonsContainer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ QSButtonsContainer f$0;

    public /* synthetic */ QSButtonsContainer$$ExternalSyntheticLambda0(QSButtonsContainer qSButtonsContainer) {
        this.f$0 = qSButtonsContainer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        CustomSdkMonitor customSdkMonitor;
        QSButtonsContainer qSButtonsContainer = this.f$0;
        int i = QSButtonsContainer.$r8$clinit;
        qSButtonsContainer.getClass();
        if (UserManager.supportsMultipleUsers()) {
            qSButtonsContainer.mMumButton.mMumAndDexHelper.updateMumSwitchVisibility();
            qSButtonsContainer.mMumButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
        }
        if (SecPanelSplitHelper.isEnabled()) {
            qSButtonsContainer.mSettingsButton.setVisibility(0);
            qSButtonsContainer.mPowerButton.setVisibility(0);
            qSButtonsContainer.mEditButton.setVisibility(qSButtonsContainer.isMassiveLandscape() ? 4 : 0);
        } else {
            qSButtonsContainer.mSettingsButton.setVisibility(0);
            qSButtonsContainer.mPowerButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
            QSEditButton qSEditButton = qSButtonsContainer.mEditButton;
            if (!qSButtonsContainer.isMassiveLandscape() && qSButtonsContainer.mExpanded) {
                QSEditButton qSEditButton2 = qSButtonsContainer.mEditButton;
                if (qSEditButton2.mQsPanelController != null) {
                    SecQSPanelResourceCommon secQSPanelResourceCommon = qSEditButton2.mResourcePicker.resourcePickHelper.getTargetPicker().common;
                    if (!secQSPanelResourceCommon.isEmergencyMode && (customSdkMonitor = ((KnoxStateMonitorImpl) ((KnoxStateMonitor) secQSPanelResourceCommon.knoxStateMonitor$delegate.getValue())).mCustomSdkMonitor) != null && customSdkMonitor.mKnoxCustomQuickPanelEditMode != 0) {
                        r1 = 0;
                    }
                }
            }
            qSEditButton.setVisibility(r1);
        }
        qSButtonsContainer.setClickable(true);
    }
}
