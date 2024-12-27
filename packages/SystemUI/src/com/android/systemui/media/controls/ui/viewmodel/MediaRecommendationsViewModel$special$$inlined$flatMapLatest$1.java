package com.android.systemui.media.controls.ui.viewmodel;

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

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class MediaRecommendationsViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaRecommendationsViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaRecommendationsViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, MediaRecommendationsViewModel mediaRecommendationsViewModel) {
        super(3, continuation);
        this.this$0 = mediaRecommendationsViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaRecommendationsViewModel$special$$inlined$flatMapLatest$1 mediaRecommendationsViewModel$special$$inlined$flatMapLatest$1 = new MediaRecommendationsViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        mediaRecommendationsViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaRecommendationsViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaRecommendationsViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final MediaRecommendationsViewModel mediaRecommendationsViewModel = this.this$0;
            final Flow flow = mediaRecommendationsViewModel.interactor.recommendations;
            Flow flow2 = new Flow() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ MediaRecommendationsViewModel this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, MediaRecommendationsViewModel mediaRecommendationsViewModel) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = mediaRecommendationsViewModel;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:19:0x005c A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:20:0x003a  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0022  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                        /*
                            r6 = this;
                            boolean r0 = r8 instanceof com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r8
                            com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1$2$1
                            r0.<init>(r8)
                        L18:
                            java.lang.Object r8 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 2
                            r4 = 1
                            if (r2 == 0) goto L3a
                            if (r2 == r4) goto L32
                            if (r2 != r3) goto L2a
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L5d
                        L2a:
                            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                            r6.<init>(r7)
                            throw r6
                        L32:
                            java.lang.Object r6 = r0.L$0
                            kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                            kotlin.ResultKt.throwOnFailure(r8)
                            goto L51
                        L3a:
                            kotlin.ResultKt.throwOnFailure(r8)
                            com.android.systemui.media.controls.shared.model.MediaRecommendationsModel r7 = (com.android.systemui.media.controls.shared.model.MediaRecommendationsModel) r7
                            kotlinx.coroutines.flow.FlowCollector r8 = r6.$this_unsafeFlow
                            r0.L$0 = r8
                            r0.label = r4
                            com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel r6 = r6.this$0
                            java.lang.Object r6 = com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel.access$toRecsViewModel(r6, r7, r0)
                            if (r6 != r1) goto L4e
                            return r1
                        L4e:
                            r5 = r8
                            r8 = r6
                            r6 = r5
                        L51:
                            r7 = 0
                            r0.L$0 = r7
                            r0.label = r3
                            java.lang.Object r6 = r6.emit(r8, r0)
                            if (r6 != r1) goto L5d
                            return r1
                        L5d:
                            kotlin.Unit r6 = kotlin.Unit.INSTANCE
                            return r6
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaRecommendationsViewModel$mediaRecsCard$lambda$1$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, mediaRecommendationsViewModel), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (FlowKt.emitAll(this, flow2, flowCollector) == coroutineSingletons) {
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
