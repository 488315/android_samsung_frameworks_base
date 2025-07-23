package com.android.systemui.media.mediaoutput.controller.media;

import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.channels.ProducerScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
final class DeviceSessionController$Companion$mediaChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ String $filterId;
    final /* synthetic */ SmartThingsMediaSdkManager $this_mediaChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DeviceSessionController$Companion$mediaChanged$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, String str, Continuation continuation) {
        super(2, continuation);
        this.$this_mediaChanged = smartThingsMediaSdkManager;
        this.$filterId = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        DeviceSessionController$Companion$mediaChanged$1 deviceSessionController$Companion$mediaChanged$1 = new DeviceSessionController$Companion$mediaChanged$1(this.$this_mediaChanged, this.$filterId, continuation);
        deviceSessionController$Companion$mediaChanged$1.L$0 = obj;
        return deviceSessionController$Companion$mediaChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((DeviceSessionController$Companion$mediaChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x007a, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r6, r7) == r0) goto L15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:14:0x007c, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x0037, code lost:
    
        if (r8 == r0) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v8, types: [com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaContentChangeCallback$1, com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback$Stub] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$deviceStatusChangeCallback$1, com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback$Stub] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaDeviceControlCallback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            kotlin.coroutines.intrinsics.CoroutineSingletons r0 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r1 = r7.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L20
            if (r1 == r3) goto L18
            if (r1 != r2) goto L10
            kotlin.ResultKt.throwOnFailure(r8)
            goto L7d
        L10:
            java.lang.IllegalStateException r7 = new java.lang.IllegalStateException
            java.lang.String r8 = "call to 'resume' before 'invoke' with coroutine"
            r7.<init>(r8)
            throw r7
        L18:
            java.lang.Object r1 = r7.L$0
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L3a
        L20:
            kotlin.ResultKt.throwOnFailure(r8)
            java.lang.Object r8 = r7.L$0
            r1 = r8
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.Unit r8 = kotlin.Unit.INSTANCE
            r7.L$0 = r1
            r7.label = r3
            r3 = r1
            kotlinx.coroutines.channels.ChannelCoroutine r3 = (kotlinx.coroutines.channels.ChannelCoroutine) r3
            kotlinx.coroutines.channels.Channel r3 = r3._channel
            java.lang.Object r8 = r3.send(r8, r7)
            if (r8 != r0) goto L3a
            goto L7c
        L3a:
            com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaDeviceControlCallback$1 r8 = new com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaDeviceControlCallback$1
            java.lang.String r3 = r7.$filterId
            r8.<init>()
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r3 = r7.$this_mediaChanged
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r3 = r3.mediaSdkOperationManager
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicecontrol.DeviceControlOperationImpl r3 = r3.deviceControlOperationImpl
            r3.getClass()
            com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaContentChangeCallback$1 r3 = new com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$mediaContentChangeCallback$1
            java.lang.String r4 = r7.$filterId
            r3.<init>()
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r4 = r7.$this_mediaChanged
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r4 = r4.mediaSdkOperationManager
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.mediacontent.MediaContentOperationImpl r4 = r4.mediaContentOperationImpl
            r4.addContentChangeCallback(r3)
            com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$deviceStatusChangeCallback$1 r4 = new com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$deviceStatusChangeCallback$1
            java.lang.String r5 = r7.$filterId
            r4.<init>()
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r5 = r7.$this_mediaChanged
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager r5 = r5.mediaSdkOperationManager
            com.samsung.android.smartthingsmediasdk.mediasdk.manager.mediasdkoperations.devicestatus.DeviceStatusOperationImpl r5 = r5.deviceStatusOperationImpl
            r5.addDeviceStatusChangeCallback(r4)
            com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager r5 = r7.$this_mediaChanged
            com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$$ExternalSyntheticLambda0 r6 = new com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1$$ExternalSyntheticLambda0
            r6.<init>()
            r8 = 0
            r7.L$0 = r8
            r7.label = r2
            java.lang.Object r7 = kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r6, r7)
            if (r7 != r0) goto L7d
        L7c:
            return r0
        L7d:
            kotlin.Unit r7 = kotlin.Unit.INSTANCE
            return r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.media.mediaoutput.controller.media.DeviceSessionController$Companion$mediaChanged$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
