package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.graphics.Rect;
import com.android.systemui.statusbar.notification.stack.NotificationStackScrollLayoutController;

/* loaded from: classes3.dex */
public final /* synthetic */ class NotificationPanelViewController$$ExternalSyntheticLambda7 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ NotificationPanelViewController f$0;

    public /* synthetic */ NotificationPanelViewController$$ExternalSyntheticLambda7(NotificationPanelViewController notificationPanelViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = notificationPanelViewController;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        int i = this.$r8$classId;
        NotificationPanelViewController notificationPanelViewController = this.f$0;
        switch (i) {
            case 0:
                Rect rect = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                notificationPanelViewController.mBottomAreaShadeAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                notificationPanelViewController.updateKeyguardSecBottomAreaAlpha();
                break;
            case 1:
                Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                notificationPanelViewController.setOverExpansionInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
                break;
            default:
                Rect rect3 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                NotificationStackScrollLayoutController notificationStackScrollLayoutController = notificationPanelViewController.mNotificationStackScrollLayoutController;
                notificationStackScrollLayoutController.mMaxAlphaForKeyguard = fFloatValue;
                notificationStackScrollLayoutController.mMaxAlphaForKeyguardSource = "MediaNowBar expand/collapse";
                notificationStackScrollLayoutController.updateAlpha$1$1();
                break;
        }
    }
}
