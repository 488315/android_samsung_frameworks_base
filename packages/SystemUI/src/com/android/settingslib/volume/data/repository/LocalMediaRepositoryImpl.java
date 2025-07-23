package com.android.settingslib.volume.data.repository;

import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl;
import com.android.settingslib.volume.shared.model.AudioManagerEvent;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Reflection;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.CallbackFlowBuilder;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterIsInstance$$inlined$filter$2;
import kotlinx.coroutines.flow.ReadonlySharedFlow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;
import kotlinx.coroutines.flow.StartedEagerly;
import kotlinx.coroutines.flow.internal.ChannelLimitedFlowMerge;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LocalMediaRepositoryImpl {
    public final ReadonlyStateFlow currentConnectedDevice;
    public final FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 devicesChanges;
    public final LocalMediaManager localMediaManager;
    public final ReadonlySharedFlow mediaDevicesUpdates;

    public LocalMediaRepositoryImpl(AudioManagerEventsReceiver audioManagerEventsReceiver, LocalMediaManager localMediaManager, CoroutineScope coroutineScope) {
        this.localMediaManager = localMediaManager;
        FlowKt__TransformKt$filterIsInstance$$inlined$filter$2 flowKt__TransformKt$filterIsInstance$$inlined$filter$2 = new FlowKt__TransformKt$filterIsInstance$$inlined$filter$2(((AudioManagerEventsReceiverImpl) audioManagerEventsReceiver).events, Reflection.getOrCreateKotlinClass(AudioManagerEvent.StreamDevicesChanged.class));
        this.devicesChanges = flowKt__TransformKt$filterIsInstance$$inlined$filter$2;
        CallbackFlowBuilder callbackFlow = FlowKt.callbackFlow(new LocalMediaRepositoryImpl$mediaDevicesUpdates$1(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlySharedFlow shareIn = FlowKt.shareIn(callbackFlow, coroutineScope, startedEagerly, 0);
        this.mediaDevicesUpdates = shareIn;
        final ChannelLimitedFlowMerge merge = FlowKt.merge(flowKt__TransformKt$filterIsInstance$$inlined$filter$2, shareIn);
        this.currentConnectedDevice = FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new LocalMediaRepositoryImpl$currentConnectedDevice$2(this, null), new Flow() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;
                public final /* synthetic */ LocalMediaRepositoryImpl this$0;

                /* renamed from: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1$2$1, reason: invalid class name */
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

                public AnonymousClass2(FlowCollector flowCollector, LocalMediaRepositoryImpl localMediaRepositoryImpl) {
                    this.$this_unsafeFlow = flowCollector;
                    this.this$0 = localMediaRepositoryImpl;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r4, kotlin.coroutines.Continuation r5) {
                    /*
                        r3 = this;
                        boolean r4 = r5 instanceof com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r4 == 0) goto L13
                        r4 = r5
                        com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1$2$1 r4 = (com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r4
                        int r0 = r4.label
                        r1 = -2147483648(0xffffffff80000000, float:-0.0)
                        r2 = r0 & r1
                        if (r2 == 0) goto L13
                        int r0 = r0 - r1
                        r4.label = r0
                        goto L18
                    L13:
                        com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1$2$1 r4 = new com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1$2$1
                        r4.<init>(r5)
                    L18:
                        java.lang.Object r5 = r4.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r1 = r4.label
                        r2 = 1
                        if (r1 == 0) goto L2f
                        if (r1 != r2) goto L27
                        kotlin.ResultKt.throwOnFailure(r5)
                        goto L45
                    L27:
                        java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
                        java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
                        r3.<init>(r4)
                        throw r3
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r5)
                        com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl r5 = r3.this$0
                        com.android.settingslib.media.LocalMediaManager r5 = r5.localMediaManager
                        com.android.settingslib.media.MediaDevice r5 = r5.getCurrentConnectedDevice()
                        r4.label = r2
                        kotlinx.coroutines.flow.FlowCollector r3 = r3.$this_unsafeFlow
                        java.lang.Object r3 = r3.emit(r5, r4)
                        if (r3 != r0) goto L45
                        return r0
                    L45:
                        kotlin.Unit r3 = kotlin.Unit.INSTANCE
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector, this), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        }), coroutineScope, startedEagerly, localMediaManager.getCurrentConnectedDevice());
    }
}
