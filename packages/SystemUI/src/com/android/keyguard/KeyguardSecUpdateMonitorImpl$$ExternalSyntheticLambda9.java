package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
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
