package com.android.systemui.statusbar.phone;

import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda12 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda12(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                keyguardStatusBarViewController.mHiddenByKnox = ((Boolean) obj).booleanValue();
                keyguardStatusBarViewController.updateViewState();
                return Unit.INSTANCE;
            case 1:
                AnimationProperties animationProperties = KeyguardStatusBarViewController.KEYGUARD_HUN_PROPERTIES;
                keyguardStatusBarViewController.getClass();
                keyguardStatusBarViewController.mSystemEventAnimatorAlpha = ((Float) obj).floatValue();
                keyguardStatusBarViewController.updateViewState();
                return Unit.INSTANCE;
            default:
                return KeyguardStatusBarViewController.$r8$lambda$gNuEL1YaK596T1XKmoXC3pJXlLE(keyguardStatusBarViewController, (Float) obj);
        }
    }
}
