package com.android.systemui.statusbar.notification.domain.interactor;

import android.util.Log;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.statusbar.notification.data.repository.NotificationLaunchAnimationRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class NotificationLaunchAnimationInteractor {
    public final NotificationLaunchAnimationRepository repository;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public NotificationLaunchAnimationInteractor(NotificationLaunchAnimationRepository notificationLaunchAnimationRepository) {
        this.repository = notificationLaunchAnimationRepository;
    }

    public final void setIsLaunchAnimationRunning(boolean z) {
        if (ActivityTransitionAnimator.DEBUG_TRANSITION_ANIMATION) {
            Log.d("NotificationLaunchAnimationInteractor", "setIsLaunchAnimationRunning(running=" + z + ")");
        }
        this.repository.isLaunchAnimationRunning.updateState(null, Boolean.valueOf(z));
    }
}
