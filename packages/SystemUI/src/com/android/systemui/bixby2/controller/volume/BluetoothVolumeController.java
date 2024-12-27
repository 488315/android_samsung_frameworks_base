package com.android.systemui.bixby2.controller.volume;

import android.content.Context;
import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.bixby2.util.AudioManagerWrapper;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

public final class BluetoothVolumeController extends VolumeType {
    public static final int $stable = 8;
    private final Lazy audioManagerWrapper$delegate;
    private final String streamTypeToString = "Media";

    public BluetoothVolumeController(final Context context) {
        this.audioManagerWrapper$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.bixby2.controller.volume.BluetoothVolumeController$audioManagerWrapper$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AudioManagerWrapper invoke() {
                return new AudioManagerWrapper(context);
            }
        });
    }

    private final AudioManagerWrapper getAudioManagerWrapper() {
        return (AudioManagerWrapper) this.audioManagerWrapper$delegate.getValue();
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStatus() {
        return getAudioManagerWrapper().isCurrentDeviceTypeBluetooth() ? 1 : 4;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStatusCode() {
        return getAudioManagerWrapper().isCurrentDeviceTypeBluetooth() ? "success" : ActionResults.RESULT_BLUETOOTH_NOT_CONNECTED;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public int getStreamType() {
        return 3;
    }

    @Override // com.android.systemui.bixby2.controller.volume.VolumeType
    public String getStreamTypeToString() {
        return this.streamTypeToString;
    }
}
