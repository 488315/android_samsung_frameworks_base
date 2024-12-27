package com.android.systemui.media.mediaoutput.viewmodel;

import com.android.settingslib.SecNotificationBlockManager$$ExternalSyntheticOutline0;
import com.samsung.android.oneconnect.mediaoutput.deviceoperations.IDeviceStatusChangeCallback;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.channels.ProducerScope;

public final class MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1 extends IDeviceStatusChangeCallback.Stub {
    public final /* synthetic */ ProducerScope $$this$callbackFlow;

    public MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1(ProducerScope producerScope) {
        this.$$this$callbackFlow = producerScope;
    }

    public final void onCloudDeviceChanged(int i, String str) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "onCloudDeviceChanged() - ", str, " : ", "MediaDeviceViewModel");
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1$onCloudDeviceChanged$1(producerScope, null), 3);
    }

    public final void onNearbyChanged(int i, String str) {
        SecNotificationBlockManager$$ExternalSyntheticOutline0.m(i, "onNearbyChanged() - ", str, " : ", "MediaDeviceViewModel");
        ProducerScope producerScope = this.$$this$callbackFlow;
        BuildersKt.launch$default(producerScope, null, null, new MediaDeviceViewModel$Companion$mediaChanged$1$deviceStatusChangeCallback$1$onNearbyChanged$1(producerScope, null), 3);
    }
}
