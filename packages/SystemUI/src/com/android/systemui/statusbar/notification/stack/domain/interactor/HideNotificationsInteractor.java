package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

public final class HideNotificationsInteractor {
    public final AnimationStatusRepository animationsStatus;
    public final PowerInteractor powerInteractor;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

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

    public HideNotificationsInteractor(UnfoldTransitionInteractor unfoldTransitionInteractor, ConfigurationInteractor configurationInteractor, AnimationStatusRepository animationStatusRepository, PowerInteractor powerInteractor) {
        this.animationsStatus = animationStatusRepository;
    }
}
