package com.android.systemui.keyguard.domain.interactor;

import java.util.UUID;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2 extends SuspendLambda implements Function2 {
    final /* synthetic */ Ref$ObjectRef<UUID> $transitionId;
    int label;
    final /* synthetic */ FromLockscreenTransitionInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2(FromLockscreenTransitionInteractor fromLockscreenTransitionInteractor, Ref$ObjectRef<UUID> ref$ObjectRef, Continuation continuation) {
        super(2, continuation);
        this.this$0 = fromLockscreenTransitionInteractor;
        this.$transitionId = ref$ObjectRef;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        return new FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2(this.this$0, this.$transitionId, continuation);
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final Flow flow = this.this$0.transitionInteractor.transitions;
            Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1$2$1, reason: invalid class name */
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
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L4c
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            r7 = r6
                            com.android.systemui.keyguard.shared.model.TransitionStep r7 = (com.android.systemui.keyguard.shared.model.TransitionStep) r7
                            com.android.systemui.keyguard.shared.model.TransitionState r2 = r7.transitionState
                            com.android.systemui.keyguard.shared.model.TransitionState r4 = com.android.systemui.keyguard.shared.model.TransitionState.CANCELED
                            if (r2 != r4) goto L4c
                            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.PRIMARY_BOUNCER
                            com.android.systemui.keyguard.shared.model.KeyguardState r7 = r7.to
                            if (r7 != r2) goto L4c
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L4c
                            return r1
                        L4c:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2$invokeSuspend$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final Ref$ObjectRef<UUID> ref$ObjectRef = this.$transitionId;
            FlowCollector flowCollector = new FlowCollector() { // from class: com.android.systemui.keyguard.domain.interactor.FromLockscreenTransitionInteractor$listenForLockscreenToPrimaryBouncerDragging$2.2
                @Override // kotlinx.coroutines.flow.FlowCollector
                public final Object emit(Object obj2, Continuation continuation) {
                    ref$ObjectRef.element = null;
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (flow2.collect(flowCollector, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
