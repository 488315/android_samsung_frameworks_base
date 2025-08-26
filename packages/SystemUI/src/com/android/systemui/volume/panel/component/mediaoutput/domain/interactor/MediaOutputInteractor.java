package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.pm.PackageManager;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.util.Log;
import com.android.settingslib.volume.data.repository.MediaControllerRepository;
import com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.volume.panel.component.mediaoutput.data.repository.LocalMediaRepositoryFactory;
import com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.shared.model.Result;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import com.android.systemui.volume.panel.shared.model.ResultKt$wrapInResult$$inlined$map$1;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* loaded from: classes3.dex */
public final class MediaOutputInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeMediaControllers;
    public final ReadonlyStateFlow activeMediaDeviceSessions;
    public final CoroutineContext backgroundCoroutineContext;
    public final Flow currentConnectedDevice;
    public final ReadonlyStateFlow defaultActiveMediaSession;
    public final Execution execution;
    public final ChannelFlowTransformLatest localMediaRepository;
    public final LocalMediaRepositoryFactory localMediaRepositoryFactory;
    public final MediaControllerInteractor mediaControllerInteractor;
    public final PackageManager packageManager;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class MediaControllers {
        public final MediaController local;
        public final MediaController remote;

        public MediaControllers(MediaController mediaController, MediaController mediaController2) {
            this.local = mediaController;
            this.remote = mediaController2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof MediaControllers)) {
                return false;
            }
            MediaControllers mediaControllers = (MediaControllers) obj;
            return Intrinsics.areEqual(this.local, mediaControllers.local) && Intrinsics.areEqual(this.remote, mediaControllers.remote);
        }

        public final int hashCode() {
            MediaController mediaController = this.local;
            int iHashCode = (mediaController == null ? 0 : mediaController.hashCode()) * 31;
            MediaController mediaController2 = this.remote;
            return iHashCode + (mediaController2 != null ? mediaController2.hashCode() : 0);
        }

        public final String toString() {
            return "MediaControllers(local=" + this.local + ", remote=" + this.remote + ")";
        }
    }

    static {
        new Companion(null);
    }

    public MediaOutputInteractor(LocalMediaRepositoryFactory localMediaRepositoryFactory, PackageManager packageManager, CoroutineScope coroutineScope, CoroutineContext coroutineContext, MediaControllerRepository mediaControllerRepository, MediaControllerInteractor mediaControllerInteractor, Execution execution) {
        this.localMediaRepositoryFactory = localMediaRepositoryFactory;
        this.packageManager = packageManager;
        this.backgroundCoroutineContext = coroutineContext;
        this.mediaControllerInteractor = mediaControllerInteractor;
        this.execution = execution;
        final ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest = FlowKt.transformLatest(((MediaControllerRepositoryImpl) mediaControllerRepository).activeSessions, new MediaOutputInteractor$special$$inlined$flatMapLatest$1(null, this));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaOutputInteractor mediaOutputInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaOutputInteractor;
                }

                /* JADX WARN: Code restructure failed: missing block: B:21:0x0068, code lost:
                
                    if (r7.emit(r9, r0) == r1) goto L22;
                 */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) throws Throwable {
                    AnonymousClass1 anonymousClass1;
                    FlowCollector flowCollector;
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
                        FlowCollector flowCollector2 = this.$this_unsafeFlow;
                        anonymousClass1.L$0 = flowCollector2;
                        anonymousClass1.label = 1;
                        int i3 = MediaOutputInteractor.$r8$clinit;
                        MediaOutputInteractor mediaOutputInteractor = this.this$0;
                        mediaOutputInteractor.getClass();
                        Object objWithContext = BuildersKt.withContext(mediaOutputInteractor.backgroundCoroutineContext, new MediaOutputInteractor$getMediaControllers$2((List) obj, mediaOutputInteractor, null), anonymousClass1);
                        if (objWithContext != coroutineSingletons) {
                            obj2 = objWithContext;
                            flowCollector = flowCollector2;
                        }
                        return coroutineSingletons;
                    }
                    if (i2 != 1) {
                        if (i2 != 2) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        ResultKt.throwOnFailure(obj2);
                        return Unit.INSTANCE;
                    }
                    flowCollector = (FlowCollector) anonymousClass1.L$0;
                    ResultKt.throwOnFailure(obj2);
                    anonymousClass1.L$0 = null;
                    anonymousClass1.label = 2;
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object objCollect = channelFlowTransformLatestTransformLatest.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow readonlyStateFlowStateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new MediaControllers(null, null));
        this.activeMediaControllers = readonlyStateFlowStateIn;
        this.activeMediaDeviceSessions = FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaOutputInteractor mediaOutputInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaOutputInteractor;
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
                        MediaOutputInteractor.MediaControllers mediaControllers = (MediaOutputInteractor.MediaControllers) obj;
                        MediaController mediaController = mediaControllers.local;
                        MediaOutputInteractor mediaOutputInteractor = this.this$0;
                        MediaDeviceSession mediaDeviceSessionAccess$mediaDeviceSession = mediaController != null ? MediaOutputInteractor.access$mediaDeviceSession(mediaOutputInteractor, mediaController) : null;
                        MediaController mediaController2 = mediaControllers.remote;
                        MediaDeviceSessions mediaDeviceSessions = new MediaDeviceSessions(mediaDeviceSessionAccess$mediaDeviceSession, mediaController2 != null ? MediaOutputInteractor.access$mediaDeviceSession(mediaOutputInteractor, mediaController2) : null);
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mediaDeviceSessions, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new MediaDeviceSessions(null, null));
        ReadonlyStateFlow readonlyStateFlowStateIn2 = FlowKt.stateIn(FlowKt.flowOn(new ResultKt$wrapInResult$$inlined$map$1(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaOutputInteractor mediaOutputInteractor) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaOutputInteractor;
                }

                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    MediaDeviceSession mediaDeviceSessionAccess$mediaDeviceSession;
                    PlaybackState playbackState;
                    PlaybackState playbackState2;
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
                        MediaOutputInteractor.MediaControllers mediaControllers = (MediaOutputInteractor.MediaControllers) obj;
                        MediaController mediaController = mediaControllers.local;
                        MediaOutputInteractor mediaOutputInteractor = this.this$0;
                        if (mediaController == null || (playbackState2 = mediaController.getPlaybackState()) == null || !playbackState2.isActive()) {
                            MediaController mediaController2 = mediaControllers.remote;
                            if (mediaController2 == null || (playbackState = mediaController2.getPlaybackState()) == null || !playbackState.isActive()) {
                                MediaController mediaController3 = mediaControllers.local;
                                mediaDeviceSessionAccess$mediaDeviceSession = mediaController3 != null ? MediaOutputInteractor.access$mediaDeviceSession(mediaOutputInteractor, mediaController3) : null;
                            } else {
                                mediaDeviceSessionAccess$mediaDeviceSession = MediaOutputInteractor.access$mediaDeviceSession(mediaOutputInteractor, mediaControllers.remote);
                            }
                        } else {
                            mediaDeviceSessionAccess$mediaDeviceSession = MediaOutputInteractor.access$mediaDeviceSession(mediaOutputInteractor, mediaControllers.local);
                        }
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(mediaDeviceSessionAccess$mediaDeviceSession, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = readonlyStateFlowStateIn.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new Result.Loading());
        this.defaultActiveMediaSession = readonlyStateFlowStateIn2;
        final ResultKt$filterData$$inlined$map$1 resultKt$filterData$$inlined$map$1FilterData = com.android.systemui.volume.panel.shared.model.ResultKt.filterData(readonlyStateFlowStateIn2);
        ChannelFlowTransformLatest channelFlowTransformLatestTransformLatest2 = FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4

            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2$1, reason: invalid class name */
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
                        MediaDeviceSession mediaDeviceSession = (MediaDeviceSession) obj;
                        String str = mediaDeviceSession != null ? mediaDeviceSession.packageName : null;
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(str, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = resultKt$filterData$$inlined$map$1FilterData.collect(new AnonymousClass2(flowCollector), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), new MediaOutputInteractor$localMediaRepository$2(this, null));
        this.localMediaRepository = channelFlowTransformLatestTransformLatest2;
        this.currentConnectedDevice = FlowKt.distinctUntilChanged(FlowKt.transformLatest(channelFlowTransformLatestTransformLatest2, new MediaOutputInteractor$special$$inlined$flatMapLatest$2(null)));
    }

    public static final MediaController access$chooseController(MediaOutputInteractor mediaOutputInteractor, MediaController mediaController, MediaController mediaController2) {
        if (mediaOutputInteractor.execution.isMainThread()) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        if (mediaController != null) {
            PlaybackState playbackState = mediaController2.getPlaybackState();
            boolean z = false;
            boolean z2 = playbackState != null && playbackState.isActive();
            PlaybackState playbackState2 = mediaController.getPlaybackState();
            if (playbackState2 != null && playbackState2.isActive()) {
                z = true;
            }
            if (!z2 || z) {
                return mediaController;
            }
        }
        return mediaController2;
    }

    public static final MediaDeviceSession access$mediaDeviceSession(MediaOutputInteractor mediaOutputInteractor, MediaController mediaController) {
        CharSequence charSequenceLoadLabel;
        if (mediaOutputInteractor.execution.isMainThread()) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        try {
            charSequenceLoadLabel = mediaOutputInteractor.packageManager.getApplicationInfo(mediaController.getPackageName(), 4194816).loadLabel(mediaOutputInteractor.packageManager);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("MediaOutputInteractor", "Unable to find info for package: " + mediaController.getPackageName());
            charSequenceLoadLabel = null;
        }
        if (charSequenceLoadLabel == null) {
            return null;
        }
        return new MediaDeviceSession(charSequenceLoadLabel, mediaController.getPackageName(), mediaController.getSessionToken(), mediaController.getPlaybackInfo().getVolumeControl() != 0);
    }
}
