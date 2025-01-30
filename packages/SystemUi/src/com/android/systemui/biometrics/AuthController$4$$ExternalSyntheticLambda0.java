package com.android.systemui.biometrics;

import android.hardware.biometrics.BiometricStateListener;
import com.android.systemui.biometrics.AuthController;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ BiometricStateListener f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ AuthController$4$$ExternalSyntheticLambda0(BiometricStateListener biometricStateListener, int i, int i2, boolean z, int i3) {
        this.$r8$classId = i3;
        this.f$0 = biometricStateListener;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AuthController.C10584 c10584 = (AuthController.C10584) this.f$0;
                AuthController.m374$$Nest$mhandleEnrollmentsChanged(AuthController.this, 2, this.f$1, this.f$2, this.f$3);
                break;
            default:
                AuthController.C10595 c10595 = (AuthController.C10595) this.f$0;
                AuthController.m374$$Nest$mhandleEnrollmentsChanged(AuthController.this, 8, this.f$1, this.f$2, this.f$3);
                break;
        }
    }
}
