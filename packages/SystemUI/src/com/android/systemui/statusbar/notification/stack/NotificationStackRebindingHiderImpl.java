package com.android.systemui.statusbar.notification.stack;

import android.animation.ObjectAnimator;
import com.android.app.animation.Interpolators;

/* loaded from: classes3.dex */
public final class NotificationStackRebindingHiderImpl implements NotificationStackRebindingHider {
    public final NotificationStackScrollLayoutController nsslController;

    public NotificationStackRebindingHiderImpl(NotificationStackScrollLayoutController notificationStackScrollLayoutController) {
        this.nsslController = notificationStackScrollLayoutController;
    }

    public final void setVisible(boolean z, boolean z2) {
        NotificationStackScrollLayoutController notificationStackScrollLayoutController = this.nsslController;
        ObjectAnimator objectAnimator = notificationStackScrollLayoutController.mRebindAlphaAnimator;
        if (objectAnimator != null) {
            objectAnimator.cancel();
        }
        float f = z ? 1.0f : 0.0f;
        if (!z2) {
            NotificationStackScrollLayoutController.HIDE_DURING_REBINDING_PROPERTY.set(notificationStackScrollLayoutController, Float.valueOf(f));
            return;
        }
        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(notificationStackScrollLayoutController, NotificationStackScrollLayoutController.HIDE_DURING_REBINDING_PROPERTY, f);
        objectAnimatorOfFloat.setInterpolator(Interpolators.STANDARD);
        objectAnimatorOfFloat.setDuration(360L);
        notificationStackScrollLayoutController.mRebindAlphaAnimator = objectAnimatorOfFloat;
        objectAnimatorOfFloat.start();
    }
}
