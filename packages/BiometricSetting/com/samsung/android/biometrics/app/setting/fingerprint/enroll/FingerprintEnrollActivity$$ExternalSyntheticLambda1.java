package com.samsung.android.biometrics.app.setting.fingerprint.enroll;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class FingerprintEnrollActivity$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FingerprintEnrollActivity$$ExternalSyntheticLambda1(int i, Object obj) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                FingerprintEnrollActivity.$r8$lambda$nnA8EVhUoLub6z16zP8Re6tZqZY((FingerprintEnrollActivity) this.f$0);
                break;
            case 1:
                FingerprintEnrollActivity.$r8$lambda$rZDxvTxrwQWkmM7M7Q4QW7t7nbk((FingerprintEnrollActivity) this.f$0);
                break;
            case 2:
                FingerprintEnrollActivity.$r8$lambda$hPgBPFgWHYutmn2ibHDGmlbkQt4((FingerprintEnrollActivity) this.f$0);
                break;
            default:
                FingerprintEnrollActivity.this.mNextButtonArea.setVisibility(0);
                break;
        }
    }
}
