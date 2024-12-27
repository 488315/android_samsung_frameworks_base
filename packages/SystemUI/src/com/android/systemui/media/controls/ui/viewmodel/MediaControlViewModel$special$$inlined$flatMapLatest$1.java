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
public final class MediaControlViewModel$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaControlViewModel this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaControlViewModel$special$$inlined$flatMapLatest$1(Continuation continuation, MediaControlViewModel mediaControlViewModel) {
        super(3, continuation);
        this.this$0 = mediaControlViewModel;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaControlViewModel$special$$inlined$flatMapLatest$1 mediaControlViewModel$special$$inlined$flatMapLatest$1 = new MediaControlViewModel$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        mediaControlViewModel$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaControlViewModel$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaControlViewModel$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final MediaControlViewModel mediaControlViewModel = this.this$0;
            final Flow flow = mediaControlViewModel.interactor.mediaControl;
            Flow flow2 = new Flow() { // from class: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ MediaControlViewModel this$0;

                    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                    /* renamed from: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, MediaControlViewModel mediaControlViewModel) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = mediaControlViewModel;
                    }

                    /* JADX WARN: Removed duplicated region for block: B:20:0x0065 A[RETURN] */
                    /* JADX WARN: Removed duplicated region for block: B:21:0x003b  */
                    /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                        /*
                            r7 = this;
                            boolean r0 = r9 instanceof com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r9
                            com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1$2$1 r0 = (com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1$2$1 r0 = new com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1$2$1
                            r0.<init>(r9)
                        L18:
                            java.lang.Object r9 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 0
                            r4 = 2
                            r5 = 1
                            if (r2 == 0) goto L3b
                            if (r2 == r5) goto L33
                            if (r2 != r4) goto L2b
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L66
                        L2b:
                            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                            r7.<init>(r8)
                            throw r7
                        L33:
                            java.lang.Object r7 = r0.L$0
                            kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                            kotlin.ResultKt.throwOnFailure(r9)
                            goto L54
                        L3b:
                            kotlin.ResultKt.throwOnFailure(r9)
                            com.android.systemui.media.controls.shared.model.MediaControlModel r8 = (com.android.systemui.media.controls.shared.model.MediaControlModel) r8
                            kotlinx.coroutines.flow.FlowCollector r9 = r7.$this_unsafeFlow
                            if (r8 == 0) goto L5a
                            r0.L$0 = r9
                            r0.label = r5
                            com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel r7 = r7.this$0
                            java.lang.Object r7 = com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel.access$toViewModel(r7, r8, r0)
                            if (r7 != r1) goto L51
                            return r1
                        L51:
                            r6 = r9
                            r9 = r7
                            r7 = r6
                        L54:
                            com.android.systemui.media.controls.ui.viewmodel.MediaPlayerViewModel r9 = (com.android.systemui.media.controls.ui.viewmodel.MediaPlayerViewModel) r9
                            r6 = r9
                            r9 = r7
                            r7 = r6
                            goto L5b
                        L5a:
                            r7 = r3
                        L5b:
                            r0.L$0 = r3
                            r0.label = r4
                            java.lang.Object r7 = r9.emit(r7, r0)
                            if (r7 != r1) goto L66
                            return r1
                        L66:
                            kotlin.Unit r7 = kotlin.Unit.INSTANCE
                            return r7
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.ui.viewmodel.MediaControlViewModel$player$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, mediaControlViewModel), continuation);
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
