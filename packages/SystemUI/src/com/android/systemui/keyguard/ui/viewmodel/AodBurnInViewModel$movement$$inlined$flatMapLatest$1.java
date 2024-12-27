package com.android.systemui.keyguard.ui.viewmodel;

import android.util.MathUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
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
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.MutableSharedFlow;

public final class AodBurnInViewModel$movement$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BurnInParameters $params$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AodBurnInViewModel this$0;

    public AodBurnInViewModel$movement$$inlined$flatMapLatest$1(Continuation continuation, AodBurnInViewModel aodBurnInViewModel, BurnInParameters burnInParameters) {
        super(3, continuation);
        this.this$0 = aodBurnInViewModel;
        this.$params$inlined = burnInParameters;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AodBurnInViewModel$movement$$inlined$flatMapLatest$1 aodBurnInViewModel$movement$$inlined$flatMapLatest$1 = new AodBurnInViewModel$movement$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$params$inlined);
        aodBurnInViewModel$movement$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        aodBurnInViewModel$movement$$inlined$flatMapLatest$1.L$1 = obj2;
        return aodBurnInViewModel$movement$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final int intValue = ((Number) this.L$1).intValue();
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1(null), this.this$0.keyguardInteractor.keyguardTranslationY);
            AodBurnInViewModel aodBurnInViewModel = this.this$0;
            BurnInParameters burnInParameters = this.$params$inlined;
            final MutableSharedFlow transitionValue = aodBurnInViewModel.keyguardTransitionInteractor.transitionValue(KeyguardState.AOD);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$2(null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1

                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2$1, reason: invalid class name */
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

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L50
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            java.lang.Number r5 = (java.lang.Number) r5
                            float r5 = r5.floatValue()
                            android.view.animation.Interpolator r6 = com.android.app.animation.Interpolators.FAST_OUT_SLOW_IN
                            android.view.animation.PathInterpolator r6 = (android.view.animation.PathInterpolator) r6
                            float r5 = r6.getInterpolation(r5)
                            java.lang.Float r6 = new java.lang.Float
                            r6.<init>(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L50
                            return r1
                        L50:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, aodBurnInViewModel.burnInInteractor.burnIn(R.dimen.burn_in_prevention_offset_y), new AodBurnInViewModel$burnIn$2(aodBurnInViewModel, burnInParameters, null)));
            GoneToAodTransitionViewModel goneToAodTransitionViewModel = this.this$0.goneToAodTransitionViewModel;
            goneToAodTransitionViewModel.getClass();
            Duration.Companion companion = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.MILLISECONDS;
            long duration = DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit);
            long duration2 = DurationKt.toDuration(500, durationUnit);
            Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
            Function1 function1 = new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$enterFromTopTranslationY$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    float floatValue = ((Number) obj2).floatValue();
                    return Float.valueOf((floatValue * (-r1)) + intValue);
                }
            };
            GoneToAodTransitionViewModel$enterFromTopTranslationY$2 goneToAodTransitionViewModel$enterFromTopTranslationY$2 = new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$enterFromTopTranslationY$2
                @Override // kotlin.jvm.functions.Function0
                public final /* bridge */ /* synthetic */ Object invoke() {
                    return Float.valueOf(0.0f);
                }
            };
            Intrinsics.checkNotNull(interpolator);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$3(null), KeyguardTransitionAnimationFlow.FlowBuilder.m1963sharedFlowWithState74qcysc$default(goneToAodTransitionViewModel.transitionAnimation, duration2, function1, duration, null, null, goneToAodTransitionViewModel$enterFromTopTranslationY$2, interpolator, null, 152));
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$4(null), this.this$0.occludedToLockscreenTransitionViewModel.lockscreenTranslationY);
            AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel = this.this$0.aodToLockscreenTransitionViewModel;
            final Function0 function0 = this.$params$inlined.translationY;
            aodToLockscreenTransitionViewModel.getClass();
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$3 combine = FlowKt.combine(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$5(null), KeyguardTransitionAnimationFlow.FlowBuilder.m1963sharedFlowWithState74qcysc$default(aodToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$translationY$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj2) {
                    return Float.valueOf(MathUtils.lerp(Ref$FloatRef.this.element, 0.0f, ((PathInterpolator) Interpolators.FAST_OUT_SLOW_IN).getInterpolation(((Number) obj2).floatValue())));
                }
            }, 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$translationY$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    Ref$FloatRef ref$FloatRef2 = Ref$FloatRef.this;
                    Float f = (Float) function0.invoke();
                    ref$FloatRef2.element = f != null ? f.floatValue() : 0.0f;
                    return Unit.INSTANCE;
                }
            }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode)), new AodBurnInViewModel$movement$1$6(null));
            this.label = 1;
            if (FlowKt.emitAll(this, combine, flowCollector) == coroutineSingletons) {
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
