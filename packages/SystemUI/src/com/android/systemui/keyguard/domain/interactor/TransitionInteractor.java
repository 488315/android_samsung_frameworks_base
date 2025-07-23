package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class TransitionInteractor {
    public final KeyguardState fromState;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final String name;
    public final PowerInteractor powerInteractor;
    public final KeyguardTransitionInteractor transitionInteractor;

    public /* synthetic */ TransitionInteractor(KeyguardState keyguardState, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardInteractor keyguardInteractor, DefaultConstructorMarker defaultConstructorMarker) {
        this(keyguardState, keyguardTransitionInteractor, coroutineDispatcher, coroutineDispatcher2, powerInteractor, keyguardOcclusionInteractor, keyguardInteractor);
    }

    public static Object startTransitionTo$default(TransitionInteractor transitionInteractor, KeyguardState keyguardState, ValueAnimator valueAnimator, TransitionModeOnCanceled transitionModeOnCanceled, String str, Continuation continuation, int i) {
        if ((i & 2) != 0) {
            valueAnimator = transitionInteractor.getDefaultAnimatorForTransitionsToState(keyguardState);
        }
        ValueAnimator valueAnimator2 = valueAnimator;
        if ((i & 4) != 0) {
            transitionModeOnCanceled = TransitionModeOnCanceled.LAST_VALUE;
        }
        TransitionModeOnCanceled transitionModeOnCanceled2 = transitionModeOnCanceled;
        if ((i & 8) != 0) {
            str = "";
        }
        transitionInteractor.getClass();
        keyguardState.checkValidState();
        KeyguardState keyguardState2 = transitionInteractor.getInternalTransitionInteractor().currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to;
        String str2 = transitionInteractor.name;
        KeyguardState keyguardState3 = transitionInteractor.fromState;
        if (keyguardState3 == keyguardState2) {
            return ((KeyguardTransitionRepositoryImpl) transitionInteractor.getTransitionRepository()).startTransition(new TransitionInfo(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, StringsKt__StringsKt.isBlank(str) ? "" : ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str, ")")), transitionInteractor.fromState, keyguardState, valueAnimator2, transitionModeOnCanceled2), continuation);
        }
        Log.e(str2, "Ignoring startTransition: This interactor asked to transition from " + keyguardState3 + " -> " + keyguardState + ", but we last transitioned to " + transitionInteractor.getInternalTransitionInteractor().currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core().to + ", not " + keyguardState3 + ". This should never happen - check currentTransitionInfoInternal or use filterRelevantKeyguardState before starting transitions.");
        return null;
    }

    public abstract ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState);

    public abstract InternalKeyguardTransitionInteractor getInternalTransitionInteractor();

    public abstract KeyguardTransitionRepository getTransitionRepository();

    public final Object listenForSleepTransition(final Function1 function1, SuspendLambda suspendLambda) {
        final PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = this.powerInteractor.isAsleep;
        final Flow sample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L46
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        java.lang.Boolean r6 = (java.lang.Boolean) r6
                        boolean r6 = r6.booleanValue()
                        if (r6 == 0) goto L46
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L46
                        return r1
                    L46:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this), this.transitionInteractor.startedKeyguardTransitionStep);
        Object collect = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ Function1 $callee$inlined;
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, Function1 function1) {
                    this.$this_unsafeFlow = flowCollector;
                    this.$callee$inlined = function1;
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
                        boolean r0 = r6 instanceof com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                        kotlin.jvm.functions.Function1 r6 = r4.$callee$inlined
                        java.lang.Object r5 = r6.mo779invoke(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector, function1), continuation);
                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$5
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation) {
                TransitionInteractor transitionInteractor = TransitionInteractor.this;
                Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(transitionInteractor, (KeyguardState) transitionInteractor.keyguardInteractor.asleepKeyguardState.$$delegate_0.getValue(), null, (TransitionModeOnCanceled) obj, "Sleep transition triggered", continuation, 2);
                return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
            }
        }, suspendLambda);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public final void listenForTransitionToCamera(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor) {
        CoroutineTracingKt.launchTraced$default(coroutineScope, null, null, new TransitionInteractor$listenForTransitionToCamera$1(this, keyguardInteractor, null), 7);
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0084, code lost:
    
        if (startTransitionTo$default(r8, r2, null, null, "Power button gesture while keyguard is dismissible", r6, 6) == r0) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a9, code lost:
    
        if (startTransitionTo$default(r8, r2, null, null, "Power button gesture on dismissable keyguard", r6, 6) == r0) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0066  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object maybeHandleInsecurePowerGesture(kotlin.coroutines.jvm.internal.ContinuationImpl r9) {
        /*
            r8 = this;
            boolean r0 = r9 instanceof com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1
            if (r0 == 0) goto L14
            r0 = r9
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1 r0 = (com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L14
            int r1 = r1 - r2
            r0.label = r1
        L12:
            r6 = r0
            goto L1a
        L14:
            com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1 r0 = new com.android.systemui.keyguard.domain.interactor.TransitionInteractor$maybeHandleInsecurePowerGesture$1
            r0.<init>(r8, r9)
            goto L12
        L1a:
            java.lang.Object r9 = r6.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L39
            if (r1 == r3) goto L35
            if (r1 != r2) goto L2d
            kotlin.ResultKt.throwOnFailure(r9)
            goto Lac
        L2d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L35:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L87
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r9 = r8.keyguardOcclusionInteractor
            com.android.systemui.power.domain.interactor.PowerInteractor r1 = r9.powerInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.detailedWakefulness
            kotlinx.coroutines.flow.StateFlow r1 = r1.$$delegate_0
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.power.shared.model.WakefulnessModel r1 = (com.android.systemui.power.shared.model.WakefulnessModel) r1
            boolean r1 = r1.powerButtonLaunchGestureTriggered
            if (r1 == 0) goto L63
            com.android.systemui.keyguard.shared.model.KeyguardState$Companion r1 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
            com.android.systemui.keyguard.domain.interactor.InternalKeyguardTransitionInteractor r4 = r9.internalTransitionInteractor
            com.android.systemui.keyguard.shared.model.TransitionInfo r4 = r4.currentTransitionInfoInternal$frameworks__base__packages__SystemUI__android_common__SystemUI_core()
            com.android.systemui.keyguard.shared.model.KeyguardState r4 = r4.to
            r1.getClass()
            boolean r1 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion.deviceIsAsleepInState(r4)
            if (r1 == 0) goto L63
            r1 = 1
            goto L64
        L63:
            r1 = 0
        L64:
            if (r1 == 0) goto Laf
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r1 = r8.keyguardInteractor
            kotlinx.coroutines.flow.StateFlowImpl r1 = r1.isKeyguardDismissible
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L8a
            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            r6.label = r3
            java.lang.String r5 = "Power button gesture while keyguard is dismissible"
            r7 = 6
            r3 = 0
            r4 = 0
            r1 = r8
            java.lang.Object r8 = startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto L87
            goto Lab
        L87:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        L8a:
            r1 = r8
            kotlinx.coroutines.flow.ReadonlyStateFlow r8 = r9.occludingActivityWillDismissKeyguard
            kotlinx.coroutines.flow.StateFlow r8 = r8.$$delegate_0
            java.lang.Object r8 = r8.getValue()
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 == 0) goto Laf
            r8 = r2
            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            r6.label = r8
            java.lang.String r5 = "Power button gesture on dismissable keyguard"
            r7 = 6
            r3 = 0
            r4 = 0
            java.lang.Object r8 = startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto Lac
        Lab:
            return r0
        Lac:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        Laf:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor.maybeHandleInsecurePowerGesture(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public abstract void start();

    private TransitionInteractor(KeyguardState keyguardState, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardInteractor keyguardInteractor) {
        this.fromState = keyguardState;
        this.transitionInteractor = keyguardTransitionInteractor;
        this.mainDispatcher = coroutineDispatcher;
        this.powerInteractor = powerInteractor;
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        this.name = simpleName == null ? "UnknownTransitionInteractor" : simpleName;
    }
}
