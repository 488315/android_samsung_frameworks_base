package com.android.systemui.statusbar.phone;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final /* synthetic */ class KeyguardStatusBarViewController$$ExternalSyntheticLambda9 implements Function1 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardStatusBarViewController f$0;

    public /* synthetic */ KeyguardStatusBarViewController$$ExternalSyntheticLambda9(KeyguardStatusBarViewController keyguardStatusBarViewController, int i) {
        this.$r8$classId = i;
        this.f$0 = keyguardStatusBarViewController;
    }

    @Override // kotlin.jvm.functions.Function1
    public final Object invoke(Object obj) {
        int i = this.$r8$classId;
        KeyguardStatusBarViewController keyguardStatusBarViewController = this.f$0;
        switch (i) {
            case 0:
                keyguardStatusBarViewController.mHiddenByKnox = ((Boolean) obj).booleanValue();
                keyguardStatusBarViewController.updateViewState();
                break;
            case 1:
                Float f = (Float) obj;
                if (keyguardStatusBarViewController.mNotificationMediaManager.isLockscreenWallpaperOnNotificationShade()) {
                    keyguardStatusBarViewController.mSystemEventAnimatorAlpha = 1.0f;
                } else {
                    keyguardStatusBarViewController.mSystemEventAnimatorAlpha = f.floatValue();
                }
                keyguardStatusBarViewController.updateViewState();
                break;
            default:
                Float f2 = (Float) obj;
                if (keyguardStatusBarViewController.mNotificationMediaManager.isLockscreenWallpaperOnNotificationShade()) {
                    ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).setTranslationX(0.0f);
                } else {
                    ((KeyguardStatusBarView) keyguardStatusBarViewController.mView).setTranslationX(f2.floatValue());
                }
                break;
        }
        return Unit.INSTANCE;
    }
}
