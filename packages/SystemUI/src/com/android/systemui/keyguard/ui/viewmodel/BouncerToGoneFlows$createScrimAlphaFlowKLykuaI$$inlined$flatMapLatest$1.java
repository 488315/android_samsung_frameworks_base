package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.keyguard.shared.model.ScrimAlpha;
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
            final boolean zBooleanValue = ((Boolean) this.L$1).booleanValue();
            Interpolator interpolator = Interpolators.EMPHASIZED_ACCELERATE;
            KeyguardTransitionAnimationFlow.FlowBuilder flowBuilder = this.$transitionAnimation$inlined;
            long j = this.$duration$inlined;
            BouncerToGoneFlows$createScrimAlphaFlow$2$1 bouncerToGoneFlows$createScrimAlphaFlow$2$1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow$2$1
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    return Float.valueOf(1.0f - ((Number) obj2).floatValue());
                }
            };
            final Ref$BooleanRef ref$BooleanRef = this.$leaveShadeOpen$inlined;
            final BouncerToGoneFlows bouncerToGoneFlows = this.this$0;
            final Ref$BooleanRef ref$BooleanRef2 = this.$willRunDismissFromKeyguard$inlined;
            final Function0 function0 = this.$willRunAnimationOnKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef3 = this.$isShadeExpanded$inlined;
            final KeyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1 keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2616sharedFlow74qcysc$default = KeyguardTransitionAnimationFlow.FlowBuilder.m2616sharedFlow74qcysc$default(flowBuilder, j, bouncerToGoneFlows$createScrimAlphaFlow$2$1, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow$2$2
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    ref$BooleanRef.element = ((StatusBarStateControllerImpl) bouncerToGoneFlows.statusBarStateController).mLeaveOpenOnKeyguardHide;
                    ref$BooleanRef2.element = ((Boolean) function0.invoke()).booleanValue();
                    ref$BooleanRef3.element = zBooleanValue;
                    return Unit.INSTANCE;
                }
            }, null, null, interpolator, null, 180);
            final Ref$BooleanRef ref$BooleanRef4 = this.$willRunDismissFromKeyguard$inlined;
            final Ref$BooleanRef ref$BooleanRef5 = this.$isShadeExpanded$inlined;
            final Ref$BooleanRef ref$BooleanRef6 = this.$leaveShadeOpen$inlined;
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.BouncerToGoneFlows$createScrimAlphaFlow_KLykuaI$lambda$8$$inlined$map$1

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

                    /* JADX WARN: Removed duplicated region for block: B:27:0x0080 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object emit(Object obj, Continuation continuation) {
                        AnonymousClass1 anonymousClass1;
                        ScrimAlpha scrimAlpha;
                        ScrimAlpha scrimAlpha2;
                        if (continuation instanceof AnonymousClass1) {
                            anonymousClass1 = (AnonymousClass1) continuation;
                            int i = anonymousClass1.label;
                            if ((i & Integer.MIN_VALUE) != 0) {
                                anonymousClass1.label = i - Integer.MIN_VALUE;
                            } else {
                                anonymousClass1 = new AnonymousClass1(continuation);
                            }
                        }
                        Object obj2 = anonymousClass1.result;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i2 = anonymousClass1.label;
                        if (i2 == 0) {
                            ResultKt.throwOnFailure(obj2);
                            float fFloatValue = ((Number) obj).floatValue();
                            if (this.$willRunDismissFromKeyguard$inlined.element) {
                                if (this.$isShadeExpanded$inlined.element) {
                                    scrimAlpha2 = new ScrimAlpha(0.0f, fFloatValue, fFloatValue, 1, null);
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(scrimAlpha2, anonymousClass1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                } else {
                                    scrimAlpha = new ScrimAlpha(0.0f, 0.0f, 0.0f, 7, null);
                                    scrimAlpha2 = scrimAlpha;
                                    anonymousClass1.label = 1;
                                    if (this.$this_unsafeFlow.emit(scrimAlpha2, anonymousClass1) == coroutineSingletons) {
                                    }
                                }
                            } else if (this.$leaveShadeOpen$inlined.element) {
                                scrimAlpha2 = new ScrimAlpha(0.0f, 0.0f, 0.0f, 1, null);
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(scrimAlpha2, anonymousClass1) == coroutineSingletons) {
                                }
                            } else {
                                scrimAlpha = new ScrimAlpha(0.0f, 0.0f, 0.0f, 5, null);
                                scrimAlpha2 = scrimAlpha;
                                anonymousClass1.label = 1;
                                if (this.$this_unsafeFlow.emit(scrimAlpha2, anonymousClass1) == coroutineSingletons) {
                                }
                            }
                        } else {
                            if (i2 != 1) {
                                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                            }
                            ResultKt.throwOnFailure(obj2);
                        }
                        return Unit.INSTANCE;
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object objCollect = keyguardTransitionAnimationFlow$FlowBuilder$sharedFlow74qcysc$$inlined$mapNotNull$1M2616sharedFlow74qcysc$default.collect(new AnonymousClass2(flowCollector2, ref$BooleanRef4, ref$BooleanRef5, ref$BooleanRef6), continuation);
                    return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
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
