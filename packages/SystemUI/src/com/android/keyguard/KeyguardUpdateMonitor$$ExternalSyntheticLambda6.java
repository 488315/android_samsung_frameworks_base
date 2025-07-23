package com.android.keyguard;

import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger;
import com.android.keyguard.logging.KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class KeyguardUpdateMonitor$$ExternalSyntheticLambda6 implements Runnable {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ boolean f$1;
    public final /* synthetic */ int f$2;

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda6(KeyguardUpdateMonitor.AnonymousClass2 anonymousClass2, int i, int i2, boolean z) {
        this.f$0 = anonymousClass2;
        this.f$2 = i2;
        this.f$1 = z;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                KeyguardUpdateMonitor keyguardUpdateMonitor = (KeyguardUpdateMonitor) this.f$0;
                boolean z = this.f$1;
                int i = this.f$2;
                KeyguardUpdateMonitorLogger keyguardUpdateMonitorLogger = keyguardUpdateMonitor.mLogger;
                keyguardUpdateMonitorLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1 = new KeyguardUpdateMonitorLogger$$ExternalSyntheticLambda1(26);
                LogBuffer logBuffer = keyguardUpdateMonitorLogger.logBuffer;
                LogMessage obtain = logBuffer.obtain("KeyguardUpdateMonitorLog", logLevel, keyguardUpdateMonitorLogger$$ExternalSyntheticLambda1, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
                logMessageImpl.bool1 = z;
                logMessageImpl.int1 = i;
                logBuffer.commit(obtain);
                keyguardUpdateMonitor.mLockPatternUtils.reportSuccessfulBiometricUnlock(z, i);
                break;
            default:
                KeyguardUpdateMonitor.AnonymousClass2 anonymousClass2 = (KeyguardUpdateMonitor.AnonymousClass2) this.f$0;
                KeyguardUpdateMonitor.this.mBiometricEnabledForUser.put(this.f$2, this.f$1);
                KeyguardUpdateMonitor.this.mHandler.post(new KeyguardUpdateMonitor$2$$ExternalSyntheticLambda1(anonymousClass2, 0));
                break;
        }
    }

    public /* synthetic */ KeyguardUpdateMonitor$$ExternalSyntheticLambda6(KeyguardUpdateMonitor keyguardUpdateMonitor, boolean z, int i) {
        this.f$0 = keyguardUpdateMonitor;
        this.f$1 = z;
        this.f$2 = i;
    }
}
