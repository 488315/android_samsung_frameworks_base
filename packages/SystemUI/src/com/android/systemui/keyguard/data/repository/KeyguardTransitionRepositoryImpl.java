package com.android.systemui.keyguard.data.repository;

import android.animation.ValueAnimator;
import android.os.Trace;
import android.util.Log;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.keyguard.shared.model.TransitionState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import java.util.UUID;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;
import kotlinx.coroutines.sync.MutexImpl;
import kotlinx.coroutines.sync.MutexKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class KeyguardTransitionRepositoryImpl implements KeyguardTransitionRepository {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _currentTransitionInfo;
    public final MutexImpl _currentTransitionMutex;
    public final SharedFlowImpl _transitions;
    public final ReadonlyStateFlow currentTransitionInfoInternal;
    public ValueAnimator lastAnimator;
    public TransitionStep lastStep;
    public final CoroutineDispatcher mainDispatcher;
    public final Flow transitions;
    public UUID updateTransitionId;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[TransitionModeOnCanceled.values().length];
            try {
                iArr[TransitionModeOnCanceled.LAST_VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TransitionModeOnCanceled.RESET.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TransitionModeOnCanceled.REVERSE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[TransitionState.values().length];
            try {
                iArr2[TransitionState.STARTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[TransitionState.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[TransitionState.CANCELED.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    static {
        new Companion(null);
    }

    public KeyguardTransitionRepositoryImpl(CoroutineDispatcher coroutineDispatcher) {
        this.mainDispatcher = coroutineDispatcher;
        SharedFlowImpl MutableSharedFlow = SharedFlowKt.MutableSharedFlow(2, 20, BufferOverflow.DROP_OLDEST);
        this._transitions = MutableSharedFlow;
        this.transitions = FlowKt.distinctUntilChanged(FlowKt.asSharedFlow(MutableSharedFlow));
        this.lastStep = new TransitionStep(null, null, 0.0f, null, null, 31, null);
        this._currentTransitionMutex = MutexKt.Mutex$default();
        KeyguardState keyguardState = KeyguardState.OFF;
        StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(new TransitionInfo("", keyguardState, keyguardState, null, null, 16, null));
        this._currentTransitionInfo = MutableStateFlow;
        this.currentTransitionInfoInternal = FlowKt.asStateFlow(MutableStateFlow);
        emitTransition(new TransitionStep(keyguardState, keyguardState, 1.0f, TransitionState.FINISHED, null, 16, null), false);
    }

    public final void emitTransition(TransitionStep transitionStep, boolean z) {
        TransitionState transitionState = TransitionState.RUNNING;
        TransitionState transitionState2 = transitionStep.transitionState;
        if (transitionState2 != transitionState) {
            String str = z ? " (manual)" : "";
            String str2 = "Transition: " + transitionStep.from + " -> " + transitionStep.to + str;
            int hashCode = str2.hashCode();
            int i = WhenMappings.$EnumSwitchMapping$1[transitionState2.ordinal()];
            if (i == 1) {
                Trace.beginAsyncSection(str2, hashCode);
            } else if (i == 2) {
                Trace.endAsyncSection(str2, hashCode);
            } else if (i == 3) {
                Trace.endAsyncSection(str2, hashCode);
            }
            Log.i("KeyguardTransitionRepository", transitionState2.name() + " transition: " + transitionStep + str);
        }
        this._transitions.tryEmit(transitionStep);
        this.lastStep = transitionStep;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0095 A[PHI: r9
      0x0095: PHI (r9v9 java.lang.Object) = (r9v8 java.lang.Object), (r9v1 java.lang.Object) binds: [B:19:0x0092, B:12:0x002a] A[DONT_GENERATE, DONT_INLINE], RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0094 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:23:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo r8, kotlin.coroutines.Continuation r9) {
        /*
            r7 = this;
            boolean r0 = r9 instanceof com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1
            if (r0 == 0) goto L13
            r0 = r9
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1 r0 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1 r0 = new com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$1
            r0.<init>(r7, r9)
        L18:
            java.lang.Object r9 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 3
            r5 = 1
            if (r2 == 0) goto L4f
            if (r2 == r5) goto L42
            r7 = 2
            if (r2 == r7) goto L36
            if (r2 != r4) goto L2e
            kotlin.ResultKt.throwOnFailure(r9)
            goto L95
        L2e:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L36:
            java.lang.Object r7 = r0.L$1
            com.android.systemui.keyguard.shared.model.TransitionInfo r7 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r7
            java.lang.Object r8 = r0.L$0
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r8 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r8
            kotlin.ResultKt.throwOnFailure(r9)
            goto L7f
        L42:
            java.lang.Object r7 = r0.L$1
            r8 = r7
            com.android.systemui.keyguard.shared.model.TransitionInfo r8 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r8
            java.lang.Object r7 = r0.L$0
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl r7 = (com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl) r7
            kotlin.ResultKt.throwOnFailure(r9)
            goto L79
        L4f:
            kotlin.ResultKt.throwOnFailure(r9)
            kotlinx.coroutines.flow.StateFlowImpl r9 = r7._currentTransitionInfo
            r9.setValue(r8)
            java.lang.StringBuilder r9 = new java.lang.StringBuilder
            java.lang.String r2 = "(Internal) Setting current transition info: "
            r9.<init>(r2)
            r9.append(r8)
            java.lang.String r9 = r9.toString()
            java.lang.String r2 = "KeyguardTransitionRepository"
            android.util.Log.d(r2, r9)
            r0.L$0 = r7
            r0.L$1 = r8
            r0.label = r5
            kotlinx.coroutines.sync.MutexImpl r9 = r7._currentTransitionMutex
            java.lang.Object r9 = r9.lock(r3, r0)
            if (r9 != r1) goto L79
            return r1
        L79:
            r7.getClass()
            r6 = r8
            r8 = r7
            r7 = r6
        L7f:
            kotlinx.coroutines.CoroutineDispatcher r9 = r8.mainDispatcher
            com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1 r2 = new com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl$startTransition$$inlined$withContext$1
            java.lang.String r5 = "KeyguardTransitionRepository#startTransition"
            r2.<init>(r5, r3, r8, r7)
            r0.L$0 = r3
            r0.L$1 = r3
            r0.label = r4
            java.lang.Object r9 = kotlinx.coroutines.BuildersKt.withContext(r9, r2, r0)
            if (r9 != r1) goto L95
            return r1
        L95:
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl.startTransition(com.android.systemui.keyguard.shared.model.TransitionInfo, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void updateTransition(UUID uuid, float f, TransitionState transitionState) {
        if (!Intrinsics.areEqual(this.updateTransitionId, uuid)) {
            Log.wtf("KeyguardTransitionRepository", "Attempting to update with old/invalid transitionId: " + uuid);
        } else {
            if (transitionState == TransitionState.FINISHED || transitionState == TransitionState.CANCELED) {
                this.updateTransitionId = null;
            }
            emitTransition(TransitionStep.copy$default(this.lastStep, f, transitionState, 19), true);
        }
    }
}
