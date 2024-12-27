package com.android.systemui.keyguard.domain.interactor;

import android.animation.ValueAnimator;
import android.util.Log;
import androidx.compose.foundation.gestures.ContentInViewNode$Request$$ExternalSyntheticOutline0;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import com.android.systemui.Flags;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepository;
import com.android.systemui.keyguard.data.repository.KeyguardTransitionRepositoryImpl;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionInfo;
import com.android.systemui.keyguard.shared.model.TransitionModeOnCanceled;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.power.domain.interactor.PowerInteractor$special$$inlined$map$2;
import com.android.systemui.util.kotlin.FlowKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.text.StringsKt__StringsJVMKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.SharedFlow;

public abstract class TransitionInteractor {
    public final SharedFlow finishedKeyguardState;
    public final KeyguardState fromState;
    public final KeyguardInteractor keyguardInteractor;
    public final KeyguardOcclusionInteractor keyguardOcclusionInteractor;
    public final CoroutineDispatcher mainDispatcher;
    public final String name;
    public final PowerInteractor powerInteractor;
    public final Flow startedKeyguardTransitionStep;
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
        KeyguardTransitionInteractor keyguardTransitionInteractor = transitionInteractor.transitionInteractor;
        KeyguardState keyguardState2 = ((TransitionInfo) keyguardTransitionInteractor.currentTransitionInfoInternal.$$delegate_0.getValue()).to;
        String str2 = transitionInteractor.name;
        KeyguardState keyguardState3 = transitionInteractor.fromState;
        if (keyguardState3 == keyguardState2) {
            return ((KeyguardTransitionRepositoryImpl) transitionInteractor.getTransitionRepository()).startTransition(new TransitionInfo(AbstractResolvableFuture$$ExternalSyntheticOutline0.m(str2, StringsKt__StringsJVMKt.isBlank(str) ^ true ? ContentInViewNode$Request$$ExternalSyntheticOutline0.m("(", str, ")") : ""), transitionInteractor.fromState, keyguardState, valueAnimator2, transitionModeOnCanceled2), continuation);
        }
        Log.e(str2, "Ignoring startTransition: This interactor asked to transition from " + keyguardState3 + " -> " + keyguardState + ", but we last transitioned to " + ((TransitionInfo) keyguardTransitionInteractor.currentTransitionInfoInternal.$$delegate_0.getValue()).to + ", not " + keyguardState3 + ". This should never happen - check currentTransitionInfoInternal or use filterRelevantKeyguardState before starting transitions.");
        if (keyguardState3 == CollectionsKt___CollectionsKt.last(keyguardTransitionInteractor.finishedKeyguardState.$$delegate_0.getReplayCache())) {
            Log.e(str2, "This transition would not have been ignored prior to ag/26681239, since we are FINISHED in " + keyguardState3 + " (but have since started another transition). If ignoring this transition has caused a regression, fix it by ensuring that transitions are exclusively started from the most recently started state.");
        }
        return null;
    }

    public abstract ValueAnimator getDefaultAnimatorForTransitionsToState(KeyguardState keyguardState);

    public abstract KeyguardTransitionRepository getTransitionRepository();

    public final Object listenForSleepTransition(final Function1 function1, Continuation continuation) {
        final PowerInteractor$special$$inlined$map$2 powerInteractor$special$$inlined$map$2 = this.powerInteractor.isAsleep;
        final Flow sample = FlowKt.sample(new TransitionInteractor$filterRelevantKeyguardState$$inlined$filter$1(new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$filter$1

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
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation2);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, this), this.startedKeyguardTransitionStep);
        Object collect = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1

            /* renamed from: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ Function1 callee$inlined;

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
                    this.callee$inlined = function1;
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
                        kotlin.jvm.functions.Function1 r6 = r4.callee$inlined
                        java.lang.Object r5 = r6.invoke(r5)
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation2) {
                Object collect2 = Flow.this.collect(new AnonymousClass2(flowCollector, function1), continuation2);
                return collect2 == CoroutineSingletons.COROUTINE_SUSPENDED ? collect2 : Unit.INSTANCE;
            }
        }.collect(new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.TransitionInteractor$listenForSleepTransition$5
            @Override // kotlinx.coroutines.flow.FlowCollector
            public final Object emit(Object obj, Continuation continuation2) {
                TransitionInteractor transitionInteractor = TransitionInteractor.this;
                Object startTransitionTo$default = TransitionInteractor.startTransitionTo$default(transitionInteractor, (KeyguardState) transitionInteractor.transitionInteractor.asleepKeyguardState.$$delegate_0.getValue(), null, (TransitionModeOnCanceled) obj, "Sleep transition triggered", continuation2, 2);
                return startTransitionTo$default == CoroutineSingletons.COROUTINE_SUSPENDED ? startTransitionTo$default : Unit.INSTANCE;
            }
        }, continuation);
        return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
    }

    public final void listenForTransitionToCamera(CoroutineScope coroutineScope, KeyguardInteractor keyguardInteractor) {
        Flags.keyguardWmStateRefactor();
        BuildersKt.launch$default(coroutineScope, null, null, new TransitionInteractor$listenForTransitionToCamera$1(this, keyguardInteractor, null), 3);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0067, code lost:
    
        if ((!r1) != false) goto L23;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0039  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object maybeHandleInsecurePowerGesture(kotlin.coroutines.Continuation r9) {
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
            goto Lb3
        L2d:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
            r8.<init>(r9)
            throw r8
        L35:
            kotlin.ResultKt.throwOnFailure(r9)
            goto L8e
        L39:
            kotlin.ResultKt.throwOnFailure(r9)
            com.android.systemui.keyguard.domain.interactor.KeyguardOcclusionInteractor r9 = r8.keyguardOcclusionInteractor
            com.android.systemui.power.domain.interactor.PowerInteractor r1 = r9.powerInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r1 = r1.detailedWakefulness
            kotlinx.coroutines.flow.StateFlow r1 = r1.$$delegate_0
            java.lang.Object r1 = r1.getValue()
            com.android.systemui.power.shared.model.WakefulnessModel r1 = (com.android.systemui.power.shared.model.WakefulnessModel) r1
            boolean r1 = r1.powerButtonLaunchGestureTriggered
            if (r1 == 0) goto L6a
            com.android.systemui.keyguard.shared.model.KeyguardState$Companion r1 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion
            com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor r4 = r9.transitionInteractor
            kotlinx.coroutines.flow.ReadonlyStateFlow r4 = r4.currentTransitionInfoInternal
            kotlinx.coroutines.flow.StateFlow r4 = r4.$$delegate_0
            java.lang.Object r4 = r4.getValue()
            com.android.systemui.keyguard.shared.model.TransitionInfo r4 = (com.android.systemui.keyguard.shared.model.TransitionInfo) r4
            com.android.systemui.keyguard.shared.model.KeyguardState r4 = r4.to
            r1.getClass()
            boolean r1 = com.android.systemui.keyguard.shared.model.KeyguardState.Companion.deviceIsAwakeInState(r4)
            r4 = 1
            r1 = r1 ^ r4
            if (r1 == 0) goto L6a
            goto L6b
        L6a:
            r4 = 0
        L6b:
            if (r4 == 0) goto Lb6
            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor r1 = r8.keyguardInteractor
            kotlinx.coroutines.flow.StateFlow r1 = r1.isKeyguardDismissible
            java.lang.Object r1 = r1.getValue()
            java.lang.Boolean r1 = (java.lang.Boolean) r1
            boolean r1 = r1.booleanValue()
            if (r1 == 0) goto L91
            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            r6.label = r3
            java.lang.String r5 = "Power button gesture while keyguard is dismissible"
            r7 = 6
            r3 = 0
            r4 = 0
            r1 = r8
            java.lang.Object r8 = startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto L8e
            return r0
        L8e:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        L91:
            kotlinx.coroutines.flow.ReadonlyStateFlow r9 = r9.occludingActivityWillDismissKeyguard
            kotlinx.coroutines.flow.StateFlow r9 = r9.$$delegate_0
            java.lang.Object r9 = r9.getValue()
            java.lang.Boolean r9 = (java.lang.Boolean) r9
            boolean r9 = r9.booleanValue()
            if (r9 == 0) goto Lb6
            com.android.systemui.keyguard.shared.model.KeyguardState r9 = com.android.systemui.keyguard.shared.model.KeyguardState.GONE
            r6.label = r2
            java.lang.String r5 = "Power button gesture on dismissable keyguard"
            r7 = 6
            r3 = 0
            r4 = 0
            r1 = r8
            r2 = r9
            java.lang.Object r8 = startTransitionTo$default(r1, r2, r3, r4, r5, r6, r7)
            if (r8 != r0) goto Lb3
            return r0
        Lb3:
            java.lang.Boolean r8 = java.lang.Boolean.TRUE
            return r8
        Lb6:
            java.lang.Boolean r8 = java.lang.Boolean.FALSE
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.TransitionInteractor.maybeHandleInsecurePowerGesture(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public abstract void start();

    private TransitionInteractor(KeyguardState keyguardState, KeyguardTransitionInteractor keyguardTransitionInteractor, CoroutineDispatcher coroutineDispatcher, CoroutineDispatcher coroutineDispatcher2, PowerInteractor powerInteractor, KeyguardOcclusionInteractor keyguardOcclusionInteractor, KeyguardInteractor keyguardInteractor) {
        this.fromState = keyguardState;
        this.transitionInteractor = keyguardTransitionInteractor;
        this.powerInteractor = powerInteractor;
        this.keyguardOcclusionInteractor = keyguardOcclusionInteractor;
        this.keyguardInteractor = keyguardInteractor;
        String simpleName = Reflection.getOrCreateKotlinClass(getClass()).getSimpleName();
        this.name = simpleName == null ? "UnknownTransitionInteractor" : simpleName;
        this.startedKeyguardTransitionStep = kotlinx.coroutines.flow.FlowKt.flowOn(keyguardTransitionInteractor.startedKeyguardTransitionStep, coroutineDispatcher2);
        this.finishedKeyguardState = keyguardTransitionInteractor.finishedKeyguardState;
    }
}
