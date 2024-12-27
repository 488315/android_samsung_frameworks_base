package com.android.systemui.biometrics;

import com.android.systemui.biometrics.AuthController;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final /* synthetic */ class AuthController$4$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ int f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ boolean f$3;

    public /* synthetic */ AuthController$4$$ExternalSyntheticLambda0(AuthController.AnonymousClass4 anonymousClass4, int i, int i2, boolean z) {
        this.f$0 = anonymousClass4;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                AuthController.AnonymousClass4 anonymousClass4 = (AuthController.AnonymousClass4) this.f$0;
                AuthController.m889$$Nest$mhandleEnrollmentsChanged(AuthController.this, 2, this.f$1, this.f$2, this.f$3);
                break;
            default:
                AuthController.AnonymousClass5 anonymousClass5 = (AuthController.AnonymousClass5) this.f$0;
                AuthController.m889$$Nest$mhandleEnrollmentsChanged(AuthController.this, 8, this.f$1, this.f$2, this.f$3);
                break;
        }
    }

    public /* synthetic */ AuthController$4$$ExternalSyntheticLambda0(AuthController.AnonymousClass5 anonymousClass5, int i, int i2, boolean z) {
        this.f$0 = anonymousClass5;
        this.f$1 = i;
        this.f$2 = i2;
        this.f$3 = z;
    }
}
