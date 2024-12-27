package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class HideNotificationsInteractor {
    public final AnimationStatusRepository animationsStatus;
    public final PowerInteractor powerInteractor;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

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

    public HideNotificationsInteractor(UnfoldTransitionInteractor unfoldTransitionInteractor, ConfigurationInteractor configurationInteractor, AnimationStatusRepository animationStatusRepository, PowerInteractor powerInteractor) {
        this.animationsStatus = animationStatusRepository;
    }
}
