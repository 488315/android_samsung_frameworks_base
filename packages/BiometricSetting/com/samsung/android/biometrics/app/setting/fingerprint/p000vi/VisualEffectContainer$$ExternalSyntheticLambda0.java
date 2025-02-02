package com.samsung.android.biometrics.app.setting.fingerprint.p000vi;

/* compiled from: R8$$SyntheticClass */
/* loaded from: classes.dex */
public final /* synthetic */ class VisualEffectContainer$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ VisualEffectContainer f$0;

    public /* synthetic */ VisualEffectContainer$$ExternalSyntheticLambda0(VisualEffectContainer visualEffectContainer, int i) {
        this.$r8$classId = i;
        this.f$0 = visualEffectContainer;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                VisualEffectContainer.$r8$lambda$WeunE3xHFN40gEF93fT2dO74a8M(this.f$0);
                break;
            case 1:
                this.f$0.stopVI();
                break;
            case 2:
                VisualEffectContainer.$r8$lambda$dhyVIR_QGXGm73WdU7zAnLLfbLQ(this.f$0);
                break;
            default:
                this.f$0.invalidate();
                break;
        }
    }
}
