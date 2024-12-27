package com.android.systemui.shade;

import android.animation.ValueAnimator;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class QuickSettingsControllerImpl$$ExternalSyntheticLambda32 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ QuickSettingsControllerImpl f$0;

    public /* synthetic */ QuickSettingsControllerImpl$$ExternalSyntheticLambda32(QuickSettingsControllerImpl quickSettingsControllerImpl, int i) {
        this.$r8$classId = i;
        this.f$0 = quickSettingsControllerImpl;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        QuickSettingsControllerImpl quickSettingsControllerImpl = this.f$0;
        switch (i) {
            case 0:
                quickSettingsControllerImpl.getClass();
                quickSettingsControllerImpl.setExpansionHeight(((Float) valueAnimator.getAnimatedValue()).floatValue());
                break;
            default:
                NotificationPanelViewController$$ExternalSyntheticLambda7 notificationPanelViewController$$ExternalSyntheticLambda7 = quickSettingsControllerImpl.mExpansionHeightSetToMaxListener;
                if (notificationPanelViewController$$ExternalSyntheticLambda7 != null) {
                    notificationPanelViewController$$ExternalSyntheticLambda7.onExpansionHeightSetToMax(true);
                }
                quickSettingsControllerImpl.mQs.setHeightOverride(((Integer) quickSettingsControllerImpl.mSizeChangeAnimator.getAnimatedValue()).intValue());
                break;
        }
    }
}
