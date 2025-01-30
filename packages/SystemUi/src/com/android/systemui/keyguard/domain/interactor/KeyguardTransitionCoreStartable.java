package com.android.systemui.keyguard.domain.interactor;

import android.util.Log;
import com.android.systemui.CoreStartable;
import java.util.Set;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyguardTransitionCoreStartable implements CoreStartable {
    public final KeyguardTransitionAuditLogger auditLogger;
    public final Set interactors;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

    public KeyguardTransitionCoreStartable(Set<TransitionInteractor> set, KeyguardTransitionAuditLogger keyguardTransitionAuditLogger) {
        this.interactors = set;
        this.auditLogger = keyguardTransitionAuditLogger;
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
        BuildersKt.launch$default(keyguardTransitionAuditLogger.scope, null, null, new KeyguardTransitionAuditLogger$start$1(keyguardTransitionAuditLogger, null), 3);
        KeyguardTransitionAuditLogger$start$2 keyguardTransitionAuditLogger$start$2 = new KeyguardTransitionAuditLogger$start$2(keyguardTransitionAuditLogger, null);
        CoroutineScope coroutineScope = keyguardTransitionAuditLogger.scope;
        BuildersKt.launch$default(coroutineScope, null, null, keyguardTransitionAuditLogger$start$2, 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$3(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$4(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$5(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$6(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$7(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$8(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$9(keyguardTransitionAuditLogger, null), 3);
        BuildersKt.launch$default(coroutineScope, null, null, new KeyguardTransitionAuditLogger$start$10(keyguardTransitionAuditLogger, null), 3);
    }
}
