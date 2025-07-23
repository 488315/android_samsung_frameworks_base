package com.android.systemui.mediaprojection.data.repository;

import android.hardware.display.DisplayManager;
import android.media.projection.MediaProjectionEvent;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.view.ContentRecordingSession;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class MediaProjectionManagerRepository implements MediaProjectionRepository {
    public final CoroutineDispatcher backgroundDispatcher;
    public final DisplayManager displayManager;
    public final Handler handler;
    public final LogBuffer logger;
    public final MediaProjectionManager mediaProjectionManager;
    public final MediaProjectionServiceHelper mediaProjectionServiceHelper;
    public final ReadonlyStateFlow mediaProjectionState;
    public final MediaProjectionManagerRepository$special$$inlined$map$1 projectionStartedDuringCallAndActivePostCallEvent;
    public final TasksRepository tasksRepository;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface CallbackEvent {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnMediaProjectionEvent implements CallbackEvent {
            public final MediaProjectionEvent event;

            public OnMediaProjectionEvent(MediaProjectionEvent mediaProjectionEvent) {
                this.event = mediaProjectionEvent;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof OnMediaProjectionEvent) && Intrinsics.areEqual(this.event, ((OnMediaProjectionEvent) obj).event);
            }

            public final int hashCode() {
                return this.event.hashCode();
            }

            public final String toString() {
                return "OnMediaProjectionEvent(event=" + this.event + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnRecordingSessionSet implements CallbackEvent {
            public final MediaProjectionInfo info;
            public final ContentRecordingSession session;

            public OnRecordingSessionSet(MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession) {
                this.info = mediaProjectionInfo;
                this.session = contentRecordingSession;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof OnRecordingSessionSet)) {
                    return false;
                }
                OnRecordingSessionSet onRecordingSessionSet = (OnRecordingSessionSet) obj;
                return Intrinsics.areEqual(this.info, onRecordingSessionSet.info) && Intrinsics.areEqual(this.session, onRecordingSessionSet.session);
            }

            public final int hashCode() {
                int hashCode = this.info.hashCode() * 31;
                ContentRecordingSession contentRecordingSession = this.session;
                return hashCode + (contentRecordingSession == null ? 0 : contentRecordingSession.hashCode());
            }

            public final String toString() {
                return "OnRecordingSessionSet(info=" + this.info + ", session=" + this.session + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnStart implements CallbackEvent {
            public final MediaProjectionInfo info;

            public OnStart(MediaProjectionInfo mediaProjectionInfo) {
                this.info = mediaProjectionInfo;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof OnStart) && Intrinsics.areEqual(this.info, ((OnStart) obj).info);
            }

            public final int hashCode() {
                MediaProjectionInfo mediaProjectionInfo = this.info;
                if (mediaProjectionInfo == null) {
                    return 0;
                }
                return mediaProjectionInfo.hashCode();
            }

            public final String toString() {
                return "OnStart(info=" + this.info + ")";
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class OnStop implements CallbackEvent {
            public static final OnStop INSTANCE = new OnStop();

            private OnStop() {
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof OnStop);
            }

            public final int hashCode() {
                return 1406453418;
            }

            public final String toString() {
                return "OnStop";
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v3, types: [com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1] */
    public MediaProjectionManagerRepository(MediaProjectionManager mediaProjectionManager, DisplayManager displayManager, Handler handler, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, TasksRepository tasksRepository, MediaProjectionServiceHelper mediaProjectionServiceHelper, LogBuffer logBuffer) {
        this.mediaProjectionManager = mediaProjectionManager;
        this.displayManager = displayManager;
        this.handler = handler;
        this.backgroundDispatcher = coroutineDispatcher;
        this.tasksRepository = tasksRepository;
        this.mediaProjectionServiceHelper = mediaProjectionServiceHelper;
        this.logger = logBuffer;
        final Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MediaProjectionManagerRepository$callbackEventsFlow$1(this, null));
        ChannelFlowTransformLatest mapLatest = FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1$2$1 r0 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1$2$1 r0 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L44
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        r6 = r5
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$CallbackEvent r6 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent) r6
                        boolean r6 = r6 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent
                        if (r6 != 0) goto L44
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L44
                        return r1
                    L44:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, new MediaProjectionManagerRepository$mediaProjectionState$2(this, null));
        SharingStarted.Companion.getClass();
        this.mediaProjectionState = FlowKt.stateIn(mapLatest, coroutineScope, SharingStarted.Companion.Lazily, MediaProjectionState.NotProjecting.INSTANCE);
        final Flow flow = new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1$2$1 r0 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1$2$1 r0 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1$2$1
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
                        r6 = r5
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$CallbackEvent r6 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent) r6
                        boolean r2 = r6 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent
                        if (r2 == 0) goto L4e
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$CallbackEvent$OnMediaProjectionEvent r6 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent) r6
                        android.media.projection.MediaProjectionEvent r6 = r6.event
                        int r6 = r6.getEventType()
                        if (r6 != 0) goto L4e
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L4e
                        return r1
                    L4e:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.projectionStartedDuringCallAndActivePostCallEvent = new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1$2$1, reason: invalid class name */
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
                        boolean r0 = r6 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1$2$1 r0 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1$2$1 r0 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1$2$1
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
                        com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$CallbackEvent r5 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.CallbackEvent) r5
                        kotlin.Unit r5 = kotlin.Unit.INSTANCE
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L41
                        return r1
                    L41:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0072, code lost:
    
        if (r10 == r1) goto L32;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x00a4  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x00ad  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$stateForSession(com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository r7, android.media.projection.MediaProjectionInfo r8, android.view.ContentRecordingSession r9, kotlin.coroutines.jvm.internal.ContinuationImpl r10) {
        /*
            r7.getClass()
            boolean r0 = r10 instanceof com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1
            if (r0 == 0) goto L16
            r0 = r10
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1 r0 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1 r0 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$1
            r0.<init>(r7, r10)
        L1b:
            java.lang.Object r10 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 2
            r5 = 1
            if (r2 == 0) goto L53
            if (r2 == r5) goto L3f
            if (r2 != r4) goto L37
            java.lang.Object r7 = r0.L$1
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r0.L$0
            java.lang.String r8 = (java.lang.String) r8
            kotlin.ResultKt.throwOnFailure(r10)
            goto La0
        L37:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L3f:
            java.lang.Object r7 = r0.L$2
            java.lang.String r7 = (java.lang.String) r7
            java.lang.Object r8 = r0.L$1
            r9 = r8
            android.view.ContentRecordingSession r9 = (android.view.ContentRecordingSession) r9
            java.lang.Object r8 = r0.L$0
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository r8 = (com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository) r8
            kotlin.ResultKt.throwOnFailure(r10)
            r6 = r8
            r8 = r7
            r7 = r6
            goto L75
        L53:
            kotlin.ResultKt.throwOnFailure(r10)
            if (r9 != 0) goto L5b
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$NotProjecting r7 = com.android.systemui.mediaprojection.data.model.MediaProjectionState.NotProjecting.INSTANCE
            return r7
        L5b:
            java.lang.String r8 = r8.getPackageName()
            com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$hostDeviceName$1 r10 = new com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stateForSession$hostDeviceName$1
            r10.<init>(r7, r9, r3)
            r0.L$0 = r7
            r0.L$1 = r9
            r0.L$2 = r8
            r0.label = r5
            kotlinx.coroutines.CoroutineDispatcher r2 = r7.backgroundDispatcher
            java.lang.Object r10 = kotlinx.coroutines.BuildersKt.withContext(r2, r10, r0)
            if (r10 != r1) goto L75
            goto L9c
        L75:
            java.lang.String r10 = (java.lang.String) r10
            int r2 = r9.getContentToRecord()
            if (r2 == 0) goto Lbe
            android.os.IBinder r2 = r9.getTokenToRecord()
            if (r2 != 0) goto L84
            goto Lbe
        L84:
            com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository r7 = r7.tasksRepository
            android.os.IBinder r9 = r9.getTokenToRecord()
            if (r9 == 0) goto Lb6
            r0.L$0 = r8
            r0.L$1 = r10
            r0.L$2 = r3
            r0.label = r4
            com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository r7 = (com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository) r7
            java.lang.Object r7 = r7.findRunningTaskFromWindowContainerToken(r9, r0)
            if (r7 != r1) goto L9d
        L9c:
            return r1
        L9d:
            r6 = r10
            r10 = r7
            r7 = r6
        La0:
            android.app.ActivityManager$RunningTaskInfo r10 = (android.app.ActivityManager.RunningTaskInfo) r10
            if (r10 != 0) goto Lad
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen r9 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen
            r8.getClass()
            r9.<init>(r8, r7)
            return r9
        Lad:
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$SingleTask r9 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$SingleTask
            r8.getClass()
            r9.<init>(r8, r7, r10)
            return r9
        Lb6:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "Required value was null."
            r7.<init>(r8)
            throw r7
        Lbe:
            com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen r7 = new com.android.systemui.mediaprojection.data.model.MediaProjectionState$Projecting$EntireScreen
            r8.getClass()
            r7.<init>(r8, r10)
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository.access$stateForSession(com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository, android.media.projection.MediaProjectionInfo, android.view.ContentRecordingSession, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public final Object stopProjecting(Continuation continuation) {
        Object withContext = BuildersKt.withContext(this.backgroundDispatcher, new MediaProjectionManagerRepository$stopProjecting$2(this, 4, null), continuation);
        return withContext == CoroutineSingletons.COROUTINE_SUSPENDED ? withContext : Unit.INSTANCE;
    }
}
