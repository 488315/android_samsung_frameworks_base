package com.android.systemui.unfold.domain.interactor;

import com.android.systemui.R;
import com.android.systemui.common.ui.domain.interactor.ConfigurationInteractor;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepository;
import com.android.systemui.unfold.data.repository.UnfoldTransitionRepositoryImpl;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

public final class UnfoldTransitionInteractor {
    public final ConfigurationInteractor configurationInteractor;
    public final UnfoldTransitionRepository repository;
    public final Flow unfoldProgress;

    public UnfoldTransitionInteractor(UnfoldTransitionRepository unfoldTransitionRepository, ConfigurationInteractor configurationInteractor) {
        this.repository = unfoldTransitionRepository;
        this.configurationInteractor = configurationInteractor;
        final Flow transitionStatus = ((UnfoldTransitionRepositoryImpl) unfoldTransitionRepository).getTransitionStatus();
        this.unfoldProgress = FlowKt.distinctUntilChanged(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new UnfoldTransitionInteractor$unfoldProgress$2(null), new Flow() { // from class: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L53
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.unfold.data.repository.UnfoldTransitionStatus r5 = (com.android.systemui.unfold.data.repository.UnfoldTransitionStatus) r5
                        boolean r6 = r5 instanceof com.android.systemui.unfold.data.repository.UnfoldTransitionStatus.TransitionInProgress
                        if (r6 == 0) goto L3b
                        com.android.systemui.unfold.data.repository.UnfoldTransitionStatus$TransitionInProgress r5 = (com.android.systemui.unfold.data.repository.UnfoldTransitionStatus.TransitionInProgress) r5
                        goto L3c
                    L3b:
                        r5 = 0
                    L3c:
                        if (r5 == 0) goto L41
                        float r5 = r5.progress
                        goto L43
                    L41:
                        r5 = 1065353216(0x3f800000, float:1.0)
                    L43:
                        java.lang.Float r6 = new java.lang.Float
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L53
                        return r1
                    L53:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }));
    }

    public final FlowKt__ZipKt$combine$$inlined$combineUnsafe$FlowKt__ZipKt$1 unfoldTranslationX(boolean z) {
        ConfigurationInteractor configurationInteractor = this.configurationInteractor;
        ChannelFlowTransformLatest dimensionPixelSize = configurationInteractor.dimensionPixelSize(R.dimen.notification_side_paddings);
        final Flow flow = configurationInteractor.layoutDirection;
        return FlowKt.combine(this.unfoldProgress, dimensionPixelSize, new Flow() { // from class: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1

            /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2$1 r0 = (com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2$1 r0 = new com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L4d
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        java.lang.Number r5 = (java.lang.Number) r5
                        int r5 = r5.intValue()
                        if (r5 != r3) goto L3c
                        r5 = -1
                        goto L3d
                    L3c:
                        r5 = r3
                    L3d:
                        java.lang.Integer r6 = new java.lang.Integer
                        r6.<init>(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r6, r0)
                        if (r4 != r1) goto L4d
                        return r1
                    L4d:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.unfold.domain.interactor.UnfoldTransitionInteractor$unfoldTranslationX$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new UnfoldTransitionInteractor$unfoldTranslationX$2(z, null));
    }
}
