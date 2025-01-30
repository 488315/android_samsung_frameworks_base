package com.android.p038wm.shell.shortcut;

import android.app.ActivityManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.p038wm.shell.common.DisplayLayout;
import com.android.p038wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutLeftKeyLaunchPolicy extends ShortcutLaunchPolicy {
    public ShortcutLeftKeyLaunchPolicy(ShortcutController shortcutController) {
        super(shortcutController, false);
    }

    @Override // com.android.p038wm.shell.shortcut.ShortcutLaunchPolicy
    public final void handleShortCutKeys(KeyEvent keyEvent) {
        ShortcutController shortcutController = this.mShortcutController;
        ActivityManager.RunningTaskInfo runningTaskInfo = shortcutController.mRunningTaskInfo;
        if (keyEvent.isCtrlPressed()) {
            if (runningTaskInfo.getWindowingMode() == 1) {
                ((SplitScreenController) shortcutController.mSplitScreenController.get()).toggleSplitScreen(1);
                if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsNewDexMode ? 2 : 1);
                    return;
                }
                return;
            }
            if (runningTaskInfo.getWindowingMode() == 5) {
                if (CoreRune.MW_MULTI_SPLIT && !MultiWindowUtils.isInSubDisplay(shortcutController.mContext)) {
                    shortcutController.moveFreeformToSplit(8);
                    return;
                }
                DisplayLayout displayLayout = shortcutController.mDisplayController.getDisplayLayout(shortcutController.mRunningTaskInfo.getDisplayId());
                int i = 0;
                if (displayLayout == null) {
                    Log.w("ShortcutController", "Failed to get new DisplayLayout.");
                } else {
                    i = displayLayout.mWidth <= displayLayout.mHeight ? 0 : 1;
                }
                if (i != 0) {
                    shortcutController.moveFreeformToSplit(8);
                } else {
                    shortcutController.moveFreeformToSplit(16);
                }
            }
        }
    }
}
