package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.pm.PackageManager;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import android.util.Log;
import com.android.settingslib.volume.data.repository.MediaControllerRepository;
import com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl;
import com.android.systemui.util.concurrency.Execution;
import com.android.systemui.volume.panel.component.mediaoutput.data.repository.LocalMediaRepositoryFactory;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
import com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession;
import com.android.systemui.volume.panel.shared.model.Result;
import com.android.systemui.volume.panel.shared.model.ResultKt;
import com.android.systemui.volume.panel.shared.model.ResultKt$filterData$$inlined$map$1;
import com.android.systemui.volume.panel.shared.model.ResultKt$wrapInResult$$inlined$map$1;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
            int hashCode = (mediaController == null ? 0 : mediaController.hashCode()) * 31;
            MediaController mediaController2 = this.remote;
            return hashCode + (mediaController2 != null ? mediaController2.hashCode() : 0);
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
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(((MediaControllerRepositoryImpl) mediaControllerRepository).activeSessions, new MediaOutputInteractor$special$$inlined$flatMapLatest$1(null, this));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Code restructure failed: missing block: B:18:0x0068, code lost:
                
                    if (r7.emit(r9, r0) != r1) goto L23;
                 */
                /* JADX WARN: Removed duplicated region for block: B:20:0x003b  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0023  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r8, kotlin.coroutines.Continuation r9) {
                    /*
                        r7 = this;
                        boolean r0 = r9 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r9
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2$1
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
                        goto L6b
                    L2b:
                        java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
                        java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
                        r7.<init>(r8)
                        throw r7
                    L33:
                        java.lang.Object r7 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                        kotlin.ResultKt.throwOnFailure(r9)
                        goto L60
                    L3b:
                        kotlin.ResultKt.throwOnFailure(r9)
                        java.util.List r8 = (java.util.List) r8
                        java.util.Collection r8 = (java.util.Collection) r8
                        kotlinx.coroutines.flow.FlowCollector r9 = r7.$this_unsafeFlow
                        r0.L$0 = r9
                        r0.label = r5
                        int r2 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.$r8$clinit
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r7 = r7.this$0
                        r7.getClass()
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getMediaControllers$2 r2 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getMediaControllers$2
                        r2.<init>(r8, r7, r3)
                        kotlin.coroutines.CoroutineContext r7 = r7.backgroundCoroutineContext
                        java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)
                        if (r7 != r1) goto L5d
                        goto L6a
                    L5d:
                        r6 = r9
                        r9 = r7
                        r7 = r6
                    L60:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r7 = r7.emit(r9, r0)
                        if (r7 != r1) goto L6b
                    L6a:
                        return r1
                    L6b:
                        kotlin.Unit r7 = kotlin.Unit.INSTANCE
                        return r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        SharingStarted.Companion companion = SharingStarted.Companion;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new MediaControllers(null, null));
        this.activeMediaControllers = stateIn;
        this.activeMediaDeviceSessions = FlowKt.stateIn(FlowKt.flowOn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto L59
                    L27:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r8)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r7 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.MediaControllers) r7
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions r8 = new com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions
                        android.media.session.MediaController r2 = r7.local
                        r4 = 0
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r5 = r6.this$0
                        if (r2 == 0) goto L42
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r2 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r5, r2)
                        goto L43
                    L42:
                        r2 = r4
                    L43:
                        android.media.session.MediaController r7 = r7.remote
                        if (r7 == 0) goto L4b
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r4 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r5, r7)
                    L4b:
                        r8.<init>(r2, r4)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r8, r0)
                        if (r6 != r1) goto L59
                        return r1
                    L59:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new MediaDeviceSessions(null, null));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.flowOn(new ResultKt$wrapInResult$$inlined$map$1(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L79
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r5 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.MediaControllers) r5
                        android.media.session.MediaController r6 = r5.local
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r2 = r4.this$0
                        if (r6 == 0) goto L4d
                        android.media.session.PlaybackState r6 = r6.getPlaybackState()
                        if (r6 == 0) goto L4d
                        boolean r6 = r6.isActive()
                        if (r6 != r3) goto L4d
                        android.media.session.MediaController r5 = r5.local
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r5 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r5)
                        goto L6e
                    L4d:
                        android.media.session.MediaController r6 = r5.remote
                        if (r6 == 0) goto L64
                        android.media.session.PlaybackState r6 = r6.getPlaybackState()
                        if (r6 == 0) goto L64
                        boolean r6 = r6.isActive()
                        if (r6 != r3) goto L64
                        android.media.session.MediaController r5 = r5.remote
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r5 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r5)
                        goto L6e
                    L64:
                        android.media.session.MediaController r5 = r5.local
                        if (r5 == 0) goto L6d
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r5 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r5)
                        goto L6e
                    L6d:
                        r5 = 0
                    L6e:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L79
                        return r1
                    L79:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(companion, 3), new Result.Loading());
        this.defaultActiveMediaSession = stateIn2;
        final ResultKt$filterData$$inlined$map$1 filterData = ResultKt.filterData(stateIn2);
        ChannelFlowTransformLatest transformLatest2 = FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        boolean r0 = r6 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2$1
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
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r5 = (com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession) r5
                        if (r5 == 0) goto L39
                        java.lang.String r5 = r5.packageName
                        goto L3a
                    L39:
                        r5 = 0
                    L3a:
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L45
                        return r1
                    L45:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), new MediaOutputInteractor$localMediaRepository$2(this, null));
        this.localMediaRepository = transformLatest2;
        this.currentConnectedDevice = FlowKt.distinctUntilChanged(FlowKt.transformLatest(transformLatest2, new MediaOutputInteractor$special$$inlined$flatMapLatest$2(null)));
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
        CharSequence charSequence;
        if (mediaOutputInteractor.execution.isMainThread()) {
            throw new IllegalArgumentException("Failed requirement.");
        }
        try {
            charSequence = mediaOutputInteractor.packageManager.getApplicationInfo(mediaController.getPackageName(), 4194816).loadLabel(mediaOutputInteractor.packageManager);
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("MediaOutputInteractor", "Unable to find info for package: " + mediaController.getPackageName());
            charSequence = null;
        }
        if (charSequence == null) {
            return null;
        }
        return new MediaDeviceSession(charSequence, mediaController.getPackageName(), mediaController.getSessionToken(), mediaController.getPlaybackInfo().getVolumeControl() != 0);
    }
}
