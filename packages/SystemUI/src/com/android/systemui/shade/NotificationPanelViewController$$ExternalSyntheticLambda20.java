package com.android.systemui.shade;

import android.animation.ValueAnimator;
import com.android.keyguard.KeyguardStatusViewController;
import com.android.systemui.plugins.clocks.ClockController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda20 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda20(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        Object obj = this.f$0;
        switch (i) {
            case 0:
                NotificationPanelViewController notificationPanelViewController = (NotificationPanelViewController) obj;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mBottomAreaShadeAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                notificationPanelViewController.updateKeyguardBottomAreaAlpha();
                break;
            case 1:
                NotificationPanelViewController notificationPanelViewController2 = (NotificationPanelViewController) obj;
                notificationPanelViewController2.getClass();
                notificationPanelViewController2.setOverExpansionInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
                break;
            default:
                KeyguardStatusViewController keyguardStatusViewController = NotificationPanelViewController.this.mKeyguardStatusViewController;
                float animatedFraction = valueAnimator.getAnimatedFraction();
                ClockController clock = keyguardStatusViewController.mKeyguardClockSwitchController.getClock();
                if (clock != null) {
                    clock.getSmallClock().getAnimations().fold(animatedFraction);
                    clock.getLargeClock().getAnimations().fold(animatedFraction);
                    break;
                }
                break;
        }
    }
}
