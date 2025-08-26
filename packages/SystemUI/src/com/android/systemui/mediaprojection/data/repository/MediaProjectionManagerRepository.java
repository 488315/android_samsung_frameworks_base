package com.android.systemui.mediaprojection.data.repository;

import android.app.ActivityManager;
import android.hardware.display.DisplayManager;
import android.media.projection.MediaProjectionEvent;
import android.media.projection.MediaProjectionInfo;
import android.media.projection.MediaProjectionManager;
import android.os.Handler;
import android.os.IBinder;
import android.view.ContentRecordingSession;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.mediaprojection.MediaProjectionServiceHelper;
import com.android.systemui.mediaprojection.data.model.MediaProjectionState;
import com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.ActivityTaskManagerTasksRepository;
import com.android.systemui.mediaprojection.taskswitcher.data.repository.TasksRepository;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
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

    public interface CallbackEvent {

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
                int iHashCode = this.info.hashCode() * 31;
                ContentRecordingSession contentRecordingSession = this.session;
                return iHashCode + (contentRecordingSession == null ? 0 : contentRecordingSession.hashCode());
            }

            public final String toString() {
                return "OnRecordingSessionSet(info=" + this.info + ", session=" + this.session + ")";
            }
        }

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

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* renamed from: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$stopProjecting$2, reason: invalid class name */
    final class AnonymousClass2 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $stopReason;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass2(int i, Continuation continuation) {
            super(2, continuation);
            this.$stopReason = i;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return MediaProjectionManagerRepository.this.new AnonymousClass2(this.$stopReason, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass2) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            LogBuffer logBuffer = MediaProjectionManagerRepository.this.logger;
            logBuffer.commit(logBuffer.obtain("MediaProjectionMngrRepo", LogLevel.DEBUG, new MediaProjectionManagerRepository$stopProjecting$2$$ExternalSyntheticLambda0(0), null));
            MediaProjectionManagerRepository.this.mediaProjectionManager.stopActiveProjection(this.$stopReason);
            return Unit.INSTANCE;
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
        final Flow flowConflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new MediaProjectionManagerRepository$callbackEventsFlow$1(this, null));
        ChannelFlowTransformLatest channelFlowTransformLatestMapLatest = FlowKt.mapLatest(new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filterNot$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        if (!(((MediaProjectionManagerRepository.CallbackEvent) obj) instanceof MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent)) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, new MediaProjectionManagerRepository$mediaProjectionState$2(this, null));
        SharingStarted.Companion.getClass();
        this.mediaProjectionState = FlowKt.stateIn(channelFlowTransformLatestMapLatest, coroutineScope, SharingStarted.Companion.Lazily, MediaProjectionState.NotProjecting.INSTANCE);
        final Flow flow = new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$filter$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        MediaProjectionManagerRepository.CallbackEvent callbackEvent = (MediaProjectionManagerRepository.CallbackEvent) obj;
                        if ((callbackEvent instanceof MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent) && ((MediaProjectionManagerRepository.CallbackEvent.OnMediaProjectionEvent) callbackEvent).event.getEventType() == 0) {
                            anonymousClass1.label = 1;
                            if (this.$this_unsafeFlow.emit(obj, anonymousClass1) == coroutineSingletons) {
                                return coroutineSingletons;
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flowConflatedCallbackFlow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.projectionStartedDuringCallAndActivePostCallEvent = new Flow() { // from class: com.android.systemui.mediaprojection.data.repository.MediaProjectionManagerRepository$special$$inlined$map$1

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

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
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
                        Unit unit = Unit.INSTANCE;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(unit, anonymousClass1) == coroutineSingletons) {
                            return coroutineSingletons;
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
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:38:0x00aa  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00b3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$stateForSession(MediaProjectionManagerRepository mediaProjectionManagerRepository, MediaProjectionInfo mediaProjectionInfo, ContentRecordingSession contentRecordingSession, ContinuationImpl continuationImpl) throws Throwable {
        MediaProjectionManagerRepository$stateForSession$1 mediaProjectionManagerRepository$stateForSession$1;
        String packageName;
        String str;
        ActivityManager.RunningTaskInfo runningTaskInfo;
        mediaProjectionManagerRepository.getClass();
        if (continuationImpl instanceof MediaProjectionManagerRepository$stateForSession$1) {
            mediaProjectionManagerRepository$stateForSession$1 = (MediaProjectionManagerRepository$stateForSession$1) continuationImpl;
            int i = mediaProjectionManagerRepository$stateForSession$1.label;
            if ((i & Integer.MIN_VALUE) != 0) {
                mediaProjectionManagerRepository$stateForSession$1.label = i - Integer.MIN_VALUE;
            } else {
                mediaProjectionManagerRepository$stateForSession$1 = new MediaProjectionManagerRepository$stateForSession$1(mediaProjectionManagerRepository, continuationImpl);
            }
        }
        Object objWithContext = mediaProjectionManagerRepository$stateForSession$1.result;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i2 = mediaProjectionManagerRepository$stateForSession$1.label;
        if (i2 == 0) {
            ResultKt.throwOnFailure(objWithContext);
            if (contentRecordingSession == null) {
                return MediaProjectionState.NotProjecting.INSTANCE;
            }
            packageName = mediaProjectionInfo.getPackageName();
            MediaProjectionManagerRepository$stateForSession$hostDeviceName$1 mediaProjectionManagerRepository$stateForSession$hostDeviceName$1 = new MediaProjectionManagerRepository$stateForSession$hostDeviceName$1(mediaProjectionManagerRepository, contentRecordingSession, null);
            mediaProjectionManagerRepository$stateForSession$1.L$0 = mediaProjectionManagerRepository;
            mediaProjectionManagerRepository$stateForSession$1.L$1 = contentRecordingSession;
            mediaProjectionManagerRepository$stateForSession$1.L$2 = packageName;
            mediaProjectionManagerRepository$stateForSession$1.label = 1;
            objWithContext = BuildersKt.withContext(mediaProjectionManagerRepository.backgroundDispatcher, mediaProjectionManagerRepository$stateForSession$hostDeviceName$1, mediaProjectionManagerRepository$stateForSession$1);
            if (objWithContext != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = (String) mediaProjectionManagerRepository$stateForSession$1.L$1;
            packageName = (String) mediaProjectionManagerRepository$stateForSession$1.L$0;
            ResultKt.throwOnFailure(objWithContext);
            runningTaskInfo = (ActivityManager.RunningTaskInfo) objWithContext;
            if (runningTaskInfo != null) {
                packageName.getClass();
                return new MediaProjectionState.Projecting.EntireScreen(packageName, str);
            }
            packageName.getClass();
            return new MediaProjectionState.Projecting.SingleTask(packageName, str, runningTaskInfo);
        }
        String str2 = (String) mediaProjectionManagerRepository$stateForSession$1.L$2;
        contentRecordingSession = (ContentRecordingSession) mediaProjectionManagerRepository$stateForSession$1.L$1;
        MediaProjectionManagerRepository mediaProjectionManagerRepository2 = (MediaProjectionManagerRepository) mediaProjectionManagerRepository$stateForSession$1.L$0;
        ResultKt.throwOnFailure(objWithContext);
        packageName = str2;
        mediaProjectionManagerRepository = mediaProjectionManagerRepository2;
        String str3 = (String) objWithContext;
        if (contentRecordingSession.getContentToRecord() == 0 || contentRecordingSession.getContentToRecord() == 2 || contentRecordingSession.getTokenToRecord() == null) {
            packageName.getClass();
            return new MediaProjectionState.Projecting.EntireScreen(packageName, str3);
        }
        TasksRepository tasksRepository = mediaProjectionManagerRepository.tasksRepository;
        IBinder tokenToRecord = contentRecordingSession.getTokenToRecord();
        if (tokenToRecord == null) {
            throw new IllegalStateException("Required value was null.");
        }
        mediaProjectionManagerRepository$stateForSession$1.L$0 = packageName;
        mediaProjectionManagerRepository$stateForSession$1.L$1 = str3;
        mediaProjectionManagerRepository$stateForSession$1.L$2 = null;
        mediaProjectionManagerRepository$stateForSession$1.label = 2;
        Object objFindRunningTaskFromWindowContainerToken = ((ActivityTaskManagerTasksRepository) tasksRepository).findRunningTaskFromWindowContainerToken(tokenToRecord, mediaProjectionManagerRepository$stateForSession$1);
        if (objFindRunningTaskFromWindowContainerToken != coroutineSingletons) {
            objWithContext = objFindRunningTaskFromWindowContainerToken;
            str = str3;
            runningTaskInfo = (ActivityManager.RunningTaskInfo) objWithContext;
            if (runningTaskInfo != null) {
            }
        }
        return coroutineSingletons;
    }

    public final Object stopProjecting(Continuation continuation) throws Throwable {
        Object objWithContext = BuildersKt.withContext(this.backgroundDispatcher, new AnonymousClass2(4, null), continuation);
        return objWithContext == CoroutineSingletons.COROUTINE_SUSPENDED ? objWithContext : Unit.INSTANCE;
    }
}
