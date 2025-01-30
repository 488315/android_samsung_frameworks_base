package com.android.p038wm.shell.shortcut;

import android.app.ActivityManager;
import android.view.KeyEvent;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.MultiWindowManager;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutDownKeyLaunchPolicy extends ShortcutLaunchPolicy {
    public ShortcutDownKeyLaunchPolicy(ShortcutController shortcutController) {
        super(shortcutController, false);
    }

    @Override // com.android.p038wm.shell.shortcut.ShortcutLaunchPolicy
    public final void handleShortCutKeys(KeyEvent keyEvent) {
        ShortcutController shortcutController = this.mShortcutController;
        ActivityManager.RunningTaskInfo runningTaskInfo = shortcutController.mRunningTaskInfo;
        if (!keyEvent.isCtrlPressed()) {
            if (keyEvent.isShiftPressed() && runningTaskInfo.getWindowingMode() == 5) {
                shortcutController.applyMaxOrMinHeight(false);
                return;
            }
            return;
        }
        if (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.isSplitScreen()) {
            shortcutController.mTaskOperations.moveToFreeform(shortcutController.mRunningTaskInfo.token);
            if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("2004", "From Keyboard shortcut", shortcutController.mIsNewDexMode ? 2 : 1);
                return;
            }
            return;
        }
        if (runningTaskInfo.isFreeform()) {
            if (CoreRune.MW_FREEFORM_MINIMIZE_SHELL_TRANSITION) {
                MultiWindowManager.getInstance().minimizeTaskById(shortcutController.mRunningTaskInfo.taskId);
            } else {
                shortcutController.mTaskOperations.minimizeTask(shortcutController.mRunningTaskInfo.token);
            }
        }
    }
}
