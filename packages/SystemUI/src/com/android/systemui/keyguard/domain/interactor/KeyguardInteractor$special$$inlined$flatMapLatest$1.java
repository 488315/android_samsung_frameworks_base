package com.android.systemui.keyguard.domain.interactor;

import com.android.systemui.common.shared.model.NotificationContainerBounds;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.shared.model.TransitionStep;
import com.android.systemui.util.kotlin.WithPrev;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SafeFlow;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyguardInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    Object L$2;
    int label;
    final /* synthetic */ KeyguardInteractor this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KeyguardInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, KeyguardInteractor keyguardInteractor) {
        super(3, continuation);
        this.this$0 = keyguardInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        KeyguardInteractor$special$$inlined$flatMapLatest$1 keyguardInteractor$special$$inlined$flatMapLatest$1 = new KeyguardInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        keyguardInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        keyguardInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return keyguardInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        FlowCollector flowCollector;
        final NotificationContainerBounds notificationContainerBounds;
        final NotificationContainerBounds notificationContainerBounds2;
        Flow safeFlow;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            flowCollector = (FlowCollector) this.L$0;
            WithPrev withPrev = (WithPrev) this.L$1;
            NotificationContainerBounds notificationContainerBounds3 = (NotificationContainerBounds) withPrev.component1();
            NotificationContainerBounds notificationContainerBounds4 = (NotificationContainerBounds) withPrev.component2();
            ReadonlyStateFlow readonlyStateFlow = this.this$0.keyguardTransitionInteractor.transitionState;
            this.L$0 = flowCollector;
            this.L$1 = notificationContainerBounds3;
            this.L$2 = notificationContainerBounds4;
            this.label = 1;
            Object first = FlowKt.first(readonlyStateFlow, this);
            if (first == coroutineSingletons) {
                return coroutineSingletons;
            }
            notificationContainerBounds = notificationContainerBounds4;
            obj = first;
            notificationContainerBounds2 = notificationContainerBounds3;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            notificationContainerBounds = (NotificationContainerBounds) this.L$2;
            notificationContainerBounds2 = (NotificationContainerBounds) this.L$1;
            flowCollector = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        final TransitionStep transitionStep = (TransitionStep) obj;
        if (transitionStep.to == KeyguardState.AOD) {
            final ReadonlyStateFlow readonlyStateFlow2 = this.this$0.keyguardTransitionInteractor.transitionState;
            safeFlow = new Flow() { // from class: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ TransitionStep $lastChangeStep$inlined;
                    public final /* synthetic */ NotificationContainerBounds $newBounds$inlined;
                    public final /* synthetic */ NotificationContainerBounds $oldBounds$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, TransitionStep transitionStep, NotificationContainerBounds notificationContainerBounds, NotificationContainerBounds notificationContainerBounds2) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$lastChangeStep$inlined = transitionStep;
                        this.$oldBounds$inlined = notificationContainerBounds;
                        this.$newBounds$inlined = notificationContainerBounds2;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                        /*
                            r17 = this;
                            r0 = r17
                            r1 = r19
                            boolean r2 = r1 instanceof com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r2 == 0) goto L17
                            r2 = r1
                            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1$2$1 r2 = (com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                            int r3 = r2.label
                            r4 = -2147483648(0xffffffff80000000, float:-0.0)
                            r5 = r3 & r4
                            if (r5 == 0) goto L17
                            int r3 = r3 - r4
                            r2.label = r3
                            goto L1c
                        L17:
                            com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1$2$1 r2 = new com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1$2$1
                            r2.<init>(r1)
                        L1c:
                            java.lang.Object r1 = r2.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r4 = r2.label
                            r5 = 1
                            if (r4 == 0) goto L33
                            if (r4 != r5) goto L2b
                            kotlin.ResultKt.throwOnFailure(r1)
                            goto L86
                        L2b:
                            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                            r0.<init>(r1)
                            throw r0
                        L33:
                            kotlin.ResultKt.throwOnFailure(r1)
                            r1 = r18
                            com.android.systemui.keyguard.shared.model.TransitionStep r1 = (com.android.systemui.keyguard.shared.model.TransitionStep) r1
                            com.android.systemui.keyguard.shared.model.TransitionStep r4 = r0.$lastChangeStep$inlined
                            float r4 = r4.value
                            float r6 = r1.value
                            com.android.systemui.keyguard.shared.model.KeyguardState r7 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                            com.android.systemui.common.shared.model.NotificationContainerBounds r8 = r0.$newBounds$inlined
                            com.android.systemui.keyguard.shared.model.KeyguardState r1 = r1.to
                            if (r1 != r7) goto L7b
                            int r1 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
                            if (r1 < 0) goto L7b
                            r1 = 1065353216(0x3f800000, float:1.0)
                            int r7 = (r4 > r1 ? 1 : (r4 == r1 ? 0 : -1))
                            if (r7 >= 0) goto L7b
                            float r6 = r6 - r4
                            float r4 = r1 - r4
                            float r6 = r6 / r4
                            r4 = 0
                            float r6 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r6, r4, r1)
                            com.android.systemui.common.shared.model.NotificationContainerBounds r7 = r0.$oldBounds$inlined
                            float r9 = r7.top
                            float r10 = r8.top
                            float r12 = com.android.systemui.statusbar.notification.NotificationUtils.interpolate(r9, r10, r6)
                            float r7 = r7.bottom
                            float r8 = r8.bottom
                            float r1 = kotlin.ranges.RangesKt___RangesKt.coerceIn(r6, r4, r1)
                            float r13 = com.android.systemui.statusbar.notification.NotificationUtils.interpolate(r7, r8, r1)
                            com.android.systemui.common.shared.model.NotificationContainerBounds r8 = new com.android.systemui.common.shared.model.NotificationContainerBounds
                            r15 = 4
                            r16 = 0
                            r14 = 0
                            r11 = r8
                            r11.<init>(r12, r13, r14, r15, r16)
                        L7b:
                            r2.label = r5
                            kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                            java.lang.Object r0 = r0.emit(r8, r2)
                            if (r0 != r3) goto L86
                            return r3
                        L86:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.domain.interactor.KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, transitionStep, notificationContainerBounds2, notificationContainerBounds), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        } else {
            safeFlow = new SafeFlow(new KeyguardInteractor$_nonSplitShadeNotifciationPlaceholderBounds$1$2(notificationContainerBounds, null));
        }
        this.L$0 = null;
        this.L$1 = null;
        this.L$2 = null;
        this.label = 2;
        if (FlowKt.emitAll(this, safeFlow, flowCollector) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
