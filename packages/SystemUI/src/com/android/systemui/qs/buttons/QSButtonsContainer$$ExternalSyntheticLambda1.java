package com.android.systemui.qs.buttons;

import android.os.UserManager;
import com.android.systemui.shade.SecPanelSplitHelper;

/* loaded from: classes2.dex */
public final /* synthetic */ class QSButtonsContainer$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ QSButtonsContainer f$0;

    public /* synthetic */ QSButtonsContainer$$ExternalSyntheticLambda1(QSButtonsContainer qSButtonsContainer) {
        this.f$0 = qSButtonsContainer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        QSButtonsContainer qSButtonsContainer = this.f$0;
        int i = QSButtonsContainer.$r8$clinit;
        qSButtonsContainer.getClass();
        int i2 = 8;
        if (UserManager.supportsMultipleUsers()) {
            qSButtonsContainer.mMumButton.mMumAndDexHelper.updateMumSwitchVisibility();
            qSButtonsContainer.mMumButton.setVisibility(!qSButtonsContainer.mExpanded ? 8 : 0);
        }
        qSButtonsContainer.mSettingsButton.setVisibility(0);
        if (SecPanelSplitHelper.isEnabled()) {
            qSButtonsContainer.mPowerButton.setVisibility(0);
            QSEditButton qSEditButton = qSButtonsContainer.mEditButton;
            if (!qSButtonsContainer.isMassiveLandscape() && qSButtonsContainer.mEditButton.isSupportEditButton()) {
                i2 = 0;
            }
            qSEditButton.setVisibility(i2);
        } else {
            qSButtonsContainer.mPowerButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
            QSEditButton qSEditButton2 = qSButtonsContainer.mEditButton;
            if (!qSButtonsContainer.isMassiveLandscape() && qSButtonsContainer.mExpanded && qSButtonsContainer.mEditButton.isSupportEditButton()) {
                i2 = 0;
            }
            qSEditButton2.setVisibility(i2);
        }
        qSButtonsContainer.setClickable(true);
    }
}
