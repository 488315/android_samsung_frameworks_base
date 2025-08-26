package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.CoreStartable;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class KeyguardTransitionCoreStartable implements CoreStartable {
    public final KeyguardTransitionAuditLogger auditLogger;
    public final Set interactors;
    public final KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor;
    public final KeyguardStateCallbackInteractor keyguardStateCallbackInteractor;
    public final StatusBarDisableFlagsInteractor statusBarDisableFlagsInteractor;

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

    public KeyguardTransitionCoreStartable(Set<TransitionInteractor> set, KeyguardTransitionAuditLogger keyguardTransitionAuditLogger, StatusBarDisableFlagsInteractor statusBarDisableFlagsInteractor, KeyguardStateCallbackInteractor keyguardStateCallbackInteractor, KeyguardServiceShowLockscreenInteractor keyguardServiceShowLockscreenInteractor) {
        this.interactors = set;
        this.auditLogger = keyguardTransitionAuditLogger;
        this.statusBarDisableFlagsInteractor = statusBarDisableFlagsInteractor;
        this.keyguardStateCallbackInteractor = keyguardStateCallbackInteractor;
        this.keyguardServiceShowLockscreenInteractor = keyguardServiceShowLockscreenInteractor;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        for (TransitionInteractor transitionInteractor : this.interactors) {
            if (transitionInteractor instanceof FromPrimaryBouncerTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromAodTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromGoneTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromLockscreenTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromDreamingTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromGlanceableHubTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromOccludedTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else if (transitionInteractor instanceof FromDozingTransitionInteractor) {
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            } else {
                if (!(transitionInteractor instanceof FromAlternateBouncerTransitionInteractor)) {
                    throw new NoWhenBranchMatchedException();
                }
                Log.d("KeyguardTransitionCoreStartable", "Started " + transitionInteractor);
            }
            transitionInteractor.start();
        }
        KeyguardTransitionAuditLogger keyguardTransitionAuditLogger = this.auditLogger;
        keyguardTransitionAuditLogger.getClass();
        KeyguardTransitionAuditLogger$start$1 keyguardTransitionAuditLogger$start$1 = new KeyguardTransitionAuditLogger$start$1(keyguardTransitionAuditLogger, null);
        CoroutineScope coroutineScope = keyguardTransitionAuditLogger.scope;
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, keyguardTransitionAuditLogger$start$1, 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$2(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$3(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$4(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$5(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$6(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$7(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$8(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$9(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$10(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$11(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$12(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$13(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$14(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$15(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$16(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$17(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$18(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$19(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$20(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$21(keyguardTransitionAuditLogger, null), 7);
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$22(keyguardTransitionAuditLogger, null), 7);
        this.statusBarDisableFlagsInteractor.getClass();
        this.keyguardStateCallbackInteractor.getClass();
        this.keyguardServiceShowLockscreenInteractor.start();
    }
}
