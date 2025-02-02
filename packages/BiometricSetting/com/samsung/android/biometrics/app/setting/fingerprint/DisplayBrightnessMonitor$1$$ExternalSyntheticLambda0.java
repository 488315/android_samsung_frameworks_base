package com.samsung.android.biometrics.app.setting.fingerprint;

import com.samsung.android.biometrics.app.setting.fingerprint.DisplayBrightnessMonitor;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class DisplayBrightnessMonitor$1$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ float f$1;

    public /* synthetic */ DisplayBrightnessMonitor$1$$ExternalSyntheticLambda0(Object obj, float f, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = f;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                DisplayBrightnessMonitor.C02361 c02361 = (DisplayBrightnessMonitor.C02361) this.f$0;
                c02361.this$0.handleDisplayBrightnessChanged(this.f$1);
                break;
            default:
                DisplayBrightnessMonitor.FileObserverC02372 fileObserverC02372 = (DisplayBrightnessMonitor.FileObserverC02372) this.f$0;
                fileObserverC02372.this$0.handleDisplayBrightnessChanged(this.f$1);
                break;
        }
    }
}
