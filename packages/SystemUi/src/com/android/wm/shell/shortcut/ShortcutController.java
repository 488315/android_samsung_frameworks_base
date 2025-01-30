package com.android.wm.shell.shortcut;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.InsetsState;
import android.view.KeyEvent;
import android.view.WindowInsets;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.common.DisplayController;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.common.SyncTransactionQueue;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.splitscreen.StageCoordinator;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecorViewModel;
import com.android.wm.shell.windowdecor.MultitaskingWindowDecoration;
import com.android.wm.shell.windowdecor.TaskOperations;
import com.android.wm.shell.windowdecor.TaskOperations$$ExternalSyntheticLambda0;
import com.android.wm.shell.windowdecor.WindowDecorViewModel;
import com.samsung.android.core.CoreSaLogger;
import com.samsung.android.multiwindow.IKeyEventListener;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.Optional;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ShortcutController {
    public final Context mContext;
    public final DexCompatRestartDialogUtils mDexCompatRestartDialogUtils;
    public final DisplayController mDisplayController;
    public boolean mIsNewDexMode;
    public final ShellExecutor mMainExecutor;
    public ActivityManager.RunningTaskInfo mRunningTaskInfo;
    public final Optional mSplitScreenController;
    public final SyncTransactionQueue mSyncQueue;
    public TaskOperations mTaskOperations;
    public final ShellTaskOrganizer mTaskOrganizer;
    public final WindowDecorViewModel mWindowDecorationViewModel;
    public final KeyEventListenerImpl mKeyEventListener = new KeyEventListenerImpl();
    public final SparseArray mShortCutPolicyMap = new SparseArray();
    public final Rect mTmpRect = new Rect();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class KeyEventListenerImpl extends IKeyEventListener.Stub {
        public KeyEventListenerImpl() {
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x004a  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x0051  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void sendShortcutKeyWithFocusedTask(int i, KeyEvent keyEvent) {
            boolean z;
            ShortcutController shortcutController = ShortcutController.this;
            if (shortcutController.mTaskOperations == null || !shortcutController.mSplitScreenController.isPresent()) {
                Log.e("ShortcutController", "sendShortcutKeyWithFocusedTask: Not set up normally");
                return;
            }
            StageCoordinator transitionHandler = ((SplitScreenController) ShortcutController.this.mSplitScreenController.get()).getTransitionHandler();
            if (transitionHandler != null) {
                ActivityManager.RunningTaskInfo runningTaskInfo = transitionHandler.mMainStage.mRootTaskInfo;
                ComponentName componentName = runningTaskInfo != null ? runningTaskInfo.topActivity : null;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = transitionHandler.mSideStage.mRootTaskInfo;
                ComponentName componentName2 = runningTaskInfo2 != null ? runningTaskInfo2.topActivity : null;
                if ((componentName != null && MultiWindowUtils.isAppsEdgeActivity(componentName)) || (componentName2 != null && MultiWindowUtils.isAppsEdgeActivity(componentName2))) {
                    z = true;
                    if (!z) {
                        Log.e("ShortcutController", "sendShortcutKeyWithFocusedTask: AppsEdge is running on top");
                        return;
                    }
                    ShortcutLaunchPolicy shortcutLaunchPolicy = (ShortcutLaunchPolicy) ShortcutController.this.mShortCutPolicyMap.get(keyEvent.getKeyCode());
                    if (shortcutLaunchPolicy == null) {
                        Log.e("ShortcutController", "sendShortcutKeyWithFocusedTask: Not found the policy : " + keyEvent);
                        return;
                    } else {
                        if (!shortcutLaunchPolicy.mSupportMultiSplitStatus && ((SplitScreenController) ShortcutController.this.mSplitScreenController.get()).isMultiSplitScreenVisible()) {
                            Log.e("ShortcutController", "sendShortcutKeyWithFocusedTask: The 3 split-mode is running");
                            return;
                        }
                        ActivityManager.RunningTaskInfo runningTaskInfo3 = ShortcutController.this.mTaskOrganizer.getRunningTaskInfo(i);
                        if (runningTaskInfo3 == null) {
                            Log.e("ShortcutController", "sendShortcutKeyWithFocusedTask: There is no running task.");
                            return;
                        }
                        ShortcutController shortcutController2 = ShortcutController.this;
                        shortcutController2.mRunningTaskInfo = runningTaskInfo3;
                        if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING && CoreRune.MT_NEW_DEX) {
                            shortcutController2.mIsNewDexMode = runningTaskInfo3.configuration.isNewDexMode();
                        }
                        shortcutLaunchPolicy.handleShortCutKeys(keyEvent);
                        return;
                    }
                }
            }
            z = false;
            if (!z) {
            }
        }
    }

    public ShortcutController(Context context, SyncTransactionQueue syncTransactionQueue, ShellExecutor shellExecutor, Optional<SplitScreenController> optional, ShellTaskOrganizer shellTaskOrganizer, DisplayController displayController, WindowDecorViewModel windowDecorViewModel, DexCompatRestartDialogUtils dexCompatRestartDialogUtils) {
        this.mContext = context;
        this.mSyncQueue = syncTransactionQueue;
        this.mMainExecutor = shellExecutor;
        this.mSplitScreenController = optional;
        this.mTaskOrganizer = shellTaskOrganizer;
        this.mDisplayController = displayController;
        this.mWindowDecorationViewModel = windowDecorViewModel;
        this.mDexCompatRestartDialogUtils = dexCompatRestartDialogUtils;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyMaxOrMinHeight(boolean z) {
        boolean z2;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRunningTaskInfo;
        this.mDexCompatRestartDialogUtils.getClass();
        boolean isDexCompatEnabled = DexCompatRestartDialogUtils.isDexCompatEnabled(runningTaskInfo);
        WindowDecorViewModel windowDecorViewModel = this.mWindowDecorationViewModel;
        if (!isDexCompatEnabled) {
            if (windowDecorViewModel instanceof MultitaskingWindowDecorViewModel) {
                z2 = runningTaskInfo.isFreeform();
                if (z2) {
                    return;
                }
                MultitaskingWindowDecorViewModel multitaskingWindowDecorViewModel = (MultitaskingWindowDecorViewModel) windowDecorViewModel;
                ActivityManager.RunningTaskInfo runningTaskInfo2 = this.mRunningTaskInfo;
                Rect bounds = runningTaskInfo2.configuration.windowConfiguration.getBounds();
                int i = runningTaskInfo2.displayId;
                InsetsState insetsState = this.mDisplayController.getInsetsState(i);
                Rect displayFrame = insetsState.getDisplayFrame();
                Rect rect = this.mTmpRect;
                rect.set(displayFrame);
                rect.inset(insetsState.calculateInsets(rect, WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout(), false));
                rect.left = bounds.left;
                rect.right = bounds.right;
                if (z) {
                    int i2 = rect.top;
                    MultitaskingWindowDecoration multitaskingWindowDecoration = (MultitaskingWindowDecoration) multitaskingWindowDecorViewModel.mWindowDecorByTaskId.get(runningTaskInfo2.taskId);
                    rect.top = (multitaskingWindowDecoration == null ? 0 : multitaskingWindowDecoration.getCaptionVisibleHeight()) + i2;
                    int i3 = rect.bottom;
                    MultitaskingWindowDecoration multitaskingWindowDecoration2 = (MultitaskingWindowDecoration) multitaskingWindowDecorViewModel.mWindowDecorByTaskId.get(runningTaskInfo2.taskId);
                    rect.bottom = i3 - (multitaskingWindowDecoration2 != null ? multitaskingWindowDecoration2.getFreeformThickness$1() : 0);
                } else {
                    int i4 = bounds.top;
                    rect.top = i4;
                    int i5 = this.mRunningTaskInfo.minHeight;
                    rect.bottom = i4 + ((int) (i5 < 0 ? r2.defaultMinSize * r5.getDisplayLayout(r2.displayId).mDensityDpi * 0.00625f : i5));
                }
                if (bounds.equals(rect)) {
                    return;
                }
                WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                windowContainerTransaction.setBounds(runningTaskInfo2.token, rect);
                this.mTaskOrganizer.applyTransaction(windowContainerTransaction);
                return;
            }
            Log.e("ShortcutController", "applyMinHeightBounds: window decoration view model is not proper type.");
        }
        z2 = false;
        if (z2) {
        }
    }

    public final void moveFreeformToSplit(int i) {
        TaskOperations taskOperations = this.mTaskOperations;
        ActivityManager.RunningTaskInfo runningTaskInfo = this.mRunningTaskInfo;
        taskOperations.getClass();
        if (runningTaskInfo != null) {
            taskOperations.mSplitScreenController.ifPresent(new TaskOperations$$ExternalSyntheticLambda0(runningTaskInfo, i));
        }
        if (CoreRune.MW_SHELL_KEYBOARD_SHORTCUT_SA_LOGGING) {
            CoreSaLogger.logForAdvanced("1000", "From Keyboard shortcut", this.mIsNewDexMode ? 2 : 1);
        }
    }
}
