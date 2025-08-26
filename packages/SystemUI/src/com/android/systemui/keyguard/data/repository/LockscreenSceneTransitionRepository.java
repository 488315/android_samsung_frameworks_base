package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* loaded from: classes2.dex */
public final class LockscreenSceneTransitionRepository {
    public static final Companion Companion = new Companion(null);
    public static final KeyguardState DEFAULT_STATE = KeyguardState.LOCKSCREEN;
    public final StateFlowImpl nextLockscreenTargetState = StateFlowKt.MutableStateFlow(null);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
