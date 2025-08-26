package com.android.systemui.keyguard;

import android.content.Intent;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
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
                Intent intent = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                safeUIKeyguardViewMediator.getClass();
                ((Boolean) obj).getClass();
                break;
            case 2:
                Intent intent2 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                safeUIKeyguardViewMediator.getClass();
                android.util.Log.e("SafeUIKeyguardViewMediator", "Attempting to set alpha on null animation target");
                break;
            default:
                Intent intent3 = SafeUIKeyguardViewMediator.USER_PRESENT_INTENT;
                safeUIKeyguardViewMediator.getClass();
                break;
        }
    }
}
