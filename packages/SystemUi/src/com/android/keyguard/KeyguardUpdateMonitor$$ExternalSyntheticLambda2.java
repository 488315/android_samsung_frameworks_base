package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId = 0;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda2(int i, KeyguardUpdateMonitor keyguardUpdateMonitor, boolean z) {
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = z;
        this.f$2 = i;
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
                KeyguardUpdateMonitor.C08052 c08052 = (KeyguardUpdateMonitor.C08052) this.f$0;
                KeyguardUpdateMonitor.this.mBiometricEnabledForUser.put(this.f$2, this.f$1);
                KeyguardUpdateMonitor.this.mHandler.post(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda0(c08052, 0));
                break;
        }
    }

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda2(KeyguardUpdateMonitor.C08052 c08052, int i, boolean z) {
        this.f$0 = c08052;
        this.f$2 = i;
        this.f$1 = z;
    }
}
