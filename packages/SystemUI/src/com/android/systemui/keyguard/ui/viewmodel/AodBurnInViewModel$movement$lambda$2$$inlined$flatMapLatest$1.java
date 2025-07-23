package com.android.systemui.keyguard.ui.viewmodel;

import android.view.animation.Interpolator;
import com.android.app.animation.Interpolators;
import com.android.systemui.R;
import com.android.systemui.keyguard.domain.interactor.KeyguardTransitionInteractor;
import com.android.systemui.keyguard.shared.model.BurnInModel;
import com.android.systemui.keyguard.shared.model.Edge;
import com.android.systemui.keyguard.shared.model.KeyguardState;
import com.android.systemui.keyguard.ui.KeyguardTransitionAnimationFlow;
import com.android.systemui.keyguard.ui.StateToValue;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.sec.ims.volte2.data.VolteConstants;
import java.util.Map;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Ref$FloatRef;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.SafeFlow;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;
import kotlinx.coroutines.flow.internal.CombineKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    final /* synthetic */ BurnInParameters $params$inlined;
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ AodBurnInViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1(Continuation continuation, AodBurnInViewModel aodBurnInViewModel, BurnInParameters burnInParameters) {
        super(3, continuation);
        this.this$0 = aodBurnInViewModel;
        this.$params$inlined = burnInParameters;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1 = new AodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0, this.$params$inlined);
        aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.L$1 = obj2;
        return aodBurnInViewModel$movement$lambda$2$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        final int i = 0;
        final int i2 = 1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i3 = this.label;
        if (i3 == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            Map map = (Map) this.L$1;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$1(null), this.this$0.keyguardInteractor.keyguardTranslationY);
            AodBurnInViewModel aodBurnInViewModel = this.this$0;
            BurnInParameters burnInParameters = this.$params$inlined;
            aodBurnInViewModel.getClass();
            Edge.Companion companion = Edge.Companion;
            KeyguardState keyguardState = KeyguardState.AOD;
            Edge.StateToState create$default = Edge.Companion.create$default(companion, null, keyguardState, 1);
            KeyguardTransitionInteractor keyguardTransitionInteractor = aodBurnInViewModel.keyguardTransitionInteractor;
            Flow transition = keyguardTransitionInteractor.transition(create$default);
            final Flow transition2 = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, keyguardState, null, 2));
            Flow flow = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                            boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
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
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L4b
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                            float r7 = r6.value
                            r2 = 1065353216(0x3f800000, float:1.0)
                            float r2 = r2 - r7
                            r7 = 27
                            r4 = 0
                            com.android.systemui.keyguard.shared.model.TransitionStep r6 = com.android.systemui.keyguard.shared.model.TransitionStep.copy$default(r6, r2, r4, r7)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L4b
                            return r1
                        L4b:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final Flow transition3 = keyguardTransitionInteractor.transition(Edge.Companion.create$default(companion, null, KeyguardState.LOCKSCREEN, 1));
            final Flow flow2 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1$2$1
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
                            com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                            com.android.systemui.keyguard.shared.model.KeyguardState r6 = r6.from
                            com.android.systemui.keyguard.shared.model.KeyguardState r2 = com.android.systemui.keyguard.shared.model.KeyguardState.AOD
                            if (r6 == r2) goto L46
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L46
                            return r1
                        L46:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            final ChannelLimitedFlowMerge merge = FlowKt.merge(transition, flow, new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2$1, reason: invalid class name */
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
                    public final java.lang.Object emit(java.lang.Object r6, kotlin.coroutines.Continuation r7) {
                        /*
                            r5 = this;
                            boolean r0 = r7 instanceof com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r7
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2$2$1
                            r0.<init>(r7)
                        L18:
                            java.lang.Object r7 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r7)
                            goto L47
                        L27:
                            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
                            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
                            r5.<init>(r6)
                            throw r5
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r7)
                            com.android.systemui.keyguard.shared.model.TransitionStep r6 = (com.android.systemui.keyguard.shared.model.TransitionStep) r6
                            r7 = 0
                            r2 = 0
                            r4 = 27
                            com.android.systemui.keyguard.shared.model.TransitionStep r6 = com.android.systemui.keyguard.shared.model.TransitionStep.copy$default(r6, r7, r2, r4)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r5 = r5.$this_unsafeFlow
                            java.lang.Object r5 = r5.emit(r6, r0)
                            if (r5 != r1) goto L47
                            return r1
                        L47:
                            kotlin.Unit r5 = kotlin.Unit.INSTANCE
                            return r5
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$2(null), new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2$1, reason: invalid class name */
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
                            boolean r0 = r6 instanceof com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2$1 r0 = (com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2$1 r0 = new com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L4e
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            com.android.systemui.keyguard.shared.model.TransitionStep r5 = (com.android.systemui.keyguard.shared.model.TransitionStep) r5
                            android.view.animation.Interpolator r6 = com.android.app.animation.Interpolators.FAST_OUT_SLOW_IN
                            float r5 = r5.value
                            android.view.animation.PathInterpolator r6 = (android.view.animation.PathInterpolator) r6
                            float r5 = r6.getInterpolation(r5)
                            java.lang.Float r6 = new java.lang.Float
                            r6.<init>(r5)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r6, r0)
                            if (r4 != r1) goto L4e
                            return r1
                        L4e:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$burnIn$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            }, aodBurnInViewModel.burnInInteractor.burnIn(R.dimen.burn_in_prevention_offset_y), new AodBurnInViewModel$burnIn$5(aodBurnInViewModel, burnInParameters, null)));
            GoneToAodTransitionViewModel goneToAodTransitionViewModel = this.this$0.goneToAodTransitionViewModel;
            Object obj2 = map.get(new Integer(R.dimen.keyguard_enter_from_top_translation_y));
            obj2.getClass();
            final int intValue = ((Number) obj2).intValue();
            goneToAodTransitionViewModel.getClass();
            Duration.Companion companion2 = Duration.Companion;
            DurationUnit durationUnit = DurationUnit.MILLISECONDS;
            long duration = DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit);
            long duration2 = DurationKt.toDuration(500, durationUnit);
            Interpolator interpolator = Interpolators.EMPHASIZED_DECELERATE;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$3(null), new SafeFlow(new GoneToAodTransitionViewModel$enterFromTopTranslationY$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(goneToAodTransitionViewModel.transitionAnimation, duration2, new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj3) {
                    int i4 = i2;
                    float floatValue = ((Float) obj3).floatValue();
                    switch (i4) {
                        case 0:
                            return Float.valueOf((floatValue * (-r1)) + intValue);
                        default:
                            return Float.valueOf((floatValue * (-r1)) + intValue);
                    }
                }
            }, duration, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), goneToAodTransitionViewModel.powerInteractor.detailedWakefulness, GoneToAodTransitionViewModel$enterFromTopTranslationY$5.INSTANCE), null)));
            GoneToAodTransitionViewModel goneToAodTransitionViewModel2 = this.this$0.goneToAodTransitionViewModel;
            Object obj3 = map.get(new Integer(R.dimen.keyguard_enter_from_side_translation_x));
            obj3.getClass();
            final int intValue2 = ((Number) obj3).intValue();
            goneToAodTransitionViewModel2.getClass();
            long duration3 = DurationKt.toDuration(500, durationUnit);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$4(null), new SafeFlow(new GoneToAodTransitionViewModel$enterFromSideTranslationX$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(goneToAodTransitionViewModel2.transitionAnimation, DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.GoneToAodTransitionViewModel$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj32) {
                    int i4 = i;
                    float floatValue = ((Float) obj32).floatValue();
                    switch (i4) {
                        case 0:
                            return Float.valueOf((floatValue * (-r1)) + intValue2);
                        default:
                            return Float.valueOf((floatValue * (-r1)) + intValue2);
                    }
                }
            }, duration3, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), goneToAodTransitionViewModel2.powerInteractor.detailedWakefulness, GoneToAodTransitionViewModel$enterFromSideTranslationX$5.INSTANCE), null)));
            LockscreenToAodTransitionViewModel lockscreenToAodTransitionViewModel = this.this$0.lockscreenToAodTransitionViewModel;
            Object obj4 = map.get(new Integer(R.dimen.keyguard_enter_from_side_translation_x));
            obj4.getClass();
            final int intValue3 = ((Number) obj4).intValue();
            lockscreenToAodTransitionViewModel.getClass();
            long duration4 = DurationKt.toDuration(VolteConstants.ErrorCode.BUSY_EVERYWHERE, durationUnit);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$15 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$5(null), new SafeFlow(new LockscreenToAodTransitionViewModel$enterFromSideTranslationX$$inlined$transform$1(com.android.systemui.util.kotlin.FlowKt.sample(KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(lockscreenToAodTransitionViewModel.transitionAnimationOnFold, DurationKt.toDuration(500, durationUnit), new Function1() { // from class: com.android.systemui.keyguard.ui.viewmodel.LockscreenToAodTransitionViewModel$$ExternalSyntheticLambda2
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo779invoke(Object obj5) {
                    float floatValue = ((Float) obj5).floatValue();
                    return Float.valueOf((floatValue * (-r1)) + intValue3);
                }
            }, duration4, null, null, new AlternateBouncerToAodTransitionViewModel$$ExternalSyntheticLambda1(), interpolator, null, 152), lockscreenToAodTransitionViewModel.powerInteractor.detailedWakefulness, LockscreenToAodTransitionViewModel$enterFromSideTranslationX$5.INSTANCE), null)));
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$16 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$6(null), this.this$0.occludedToLockscreenTransitionViewModel.lockscreenTranslationY);
            AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel = this.this$0.aodToLockscreenTransitionViewModel;
            final Function0 function0 = this.$params$inlined.translationX;
            aodToLockscreenTransitionViewModel.getClass();
            final Ref$FloatRef ref$FloatRef = new Ref$FloatRef();
            final int i4 = 1;
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$17 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$7(null), KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(aodToLockscreenTransitionViewModel.transitionAnimation, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef, 1), 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i4) {
                        case 0:
                            Float f = (Float) function0.invoke();
                            ref$FloatRef.element = f != null ? f.floatValue() : 0.0f;
                            break;
                        default:
                            Float f2 = (Float) function0.invoke();
                            ref$FloatRef.element = f2 != null ? f2.floatValue() : 0.0f;
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode));
            AodToLockscreenTransitionViewModel aodToLockscreenTransitionViewModel2 = this.this$0.aodToLockscreenTransitionViewModel;
            final Function0 function02 = this.$params$inlined.translationY;
            aodToLockscreenTransitionViewModel2.getClass();
            final Ref$FloatRef ref$FloatRef2 = new Ref$FloatRef();
            final int i5 = 0;
            final Flow[] flowArr = {flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$13, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$14, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$15, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$16, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$17, new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new AodBurnInViewModel$movement$1$1$8(null), KeyguardTransitionAnimationFlow.FlowBuilder.m2600sharedFlowWithState74qcysc$default(aodToLockscreenTransitionViewModel2.transitionAnimation, DurationKt.toDuration(500, durationUnit), new AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda0(ref$FloatRef2, 0), 0L, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodToLockscreenTransitionViewModel$$ExternalSyntheticLambda1
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    switch (i5) {
                        case 0:
                            Float f = (Float) function02.invoke();
                            ref$FloatRef2.element = f != null ? f.floatValue() : 0.0f;
                            break;
                        default:
                            Float f2 = (Float) function02.invoke();
                            ref$FloatRef2.element = f2 != null ? f2.floatValue() : 0.0f;
                            break;
                    }
                    return Unit.INSTANCE;
                }
            }, null, null, null, null, IKnoxCustomManager.Stub.TRANSACTION_getHardKeyIntentMode))};
            Flow flow3 = new Flow() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1$3, reason: invalid class name */
                public final class AnonymousClass3 extends SuspendLambda implements Function3 {
                    private /* synthetic */ Object L$0;
                    /* synthetic */ Object L$1;
                    int label;

                    public AnonymousClass3(Continuation continuation) {
                        super(3, continuation);
                    }

                    @Override // kotlin.jvm.functions.Function3
                    public final Object invoke(Object obj, Object obj2, Object obj3) {
                        AnonymousClass3 anonymousClass3 = new AnonymousClass3((Continuation) obj3);
                        anonymousClass3.L$0 = (FlowCollector) obj;
                        anonymousClass3.L$1 = (Object[]) obj2;
                        return anonymousClass3.invokeSuspend(Unit.INSTANCE);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        float f;
                        float f2;
                        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                        int i = this.label;
                        if (i == 0) {
                            ResultKt.throwOnFailure(obj);
                            FlowCollector flowCollector = (FlowCollector) this.L$0;
                            Object[] objArr = (Object[]) this.L$1;
                            float floatValue = ((Float) objArr[0]).floatValue();
                            BurnInModel burnInModel = (BurnInModel) objArr[1];
                            StateToValue stateToValue = (StateToValue) objArr[2];
                            StateToValue stateToValue2 = (StateToValue) objArr[3];
                            StateToValue stateToValue3 = (StateToValue) objArr[4];
                            float floatValue2 = ((Float) objArr[5]).floatValue();
                            StateToValue stateToValue4 = (StateToValue) objArr[6];
                            StateToValue stateToValue5 = (StateToValue) objArr[7];
                            if (stateToValue5.transitionState.isTransitioning()) {
                                Float f3 = stateToValue5.value;
                                f2 = f3 != null ? f3.floatValue() : 0.0f;
                            } else {
                                if (stateToValue.transitionState.isTransitioning()) {
                                    Float f4 = stateToValue.value;
                                    f = f4 != null ? f4.floatValue() : 0.0f;
                                    floatValue = burnInModel.translationY;
                                } else {
                                    f = burnInModel.translationY + floatValue2;
                                }
                                f2 = f + floatValue;
                            }
                            if (stateToValue4.transitionState.isTransitioning()) {
                                Float f5 = stateToValue4.value;
                                if (f5 != null) {
                                    r11 = f5.floatValue();
                                }
                            } else {
                                float f6 = burnInModel.translationX;
                                Float f7 = stateToValue2.value;
                                float floatValue3 = f6 + (f7 != null ? f7.floatValue() : 0.0f);
                                Float f8 = stateToValue3.value;
                                r11 = (f8 != null ? f8.floatValue() : 0.0f) + floatValue3;
                            }
                            BurnInModel burnInModel2 = new BurnInModel((int) r11, (int) f2, burnInModel.scale, burnInModel.scaleClockOnly);
                            this.label = 1;
                            if (flowCollector.emit(burnInModel2, this) == coroutineSingletons) {
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

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    final Flow[] flowArr2 = flowArr;
                    Object combineInternal = CombineKt.combineInternal(flowArr2, new Function0() { // from class: com.android.systemui.keyguard.ui.viewmodel.AodBurnInViewModel$movement$lambda$2$lambda$1$$inlined$combine$1.2
                        @Override // kotlin.jvm.functions.Function0
                        public final Object invoke() {
                            return new Object[flowArr2.length];
                        }
                    }, new AnonymousClass3(null), flowCollector2, continuation);
                    return combineInternal == CoroutineSingletons.COROUTINE_SUSPENDED ? combineInternal : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(flowCollector, flow3, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
