package com.android.systemui.keyguard;

import android.app.IActivityTaskManager;
import com.android.internal.widget.LockPatternUtils;
import com.android.systemui.keyguard.domain.interactor.KeyguardDismissTransitionInteractor;
import com.android.systemui.keyguard.domain.interactor.KeyguardShowWhileAwakeInteractor;
import com.android.systemui.keyguard.ui.binder.KeyguardSurfaceBehindParamsApplier;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.wm.shell.keyguard.KeyguardTransitions;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes2.dex */
public final class WindowManagerLockscreenVisibilityManager {
    public static final String TAG = null;
    public final IActivityTaskManager activityTaskManagerService;
    public final Executor executor;
    public final KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor;
    public final KeyguardShowWhileAwakeInteractor keyguardShowWhileAwakeInteractor;
    public final KeyguardStateController keyguardStateController;
    public final KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindAnimator;
    public final LockPatternUtils lockPatternUtils;
    public final SelectedUserInteractor selectedUserInteractor;
    public final Executor uiBgExecutor;

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

    public WindowManagerLockscreenVisibilityManager(Executor executor, Executor executor2, IActivityTaskManager iActivityTaskManager, KeyguardStateController keyguardStateController, KeyguardSurfaceBehindParamsApplier keyguardSurfaceBehindParamsApplier, KeyguardDismissTransitionInteractor keyguardDismissTransitionInteractor, KeyguardTransitions keyguardTransitions, SelectedUserInteractor selectedUserInteractor, LockPatternUtils lockPatternUtils, KeyguardShowWhileAwakeInteractor keyguardShowWhileAwakeInteractor) {
        this.executor = executor;
        this.uiBgExecutor = executor2;
        this.activityTaskManagerService = iActivityTaskManager;
        this.keyguardStateController = keyguardStateController;
        this.keyguardSurfaceBehindAnimator = keyguardSurfaceBehindParamsApplier;
        this.keyguardDismissTransitionInteractor = keyguardDismissTransitionInteractor;
        this.selectedUserInteractor = selectedUserInteractor;
        this.lockPatternUtils = lockPatternUtils;
        this.keyguardShowWhileAwakeInteractor = keyguardShowWhileAwakeInteractor;
    }
}
