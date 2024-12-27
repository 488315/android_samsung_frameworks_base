package com.android.systemui.keyguard.data.repository;

import com.android.systemui.keyguard.shared.model.KeyguardState;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class LockscreenSceneTransitionRepository {
    public static final Companion Companion = new Companion(null);
    public static final KeyguardState DEFAULT_STATE = KeyguardState.LOCKSCREEN;
    public final StateFlowImpl nextLockscreenTargetState = StateFlowKt.MutableStateFlow(DEFAULT_STATE);

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }
}
