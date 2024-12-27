package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
final class DeviceAudioPathViewModel$Companion$mediaoutputChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SmartThingsMediaSdkManager $this_mediaoutputChanged;
    private /* synthetic */ Object L$0;
    Object L$1;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceAudioPathViewModel$Companion$mediaoutputChanged$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, Continuation continuation) {
        super(2, continuation);
        this.$this_mediaoutputChanged = smartThingsMediaSdkManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceAudioPathViewModel$Companion$mediaoutputChanged$1 deviceAudioPathViewModel$Companion$mediaoutputChanged$1 = new DeviceAudioPathViewModel$Companion$mediaoutputChanged$1(this.$this_mediaoutputChanged, continuation);
        deviceAudioPathViewModel$Companion$mediaoutputChanged$1.L$0 = obj;
        return deviceAudioPathViewModel$Companion$mediaoutputChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceAudioPathViewModel$Companion$mediaoutputChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        Iterator it;
        ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            List devices = this.$this_mediaoutputChanged.mediaSdkOperationManager.deviceStatusOperationImpl.getDevices();
            ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(devices, 10));
            Iterator it2 = devices.iterator();
            while (it2.hasNext()) {
                arrayList.add(((DeviceDomain) it2.next()).deviceId);
            }
            it = arrayList.iterator();
            producerScope = producerScope2;
        } else {
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                return Unit.INSTANCE;
            }
            it = (Iterator) this.L$1;
            producerScope = (ProducerScope) this.L$0;
            ResultKt.throwOnFailure(obj);
        }
        while (it.hasNext()) {
            String str = (String) it.next();
            this.L$0 = producerScope;
            this.L$1 = it;
            this.label = 1;
            if (((ChannelCoroutine) producerScope)._channel.send(str, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        final DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 = new DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1(producerScope);
        this.$this_mediaoutputChanged.mediaSdkOperationManager.mediaOutputSelectedOperationImpl.addMediaOutputSelectedCallback(deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1);
        final SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.$this_mediaoutputChanged;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1.3
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.d("DeviceAudioPathViewModel", "removeMediaOutputSelectedCallback");
                SmartThingsMediaSdkManager smartThingsMediaSdkManager2 = SmartThingsMediaSdkManager.this;
                smartThingsMediaSdkManager2.mediaSdkOperationManager.mediaOutputSelectedOperationImpl.removeMediaOutputSelectedCallback(deviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.L$1 = null;
        this.label = 2;
        if (ProduceKt.awaitClose(producerScope, function0, this) == coroutineSingletons) {
            return coroutineSingletons;
        }
        return Unit.INSTANCE;
    }
}
