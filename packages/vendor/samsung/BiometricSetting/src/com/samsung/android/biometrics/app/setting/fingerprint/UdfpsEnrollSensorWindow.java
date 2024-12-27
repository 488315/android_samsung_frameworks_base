package com.samsung.android.biometrics.app.setting.fingerprint;

public final class UdfpsEnrollSensorWindow extends UdfpsSensorWindow {
    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final void showSensorIcon() {
        this.mH.post(new UdfpsSensorWindow$$ExternalSyntheticLambda0(this, 0));
    }

    @Override // com.samsung.android.biometrics.app.setting.fingerprint.UdfpsSensorWindow
    public final void setSensorIcon() {}
}
