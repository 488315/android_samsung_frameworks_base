package com.android.systemui.statusbar.phone;

import android.content.ComponentName;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 implements KeyguardShortcutManager.ShortcutCallback {
    public final /* synthetic */ KeyguardSecBottomAreaViewController this$0;

    public KeyguardSecBottomAreaViewController$shortcutManagerCallback$1(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        this.this$0 = keyguardSecBottomAreaViewController;
    }

    public final void updateShortcutView(final int i) {
        String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
        final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.this$0;
        ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1$updateShortcutView$1
            /* JADX WARN: Code restructure failed: missing block: B:24:0x00aa, code lost:
            
                if ((r4 == null ? false : kotlin.jvm.internal.Intrinsics.areEqual("com.samsung.android.app.galaxyraw", r4.getPackageName())) != false) goto L25;
             */
            @Override // java.lang.Runnable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final void run() {
                boolean z = false;
                boolean z2 = true;
                if (i != 0) {
                    KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = keyguardSecBottomAreaViewController;
                    String str2 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                    if (keyguardSecBottomAreaViewController2.getRightView() != null) {
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = keyguardSecBottomAreaViewController;
                        keyguardSecBottomAreaViewController3.updateCustomShortcutIcon(keyguardSecBottomAreaViewController3.getRightView(), 1, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1));
                        KeyguardSecAffordanceView rightView = keyguardSecBottomAreaViewController.getRightView();
                        if (!keyguardSecBottomAreaViewController.shortcutManager.isShortcutForCamera(1)) {
                            KeyguardShortcutManager.ShortcutData shortcutData = keyguardSecBottomAreaViewController.shortcutManager.mShortcuts[1];
                            Intrinsics.checkNotNull(shortcutData);
                            ComponentName componentName = shortcutData.mComponentName;
                        }
                        z = true;
                        rightView.mShortcutForCamera = z;
                        rightView.setRectangleColor();
                        keyguardSecBottomAreaViewController.getRightView().mIsShortcutForPhone = keyguardSecBottomAreaViewController.shortcutManager.isShortcutForPhone(1);
                        return;
                    }
                    return;
                }
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController4 = keyguardSecBottomAreaViewController;
                String str3 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                if (keyguardSecBottomAreaViewController4.getLeftView() != null) {
                    KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController5 = keyguardSecBottomAreaViewController;
                    keyguardSecBottomAreaViewController5.updateCustomShortcutIcon(keyguardSecBottomAreaViewController5.getLeftView(), 0, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0));
                    KeyguardSecAffordanceView leftView = keyguardSecBottomAreaViewController.getLeftView();
                    if (!keyguardSecBottomAreaViewController.shortcutManager.isShortcutForCamera(0)) {
                        KeyguardShortcutManager.ShortcutData shortcutData2 = keyguardSecBottomAreaViewController.shortcutManager.mShortcuts[0];
                        Intrinsics.checkNotNull(shortcutData2);
                        ComponentName componentName2 = shortcutData2.mComponentName;
                        if (!(componentName2 == null ? false : Intrinsics.areEqual("com.samsung.android.app.galaxyraw", componentName2.getPackageName()))) {
                            z2 = false;
                        }
                    }
                    leftView.mShortcutForCamera = z2;
                    leftView.setRectangleColor();
                    keyguardSecBottomAreaViewController.getLeftView().mIsShortcutForPhone = keyguardSecBottomAreaViewController.shortcutManager.isShortcutForPhone(0);
                }
            }
        });
        boolean z = keyguardSecBottomAreaViewController.isAllShortcutDisabled;
        boolean z2 = false;
        if (!keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0) && !keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1)) {
            z2 = true;
        }
        keyguardSecBottomAreaViewController.isAllShortcutDisabled = z2;
        if (z != keyguardSecBottomAreaViewController.isAllShortcutDisabled) {
            ((KeyguardSecBottomAreaView) keyguardSecBottomAreaViewController.mView).updateLayout();
        }
    }
}
