package com.android.systemui.media.mediaoutput.viewmodel;

import android.util.Log;
import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaDeviceControlCallback;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;

public final class MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1 extends IMediaDeviceControlCallback.Stub {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;

    public MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    public final void onMuteChanged(String str, boolean z) {
        Log.d("MediaDeviceViewModel", "onMuteChanged() - " + str + " : " + z);
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1$onMuteChanged$1(producerScope, null), 3);
    }

    public final void onVolumeChanged(int i, String str) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "onVolumeChanged() - ", str, " : ", "MediaDeviceViewModel");
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaDeviceControlCallback$1$onVolumeChanged$1(producerScope, null), 3);
    }
}
