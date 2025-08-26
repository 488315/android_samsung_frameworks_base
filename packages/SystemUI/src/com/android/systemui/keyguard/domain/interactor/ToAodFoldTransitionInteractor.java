package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.shade.NotificationPanelViewController;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* loaded from: classes2.dex */
public final class ToAodFoldTransitionInteractor {
    public final ToAodFoldTransitionInteractor$foldAnimator$1 foldAnimator = new ToAodFoldTransitionInteractor$foldAnimator$1(this);
    public final KeyguardClockInteractor keyguardClockInteractor;
    public NotificationPanelViewController.ShadeFoldAnimatorImpl parentAnimator;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        Reflection.getOrCreateKotlinClass(ToAodFoldTransitionInteractor.class).getSimpleName().getClass();
    }

    public ToAodFoldTransitionInteractor(KeyguardClockInteractor keyguardClockInteractor) {
        this.keyguardClockInteractor = keyguardClockInteractor;
    }
}
