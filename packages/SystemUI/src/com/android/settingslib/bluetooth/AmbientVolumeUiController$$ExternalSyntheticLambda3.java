package com.android.settingslib.bluetooth;

import android.util.ArraySet;
import android.widget.Toast;
import com.android.settingslib.utils.ThreadUtils;
import com.android.systemui.R;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class AmbientVolumeUiController$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ AmbientVolumeUiController f$0;

    public /* synthetic */ AmbientVolumeUiController$$ExternalSyntheticLambda3(AmbientVolumeUiController ambientVolumeUiController, int i) {
        this.$r8$classId = i;
        this.f$0 = ambientVolumeUiController;
    }

    @Override // java.lang.Runnable
    public final void run() {
        int i = this.$r8$classId;
        AmbientVolumeUiController ambientVolumeUiController = this.f$0;
        switch (i) {
            case 0:
                ambientVolumeUiController.loadDevice(ambientVolumeUiController.mCachedDevice);
                ThreadUtils.postOnBackgroundThread(new AmbientVolumeUiController$$ExternalSyntheticLambda3(ambientVolumeUiController, 3));
                break;
            case 1:
                Toast.makeText(ambientVolumeUiController.mContext, R.string.bluetooth_hearing_device_ambient_error, 0).show();
                ambientVolumeUiController.refresh();
                break;
            case 2:
                ambientVolumeUiController.refresh();
                break;
            default:
                ((ArraySet) ambientVolumeUiController.mCachedDevices).forEach(new AmbientVolumeUiController$$ExternalSyntheticLambda0(ambientVolumeUiController, 3));
                break;
        }
    }
}
