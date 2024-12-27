package com.samsung.android.biometrics.app.setting.fingerprint;

public final /* synthetic */ class UdfpsSensorWindow$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ UdfpsSensorWindow f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ UdfpsSensorWindow$$ExternalSyntheticLambda0(
            UdfpsSensorWindow udfpsSensorWindow, int i) {
        this.f$0 = udfpsSensorWindow;
        this.f$1 = i;
    }

    @Override // java.lang.Runnable
    public final void run() {
        UdfpsSensorWindow udfpsSensorWindow = this.f$0;
        int i = this.f$1;
        UdfpsWindowCallback udfpsWindowCallback = udfpsSensorWindow.mCallback;
        if (udfpsWindowCallback != null) {
            udfpsWindowCallback.onSensorIconVisibilityChanged(i);
        }
    }
}
