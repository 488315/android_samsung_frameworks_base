package com.android.systemui.statusbar.phone;

import android.content.res.Resources;
import com.android.systemui.statusbar.notification.stack.AnimationProperties;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

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
    public final Object mo781invoke(Object obj) throws Resources.NotFoundException {
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
