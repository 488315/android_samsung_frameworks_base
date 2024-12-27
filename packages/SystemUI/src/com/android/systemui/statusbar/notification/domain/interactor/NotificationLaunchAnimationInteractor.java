package com.android.systemui.statusbar.notification.domain.interactor;

import android.util.Log;
import com.android.systemui.animation.ActivityTransitionAnimator;
import com.android.systemui.statusbar.notification.data.repository.NotificationLaunchAnimationRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class NotificationLaunchAnimationInteractor {
    public final NotificationLaunchAnimationRepository repository;

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
