package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.util.Log;
import android.util.SparseArray;
import android.view.KeyEvent;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.windowdecor.TaskOperations;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class ShortcutController {
    public static volatile ShortcutController sSingleton;
    public Context mContext;
    public DisplayController mDisplayController;
    public ShellExecutor mMainExecutor;
    public ActivityManager.RunningTaskInfo mRunningTaskInfo;
    public ShellTaskOrganizer mShellTaskOrganizer;
    public SplitScreenController mSplitScreenController;
    public TaskOperations mTaskOperations;
    public final KeyEventListenerImpl mKeyEventListener = new KeyEventListenerImpl();
    public final SparseArray mShortCutPolicyMap = new SparseArray();
    public boolean mInitialized = false;
    public boolean mIsInDeskMode = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class KeyEventListenerImpl extends IKeyEventListener.Stub {
        public KeyEventListenerImpl() {
        }

        public final void sendShortcutKey(KeyEvent keyEvent) {
            if (ShortcutController.this.isReadyToOperateShortcuts(-1)) {
                if (((ShortcutLaunchPolicy) ShortcutController.this.mShortCutPolicyMap.get(keyEvent.getKeyCode())) == null) {
                    Log.e("ShortcutController", "sendShortcutKey: Not found the policy : " + keyEvent);
                } else if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                    ShortcutController shortcutController = ShortcutController.this;
                    int displayId = keyEvent.getDisplayId();
                    DesktopStateImpl.Companion.getClass();
                    shortcutController.mIsInDeskMode = DesktopStateImpl.Companion.inDesktopWindowing(displayId);
                }
            }
        }
    }

    private ShortcutController() {
    }

    public static ShortcutController getInstance() {
        if (sSingleton == null) {
            synchronized (ShortcutController.class) {
                try {
                    if (sSingleton == null) {
                        sSingleton = new ShortcutController();
                    }
                } finally {
                }
            }
        }
        return sSingleton;
    }

    public final boolean isReadyToOperateShortcuts(int i) {
        if (!this.mInitialized) {
            Log.e("ShortcutController", "isReadyToOperateShorcuts: not initialized yet");
            return false;
        }
        StageCoordinator transitionHandler = this.mSplitScreenController.getTransitionHandler();
        if (transitionHandler != null) {
            ActivityManager.RunningTaskInfo runningTaskInfo = transitionHandler.mMainStage.mRootTaskInfo;
            ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
            ActivityManager.RunningTaskInfo runningTaskInfo2 = transitionHandler.mSideStage.mRootTaskInfo;
            ComponentName componentName2 = runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null;
            if ((componentName != null && MultiWindowUtils.isAppsEdgeActivity(componentName)) || (componentName2 != null && MultiWindowUtils.isAppsEdgeActivity(componentName2))) {
                Log.e("ShortcutController", "isReadyToOperateShorcuts: AppsEdge is running on top");
                return false;
            }
        }
        if (this.mSplitScreenController.isMultiSplitScreenVisible()) {
            Log.e("ShortcutController", "isReadyToOperateShorcuts: The 3 split-mode is running");
            return false;
        }
        if (i == -1) {
            Log.d("ShortcutController", "isReadyToOperateShorcuts: focusedDisplayId is -1");
            ShellTaskOrganizer shellTaskOrganizer = this.mShellTaskOrganizer;
            this.mRunningTaskInfo = shellTaskOrganizer.getFocusedActivityTaskWithWinMode(shellTaskOrganizer.mFocusTransitionObserver.mFocusedDisplayId, 0);
        } else {
            this.mRunningTaskInfo = this.mShellTaskOrganizer.getFocusedActivityTaskWithWinMode(i, 0);
        }
        if (this.mRunningTaskInfo != null) {
            return true;
        }
        Log.e("ShortcutController", "isReadyToOperateShorcuts: taskInfo == null");
        return false;
    }

    public final void moveFocusedTaskToStageSplit(int i, boolean z) {
        boolean isLandscape;
        final int i2;
        if (isReadyToOperateShortcuts(i)) {
            int displayId = this.mRunningTaskInfo.getDisplayId();
            DesktopStateImpl.Companion.getClass();
            if (DesktopStateImpl.Companion.inDesktopWindowing(displayId)) {
                Log.e("ShortcutController", "moveFocusedTaskToStageSplit: split is not supported in DW");
                return;
            }
            if (this.mRunningTaskInfo.getWindowingMode() == 1) {
                ShellExecutor shellExecutor = this.mMainExecutor;
                final int i3 = 1;
                final int i4 = z ? 1 : 0;
                shellExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.shortcut.ShortcutController$$ExternalSyntheticLambda0
                    public final /* synthetic */ ShortcutController f$0;

                    {
                        this.f$0 = this;
                    }

                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                ShortcutController shortcutController = this.f$0;
                                shortcutController.mTaskOperations.moveFreeformToSplit(shortcutController.mRunningTaskInfo, i4);
                                if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                    CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsInDeskMode ? 2 : 1);
                                    break;
                                }
                                break;
                            default:
                                ShortcutController shortcutController2 = this.f$0;
                                shortcutController2.mSplitScreenController.toggleSplitScreen(i4);
                                if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                    CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController2.mIsInDeskMode ? 2 : 1);
                                    break;
                                }
                                break;
                        }
                    }
                });
                return;
            }
            if (this.mRunningTaskInfo.isFreeform()) {
                if (CoreRune.MW_MULTI_SPLIT && !MultiWindowUtils.isInSubDisplay(this.mContext)) {
                    i2 = z ? 8 : 32;
                    final int i5 = 0;
                    this.mMainExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.shortcut.ShortcutController$$ExternalSyntheticLambda0
                        public final /* synthetic */ ShortcutController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i5) {
                                case 0:
                                    ShortcutController shortcutController = this.f$0;
                                    shortcutController.mTaskOperations.moveFreeformToSplit(shortcutController.mRunningTaskInfo, i2);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                                default:
                                    ShortcutController shortcutController2 = this.f$0;
                                    shortcutController2.mSplitScreenController.toggleSplitScreen(i2);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController2.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
                    return;
                }
                DisplayLayout displayLayout = this.mDisplayController.getDisplayLayout(this.mRunningTaskInfo.getDisplayId());
                if (displayLayout == null) {
                    Log.w("ShortcutController", "Failed to get new DisplayLayout.");
                    isLandscape = false;
                } else {
                    isLandscape = displayLayout.isLandscape();
                }
                if (isLandscape) {
                    i2 = z ? 8 : 32;
                    final int i6 = 0;
                    this.mMainExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.shortcut.ShortcutController$$ExternalSyntheticLambda0
                        public final /* synthetic */ ShortcutController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i6) {
                                case 0:
                                    ShortcutController shortcutController = this.f$0;
                                    shortcutController.mTaskOperations.moveFreeformToSplit(shortcutController.mRunningTaskInfo, i2);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                                default:
                                    ShortcutController shortcutController2 = this.f$0;
                                    shortcutController2.mSplitScreenController.toggleSplitScreen(i2);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController2.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
                } else {
                    final int i7 = z ? 16 : 64;
                    final int i8 = 0;
                    this.mMainExecutor.execute(new Runnable(this) { // from class: com.android.wm.shell.shortcut.ShortcutController$$ExternalSyntheticLambda0
                        public final /* synthetic */ ShortcutController f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i8) {
                                case 0:
                                    ShortcutController shortcutController = this.f$0;
                                    shortcutController.mTaskOperations.moveFreeformToSplit(shortcutController.mRunningTaskInfo, i7);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                                default:
                                    ShortcutController shortcutController2 = this.f$0;
                                    shortcutController2.mSplitScreenController.toggleSplitScreen(i7);
                                    if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
                                        CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", shortcutController2.mIsInDeskMode ? 2 : 1);
                                        break;
                                    }
                                    break;
                            }
                        }
                    });
                }
            }
        }
    }
}
