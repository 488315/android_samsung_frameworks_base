package com.android.systemui.statusbar.notification.stack.domain.interactor;

import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor;
import com.android.systemui.util.animation.data.repository.AnimationStatusRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class HideNotificationsInteractor {
    public final AnimationStatusRepository animationsStatus;
    public final PowerInteractor powerInteractor;
    public final UnfoldTransitionInteractor unfoldTransitionInteractor;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public HideNotificationsInteractor(UnfoldTransitionInteractor unfoldTransitionInteractor, ConfigurationInteractor configurationInteractor, AnimationStatusRepository animationStatusRepository, PowerInteractor powerInteractor) {
        this.unfoldTransitionInteractor = unfoldTransitionInteractor;
        this.animationsStatus = animationStatusRepository;
        this.powerInteractor = powerInteractor;
    }
}
