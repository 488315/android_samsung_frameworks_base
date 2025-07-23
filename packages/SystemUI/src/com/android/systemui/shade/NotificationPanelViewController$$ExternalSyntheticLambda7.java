package com.android.systemui.shade;

import android.animation.ValueAnimator;
import android.graphics.Rect;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            default:
                Rect rect2 = NotificationPanelViewController.M_DUMMY_DIRTY_RECT;
                notificationPanelViewController.getClass();
                notificationPanelViewController.setOverExpansionInternal(((Float) valueAnimator.getAnimatedValue()).floatValue(), false);
                break;
        }
    }
}
