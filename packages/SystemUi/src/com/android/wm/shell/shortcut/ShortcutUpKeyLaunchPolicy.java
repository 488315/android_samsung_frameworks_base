package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.view.KeyEvent;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutUpKeyLaunchPolicy extends ShortcutLaunchPolicy {
    public ShortcutUpKeyLaunchPolicy(ShortcutController shortcutController) {
        super(shortcutController, false);
    }

    @Override // com.android.wm.shell.shortcut.ShortcutLaunchPolicy
    public final void handleShortCutKeys(KeyEvent keyEvent) {
        ShortcutController shortcutController = this.mShortcutController;
        ActivityManager.RunningTaskInfo runningTaskInfo = shortcutController.mRunningTaskInfo;
        if (!keyEvent.isCtrlPressed()) {
            if (keyEvent.isShiftPressed() && runningTaskInfo.getWindowingMode() == 5) {
                shortcutController.applyMaxOrMinHeight(true);
                return;
            }
            return;
        }
        if (runningTaskInfo.getWindowingMode() != 1) {
            ActivityManager.RunningTaskInfo runningTaskInfo2 = shortcutController.mRunningTaskInfo;
            DexCompatRestartDialogUtils dexCompatRestartDialogUtils = shortcutController.mDexCompatRestartDialogUtils;
            dexCompatRestartDialogUtils.getClass();
            if (DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo2)) {
                dexCompatRestartDialogUtils.toggleFreeformForDexCompatApp(runningTaskInfo2.taskId);
            } else {
                shortcutController.mTaskOperations.maximizeTask(runningTaskInfo2);
            }
            if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                CoreSaLogger.logForAdvanced("2090", "From Keyboard shortcut", shortcutController.mIsNewDexMode ? 2 : 1);
            }
        }
    }
}
