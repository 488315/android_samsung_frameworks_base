package com.android.settingslib.volume.shared;

import android.content.Context;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class AudioManagerEventsReceiverImpl implements AudioManagerEventsReceiver {
    public final Context context;
    public final ReadonlySharedFlow events;

    public AudioManagerEventsReceiverImpl(Context context, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.context = context;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.callbackFlow(new AudioManagerEventsReceiverImpl$events$1(this, null)));
        final Flow flow = new Flow() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AudioManagerEventsReceiverImpl this$0;

                /* renamed from: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioManagerEventsReceiverImpl audioManagerEventsReceiverImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = audioManagerEventsReceiverImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r11, kotlin.coroutines.Continuation r12) {
                    /*
                        r10 = this;
                        boolean r0 = r12 instanceof com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r12
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1$2$1 r0 = (com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1$2$1 r0 = new com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1$2$1
                        r0.<init>(r12)
                    L18:
                        java.lang.Object r12 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r12)
                        goto L67
                    L27:
                        java.lang.IllegalStateException r10 = new java.lang.IllegalStateException
                        java.lang.String r11 = "call to 'resume' before 'invoke' with coroutine"
                        r10.<init>(r11)
                        throw r10
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r12)
                        r12 = r11
                        android.content.Intent r12 = (android.content.Intent) r12
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl r2 = r10.this$0
                        r2.getClass()
                        java.lang.String r8 = "android.media.STREAM_DEVICES_CHANGED_ACTION"
                        java.lang.String r9 = "android.media.VOLUME_CHANGED_ACTION"
                        java.lang.String r4 = "android.media.STREAM_MUTE_CHANGED_ACTION"
                        java.lang.String r5 = "android.media.MASTER_MUTE_CHANGED_ACTION"
                        java.lang.String r6 = "android.media.VOLUME_CHANGED_ACTION"
                        java.lang.String r7 = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"
                        java.lang.String[] r2 = new java.lang.String[]{r4, r5, r6, r7, r8, r9}
                        java.util.Set r2 = kotlin.collections.ArraysKt___ArraysKt.toSet(r2)
                        java.util.Collection r2 = (java.util.Collection) r2
                        java.lang.Iterable r2 = (java.lang.Iterable) r2
                        java.lang.String r12 = r12.getAction()
                        boolean r12 = kotlin.collections.CollectionsKt___CollectionsKt.contains(r2, r12)
                        if (r12 == 0) goto L67
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r10 = r10.$this_unsafeFlow
                        java.lang.Object r10 = r10.emit(r11, r0)
                        if (r10 != r1) goto L67
                        return r1
                    L67:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.events = FlowKt.shareIn(FlowKt.flowOn(new Flow() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ AudioManagerEventsReceiverImpl this$0;

                /* renamed from: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, AudioManagerEventsReceiverImpl audioManagerEventsReceiverImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = audioManagerEventsReceiverImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x0030  */
                /* JADX WARN: Removed duplicated region for block: B:27:0x00c1  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r7, kotlin.coroutines.Continuation r8) {
                    /*
                        r6 = this;
                        boolean r0 = r8 instanceof com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r8
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1$2$1 r0 = (com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1$2$1 r0 = new com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1$2$1
                        r0.<init>(r8)
                    L18:
                        java.lang.Object r8 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L30
                        if (r2 != r3) goto L28
                        kotlin.ResultKt.throwOnFailure(r8)
                        goto Lcc
                    L28:
                        java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
                        java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
                        r6.<init>(r7)
                        throw r6
                    L30:
                        kotlin.ResultKt.throwOnFailure(r8)
                        android.content.Intent r7 = (android.content.Intent) r7
                        com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl r8 = r6.this$0
                        r8.getClass()
                        java.lang.String r8 = r7.getAction()
                        if (r8 == 0) goto L78
                        int r2 = r8.hashCode()
                        r4 = -1315844839(0xffffffffb191cd19, float:-4.2433723E-9)
                        if (r2 == r4) goto L6c
                        r4 = 100931828(0x60418f4, float:2.4844773E-35)
                        if (r2 == r4) goto L60
                        r4 = 1170999219(0x45cc07b3, float:6528.9624)
                        if (r2 == r4) goto L54
                        goto L78
                    L54:
                        java.lang.String r2 = "android.media.MASTER_MUTE_CHANGED_ACTION"
                        boolean r8 = r8.equals(r2)
                        if (r8 != 0) goto L5d
                        goto L78
                    L5d:
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamMasterMuteChanged r7 = com.android.settingslib.volume.shared.model.AudioManagerEvent.StreamMasterMuteChanged.INSTANCE
                        goto Lbf
                    L60:
                        java.lang.String r2 = "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION"
                        boolean r8 = r8.equals(r2)
                        if (r8 != 0) goto L69
                        goto L78
                    L69:
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$InternalRingerModeChanged r7 = com.android.settingslib.volume.shared.model.AudioManagerEvent.InternalRingerModeChanged.INSTANCE
                        goto Lbf
                    L6c:
                        java.lang.String r2 = "android.media.STREAM_DEVICES_CHANGED_ACTION"
                        boolean r8 = r8.equals(r2)
                        if (r8 != 0) goto L75
                        goto L78
                    L75:
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamDevicesChanged r7 = com.android.settingslib.volume.shared.model.AudioManagerEvent.StreamDevicesChanged.INSTANCE
                        goto Lbf
                    L78:
                        java.lang.String r8 = "android.media.EXTRA_VOLUME_STREAM_TYPE"
                        r2 = -1
                        int r8 = r7.getIntExtra(r8, r2)
                        r4 = 0
                        if (r8 != r2) goto L8b
                        java.lang.String r7 = "AudioManagerIntentsReceiver"
                        java.lang.String r8 = "Intent doesn't have AudioManager.EXTRA_VOLUME_STREAM_TYPE extra"
                        android.util.Log.e(r7, r8)
                    L89:
                        r7 = r4
                        goto Lbf
                    L8b:
                        com.android.settingslib.volume.shared.model.AudioStream.m989constructorimpl(r8)
                        java.lang.String r7 = r7.getAction()
                        if (r7 == 0) goto L89
                        int r2 = r7.hashCode()
                        r5 = -1940635523(0xffffffff8c54407d, float:-1.6351292E-31)
                        if (r2 == r5) goto Lb1
                        r5 = 1920758225(0x727c71d1, float:5.0001804E30)
                        if (r2 == r5) goto La3
                        goto L89
                    La3:
                        java.lang.String r2 = "android.media.STREAM_MUTE_CHANGED_ACTION"
                        boolean r7 = r7.equals(r2)
                        if (r7 == 0) goto L89
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamMuteChanged r7 = new com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamMuteChanged
                        r7.<init>(r8, r4)
                        goto Lbf
                    Lb1:
                        java.lang.String r2 = "android.media.VOLUME_CHANGED_ACTION"
                        boolean r7 = r7.equals(r2)
                        if (r7 != 0) goto Lba
                        goto L89
                    Lba:
                        com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamVolumeChanged r7 = new com.android.settingslib.volume.shared.model.AudioManagerEvent$StreamVolumeChanged
                        r7.<init>(r8, r4)
                    Lbf:
                        if (r7 == 0) goto Lcc
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r6 = r6.$this_unsafeFlow
                        java.lang.Object r6 = r6.emit(r7, r0)
                        if (r6 != r1) goto Lcc
                        return r1
                    Lcc:
                        kotlin.Unit r6 = kotlin.Unit.INSTANCE
                        return r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }, coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }
}
