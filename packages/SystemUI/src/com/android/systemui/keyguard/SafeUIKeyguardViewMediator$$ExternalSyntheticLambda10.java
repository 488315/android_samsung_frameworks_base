package com.android.systemui.keyguard;

import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ SafeUIKeyguardViewMediator f$0;

    public /* synthetic */ SafeUIKeyguardViewMediator$$ExternalSyntheticLambda10(SafeUIKeyguardViewMediator safeUIKeyguardViewMediator, int i) {
        this.$r8$classId = i;
        this.f$0 = safeUIKeyguardViewMediator;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        SafeUIKeyguardViewMediator safeUIKeyguardViewMediator = this.f$0;
        switch (i) {
            case 0:
                safeUIKeyguardViewMediator.mWallpaperSupportsAmbientMode = ((Boolean) obj).booleanValue();
                break;
            case 1:
                safeUIKeyguardViewMediator.getClass();
                ((Boolean) obj).getClass();
                break;
            case 2:
                safeUIKeyguardViewMediator.getClass();
                android.util.Log.e("SafeUIKeyguardViewMediator", "Attempting to set alpha on null animation target");
                break;
            default:
                safeUIKeyguardViewMediator.getClass();
                break;
        }
    }
}
