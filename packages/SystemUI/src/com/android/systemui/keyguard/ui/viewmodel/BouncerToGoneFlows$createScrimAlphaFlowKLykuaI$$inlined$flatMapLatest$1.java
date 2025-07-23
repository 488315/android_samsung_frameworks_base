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
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow-KLykuaI$$inlined$flatMapLatest$1, reason: invalid class name */
/* loaded from: classes2.dex */
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
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj2) {
                    return Float.valueOf(1.0f - ((Number) obj2).floatValue());
                }
            };
            final Ref$BooleanRef ref$BooleanRef = this.$leaveShadeOpen$inlined;
            final BouncerToGoneFlows bouncerToGoneFlows = this.this$0;
            final Ref$BooleanRef ref$BooleanRef2 = this.$willRunDismissFromKeyguard$inlined;
            final Function0 function0 = this.$willRunAnimationOnKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef3 = this.$isShadeExpanded$inlined;
            final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 m2599sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2599sharedFlow74qcysc$default(flowBuilder, j, bouncerToGoneFlows$createScrimAlphaFlow$2$1, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow$2$2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Ref$BooleanRef.this.element = ((StatusBarStateControllerImpl) bouncerToGoneFlows.statusBarStateController).mLeaveOpenOnKeyguardHide;
                    ref$BooleanRef2.element = ((Boolean) function0.invoke()).booleanValue();
                    ref$BooleanRef3.element = booleanValue;
                    return Unit.INSTANCE;
                }
            }, null, null, interpolator, null, 180);
            final Ref$BooleanRef ref$BooleanRef4 = this.$willRunDismissFromKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef5 = this.$isShadeExpanded$inlined;
            final Ref$BooleanRef ref$BooleanRef6 = this.$leaveShadeOpen$inlined;
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ Ref$BooleanRef $isShadeExpanded$inlined;
                    public final /* synthetic */ Ref$BooleanRef $leaveShadeOpen$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ Ref$BooleanRef $willRunDismissFromKeyguard$inlined;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1$2$1, reason: invalid class name */
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

                    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                    /* JADX WARN: Removed duplicated region for block: B:22:0x0080 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r12, kotlin.coroutines.Continuation r13) {
                        /*
                            r11 = this;
                            boolean r0 = r13 instanceof com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r13
                            com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1$2$1
                            r0.<init>(r13)
                        L18:
                            java.lang.Object r13 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r13)
                            goto L81
                        L27:
                            java.lang.IllegalStateException r11 = new java.lang.IllegalStateException
                            java.lang.String r12 = "call to 'resume' before 'invoke' with coroutine"
                            r11.<init>(r12)
                            throw r11
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r13)
                            java.lang.Number r12 = (java.lang.Number) r12
                            float r6 = r12.floatValue()
                            kotlin.jvm.internal.Ref$BooleanRef r12 = r11.$willRunDismissFromKeyguard$inlined
                            boolean r12 = r12.element
                            if (r12 == 0) goto L5a
                            kotlin.jvm.internal.Ref$BooleanRef r12 = r11.$isShadeExpanded$inlined
                            boolean r12 = r12.element
                            if (r12 == 0) goto L4e
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r4 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r8 = 1
                            r9 = 0
                            r5 = 0
                            r7 = r6
                            r4.<init>(r5, r6, r7, r8, r9)
                            goto L76
                        L4e:
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r5 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r9 = 7
                            r10 = 0
                            r6 = 0
                            r7 = 0
                            r8 = 0
                            r5.<init>(r6, r7, r8, r9, r10)
                        L58:
                            r4 = r5
                            goto L76
                        L5a:
                            kotlin.jvm.internal.Ref$BooleanRef r12 = r11.$leaveShadeOpen$inlined
                            boolean r12 = r12.element
                            if (r12 == 0) goto L6b
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r4 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r8 = 1
                            r9 = 0
                            r5 = 0
                            r6 = 0
                            r7 = 0
                            r4.<init>(r5, r6, r7, r8, r9)
                            goto L76
                        L6b:
                            com.android.systemui.keyguard.shared.model.ScrimAlpha r5 = new com.android.systemui.keyguard.shared.model.ScrimAlpha
                            r9 = 5
                            r10 = 0
                            r6 = 0
                            r7 = 0
                            r8 = 0
                            r5.<init>(r6, r7, r8, r9, r10)
                            goto L58
                        L76:
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r11 = r11.$this_unsafeFlow
                            java.lang.Object r11 = r11.emit(r4, r0)
                            if (r11 != r1) goto L81
                            return r1
                        L81:
                            kotlin.Unit r11 = kotlin.Unit.INSTANCE
                            return r11
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, ref$BooleanRef4, ref$BooleanRef5, ref$BooleanRef6), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow, this) == coroutineSingletons) {
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
