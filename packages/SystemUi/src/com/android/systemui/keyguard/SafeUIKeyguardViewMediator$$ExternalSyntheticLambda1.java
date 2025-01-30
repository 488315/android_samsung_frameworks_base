package com.android.systemui.keyguard;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda1(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = safeUIKeyguardViewMediator;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                this.f$0.mTrustManager.reportKeyguardShowingChanged();
                break;
            case 1:
                SafeUIKeyguardViewMediator.$r8$lambda$GNdiXm3mHNV8n3Qc7UuNKv7SQHY(this.f$0);
                break;
            default:
                SafeUIKeyguardViewMediator.$r8$lambda$GIdRB5htbmIpZ8nvMvkSUjeggGI(this.f$0);
                break;
        }
    }
}
