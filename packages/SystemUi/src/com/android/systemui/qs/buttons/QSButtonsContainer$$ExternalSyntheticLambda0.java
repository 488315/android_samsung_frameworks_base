package com.android.systemui.qs.buttons;

import android.view.View;
import com.android.systemui.knox.CustomSdkMonitor;
import com.android.systemui.knox.KnoxStateMonitorImpl;
import com.android.systemui.qs.SecQSPanelResourcePicker;
import com.android.systemui.util.DeviceState;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class QSButtonsContainer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ QSButtonsContainer f$0;

    public /* synthetic */ QSButtonsContainer$$ExternalSyntheticLambda0(QSButtonsContainer qSButtonsContainer) {
        this.f$0 = qSButtonsContainer;
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0066  */
    @Override // java.lang.Runnable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void run() {
        boolean z;
        boolean z2;
        boolean z3;
        QSButtonsContainer qSButtonsContainer = this.f$0;
        int i = QSButtonsContainer.$r8$clinit;
        qSButtonsContainer.getClass();
        int i2 = 4;
        if (DeviceState.supportsMultipleUsers()) {
            qSButtonsContainer.mMumButton.mMumAndDexHelper.updateMumSwitchVisibility();
            qSButtonsContainer.mMumButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
        }
        View view = qSButtonsContainer.mSettingsButton.mSettingsButtonBadge;
        if (view != null) {
            view.setVisibility(8);
        }
        qSButtonsContainer.mPowerButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
        QSEditButton qSEditButton = qSButtonsContainer.mEditButton;
        if (qSButtonsContainer.mExpanded) {
            if (qSEditButton.mQsPanelController != null) {
                SecQSPanelResourcePicker secQSPanelResourcePicker = qSEditButton.mResourcePicker;
                if (!secQSPanelResourcePicker.mSettingsHelper.isEmergencyMode()) {
                    CustomSdkMonitor customSdkMonitor = ((KnoxStateMonitorImpl) secQSPanelResourcePicker.mKnoxStateMonitor).mCustomSdkMonitor;
                    if (customSdkMonitor != null) {
                        if (customSdkMonitor.mKnoxCustomQuickPanelEditMode != 0) {
                            z3 = true;
                            if (z3) {
                                z2 = false;
                                z = !z2;
                            }
                        }
                    }
                    z3 = false;
                    if (z3) {
                    }
                }
                z2 = true;
                z = !z2;
            } else {
                z = false;
            }
            if (z) {
                i2 = 0;
            }
        }
        qSEditButton.setVisibility(i2);
        qSButtonsContainer.setClickable(true);
    }
}
