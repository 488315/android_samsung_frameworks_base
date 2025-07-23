package com.android.systemui.media.mediaoutput.viewmodel;

import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
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

    /* JADX WARN: Code restructure failed: missing block: B:22:0x009c, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r4, r3, r6) == r0) goto L22;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r7) {
        /*
            r6 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r6.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L25
            if (r1 == r3) goto L19
            if (r1 != r2) goto L11
            kotlin.ResultKt.throwOnFailure(r7)
            goto L9f
        L11:
            java.lang.IllegalStateException r6 = new java.lang.IllegalStateException
            java.lang.String r7 = "call to 'resume' before 'invoke' with coroutine"
            r6.<init>(r7)
            throw r6
        L19:
            java.lang.Object r1 = r6.L$1
            java.util.Iterator r1 = (java.util.Iterator) r1
            java.lang.Object r4 = r6.L$0
            kotlinx.coroutines.channels.ProducerScope r4 = (kotlinx.coroutines.channels.ProducerScope) r4
            kotlin.ResultKt.throwOnFailure(r7)
            goto L5e
        L25:
            kotlin.ResultKt.throwOnFailure(r7)
            java.lang.Object r7 = r6.L$0
            kotlinx.coroutines.channels.ProducerScope r7 = (kotlinx.coroutines.channels.ProducerScope) r7
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r1 = r6.$this_mediaoutputChanged
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r1 = r1.mediaSdkOperationManager
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl r1 = r1.deviceStatusOperationImpl
            java.util.List r1 = r1.getDevices()
            java.lang.Iterable r1 = (java.lang.Iterable) r1
            java.util.ArrayList r4 = new java.util.ArrayList
            r5 = 10
            int r5 = kotlin.collections.CollectionsKt__IterablesKt.collectionSizeOrDefault(r1, r5)
            r4.<init>(r5)
            java.util.Iterator r1 = r1.iterator()
        L47:
            boolean r5 = r1.hasNext()
            if (r5 == 0) goto L59
            java.lang.Object r5 = r1.next()
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain r5 = (com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceDomain) r5
            java.lang.String r5 = r5.deviceId
            r4.add(r5)
            goto L47
        L59:
            java.util.Iterator r1 = r4.iterator()
            r4 = r7
        L5e:
            boolean r7 = r1.hasNext()
            if (r7 == 0) goto L7c
            java.lang.Object r7 = r1.next()
            java.lang.String r7 = (java.lang.String) r7
            r6.L$0 = r4
            r6.L$1 = r1
            r6.label = r3
            r5 = r4
            kotlinx.coroutines.channels.ChannelCoroutine r5 = (kotlinx.coroutines.channels.ChannelCoroutine) r5
            kotlinx.coroutines.channels.Channel r5 = r5._channel
            java.lang.Object r7 = r5.send(r7, r6)
            if (r7 != r0) goto L5e
            goto L9e
        L7c:
            com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 r7 = new com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1
            r7.<init>(r4)
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r1 = r6.$this_mediaoutputChanged
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r1 = r1.mediaSdkOperationManager
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediaoutputselection.MediaOutputSelectedOperationImpl r1 = r1.mediaOutputSelectedOperationImpl
            r1.addMediaOutputSelectedCallback(r7)
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r1 = r6.$this_mediaoutputChanged
            com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$$ExternalSyntheticLambda0 r3 = new com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$$ExternalSyntheticLambda0
            r3.<init>()
            r7 = 0
            r6.L$0 = r7
            r6.L$1 = r7
            r6.label = r2
            java.lang.Object r6 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r4, r3, r6)
            if (r6 != r0) goto L9f
        L9e:
            return r0
        L9f:
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.viewmodel.DeviceAudioPathViewModel$Companion$mediaoutputChanged$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
