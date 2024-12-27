package com.samsung.android.biometrics.app.setting.fingerprint;

/* compiled from: qb/89523975 3d932b551ea0d034372835fb60fef8bf79c4dff86d0cff0c41e74d050161944e */
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
