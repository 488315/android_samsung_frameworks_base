package com.android.settingslib.volume.data.repository;

import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiver;
import com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl;
import com.android.settingslib.volume.shared.model.AudioManagerEvent;
import kotlin.ResultKt;
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
        CallbackFlowBuilder callbackFlowBuilderCallbackFlow = FlowKt.callbackFlow(new LocalMediaRepositoryImpl$mediaDevicesUpdates$1(this, null));
        SharingStarted.Companion.getClass();
        StartedEagerly startedEagerly = SharingStarted.Companion.Eagerly;
        ReadonlySharedFlow readonlySharedFlowShareIn = FlowKt.shareIn(callbackFlowBuilderCallbackFlow, coroutineScope, startedEagerly, 0);
        this.mediaDevicesUpdates = readonlySharedFlowShareIn;
        final ChannelLimitedFlowMerge channelLimitedFlowMergeMerge = FlowKt.merge(flowKt__TransformKt$filterIsInstance$$inlined$filter$2, readonlySharedFlowShareIn);
        this.currentConnectedDevice = FlowKt.stateIn(new FlowKt__EmittersKt$onStart$$inlined$unsafeFlow$1(new LocalMediaRepositoryImpl$currentConnectedDevice$2(this, null), new Flow() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$special$$inlined$map$1

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
                        MediaDevice currentConnectedDevice = this.this$0.localMediaManager.getCurrentConnectedDevice();
                        anonymousClass1.label = 1;
                        if (this.$this_unsafeFlow.emit(currentConnectedDevice, anonymousClass1) == coroutineSingletons) {
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
                Object objCollect = channelLimitedFlowMergeMerge.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }), coroutineScope, startedEagerly, localMediaManager.getCurrentConnectedDevice());
    }
}
