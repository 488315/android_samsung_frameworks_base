package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.ArrayList;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

public final class MediaOutputInteractor$special$$inlined$flatMapLatest$1 extends SuspendLambda implements Function3 {
    private /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;
    final /* synthetic */ MediaOutputInteractor this$0;

    public MediaOutputInteractor$special$$inlined$flatMapLatest$1(Continuation continuation, MediaOutputInteractor mediaOutputInteractor) {
        super(3, continuation);
        this.this$0 = mediaOutputInteractor;
    }

    @Override // kotlin.jvm.functions.Function3
    public final Object invoke(Object obj, Object obj2, Object obj3) {
        MediaOutputInteractor$special$$inlined$flatMapLatest$1 mediaOutputInteractor$special$$inlined$flatMapLatest$1 = new MediaOutputInteractor$special$$inlined$flatMapLatest$1((Continuation) obj3, this.this$0);
        mediaOutputInteractor$special$$inlined$flatMapLatest$1.L$0 = (FlowCollector) obj;
        mediaOutputInteractor$special$$inlined$flatMapLatest$1.L$1 = obj2;
        return mediaOutputInteractor$special$$inlined$flatMapLatest$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Flow flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            FlowCollector flowCollector = (FlowCollector) this.L$0;
            final List list = (List) this.L$1;
            List<MediaController> list2 = list;
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list2, 10));
            for (final MediaController mediaController : list2) {
                MediaOutputInteractor mediaOutputInteractor = this.this$0;
                if (mediaController == null) {
                    int i2 = MediaOutputInteractor.$r8$clinit;
                    mediaOutputInteractor.getClass();
                    flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__BuildersKt$flowOf$$inlined$unsafeFlow$2(null);
                } else {
                    MediaControllerInteractorImpl mediaControllerInteractorImpl = (MediaControllerInteractorImpl) mediaOutputInteractor.mediaControllerInteractor;
                    mediaControllerInteractorImpl.getClass();
                    final Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MediaControllerInteractorImpl$stateChanges$1(mediaController, mediaControllerInteractorImpl, null));
                    flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MediaOutputInteractor$stateChanges$2(mediaController, null), new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1

                        /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1$2, reason: invalid class name */
                        public final class AnonymousClass2 implements FlowCollector {
                            public final /* synthetic */ MediaController $this_stateChanges$inlined;
                            public final /* synthetic */ FlowCollector $this_unsafeFlow;

                            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1$2$1, reason: invalid class name */
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

                            public AnonymousClass2(FlowCollector flowCollector, MediaController mediaController) {
                                this.$this_unsafeFlow = flowCollector;
                                this.$this_stateChanges$inlined = mediaController;
                            }

                            @Override // kotlinx.coroutines.flow.FlowCollector
                            /*
                                Code decompiled incorrectly, please refer to instructions dump.
                                To view partially-correct code enable 'Show inconsistent code' option in preferences
                            */
                            public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                                /*
                                    r4 = this;
                                    boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1.AnonymousClass2.AnonymousClass1
                                    if (r0 == 0) goto L13
                                    r0 = r6
                                    com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                                    int r1 = r0.label
                                    r2 = -2147483648(0xffffffff80000000, float:-0.0)
                                    r3 = r1 & r2
                                    if (r3 == 0) goto L13
                                    int r1 = r1 - r2
                                    r0.label = r1
                                    goto L18
                                L13:
                                    com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1$2$1
                                    r0.<init>(r6)
                                L18:
                                    java.lang.Object r6 = r0.result
                                    kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                                    int r2 = r0.label
                                    r3 = 1
                                    if (r2 == 0) goto L2f
                                    if (r2 != r3) goto L27
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    goto L41
                                L27:
                                    java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                                    java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                                    r4.<init>(r5)
                                    throw r4
                                L2f:
                                    kotlin.ResultKt.throwOnFailure(r6)
                                    com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel) r5
                                    android.media.session.MediaController r5 = r4.$this_stateChanges$inlined
                                    r0.label = r3
                                    kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                                    java.lang.Object r4 = r4.emit(r5, r0)
                                    if (r4 != r1) goto L41
                                    return r1
                                L41:
                                    kotlin.Unit r4 = kotlin.Unit.INSTANCE
                                    return r4
                                */
                                throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$stateChanges$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                            }
                        }

                        @Override // kotlinx.coroutines.flow.Flow
                        public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                            Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, mediaController), continuation);
                            return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                        }
                    });
                }
                arrayList.add(flowKt__EmittersKt$onStart$$inlined$unsafeFlow$1);
            }
            final ChannelLimitedFlowMerge merge = FlowKt.merge(arrayList);
            FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1 flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12 = new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MediaOutputInteractor$activeMediaControllers$1$3(list, null), new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ List $activeSessions$inlined;
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;

                    /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, List list) {
                        this.$this_unsafeFlow = flowCollector;
                        this.$activeSessions$inlined = list;
                    }

                    @Override // kotlinx.coroutines.flow.FlowCollector
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                        /*
                            r4 = this;
                            boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L41
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            android.media.session.MediaController r5 = (android.media.session.MediaController) r5
                            java.util.List r5 = r4.$activeSessions$inlined
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L41
                            return r1
                        L41:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$activeMediaControllers$lambda$2$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector2, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector2, list), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            });
            this.label = 1;
            if (FlowKt.emitAll(this, flowKt__EmittersKt$onStart$$inlined$unsafeFlow$12, flowCollector) == coroutineSingletons) {
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
