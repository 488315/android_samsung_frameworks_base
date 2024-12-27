package com.samsung.android.displayquality;

import android.content.Context;
import android.util.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class SemDisplayQuality extends SemDisplayQualityAP {
    private static final String TAG = "SemDisplayQualityDummy";

    public SemDisplayQuality(Context context) {
        super(context);
        if (this.DEBUG) {
            Slog.d(TAG, "SemDisplayQuality");
        }
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOff() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOn() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenModeChanged(int i) {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOff() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOffAsync() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOn() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOnAsync() {
    }
}
