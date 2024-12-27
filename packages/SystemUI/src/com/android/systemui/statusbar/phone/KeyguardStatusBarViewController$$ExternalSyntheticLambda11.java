package com.android.systemui.statusbar.phone;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda11 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda11(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                boolean booleanValue = ((Boolean) obj).booleanValue();
                KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
                keyguardStatusBarViewController.mHiddenByKnox = booleanValue;
                keyguardStatusBarViewController.updateViewState();
                return Unit.INSTANCE;
            case 1:
                KeyguardStatusBarViewController keyguardStatusBarViewController2 = this.f$0;
                keyguardStatusBarViewController2.getClass();
                keyguardStatusBarViewController2.mSystemEventAnimatorAlpha = ((Float) obj).floatValue();
                keyguardStatusBarViewController2.updateViewState();
                return Unit.INSTANCE;
            default:
                return KeyguardStatusBarViewController.m2230$r8$lambda$YTsAQhIMZIR1qbmyZFXUfHY0U(this.f$0, (Float) obj);
        }
    }
}
