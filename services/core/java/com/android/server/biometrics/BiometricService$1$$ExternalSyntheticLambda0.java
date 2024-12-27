package com.android.server.biometrics;


/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class BiometricService$1$$ExternalSyntheticLambda0
        implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ long f$1;
    public final /* synthetic */ int f$2;
    public final /* synthetic */ byte[] f$3;

    public /* synthetic */ BiometricService$1$$ExternalSyntheticLambda0(
            BiometricService.AnonymousClass1 anonymousClass1, long j, int i, byte[] bArr) {
        this.f$0 = anonymousClass1;
        this.f$1 = j;
        this.f$2 = i;
        this.f$3 = bArr;
    }

    public /* synthetic */ BiometricService$1$$ExternalSyntheticLambda0(
            BiometricService.AnonymousClass2 anonymousClass2, long j, int i, byte[] bArr) {
        this.f$0 = anonymousClass2;
        this.f$1 = j;
        this.f$2 = i;
        this.f$3 = bArr;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                BiometricService.AnonymousClass1 anonymousClass1 =
                        (BiometricService.AnonymousClass1) this.f$0;
                anonymousClass1.this$0.handleAuthenticationSucceeded(
                        this.f$1, this.f$2, this.f$3, null);
                break;
            default:
                BiometricService.AnonymousClass2 anonymousClass2 =
                        (BiometricService.AnonymousClass2) this.f$0;
                long j = this.f$1;
                int i = this.f$2;
                anonymousClass2.this$0.handleOnDismissed(j, this.f$3, i);
                break;
        }
    }
}
