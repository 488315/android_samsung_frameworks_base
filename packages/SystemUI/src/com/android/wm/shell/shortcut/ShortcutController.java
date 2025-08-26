package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.app.ActivityTaskManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Pair;
import android.view.KeyEvent;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.DisplayLayout;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.shortcut.ShortcutController;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.windowdecor.TaskOperations;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public class ShortcutController {
    public static volatile ShortcutController sSingleton;
    public Context mContext;
    public DesktopStateImpl mDesktopStateImpl;
    public DesktopTasksController mDesktopTasksController;
    public DisplayController mDisplayController;
    public ShellExecutor mMainExecutor;
    public ActivityManager.RunningTaskInfo mRunningTaskInfo;
    public ShellTaskOrganizer mShellTaskOrganizer;
    public SplitScreenController mSplitScreenController;
    public TaskOperations mTaskOperations;
    public final KeyEventListenerImpl mKeyEventListener = new KeyEventListenerImpl(this, 0);
    public boolean mInitialized = false;
    public boolean mIsInDeskMode = false;

    public class KeyEventListenerImpl extends IKeyEventListener.Stub {
        public /* synthetic */ KeyEventListenerImpl(ShortcutController shortcutController, int i) {
            this();
        }

        public final void sendShortcutKey(final KeyEvent keyEvent) {
            ShortcutController.this.mMainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.shortcut.ShortcutController$KeyEventListenerImpl$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws Resources.NotFoundException {
                    ShortcutController.KeyEventListenerImpl keyEventListenerImpl = this.f$0;
                    KeyEvent keyEvent2 = keyEvent;
                    ShortcutController shortcutController = ShortcutController.this;
                    if (shortcutController.mDesktopStateImpl.isDesktopModeSupportedOnDisplay(0)) {
                        if ((keyEvent2.getKeyCode() != 1084 || keyEvent2.getAction() == 0) && !(keyEvent2.getKeyCode() == 51 && keyEvent2.getAction() == 0 && keyEvent2.getRepeatCount() == 0 && !keyEvent2.isCtrlPressed() && keyEvent2.isMetaPressed())) {
                            return;
                        }
                        DesktopTasksController desktopTasksController = shortcutController.mDesktopTasksController;
                        if (desktopTasksController.isDesktopModeShowing(0)) {
                            DesktopTasksController.exitDefaultDisplayDesktopWindowing$default(desktopTasksController, null, 1);
                            return;
                        }
                        boolean z = false;
                        for (ActivityManager.RunningTaskInfo runningTaskInfo : ActivityTaskManager.getInstance().getTasks(99, false, false, 0)) {
                            if (runningTaskInfo.getActivityType() == 2) {
                                break;
                            }
                            if (runningTaskInfo.getActivityType() != 3) {
                                int i = runningTaskInfo.taskId;
                                if (runningTaskInfo.isSplitScreen()) {
                                    if (!z) {
                                        SplitScreenController splitScreenController = desktopTasksController.splitScreenController;
                                        if (splitScreenController == null) {
                                            splitScreenController = null;
                                        }
                                        ActivityManager.RunningTaskInfo mainStageRootTaskInfo = splitScreenController.getMainStageRootTaskInfo();
                                        if (mainStageRootTaskInfo != null) {
                                            i = mainStageRootTaskInfo.parentTaskId;
                                            z = true;
                                        }
                                    }
                                }
                                desktopTasksController.taskIdsOfTabletMode.add(new Pair(Integer.valueOf(i), Integer.valueOf(runningTaskInfo.getWindowingMode())));
                            }
                        }
                        Integer orCreateDefaultDeskId = desktopTasksController.getOrCreateDefaultDeskId(0, true);
                        if (orCreateDefaultDeskId != null) {
                            DesktopTasksController.activateDesk$default(desktopTasksController, orCreateDefaultDeskId.intValue(), null, 0, 0, 14);
                        }
                    }
                }
            });
        }

        private KeyEventListenerImpl() {
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

    public final void moveFocusedTaskToStageSplit(int i, boolean z) {
        final int i2;
        if (!this.mInitialized) {
            Log.e("ShortcutController", "isReadyToOperateShortcuts: not initialized yet");
            return;
        }
        StageCoordinator transitionHandler = this.mSplitScreenController.getTransitionHandler();
        if (transitionHandler != null) {
            ActivityManager.RunningTaskInfo runningTaskInfo = transitionHandler.mMainStage.mRootTaskInfo;
            ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
            ActivityManager.RunningTaskInfo runningTaskInfo2 = transitionHandler.mSideStage.mRootTaskInfo;
            ComponentName componentName2 = runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null;
            if ((componentName != null && MultiWindowUtils.isAppsEdgeActivity(componentName)) || (componentName2 != null && MultiWindowUtils.isAppsEdgeActivity(componentName2))) {
                Log.e("ShortcutController", "isReadyToOperateShortcuts: AppsEdge is running on top");
                return;
            }
        }
        if (this.mSplitScreenController.isMultiSplitScreenVisible()) {
            Log.e("ShortcutController", "isReadyToOperateShortcuts: The 3 split-mode is running");
            return;
        }
        boolean zIsLandscape = false;
        if (i == -1) {
            Log.d("ShortcutController", "isReadyToOperateShortcuts: focusedDisplayId is -1");
            ShellTaskOrganizer shellTaskOrganizer = this.mShellTaskOrganizer;
            this.mRunningTaskInfo = shellTaskOrganizer.getFocusedActivityTaskWithWinMode(shellTaskOrganizer.mFocusTransitionObserver.mFocusedDisplayId, 0);
        } else {
            this.mRunningTaskInfo = this.mShellTaskOrganizer.getFocusedActivityTaskWithWinMode(i, 0);
        }
        if (this.mRunningTaskInfo == null) {
            Log.e("ShortcutController", "isReadyToOperateShortcuts: taskInfo == null");
            return;
        }
        if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
            if (i == -1) {
                i = 0;
            }
            DesktopStateImpl.Companion.getClass();
            this.mIsInDeskMode = DesktopStateImpl.Companion.inDesktopWindowing(i);
        }
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
            } else {
                zIsLandscape = displayLayout.isLandscape();
            }
            if (zIsLandscape) {
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
