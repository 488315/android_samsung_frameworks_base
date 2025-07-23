package com.android.settingslib.volume.data.repository;

import android.media.session.MediaSessionManager;
import com.android.settingslib.bluetooth.LocalBluetoothManager;
import com.android.settingslib.bluetooth.LocalBluetoothManagerExtKt;
import com.android.settingslib.media.session.MediaSessionManagerExtKt;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl;
import com.android.settingslib.volume.shared.model.AudioManagerEvent;
import kotlin.Unit;
import kotlin.collections.EmptyList;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.EmptyFlow;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class MediaControllerRepositoryImpl implements MediaControllerRepository {
    public final ReadonlyStateFlow activeSessions;
    public final MediaSessionManager mediaSessionManager;

    public MediaControllerRepositoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, MediaSessionManager mediaSessionManager, LocalBluetoothManager localBluetoothManager, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        Flow flow;
        this.mediaSessionManager = mediaSessionManager;
        final Flow defaultRemoteSessionChanged = MediaSessionManagerExtKt.getDefaultRemoteSessionChanged(mediaSessionManager);
        Flow flow2 = new Flow() { // from class: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControllerRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaControllerRepositoryImpl mediaControllerRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControllerRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1$2$1 r0 = (com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1$2$1 r0 = new com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        android.media.session.MediaSession$Token r5 = (android.media.session.MediaSession.Token) r5
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl r5 = r4.this$0
                        android.media.session.MediaSessionManager r5 = r5.mediaSessionManager
                        r6 = 0
                        java.util.List r5 = r5.getActiveSessions(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(MediaSessionManagerExtKt.getActiveMediaChanges(mediaSessionManager));
        if (localBluetoothManager != null) {
            final CallbackFlowBuilder headsetAudioModeChanges = LocalBluetoothManagerExtKt.getHeadsetAudioModeChanges(localBluetoothManager);
            flow = new Flow() { // from class: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2

                /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
                /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2$2, reason: invalid class name */
                public final class AnonymousClass2 implements FlowCollector {
                    public final /* synthetic */ FlowCollector $this_unsafeFlow;
                    public final /* synthetic */ MediaControllerRepositoryImpl this$0;

                    /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2$2$1, reason: invalid class name */
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

                    public AnonymousClass2(FlowCollector flowCollector, MediaControllerRepositoryImpl mediaControllerRepositoryImpl) {
                        this.$this_unsafeFlow = flowCollector;
                        this.this$0 = mediaControllerRepositoryImpl;
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
                            boolean r0 = r6 instanceof com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1
                            if (r0 == 0) goto L13
                            r0 = r6
                            com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2$2$1 r0 = (com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.AnonymousClass1) r0
                            int r1 = r0.label
                            r2 = -2147483648(0xffffffff80000000, float:-0.0)
                            r3 = r1 & r2
                            if (r3 == 0) goto L13
                            int r1 = r1 - r2
                            r0.label = r1
                            goto L18
                        L13:
                            com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2$2$1 r0 = new com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2$2$1
                            r0.<init>(r6)
                        L18:
                            java.lang.Object r6 = r0.result
                            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                            int r2 = r0.label
                            r3 = 1
                            if (r2 == 0) goto L2f
                            if (r2 != r3) goto L27
                            kotlin.ResultKt.throwOnFailure(r6)
                            goto L48
                        L27:
                            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                            r4.<init>(r5)
                            throw r4
                        L2f:
                            kotlin.ResultKt.throwOnFailure(r6)
                            kotlin.Unit r5 = (kotlin.Unit) r5
                            com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl r5 = r4.this$0
                            android.media.session.MediaSessionManager r5 = r5.mediaSessionManager
                            r6 = 0
                            java.util.List r5 = r5.getActiveSessions(r6)
                            r0.label = r3
                            kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                            java.lang.Object r4 = r4.emit(r5, r0)
                            if (r4 != r1) goto L48
                            return r1
                        L48:
                            kotlin.Unit r4 = kotlin.Unit.INSTANCE
                            return r4
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$2.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                    }
                }

                @Override // kotlinx.coroutines.flow.Flow
                public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                    Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                    return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
                }
            };
        } else {
            flow = EmptyFlow.INSTANCE;
        }
        final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(((AudioManagerEventsReceiverImpl) audioManagerEventsReceiver).events, Reflection.getOrCreateKotlinClass(AudioManagerEvent.StreamDevicesChanged.class));
        this.activeSessions = FlowKt.stateIn(FlowKt.flowOn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new MediaControllerRepositoryImpl$activeSessions$4(this, null), FlowKt.merge(flow2, flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1, flow, new Flow() { // from class: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ MediaControllerRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, MediaControllerRepositoryImpl mediaControllerRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = mediaControllerRepositoryImpl;
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
                        boolean r0 = r6 instanceof com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3$2$1 r0 = (com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3$2$1 r0 = new com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamDevicesChanged r5 = (com.android.settingslib.volume.shared.model.AudioManagerEvent.StreamDevicesChanged) r5
                        com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl r5 = r4.this$0
                        android.media.session.MediaSessionManager r5 = r5.mediaSessionManager
                        r6 = 0
                        java.util.List r5 = r5.getActiveSessions(r6)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.data.repository.MediaControllerRepositoryImpl$special$$inlined$map$3.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        })), coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), EmptyList.INSTANCE);
    }
}
