package com.android.systemui.keyguard;

import android.app.IActivityTaskManager;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class WindowManagerLockscreenVisibilityManager {
    public static final String TAG = null;
    public final IActivityTaskManager activityTaskManagerService;
    public final Executor executor;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindAnimator;

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

    public WindowManagerLockscreenVisibilityManager(Executor executor, IActivityTaskManager iActivityTaskManager, KeyguardStateController keyguardStateController, KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier, KeyguardTransitionInteractor keyguardTransitionInteractor) {
        this.executor = executor;
    }
}
