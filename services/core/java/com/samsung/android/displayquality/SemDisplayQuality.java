package com.samsung.android.displayquality;

import android.content.Context;
import android.util.Slog;

public class SemDisplayQuality extends SemDisplayQualityAP {
    private static final String TAG = "SemDisplayQualityDummy";

    public SemDisplayQuality(Context context) {
        super(context);
        if (this.DEBUG) {
            Slog.d(TAG, "SemDisplayQuality");
        }
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOff() {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOn() {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenModeChanged(int i) {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOff() {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOffAsync() {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOn() {}

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOnAsync() {}
}
