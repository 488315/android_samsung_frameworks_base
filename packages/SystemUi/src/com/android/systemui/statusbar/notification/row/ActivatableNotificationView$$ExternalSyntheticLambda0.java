package com.android.systemui.statusbar.notification.row;

import android.animation.ValueAnimator;
import com.android.systemui.statusbar.notification.NotificationUtils;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class ActivatableNotificationView$$ExternalSyntheticLambda0 implements ValueAnimator.AnimatorUpdateListener {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ ActivatableNotificationView f$0;

    public /* synthetic */ ActivatableNotificationView$$ExternalSyntheticLambda0(ActivatableNotificationView activatableNotificationView, int i) {
        this.$r8$classId = i;
        this.f$0 = activatableNotificationView;
    }

    @Override // android.animation.ValueAnimator.AnimatorUpdateListener
    public final void onAnimationUpdate(ValueAnimator valueAnimator) {
        switch (this.$r8$classId) {
            case 0:
                ActivatableNotificationView activatableNotificationView = this.f$0;
                activatableNotificationView.setBackgroundTintColor(NotificationUtils.interpolateColors(valueAnimator.getAnimatedFraction(), activatableNotificationView.mStartTint, activatableNotificationView.mTargetTint));
                break;
            default:
                ActivatableNotificationView activatableNotificationView2 = this.f$0;
                int i = ActivatableNotificationView.$r8$clinit;
                activatableNotificationView2.getClass();
                activatableNotificationView2.mAppearAnimationFraction = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                activatableNotificationView2.updateAppearAnimationAlpha();
                activatableNotificationView2.updateAppearRect();
                activatableNotificationView2.invalidate();
                break;
        }
    }
}
