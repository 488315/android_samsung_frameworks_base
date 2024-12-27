package com.android.keyguard;

import java.util.function.Consumer;

public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda9(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        boolean z = this.f$0;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = (KeyguardUpdateMonitorCallback) obj;
        switch (i) {
            case 0:
                keyguardUpdateMonitorCallback.onKeyguardVisibilityChanged(z);
                break;
            case 1:
                keyguardUpdateMonitorCallback.onTableModeChanged(z);
                break;
            case 2:
                keyguardUpdateMonitorCallback.onPrimaryBouncerVisibilityChanged(z);
                break;
            case 3:
                keyguardUpdateMonitorCallback.onUSBRestrictionChanged(z);
                break;
            default:
                keyguardUpdateMonitorCallback.onFaceWidgetFullscreenModeChanged(z);
                break;
        }
    }
}
