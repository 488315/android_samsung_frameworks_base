package com.android.settingslib.volume.data.repository;

import com.android.settingslib.media.LocalMediaManager;
import com.android.settingslib.media.MediaDevice;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LocalMediaRepositoryImpl$mediaDevicesUpdates$1 extends SuspendLambda implements Function2 {
    private /* synthetic */ Object L$0;
    int label;
    final /* synthetic */ LocalMediaRepositoryImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LocalMediaRepositoryImpl$mediaDevicesUpdates$1(LocalMediaRepositoryImpl localMediaRepositoryImpl, Continuation continuation) {
        super(2, continuation);
        this.this$0 = localMediaRepositoryImpl;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        LocalMediaRepositoryImpl$mediaDevicesUpdates$1 localMediaRepositoryImpl$mediaDevicesUpdates$1 = new LocalMediaRepositoryImpl$mediaDevicesUpdates$1(this.this$0, continuation);
        localMediaRepositoryImpl$mediaDevicesUpdates$1.L$0 = obj;
        return localMediaRepositoryImpl$mediaDevicesUpdates$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((LocalMediaRepositoryImpl$mediaDevicesUpdates$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.settingslib.media.LocalMediaManager$DeviceCallback, com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$mediaDevicesUpdates$1$callback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            final ProducerScope producerScope = (ProducerScope) this.L$0;
            final ?? r1 = new LocalMediaManager.DeviceCallback() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$mediaDevicesUpdates$1$callback$1
                @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
                public final void onDeviceAttributesChanged() {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new Object() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceAttributesChanged
                        public final boolean equals(Object obj2) {
                            return this == obj2 || (obj2 instanceof LocalMediaRepositoryImpl$DevicesUpdate$DeviceAttributesChanged);
                        }

                        public final int hashCode() {
                            return 1697838064;
                        }

                        public final String toString() {
                            return "DeviceAttributesChanged";
                        }
                    });
                }

                @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
                public final void onDeviceListUpdate(List list) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new Object(list) { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate
                        public final List newDevices;

                        {
                            this.newDevices = list;
                        }

                        public final boolean equals(Object obj2) {
                            if (this == obj2) {
                                return true;
                            }
                            return (obj2 instanceof LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate) && Intrinsics.areEqual(this.newDevices, ((LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate) obj2).newDevices);
                        }

                        public final int hashCode() {
                            List list2 = this.newDevices;
                            if (list2 == null) {
                                return 0;
                            }
                            return list2.hashCode();
                        }

                        public final String toString() {
                            return LocalMediaRepositoryImpl$DevicesUpdate$DeviceListUpdate$$ExternalSyntheticOutline0.m("DeviceListUpdate(newDevices=", this.newDevices, ")");
                        }
                    });
                }

                @Override // com.android.settingslib.media.LocalMediaManager.DeviceCallback
                public final void onSelectedDeviceStateChanged(MediaDevice mediaDevice) {
                    ((ChannelCoroutine) ProducerScope.this).mo3456trySendJP2dKIU(new Object() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$DevicesUpdate$SelectedDeviceStateChanged
                        public final boolean equals(Object obj2) {
                            return this == obj2 || (obj2 instanceof LocalMediaRepositoryImpl$DevicesUpdate$SelectedDeviceStateChanged);
                        }

                        public final int hashCode() {
                            return -1423678101;
                        }

                        public final String toString() {
                            return "SelectedDeviceStateChanged";
                        }
                    });
                }
            };
            this.this$0.localMediaManager.registerCallback(r1);
            final LocalMediaRepositoryImpl localMediaRepositoryImpl = this.this$0;
            Function0 function0 = new Function0() { // from class: com.android.settingslib.volume.data.repository.LocalMediaRepositoryImpl$mediaDevicesUpdates$1$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function0
                public final Object invoke() {
                    LocalMediaRepositoryImpl.this.localMediaManager.unregisterCallback(r1);
                    return Unit.INSTANCE;
                }
            };
            this.label = 1;
            if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
        }
        return Unit.INSTANCE;
    }
}
