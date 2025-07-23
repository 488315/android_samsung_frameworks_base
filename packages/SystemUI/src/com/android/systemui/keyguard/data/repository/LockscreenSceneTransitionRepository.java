package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class LockscreenSceneTransitionRepository {
    public static final Companion Companion = new Companion(null);
    public static final KeyguardState DEFAULT_STATE = KeyguardState.LOCKSCREEN;
    public final StateFlowImpl nextLockscreenTargetState = StateFlowKt.MutableStateFlow(null);

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
