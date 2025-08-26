package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.android.settingslib.volume.MediaSessions$H$$ExternalSyntheticOutline0;
import com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback;
import com.samsung.android.smartthingsmediasdk.mediasdk.SmartThingsMediaSdkManager;
import com.samsung.android.smartthingsmediasdk.mediasdk.manager.MediaSdkOperationManager;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ChannelCoroutine;
import kotlinx.coroutines.channels.ProducerScope;

/* loaded from: classes2.dex */
final class MediaDeviceViewModel$Companion$mediaChanged$1 extends SuspendLambda implements Function2 {
    final /* synthetic */ SmartThingsMediaSdkManager $this_mediaChanged;
    private /* synthetic */ Object L$0;
    int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaDeviceViewModel$Companion$mediaChanged$1(SmartThingsMediaSdkManager smartThingsMediaSdkManager, Continuation continuation) {
        super(2, continuation);
        this.$this_mediaChanged = smartThingsMediaSdkManager;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        MediaDeviceViewModel$Companion$mediaChanged$1 mediaDeviceViewModel$Companion$mediaChanged$1 = new MediaDeviceViewModel$Companion$mediaChanged$1(this.$this_mediaChanged, continuation);
        mediaDeviceViewModel$Companion$mediaChanged$1.L$0 = obj;
        return mediaDeviceViewModel$Companion$mediaChanged$1;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        return ((MediaDeviceViewModel$Companion$mediaChanged$1) create((ProducerScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0074, code lost:
    
        if (kotlinx.coroutines.channels.ProduceKt.awaitClose(r1, r6, r7) == r0) goto L15;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v7, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1, com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback$Stub] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1, com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback$Stub] */
    /* JADX WARN: Type inference failed for: r8v4, types: [com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) {
        final ProducerScope producerScope;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure(obj);
            ProducerScope producerScope2 = (ProducerScope) this.L$0;
            Unit unit = Unit.INSTANCE;
            this.L$0 = producerScope2;
            this.label = 1;
            Object objSend = ((ChannelCoroutine) producerScope2)._channel.send(unit, this);
            producerScope = producerScope2;
            if (objSend != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            return Unit.INSTANCE;
        }
        ProducerScope producerScope3 = (ProducerScope) this.L$0;
        ResultKt.throwOnFailure(obj);
        producerScope = producerScope3;
        final ?? r8 = new IMediaDeviceControlCallback.Stub() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1
            @Override // com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback
            public final void onMuteChanged(String str, boolean z) {
                Log.d("MediaDeviceViewModel", "onMuteChanged() - " + str + " : " + z);
                ProducerScope producerScope4 = producerScope;
                BuildersKt.launch$default(producerScope4, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1$onMuteChanged$1(producerScope4, null), 3);
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback
            public final void onVolumeChanged(int i2, String str) {
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i2, "onVolumeChanged() - ", str, " : ", "MediaDeviceViewModel");
                ProducerScope producerScope4 = producerScope;
                BuildersKt.launch$default(producerScope4, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1$onVolumeChanged$1(producerScope4, null), 3);
            }
        };
        this.$this_mediaChanged.mediaSdkOperationManager.deviceControlOperationImpl.getClass();
        final ?? r3 = new IMediaContentChangeCallback.Stub() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1
            @Override // com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback
            public final void onChanged(String str, String str2) {
                MediaSessions$H$$ExternalSyntheticOutline0.m("onChanged() - ", str, " : ", str2, "MediaDeviceViewModel");
                ProducerScope producerScope4 = producerScope;
                BuildersKt.launch$default(producerScope4, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1$onChanged$1(producerScope4, null), 3);
            }
        };
        this.$this_mediaChanged.mediaSdkOperationManager.mediaContentOperationImpl.addContentChangeCallback(r3);
        final ?? r4 = new IDeviceStatusChangeCallback.Stub() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1
            @Override // com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback
            public final void onCloudDeviceChanged(int i2, String str) {
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i2, "onCloudDeviceChanged() - ", str, " : ", "MediaDeviceViewModel");
                ProducerScope producerScope4 = producerScope;
                BuildersKt.launch$default(producerScope4, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1$onCloudDeviceChanged$1(producerScope4, null), 3);
            }

            @Override // com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback
            public final void onNearbyChanged(int i2, String str) {
                SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i2, "onNearbyChanged() - ", str, " : ", "MediaDeviceViewModel");
                ProducerScope producerScope4 = producerScope;
                BuildersKt.launch$default(producerScope4, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1$onNearbyChanged$1(producerScope4, null), 3);
            }
        };
        this.$this_mediaChanged.mediaSdkOperationManager.deviceStatusOperationImpl.addDeviceStatusChangeCallback(r4);
        final SmartThingsMediaSdkManager smartThingsMediaSdkManager = this.$this_mediaChanged;
        Function0 function0 = new Function0() { // from class: com.android.systemui.media.mediaoutput.viewmodel.MediaDeviceViewModel$Companion$mediaChanged$1$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                Log.d("MediaDeviceViewModel", "removeCallback");
                SmartThingsMediaSdkManager smartThingsMediaSdkManager2 = smartThingsMediaSdkManager;
                smartThingsMediaSdkManager2.mediaSdkOperationManager.deviceControlOperationImpl.getClass();
                MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1 mediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1 = r3;
                MediaSdkOperationManager mediaSdkOperationManager = smartThingsMediaSdkManager2.mediaSdkOperationManager;
                mediaSdkOperationManager.mediaContentOperationImpl.removeContentChangeCallback(mediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1);
                mediaSdkOperationManager.deviceStatusOperationImpl.removeDeviceStatusChangeCallback(r4);
                return Unit.INSTANCE;
            }
        };
        this.L$0 = null;
        this.label = 2;
    }
}
