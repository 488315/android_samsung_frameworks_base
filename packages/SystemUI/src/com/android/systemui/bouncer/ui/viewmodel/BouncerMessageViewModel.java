package com.android.systemui.bouncer.ui.viewmodel;

import android.content.Context;
import com.android.systemui.Flags;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlags;
import com.android.systemui.bouncer.shared.flag.ComposeBouncerFlagsImpl;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFingerprintAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryInteractor;
import com.android.systemui.util.time.SystemClock;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class BouncerMessageViewModel {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final AuthenticationInteractor authenticationInteractor;
    public final BiometricMessageInteractor biometricMessageInteractor;
    public final BouncerInteractor bouncerInteractor;
    public final SystemClock clock;
    public final DeviceEntryInteractor deviceEntryInteractor;
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final DeviceEntryFingerprintAuthInteractor fingerprintInteractor;
    public final BouncerMessageViewModel$special$$inlined$map$1 isLockoutMessagePresent;
    public final StateFlowImpl lockoutMessage;
    public final StateFlowImpl message;
    public final SharedFlowImpl resetToDefault;
    public final SimBouncerInteractor simBouncerInteractor;

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

    /* JADX WARN: Type inference failed for: r3v1, types: [com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1] */
    public BouncerMessageViewModel(Context context, CoroutineScope coroutineScope, BouncerInteractor bouncerInteractor, SimBouncerInteractor simBouncerInteractor, AuthenticationInteractor authenticationInteractor, Flow flow, SystemClock systemClock, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceEntryInteractor deviceEntryInteractor, DeviceEntryFingerprintAuthInteractor deviceEntryFingerprintAuthInteractor, ComposeBouncerFlags composeBouncerFlags) {
        this.clock = systemClock;
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.isLockoutMessagePresent = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r5
                        if (r5 == 0) goto L38
                        r5 = r3
                        goto L39
                    L38:
                        r5 = 0
                    L39:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        StateFlowKt.MutableStateFlow(null);
        this.resetToDefault = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
        ((ComposeBouncerFlagsImpl) composeBouncerFlags).getClass();
        Flags.sceneContainer();
        Flags.FEATURE_FLAGS.getClass();
    }
}
