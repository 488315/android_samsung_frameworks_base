package com.samsung.android.biometrics.app.setting.fingerprint;

public final /* synthetic */ class DisplayBrightnessMonitor$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ DisplayBrightnessMonitor f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ DisplayBrightnessMonitor$$ExternalSyntheticLambda0(
            DisplayBrightnessMonitor displayBrightnessMonitor, float f) {
        this.f$0 = displayBrightnessMonitor;
        this.f$1 = f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.f$0.handleDisplayBrightnessChanged(this.f$1);
    }
}
