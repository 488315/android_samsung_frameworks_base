package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;

public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda5 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda5(KeyguardUpdateMonitor.AnonymousClass2 anonymousClass2, int i, boolean z) {
        this.f$0 = anonymousClass2;
        this.f$2 = i;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.f$0;
                boolean z = this.f$1;
                int i = this.f$2;
                keyguardUpdateMonitor.mLogger.logReportSuccessfulBiometricUnlock(i, z);
                keyguardUpdateMonitor.mLockPatternUtils.reportSuccessfulBiometricUnlock(z, i);
                break;
            default:
                KeyguardUpdateMonitor.AnonymousClass2 anonymousClass2 = (KeyguardUpdateMonitor.AnonymousClass2) this.f$0;
                KeyguardUpdateMonitor.this.mBiometricEnabledForUser.put(this.f$2, this.f$1);
                KeyguardUpdateMonitor.this.mHandler.post(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1(anonymousClass2, 0));
                break;
        }
    }

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda5(KeyguardUpdateMonitor keyguardUpdateMonitor, boolean z, int i) {
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = z;
        this.f$2 = i;
    }
}
