package com.android.systemui.volume.panel.component.mediaoutput.domain.interactor;

import android.content.pm.PackageManager;
import android.media.session.MediaController;
import android.media.session.PlaybackState;
import com.android.settingslib.volume.data.repository.MediaControllerRepository;
import com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl;
import com.android.systemui.volume.panel.component.mediaoutput.data.repository.LocalMediaRepositoryFactory;
import com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions;
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
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class MediaOutputInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ReadonlyStateFlow activeMediaControllers;
    public final ReadonlyStateFlow activeMediaDeviceSessions;
    public final CoroutineContext backgroundCoroutineContext;
    public final Flow currentConnectedDevice;
    public final ReadonlyStateFlow defaultActiveMediaSession;
    public final ChannelFlowTransformLatest localMediaRepository;
    public final LocalMediaRepositoryFactory localMediaRepositoryFactory;
    public final MediaControllerInteractor mediaControllerInteractor;
    public final PackageManager packageManager;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public MediaOutputInteractor(LocalMediaRepositoryFactory localMediaRepositoryFactory, PackageManager packageManager, CoroutineScope coroutineScope, CoroutineContext coroutineContext, MediaControllerRepository mediaControllerRepository, MediaControllerInteractor mediaControllerInteractor) {
        this.localMediaRepositoryFactory = localMediaRepositoryFactory;
        this.packageManager = packageManager;
        this.backgroundCoroutineContext = coroutineContext;
        this.mediaControllerInteractor = mediaControllerInteractor;
        final ChannelFlowTransformLatest transformLatest = FlowKt.transformLatest(((MediaControllerRepositoryImpl) mediaControllerRepository).activeSessions, new MediaOutputInteractor$special$$inlined$flatMapLatest$1(null, this));
        Flow flow = new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
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
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto Lb0
                    L28:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L30:
                        kotlin.ResultKt.throwOnFailure(r12)
                        java.util.List r11 = (java.util.List) r11
                        java.util.Collection r11 = (java.util.Collection) r11
                        int r12 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.$r8$clinit
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r12 = r10.this$0
                        r12.getClass()
                        java.util.LinkedHashSet r12 = new java.util.LinkedHashSet
                        r12.<init>()
                        java.util.Iterator r11 = r11.iterator()
                        r2 = 0
                        r4 = r2
                        r5 = r4
                    L4a:
                        boolean r6 = r11.hasNext()
                        if (r6 == 0) goto La0
                        java.lang.Object r6 = r11.next()
                        android.media.session.MediaController r6 = (android.media.session.MediaController) r6
                        android.media.session.MediaController$PlaybackInfo r7 = r6.getPlaybackInfo()
                        if (r7 != 0) goto L5d
                        goto L4a
                    L5d:
                        int r7 = r7.getPlaybackType()
                        if (r7 == r3) goto L91
                        r8 = 2
                        if (r7 == r8) goto L67
                        goto L4a
                    L67:
                        if (r4 == 0) goto L6e
                        java.lang.String r7 = r4.getPackageName()
                        goto L6f
                    L6e:
                        r7 = r2
                    L6f:
                        java.lang.String r8 = r6.getPackageName()
                        r9 = 0
                        boolean r7 = kotlin.text.StringsKt__StringsJVMKt.equals(r7, r8, r9)
                        if (r7 == 0) goto L7b
                        r4 = r2
                    L7b:
                        java.lang.String r7 = r6.getPackageName()
                        boolean r7 = r12.contains(r7)
                        if (r7 != 0) goto L4a
                        java.lang.String r7 = r6.getPackageName()
                        r12.add(r7)
                        android.media.session.MediaController r5 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.chooseController(r5, r6)
                        goto L4a
                    L91:
                        java.lang.String r7 = r6.getPackageName()
                        boolean r7 = r12.contains(r7)
                        if (r7 != 0) goto L4a
                        android.media.session.MediaController r4 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.chooseController(r4, r6)
                        goto L4a
                    La0:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r11 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers
                        r11.<init>(r4, r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto Lb0
                        return r1
                    Lb0:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
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
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        final ReadonlyStateFlow stateIn = FlowKt.stateIn(flow, coroutineScope, startedEagerly, new MediaControllers(null, null));
        this.activeMediaDeviceSessions = FlowKt.stateIn(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
                /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    Object L$1;
                    Object L$2;
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

                /* JADX WARN: Removed duplicated region for block: B:21:0x00ad A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:26:0x0081  */
                /* JADX WARN: Removed duplicated region for block: B:30:0x0098  */
                /* JADX WARN: Removed duplicated region for block: B:31:0x0053  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
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
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 3
                        r4 = 2
                        r5 = 1
                        r6 = 0
                        if (r2 == 0) goto L53
                        if (r2 == r5) goto L43
                        if (r2 == r4) goto L37
                        if (r2 != r3) goto L2f
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto Lae
                    L2f:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L37:
                        java.lang.Object r8 = r0.L$1
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r8 = (com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession) r8
                        java.lang.Object r9 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L95
                    L43:
                        java.lang.Object r8 = r0.L$2
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r8 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.MediaControllers) r8
                        java.lang.Object r9 = r0.L$1
                        kotlinx.coroutines.flow.FlowCollector r9 = (kotlinx.coroutines.flow.FlowCollector) r9
                        java.lang.Object r2 = r0.L$0
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2$2 r2 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2) r2
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L73
                    L53:
                        kotlin.ResultKt.throwOnFailure(r10)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r9 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.MediaControllers) r9
                        android.media.session.MediaController r10 = r9.local
                        kotlinx.coroutines.flow.FlowCollector r2 = r8.$this_unsafeFlow
                        if (r10 == 0) goto L7a
                        r0.L$0 = r8
                        r0.L$1 = r2
                        r0.L$2 = r9
                        r0.label = r5
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r5 = r8.this$0
                        java.lang.Object r10 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r5, r10, r0)
                        if (r10 != r1) goto L6f
                        return r1
                    L6f:
                        r7 = r2
                        r2 = r8
                        r8 = r9
                        r9 = r7
                    L73:
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r10 = (com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession) r10
                        r7 = r9
                        r9 = r8
                        r8 = r10
                        r10 = r7
                        goto L7d
                    L7a:
                        r10 = r2
                        r2 = r8
                        r8 = r6
                    L7d:
                        android.media.session.MediaController r9 = r9.remote
                        if (r9 == 0) goto L98
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r2 = r2.this$0
                        r0.L$0 = r10
                        r0.L$1 = r8
                        r0.L$2 = r6
                        r0.label = r4
                        java.lang.Object r9 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r9, r0)
                        if (r9 != r1) goto L92
                        return r1
                    L92:
                        r7 = r10
                        r10 = r9
                        r9 = r7
                    L95:
                        com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r10 = (com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession) r10
                        goto L9a
                    L98:
                        r9 = r10
                        r10 = r6
                    L9a:
                        com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions r2 = new com.android.systemui.volume.panel.component.mediaoutput.domain.model.MediaDeviceSessions
                        r2.<init>(r8, r10)
                        r0.L$0 = r6
                        r0.L$1 = r6
                        r0.L$2 = r6
                        r0.label = r3
                        java.lang.Object r8 = r9.emit(r2, r0)
                        if (r8 != r1) goto Lae
                        return r1
                    Lae:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineScope, startedEagerly, new MediaDeviceSessions(null, null));
        ReadonlyStateFlow stateIn2 = FlowKt.stateIn(FlowKt.flowOn(new ResultKt$wrapInResult$$inlined$map$1(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaOutputInteractor this$0;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

                /* JADX WARN: Removed duplicated region for block: B:21:0x009f A[RETURN] */
                /* JADX WARN: Removed duplicated region for block: B:22:0x0042  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r9, kotlin.coroutines.Continuation r10) {
                    /*
                        r8 = this;
                        boolean r0 = r10 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r10
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
                        r0.<init>(r10)
                    L18:
                        java.lang.Object r10 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 0
                        r4 = 4
                        r5 = 3
                        r6 = 2
                        r7 = 1
                        if (r2 == 0) goto L42
                        if (r2 == r7) goto L3a
                        if (r2 == r6) goto L3a
                        if (r2 == r5) goto L3a
                        if (r2 != r4) goto L32
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto La0
                    L32:
                        java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
                        java.lang.String r9 = "call to 'resume' before 'invoke' with coroutine"
                        r8.<init>(r9)
                        throw r8
                    L3a:
                        java.lang.Object r8 = r0.L$0
                        kotlinx.coroutines.flow.FlowCollector r8 = (kotlinx.coroutines.flow.FlowCollector) r8
                        kotlin.ResultKt.throwOnFailure(r10)
                        goto L95
                    L42:
                        kotlin.ResultKt.throwOnFailure(r10)
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$MediaControllers r9 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.MediaControllers) r9
                        android.media.session.MediaController r10 = r9.local
                        com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r2 = r8.this$0
                        kotlinx.coroutines.flow.FlowCollector r8 = r8.$this_unsafeFlow
                        if (r10 == 0) goto L68
                        android.media.session.PlaybackState r10 = r10.getPlaybackState()
                        if (r10 == 0) goto L68
                        boolean r10 = r10.isActive()
                        if (r10 != r7) goto L68
                        android.media.session.MediaController r9 = r9.local
                        r0.L$0 = r8
                        r0.label = r7
                        java.lang.Object r10 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r9, r0)
                        if (r10 != r1) goto L95
                        return r1
                    L68:
                        android.media.session.MediaController r10 = r9.remote
                        if (r10 == 0) goto L85
                        android.media.session.PlaybackState r10 = r10.getPlaybackState()
                        if (r10 == 0) goto L85
                        boolean r10 = r10.isActive()
                        if (r10 != r7) goto L85
                        android.media.session.MediaController r9 = r9.remote
                        r0.L$0 = r8
                        r0.label = r6
                        java.lang.Object r10 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r9, r0)
                        if (r10 != r1) goto L95
                        return r1
                    L85:
                        android.media.session.MediaController r9 = r9.local
                        if (r9 == 0) goto L94
                        r0.L$0 = r8
                        r0.label = r5
                        java.lang.Object r10 = com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(r2, r9, r0)
                        if (r10 != r1) goto L95
                        return r1
                    L94:
                        r10 = r3
                    L95:
                        r0.L$0 = r3
                        r0.label = r4
                        java.lang.Object r8 = r8.emit(r10, r0)
                        if (r8 != r1) goto La0
                        return r1
                    La0:
                        kotlin.Unit r8 = kotlin.Unit.INSTANCE
                        return r8
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineContext), coroutineScope, startedEagerly, new Result.Loading());
        this.defaultActiveMediaSession = stateIn2;
        final ResultKt$filterData$$inlined$map$1 filterData = ResultKt.filterData(stateIn2);
        this.currentConnectedDevice = FlowKt.distinctUntilChanged(FlowKt.transformLatest(FlowKt.transformLatest(FlowKt.distinctUntilChanged(new Flow() { // from class: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4

            /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
            /* renamed from: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$special$$inlined$map$4$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        }), new MediaOutputInteractor$localMediaRepository$2(this, null)), new MediaOutputInteractor$special$$inlined$flatMapLatest$2(null)));
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0079 A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:14:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$mediaDeviceSession(com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor r6, android.media.session.MediaController r7, kotlin.coroutines.Continuation r8) {
        /*
            r6.getClass()
            boolean r0 = r8 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$mediaDeviceSession$1
            if (r0 == 0) goto L16
            r0 = r8
            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$mediaDeviceSession$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$mediaDeviceSession$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$mediaDeviceSession$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$mediaDeviceSession$1
            r0.<init>(r6, r8)
        L1b:
            java.lang.Object r8 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L3d
            if (r2 != r4) goto L35
            int r6 = r0.I$0
            java.lang.Object r7 = r0.L$1
            android.media.session.MediaSession$Token r7 = (android.media.session.MediaSession.Token) r7
            java.lang.Object r0 = r0.L$0
            java.lang.String r0 = (java.lang.String) r0
            kotlin.ResultKt.throwOnFailure(r8)
            goto L75
        L35:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L3d:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.String r8 = r7.getPackageName()
            android.media.session.MediaSession$Token r2 = r7.getSessionToken()
            android.media.session.MediaController$PlaybackInfo r5 = r7.getPlaybackInfo()
            if (r5 == 0) goto L5d
            android.media.session.MediaController$PlaybackInfo r5 = r7.getPlaybackInfo()
            if (r5 == 0) goto L5b
            int r5 = r5.getVolumeControl()
            if (r5 != 0) goto L5b
            goto L5d
        L5b:
            r5 = r4
            goto L5e
        L5d:
            r5 = r3
        L5e:
            java.lang.String r7 = r7.getPackageName()
            r0.L$0 = r8
            r0.L$1 = r2
            r0.I$0 = r5
            r0.label = r4
            java.lang.Object r6 = r6.getApplicationLabel(r7, r0)
            if (r6 != r1) goto L71
            goto L89
        L71:
            r0 = r8
            r7 = r2
            r8 = r6
            r6 = r5
        L75:
            java.lang.CharSequence r8 = (java.lang.CharSequence) r8
            if (r8 != 0) goto L7b
            r1 = 0
            goto L89
        L7b:
            com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession r1 = new com.android.systemui.volume.panel.component.mediaoutput.shared.model.MediaDeviceSession
            kotlin.jvm.internal.Intrinsics.checkNotNull(r0)
            kotlin.jvm.internal.Intrinsics.checkNotNull(r7)
            if (r6 == 0) goto L86
            r3 = r4
        L86:
            r1.<init>(r8, r0, r7, r3)
        L89:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.access$mediaDeviceSession(com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor, android.media.session.MediaController, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public static MediaController chooseController(MediaController mediaController, MediaController mediaController2) {
        if (mediaController == null) {
            return mediaController2;
        }
        PlaybackState playbackState = mediaController2.getPlaybackState();
        boolean z = false;
        boolean z2 = playbackState != null && playbackState.isActive();
        PlaybackState playbackState2 = mediaController.getPlaybackState();
        if (playbackState2 != null && playbackState2.isActive()) {
            z = true;
        }
        return (!z2 || z) ? mediaController : mediaController2;
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0035  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object getApplicationLabel(java.lang.String r6, kotlin.coroutines.Continuation r7) {
        /*
            r5 = this;
            boolean r0 = r7 instanceof com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$1
            if (r0 == 0) goto L13
            r0 = r7
            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$1 r0 = (com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$1 r0 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$1
            r0.<init>(r5, r7)
        L18:
            java.lang.Object r7 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 0
            r4 = 1
            if (r2 == 0) goto L35
            if (r2 != r4) goto L2d
            java.lang.Object r5 = r0.L$0
            r6 = r5
            java.lang.String r6 = (java.lang.String) r6
            kotlin.ResultKt.throwOnFailure(r7)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            goto L4a
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            kotlin.ResultKt.throwOnFailure(r7)
            kotlin.coroutines.CoroutineContext r7 = r5.backgroundCoroutineContext     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$2 r2 = new com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor$getApplicationLabel$2     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            r2.<init>(r5, r6, r3)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            r0.L$0 = r6     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            r0.label = r4     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            java.lang.Object r7 = kotlinx.coroutines.BuildersKt.withContext(r7, r2, r0)     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            if (r7 != r1) goto L4a
            return r1
        L4a:
            java.lang.CharSequence r7 = (java.lang.CharSequence) r7     // Catch: android.content.pm.PackageManager.NameNotFoundException -> L4e
            r3 = r7
            goto L55
        L4e:
            java.lang.String r5 = "Unable to find info for package: "
            java.lang.String r7 = "MediaOutputInteractor"
            androidx.constraintlayout.motion.widget.MotionLayout$$ExternalSyntheticOutline0.m$1(r5, r6, r7)
        L55:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.volume.panel.component.mediaoutput.domain.interactor.MediaOutputInteractor.getApplicationLabel(java.lang.String, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
