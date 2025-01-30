package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.util.Log;
import android.view.KeyEvent;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutRightKeyLaunchPolicy extends ShortcutLaunchPolicy {
    public ShortcutRightKeyLaunchPolicy(ShortcutController shortcutController) {
        super(shortcutController, false);
    }

    @Override // com.android.wm.shell.shortcut.ShortcutLaunchPolicy
    public final void handleShortCutKeys(KeyEvent keyEvent) {
        ShortcutController shortcutController = this.mShortcutController;
        ActivityManager.RunningTaskInfo runningTaskInfo = shortcutController.mRunningTaskInfo;
        if (keyEvent.isCtrlPressed()) {
            boolean z = false;
            if (runningTaskInfo.getWindowingMode() == 1) {
                ((SplitScreenController) shortcutController.mSplitScreenController.get()).toggleSplitScreen(0);
                if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                    CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsNewDexMode ? 2 : 1);
                    return;
                }
                return;
            }
            if (runningTaskInfo.getWindowingMode() == 5) {
                if (CoreRune.MW_MULTI_SPLIT && !MultiWindowUtils.isInSubDisplay(shortcutController.mContext)) {
                    shortcutController.moveFreeformToSplit(32);
                    return;
                }
                DisplayLayout displayLayout = shortcutController.mDisplayController.getDisplayLayout(shortcutController.mRunningTaskInfo.getDisplayId());
                if (displayLayout == null) {
                    Log.w("ShortcutController", "Failed to get new DisplayLayout.");
                } else if (displayLayout.mWidth > displayLayout.mHeight) {
                    z = true;
                }
                if (z) {
                    shortcutController.moveFreeformToSplit(32);
                } else {
                    shortcutController.moveFreeformToSplit(64);
                }
            }
        }
    }
}
