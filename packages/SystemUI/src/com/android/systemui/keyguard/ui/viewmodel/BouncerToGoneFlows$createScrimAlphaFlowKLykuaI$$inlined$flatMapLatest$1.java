package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow-KLykuaI$$inlined$flatMapLatest$1, reason: invalid class name */
public final class BouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ long $duration$inlined;
    final /* synthetic */ Ref$BooleanRef $isShadeExpanded$inlined;
    final /* synthetic */ Ref$BooleanRef $leaveShadeOpen$inlined;
    final /* synthetic */ KeyguardTransitionAnimationFlow.FlowBuilder $transitionAnimation$inlined;
    final /* synthetic */ Function0 $willRunAnimationOnKeyguard$inlined;
    final /* synthetic */ Ref$BooleanRef $willRunDismissFromKeyguard$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ BouncerToGoneFlows this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1(Continuation continuation, KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder, long j, Ref$BooleanRef ref$BooleanRef, BouncerToGoneFlows bouncerToGoneFlows, Ref$BooleanRef ref$BooleanRef2, Function0 function0, Ref$BooleanRef ref$BooleanRef3) {
        super(3, continuation);
        this.$transitionAnimation$inlined = flowBuilder;
        this.$duration$inlined = j;
        this.$leaveShadeOpen$inlined = ref$BooleanRef;
        this.this$0 = bouncerToGoneFlows;
        this.$willRunDismissFromKeyguard$inlined = ref$BooleanRef2;
        this.$willRunAnimationOnKeyguard$inlined = function0;
        this.$isShadeExpanded$inlined = ref$BooleanRef3;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        BouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1 bouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1 = new BouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1((Continuation) obj3, this.$transitionAnimation$inlined, this.$duration$inlined, this.$leaveShadeOpen$inlined, this.this$0, this.$willRunDismissFromKeyguard$inlined, this.$willRunAnimationOnKeyguard$inlined, this.$isShadeExpanded$inlined);
        bouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        bouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1.L$1 = obj2;
        return bouncerToGoneFlows$createScrimAlphaFlowKLykuaI$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final boolean booleanValue = ((Boolean) this.L$1).booleanValue();
            Interpolator interpolator = Interpolators.EMPHASIZED_ACCELERATE;
            KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.$transitionAnimation$inlined;
            long j = this.$duration$inlined;
            BouncerToGoneFlows$createScrimAlphaFlow$2$1 bouncerToGoneFlows$createScrimAlphaFlow$2$1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow$2$1
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return Float.valueOf(1.0f - ((Number) obj2).floatValue());
                }
            };
            final Ref$BooleanRef ref$BooleanRef = this.$leaveShadeOpen$inlined;
            final BouncerToGoneFlows bouncerToGoneFlows = this.this$0;
            final Ref$BooleanRef ref$BooleanRef2 = this.$willRunDismissFromKeyguard$inlined;
            final Function0 function0 = this.$willRunAnimationOnKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef3 = this.$isShadeExpanded$inlined;
            Function0 function02 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow$2$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Ref$BooleanRef.this.element = ((StatusBarStateControllerImpl) bouncerToGoneFlows.statusBarStateController).mLeaveOpenOnKeyguardHide;
                    ref$BooleanRef2.element = ((Boolean) function0.invoke()).booleanValue();
                    ref$BooleanRef3.element = booleanValue;
                    return Unit.INSTANCE;
                }
            };
            Intrinsics.checkNotNull(interpolator);
            final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m1962sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m1962sharedFlow74qcysc$default(flowBuilder, j, bouncerToGoneFlows$createScrimAlphaFlow$2$1, 0L, function02, null, null, interpolator, null, 180);
            final Ref$BooleanRef ref$BooleanRef4 = this.$willRunDismissFromKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef5 = this.$isShadeExpanded$inlined;
            final Ref$BooleanRef ref$BooleanRef6 = this.$leaveShadeOpen$inlined;
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ Ref$BooleanRef $isShadeExpanded$inlined;
                    public final /* synthetic */ Ref$BooleanRef $leaveShadeOpen$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ Ref$BooleanRef $willRunDismissFromKeyguard$inlined;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, Ref$BooleanRef ref$BooleanRef, Ref$BooleanRef ref$BooleanRef2, Ref$BooleanRef ref$BooleanRef3) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$willRunDismissFromKeyguard$inlined = ref$BooleanRef;
                        this.$isShadeExpanded$inlined = ref$BooleanRef2;
                        this.$leaveShadeOpen$inlined = ref$BooleanRef3;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r19, kotlin.coroutines.Continuation r20) {
                        /*
                            r18 = this;
                            r0 = r18
                            r1 = r20
                            boolean r2 = r1 instanceof com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r2 == 0) goto L17
                            r2 = r1
                            com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1$2$1 r2 = (com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1.AnonymousClass2.AnonymousClass1) r2
                            int r3 = r2.label
                            r4 = -2147483648(0xffffffff80000000, float:-0.0)
                            r5 = r3 & r4
                            if (r5 == 0) goto L17
                            int r3 = r3 - r4
                            r2.label = r3
                            goto L1c
                        L17:
                            com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1$2$1 r2 = new com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1$2$1
                            r2.<init>(r1)
                        L1c:
                            java.lang.Object r1 = r2.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r3 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r4 = r2.label
                            r5 = 1
                            if (r4 == 0) goto L33
                            if (r4 != r5) goto L2b
                            kotlin.ResultKt.throwOnFailure(r1)
                            goto L8d
                        L2b:
                            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                            r0.<init>(r1)
                            throw r0
                        L33:
                            kotlin.ResultKt.throwOnFailure(r1)
                            r1 = r19
                            java.lang.Number r1 = (java.lang.Number) r1
                            float r9 = r1.floatValue()
                            kotlin.jvm.internal.Ref$BooleanRef r1 = r0.$willRunDismissFromKeyguard$inlined
                            boolean r1 = r1.element
                            if (r1 == 0) goto L63
                            kotlin.jvm.internal.Ref$BooleanRef r1 = r0.$isShadeExpanded$inlined
                            boolean r1 = r1.element
                            if (r1 == 0) goto L55
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r1 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r10 = 1
                            r11 = 0
                            r7 = 0
                            r6 = r1
                            r8 = r9
                            r6.<init>(r7, r8, r9, r10, r11)
                            goto L82
                        L55:
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r1 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r16 = 7
                            r17 = 0
                            r13 = 0
                            r14 = 0
                            r15 = 0
                            r12 = r1
                            r12.<init>(r13, r14, r15, r16, r17)
                            goto L82
                        L63:
                            kotlin.jvm.internal.Ref$BooleanRef r1 = r0.$leaveShadeOpen$inlined
                            boolean r1 = r1.element
                            if (r1 == 0) goto L75
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r1 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r10 = 1
                            r11 = 0
                            r7 = 0
                            r8 = 0
                            r9 = 0
                            r6 = r1
                            r6.<init>(r7, r8, r9, r10, r11)
                            goto L82
                        L75:
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r1 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r16 = 5
                            r17 = 0
                            r13 = 0
                            r14 = 0
                            r15 = 0
                            r12 = r1
                            r12.<init>(r13, r14, r15, r16, r17)
                        L82:
                            r2.label = r5
                            kotlinx.coroutines.flow.FlowCollector r0 = r0.$this_unsafeFlow
                            java.lang.Object r0 = r0.emit(r1, r2)
                            if (r0 != r3) goto L8d
                            return r3
                        L8d:
                            kotlin.Unit r0 = kotlin.Unit.INSTANCE
                            return r0
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$4$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, ref$BooleanRef4, ref$BooleanRef5, ref$BooleanRef6), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow, flowCollector) == coroutineSingletons) {
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
