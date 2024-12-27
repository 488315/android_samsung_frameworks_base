package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.systemui.bixby2.controller.MWBixbyController$$ExternalSyntheticOutline0;
import com.samsung.android.oneconnect.mediaoutput.mediadeviceoperations.IMediaContentChangeCallback;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;

public final class MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1 extends IMediaContentChangeCallback.Stub {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;

    public MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    public final void onChanged(String str, String str2) {
        MWBixbyController$$ExternalSyntheticOutline0.m("onChanged() - ", str, " : ", str2, "MediaDeviceViewModel");
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$mediaContentChangeCallback$1$onChanged$1(producerScope, null), 3);
    }
}
