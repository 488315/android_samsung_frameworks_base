package com.android.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ boolean f$0;

    public /* synthetic */ KeyguardSecUpdateMonitorImpl$$ExternalSyntheticLambda10(boolean z, int i) {
        this.$r8$classId = i;
        this.f$0 = z;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ((KeyguardUpdateMonitorCallback) obj).onKeyguardVisibilityChanged(this.f$0);
                break;
            case 1:
                ((KeyguardUpdateMonitorCallback) obj).onUSBRestrictionChanged(this.f$0);
                break;
            case 2:
                ((KeyguardUpdateMonitorCallback) obj).onTableModeChanged(this.f$0);
                break;
            case 3:
                ((KeyguardUpdateMonitorCallback) obj).getClass();
                break;
            default:
                ((KeyguardUpdateMonitorCallback) obj).onPrimaryBouncerVisibilityChanged(this.f$0);
                break;
        }
    }
}
