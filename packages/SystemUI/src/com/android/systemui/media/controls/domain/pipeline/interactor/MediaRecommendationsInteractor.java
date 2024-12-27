package com.android.systemui.media.controls.domain.pipeline.interactor;

import android.content.Context;
import android.content.Intent;
import com.android.systemui.broadcast.BroadcastSender;
import com.android.systemui.media.controls.data.repository.MediaFilterRepository;
import com.android.systemui.media.controls.domain.pipeline.MediaDataProcessor;
import com.android.systemui.plugins.ActivityStarter;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

public final class MediaRecommendationsInteractor {
    public static final Intent SETTINGS_INTENT;
    public final ActivityStarter activityStarter;
    public final Context applicationContext;
    public final BroadcastSender broadcastSender;
    public final MediaDataProcessor mediaDataProcessor;
    public final Flow onAnyMediaConfigurationChange;
    public final Flow recommendations;
    public final MediaFilterRepository repository;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getEXPORTED_SMARTSPACE_TRAMPOLINE_ACTIVITY_NAME$annotations() {
        }
    }

    static {
        new Companion(null);
        SETTINGS_INTENT = new Intent("android.settings.ACTION_MEDIA_CONTROLS_SETTINGS");
    }

    public MediaRecommendationsInteractor(CoroutineScope coroutineScope, Context context, MediaFilterRepository mediaFilterRepository, MediaDataProcessor mediaDataProcessor, BroadcastSender broadcastSender, ActivityStarter activityStarter) {
        this.applicationContext = context;
        this.repository = mediaFilterRepository;
        this.mediaDataProcessor = mediaDataProcessor;
        this.broadcastSender = broadcastSender;
        this.activityStarter = activityStarter;
        final ReadonlyStateFlow readonlyStateFlow = mediaFilterRepository.smartspaceMediaData;
        this.recommendations = FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaRecommendationsInteractor this$0;

                /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaRecommendationsInteractor mediaRecommendationsInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaRecommendationsInteractor;
                }

                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r18, kotlin.coroutines.Continuation r19) {
                    /*
                        Method dump skipped, instructions count: 278
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        });
        FlowKt.stateIn(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.media.controls.shared.model.SmartspaceMediaData r5 = (com.android.systemui.media.controls.shared.model.SmartspaceMediaData) r5
                        boolean r5 = r5.isActive
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.controls.domain.pipeline.interactor.MediaRecommendationsInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.FALSE);
        this.onAnyMediaConfigurationChange = mediaFilterRepository.onAnyMediaConfigurationChange;
    }
}
