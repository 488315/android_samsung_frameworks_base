package com.android.settingslib.bluetooth;

import android.bluetooth.BluetoothDevice;
import java.util.function.BiConsumer;

/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda8 implements BiConsumer {
    public final /* synthetic */ AmbientVolumeUiController f$0;
    public final /* synthetic */ boolean f$1;

    public /* synthetic */ AmbientVolumeUiController$$ExternalSyntheticLambda8(AmbientVolumeUiController ambientVolumeUiController, boolean z) {
        this.f$0 = ambientVolumeUiController;
        this.f$1 = z;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        AmbientVolumeUiController ambientVolumeUiController = this.f$0;
        boolean z = this.f$1;
        ambientVolumeUiController.mLocalDataManager.updateAmbientControlExpanded((BluetoothDevice) obj2, z);
    }
}
