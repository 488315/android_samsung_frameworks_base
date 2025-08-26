package com.android.settingslib.volume.shared;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.android.settingslib.volume.shared.model.AudioManagerEvent;
import com.android.settingslib.volume.shared.model.AudioStream;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
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

/* loaded from: classes.dex */
public final class AudioManagerEventsReceiverImpl implements AudioManagerEventsReceiver {
    public final Context context;
    public final ReadonlySharedFlow events;

    public AudioManagerEventsReceiverImpl(Context context, CoroutineScope coroutineScope, CoroutineContext coroutineContext) {
        this.context = context;
        final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(FlowKt.callbackFlow(new AudioManagerEventsReceiverImpl$events$1(this, null)));
        final Flow flow = new Flow() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$filter$1

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
                        this.this$0.getClass();
                        if (CollectionsKt___CollectionsKt.contains(ArraysKt___ArraysKt.toSet(new String[]{"android.media.STREAM_MUTE_CHANGED_ACTION", "android.media.MASTER_MUTE_CHANGED_ACTION", "android.media.VOLUME_CHANGED_ACTION", "android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION", "android.media.STREAM_DEVICES_CHANGED_ACTION", "android.media.VOLUME_CHANGED_ACTION"}), ((Intent) obj).getAction())) {
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
                Object objCollect = flowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        };
        this.events = FlowKt.shareIn(FlowKt.flowOn(new Flow() { // from class: com.android.settingslib.volume.shared.AudioManagerEventsReceiverImpl$special$$inlined$mapNotNull$1

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

                /* JADX WARN: Removed duplicated region for block: B:35:0x0078  */
                /* JADX WARN: Removed duplicated region for block: B:54:0x00c1  */
                /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object emit(Object obj, Continuation continuation) {
                    AnonymousClass1 anonymousClass1;
                    Object streamVolumeChanged;
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
                        Intent intent = (Intent) obj;
                        this.this$0.getClass();
                        String action = intent.getAction();
                        if (action == null) {
                            int intExtra = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_TYPE", -1);
                            if (intExtra == -1) {
                                Log.e("AudioManagerIntentsReceiver", "Intent doesn't have AudioManager.EXTRA_VOLUME_STREAM_TYPE extra");
                            } else {
                                AudioStream.m991constructorimpl(intExtra);
                                String action2 = intent.getAction();
                                if (action2 != null) {
                                    int iHashCode = action2.hashCode();
                                    if (iHashCode != -1940635523) {
                                        if (iHashCode == 1920758225 && action2.equals("android.media.STREAM_MUTE_CHANGED_ACTION")) {
                                            streamVolumeChanged = new AudioManagerEvent.StreamMuteChanged(intExtra, null);
                                        }
                                        if (streamVolumeChanged != null) {
                                            anonymousClass1.label = 1;
                                            if (this.$this_unsafeFlow.emit(streamVolumeChanged, anonymousClass1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                        }
                                    } else {
                                        if (action2.equals("android.media.VOLUME_CHANGED_ACTION")) {
                                            streamVolumeChanged = new AudioManagerEvent.StreamVolumeChanged(intExtra, null);
                                        }
                                        if (streamVolumeChanged != null) {
                                        }
                                    }
                                }
                            }
                            streamVolumeChanged = null;
                            if (streamVolumeChanged != null) {
                            }
                        } else {
                            int iHashCode2 = action.hashCode();
                            if (iHashCode2 == -1315844839) {
                                if (action.equals("android.media.STREAM_DEVICES_CHANGED_ACTION")) {
                                    streamVolumeChanged = AudioManagerEvent.StreamDevicesChanged.INSTANCE;
                                }
                                if (streamVolumeChanged != null) {
                                }
                            } else if (iHashCode2 != 100931828) {
                                if (iHashCode2 == 1170999219 && action.equals("android.media.MASTER_MUTE_CHANGED_ACTION")) {
                                    streamVolumeChanged = AudioManagerEvent.StreamMasterMuteChanged.INSTANCE;
                                }
                                if (streamVolumeChanged != null) {
                                }
                            } else {
                                if (action.equals("android.media.INTERNAL_RINGER_MODE_CHANGED_ACTION")) {
                                    streamVolumeChanged = AudioManagerEvent.InternalRingerModeChanged.INSTANCE;
                                }
                                if (streamVolumeChanged != null) {
                                }
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
                Object objCollect = flow.collect(new AnonymousClass2(flowCollector, this), continuation);
                return objCollect == CoroutineSingletons.COROUTINE_SUSPENDED ? objCollect : Unit.INSTANCE;
            }
        }, coroutineContext), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), 0);
    }
}
