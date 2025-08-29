package com.android.systemui.statusbar.phone;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* loaded from: classes3.dex */
public final /* synthetic */ class KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ KeyguardSecBottomAreaViewController f$0;

    public /* synthetic */ KeyguardSecBottomAreaViewController$$ExternalSyntheticLambda0(int i, KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        this.$r8$classId = i;
        this.f$0 = keyguardSecBottomAreaViewController;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.f$0;
        switch (this.$r8$classId) {
            case 0:
                String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.showShortcutsIfPossible();
                return Unit.INSTANCE;
            case 1:
                return KeyguardSecBottomAreaViewController.m3085$r8$lambda$D4kp5cmGQ_rGLdyL6oXY1kQhJo(keyguardSecBottomAreaViewController);
            case 2:
                return KeyguardSecBottomAreaViewController.m3087$r8$lambda$YOkFmRiWxOyhXrDzbAm3sPmT8(keyguardSecBottomAreaViewController);
            case 3:
                return KeyguardSecBottomAreaViewController.$r8$lambda$ZdrlybJBwxxHo4Ff_gUFuEpgBw8(keyguardSecBottomAreaViewController);
            case 4:
                String str2 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.updateCustomShortcutIcon(keyguardSecBottomAreaViewController.getLeftView(), 0, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0));
                return Unit.INSTANCE;
            case 5:
                String str3 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.updateCustomShortcutIcon(keyguardSecBottomAreaViewController.getRightView(), 1, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1));
                return Unit.INSTANCE;
            case 6:
                String str4 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                keyguardSecBottomAreaViewController.setUsimTextAreaVisibility();
                return Unit.INSTANCE;
            case 7:
                return ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getLeftView();
            case 8:
                return ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).getRightView();
            case 9:
                return KeyguardSecBottomAreaViewController.m3086$r8$lambda$Jgv21cVoSLZlA51xet8mwVwiBs(keyguardSecBottomAreaViewController);
            case 10:
                return KeyguardSecBottomAreaViewController.$r8$lambda$M7LuDowF8AhrK_kb4xF8Rm3VIPs(keyguardSecBottomAreaViewController);
            case 11:
                return KeyguardSecBottomAreaViewController.$r8$lambda$3tiNAI20Fxhrbl_RX2nDpohwyjw(keyguardSecBottomAreaViewController);
            default:
                return KeyguardSecBottomAreaViewController.$r8$lambda$dPSJRC_qbr4lGWx6ve6RagZjQOo(keyguardSecBottomAreaViewController);
        }
    }
}
