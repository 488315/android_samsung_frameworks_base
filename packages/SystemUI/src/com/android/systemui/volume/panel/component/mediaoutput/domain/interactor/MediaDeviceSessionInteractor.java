package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.media.session.MediaController;
import com.android.settingslib.volume.data.repository.MediaControllerRepository;
import com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class MediaDeviceSessionInteractor {
    public final CoroutineContext backgroundCoroutineContext;
    public final MediaControllerInteractor mediaControllerInteractor;
    public final MediaControllerRepository mediaControllerRepository;

    public MediaDeviceSessionInteractor(CoroutineContext coroutineContext, MediaControllerInteractor mediaControllerInteractor, MediaControllerRepository mediaControllerRepository) {
        this.backgroundCoroutineContext = coroutineContext;
        this.mediaControllerInteractor = mediaControllerInteractor;
        this.mediaControllerRepository = mediaControllerRepository;
    }

    public static final MediaController access$findControllerForSession(MediaDeviceSessionInteractor mediaDeviceSessionInteractor, Collection collection, MediaDeviceSession mediaDeviceSession) {
        Object obj;
        mediaDeviceSessionInteractor.getClass();
        Iterator it = collection.iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            if (Intrinsics.areEqual(((MediaController) obj).getSessionToken(), mediaDeviceSession.sessionToken)) {
                break;
            }
        }
        return (MediaController) obj;
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1] */
    public final MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1 playbackInfo(MediaDeviceSession mediaDeviceSession) {
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(FlowKt.flowOn(FlowKt.transformLatest(((MediaControllerRepositoryImpl) this.mediaControllerRepository).activeSessions, new MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1(null, this, mediaDeviceSession, new MediaDeviceSessionInteractor$playbackInfo$1(null))), this.backgroundCoroutineContext), Reflection.getOrCreateKotlinClass(MediaControllerChangeModel.AudioInfoChanged.class));
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1$2$1
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
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel$AudioInfoChanged r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel.AudioInfoChanged) r5
                        android.media.session.MediaController$PlaybackInfo r5 = r5.info
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackInfo$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1] */
    public final MediaDeviceSessionInteractor$playbackState$$inlined$map$1 playbackState(MediaDeviceSession mediaDeviceSession) {
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(FlowKt.flowOn(FlowKt.transformLatest(((MediaControllerRepositoryImpl) this.mediaControllerRepository).activeSessions, new MediaDeviceSessionInteractor$stateChanges$$inlined$flatMapLatest$1(null, this, mediaDeviceSession, new MediaDeviceSessionInteractor$playbackState$1(null))), this.backgroundCoroutineContext), Reflection.getOrCreateKotlinClass(MediaControllerChangeModel.PlaybackStateChanged.class));
        return new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1$2$1
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
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel$PlaybackStateChanged r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaControllerChangeModel.PlaybackStateChanged) r5
                        android.media.session.PlaybackState r5 = r5.state
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaDeviceSessionInteractor$playbackState$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    public final Object setSessionVolume(MediaDeviceSession mediaDeviceSession, int i, Continuation continuation) {
        if (!mediaDeviceSession.canAdjustVolume) {
            return Boolean.FALSE;
        }
        return BuildersKt.withContext(this.backgroundCoroutineContext, new MediaDeviceSessionInteractor$setSessionVolume$2(this, mediaDeviceSession, i, null), continuation);
    }
}
