package com.android.systemui.statusbar.phone;

import android.view.View;
import com.android.systemui.statusbar.KeyguardSecAffordanceView;
import com.android.systemui.statusbar.KeyguardShortcutManager;
import com.android.systemui.util.ViewController;

public final class KeyguardSecBottomAreaViewController$shortcutManagerCallback$1 implements KeyguardShortcutManager.ShortcutCallback {
    public final /* synthetic */ KeyguardSecBottomAreaViewController this$0;

    public KeyguardSecBottomAreaViewController$shortcutManagerCallback$1(KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController) {
        this.this$0 = keyguardSecBottomAreaViewController;
    }

    public final void updateShortcutView(final int i) {
        View view;
        View view2;
        final KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController = this.this$0;
        view = ((ViewController) keyguardSecBottomAreaViewController).mView;
        ((KeyguardSecBottomAreaView) view).post(new Runnable() { // from class: com.android.systemui.statusbar.phone.KeyguardSecBottomAreaViewController$shortcutManagerCallback$1$updateShortcutView$1
            @Override // java.lang.Runnable
            public final void run() {
                if (i == 0) {
                    KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController2 = keyguardSecBottomAreaViewController;
                    String str = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                    if (keyguardSecBottomAreaViewController2.getLeftView() != null) {
                        KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController3 = keyguardSecBottomAreaViewController;
                        keyguardSecBottomAreaViewController3.updateCustomShortcutIcon(keyguardSecBottomAreaViewController3.getLeftView(), 0, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(0));
                        KeyguardSecAffordanceView leftView = keyguardSecBottomAreaViewController.getLeftView();
                        leftView.mShortcutForCamera = KeyguardShortcutManager.isSamsungCameraPackage(keyguardSecBottomAreaViewController.shortcutManager.shortcutsData[0].componentName);
                        leftView.setRectangleColor();
                        keyguardSecBottomAreaViewController.getLeftView().mIsShortcutForPhone = keyguardSecBottomAreaViewController.shortcutManager.isShortcutForPhone(0);
                        return;
                    }
                    return;
                }
                KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController4 = keyguardSecBottomAreaViewController;
                String str2 = KeyguardSecBottomAreaViewController.KEY_HELP_TEXT_VISIBILITY;
                if (keyguardSecBottomAreaViewController4.getRightView() != null) {
                    KeyguardSecBottomAreaViewController keyguardSecBottomAreaViewController5 = keyguardSecBottomAreaViewController;
                    keyguardSecBottomAreaViewController5.updateCustomShortcutIcon(keyguardSecBottomAreaViewController5.getRightView(), 1, keyguardSecBottomAreaViewController.shortcutManager.hasShortcut(1));
                    KeyguardSecAffordanceView rightView = keyguardSecBottomAreaViewController.getRightView();
                    rightView.mShortcutForCamera = KeyguardShortcutManager.isSamsungCameraPackage(keyguardSecBottomAreaViewController.shortcutManager.shortcutsData[1].componentName);
                    rightView.setRectangleColor();
                    keyguardSecBottomAreaViewController.getRightView().mIsShortcutForPhone = keyguardSecBottomAreaViewController.shortcutManager.isShortcutForPhone(1);
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
            view2 = ((ViewController) keyguardSecBottomAreaViewController).mView;
            ((KeyguardSecBottomAreaView) view2).updateLayout();
        }
    }
}
