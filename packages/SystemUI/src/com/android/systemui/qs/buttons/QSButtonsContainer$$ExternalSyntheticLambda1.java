package com.android.systemui.qs.buttons;

import android.os.UserManager;
import com.android.systemui.shade.SecPanelSplitHelper;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
        if (UserManager.supportsMultipleUsers()) {
            qSButtonsContainer.mMumButton.mMumAndDexHelper.updateMumSwitchVisibility();
            qSButtonsContainer.mMumButton.setVisibility(!qSButtonsContainer.mExpanded ? 4 : 0);
        }
        qSButtonsContainer.mSettingsButton.setVisibility(0);
        if (SecPanelSplitHelper.isEnabled()) {
            qSButtonsContainer.mPowerButton.setVisibility(0);
            qSButtonsContainer.mEditButton.setVisibility((qSButtonsContainer.isMassiveLandscape() || !qSButtonsContainer.mEditButton.isSupportEditButton()) ? 8 : 0);
        } else {
            qSButtonsContainer.mPowerButton.setVisibility(qSButtonsContainer.mExpanded ? 0 : 4);
            qSButtonsContainer.mEditButton.setVisibility((!qSButtonsContainer.isMassiveLandscape() && qSButtonsContainer.mExpanded && qSButtonsContainer.mEditButton.isSupportEditButton()) ? 0 : 8);
        }
        qSButtonsContainer.setClickable(true);
    }
}
