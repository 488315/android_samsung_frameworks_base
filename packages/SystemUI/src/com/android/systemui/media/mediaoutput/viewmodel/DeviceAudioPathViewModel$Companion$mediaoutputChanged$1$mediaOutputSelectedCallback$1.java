package com.android.systemui.media.mediaoutput.viewmodel;

import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaOutputSelectedCallback;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;

public final class DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1 extends IMediaOutputSelectedCallback.Stub {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;

    public DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    public final void onMediaOutputSelected(String str) {
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new DeviceAudioPathViewModel$Companion$mediaoutputChanged$1$mediaOutputSelectedCallback$1$onMediaOutputSelected$1(producerScope, str, null), 3);
    }
}
