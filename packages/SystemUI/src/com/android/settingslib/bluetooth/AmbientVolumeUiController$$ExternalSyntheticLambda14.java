package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import java.util.function.BiConsumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda14 implements BiConsumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientVolumeUiController f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ AmbientVolumeUiController$$ExternalSyntheticLambda14(AmbientVolumeUiController ambientVolumeUiController, int i, int i2) {
        this.$r8$classId = i2;
        this.f$0 = ambientVolumeUiController;
        this.f$1 = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        switch (this.$r8$classId) {
            case 0:
                AmbientVolumeUiController ambientVolumeUiController = this.f$0;
                int i = this.f$1;
                ambientVolumeUiController.mLocalDataManager.updateGroupAmbient((BluetoothDevice) obj2, i);
                break;
            default:
                AmbientVolumeUiController ambientVolumeUiController2 = this.f$0;
                int i2 = this.f$1;
                ambientVolumeUiController2.mVolumeController.setAmbient((BluetoothDevice) obj2, i2);
                break;
        }
    }
}
