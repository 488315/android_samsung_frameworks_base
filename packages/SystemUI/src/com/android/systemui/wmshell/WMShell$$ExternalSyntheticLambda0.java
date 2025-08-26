package com.android.systemui.wmshell;

import android.app.ActivityManager;
import android.app.TaskInfo;
import android.content.res.Resources;
import android.window.WindowContainerTransaction;
import com.android.systemui.model.SysUiState;
import com.android.systemui.model.SysUiStateImpl;
import com.android.systemui.statusbar.CommandQueue;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.desktopmode.DesktopMode;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.common.ToggleTaskSizeInteraction;
import com.android.wm.shell.onehanded.OneHanded;
import com.android.wm.shell.pip.Pip;
import com.android.wm.shell.recents.RecentTasks;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.splitscreen.EnterSplitGestureHandler;
import com.android.wm.shell.splitscreen.SplitScreen;
import com.android.wm.shell.splitscreen.SplitScreenController;
import com.android.wm.shell.windowdecor.DesktopModeWindowDecorViewModel;
import com.samsung.android.multiwindow.MultiWindowUtils;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import kotlin.collections.CollectionsKt___CollectionsKt;

/* loaded from: classes3.dex */
public final /* synthetic */ class WMShell$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ WMShell f$0;

    public /* synthetic */ WMShell$$ExternalSyntheticLambda0(WMShell wMShell, int i) {
        this.$r8$classId = i;
        this.f$0 = wMShell;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        int i = this.$r8$classId;
        final WMShell wMShell = this.f$0;
        switch (i) {
            case 0:
                int i2 = WMShell.$r8$clinit;
                if (!((Boolean) obj).booleanValue()) {
                    SysUiState flag = wMShell.mSysUiState.setFlag(17179869184L, false);
                    wMShell.mDisplayTracker.getClass();
                    ((SysUiStateImpl) flag).commitUpdate();
                    break;
                }
                break;
            case 1:
                wMShell.initRecentTasks((RecentTasks) obj);
                break;
            case 2:
                int i3 = WMShell.$r8$clinit;
                wMShell.getClass();
                ((SysUiStateImpl) wMShell.mSysUiState).addCallback(new WMShell$$ExternalSyntheticLambda1(wMShell, (EnterSplitGestureHandler) obj, 1));
                break;
            case 3:
                wMShell.initPip((Pip) obj);
                break;
            case 4:
                wMShell.initSplitScreen((SplitScreen) obj);
                break;
            case 5:
                wMShell.initOneHanded((OneHanded) obj);
                break;
            default:
                DesktopMode desktopMode = (DesktopMode) obj;
                wMShell.getClass();
                final DesktopRepository.VisibleTasksListener visibleTasksListener = new DesktopRepository.VisibleTasksListener() { // from class: com.android.systemui.wmshell.WMShell.17
                    public AnonymousClass17() {
                    }

                    @Override // com.android.wm.shell.desktopmode.DesktopRepository.VisibleTasksListener
                    public final void onTasksVisibilityChanged(int i4, int i5) {
                        if (i4 == 0) {
                            WMShell wMShell2 = WMShell.this;
                            SysUiState flag2 = wMShell2.mSysUiState.setFlag(67108864L, i5 > 0);
                            wMShell2.mDisplayTracker.getClass();
                            ((SysUiStateImpl) flag2).commitUpdate();
                        }
                    }
                };
                final Executor executor = wMShell.mSysUiMainExecutor;
                final DesktopTasksController desktopTasksController = DesktopTasksController.this;
                desktopTasksController.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$addVisibleTasksListener$1
                    @Override // java.lang.Runnable
                    public final void run() {
                        DesktopTasksController desktopTasksController2 = desktopTasksController;
                        desktopTasksController2.taskRepository.addVisibleTasksListener(visibleTasksListener, executor);
                    }
                });
                wMShell.mCommandQueue.addCallback(new CommandQueue.Callbacks(wMShell, desktopMode) { // from class: com.android.systemui.wmshell.WMShell.18
                    public final /* synthetic */ DesktopMode val$desktopMode;

                    public AnonymousClass18(final WMShell wMShell2, DesktopMode desktopMode2) {
                        this.val$desktopMode = desktopMode2;
                    }

                    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                    public final void moveFocusedTaskToDesktop(final int i4) {
                        final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                        final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                        if (MultiWindowUtils.isInSubDisplay(desktopTasksController2.context)) {
                            DesktopTasksController.logD$1("moveFocusedTaskToDesktop in SubDisplay", new Object[0]);
                            return;
                        }
                        DesktopTasksController.logV$1("moveFocusedTaskToDesktop", new Object[0]);
                        desktopTasksController2.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToDesktop$1
                            @Override // java.lang.Runnable
                            public final void run() {
                                ActivityManager.RunningTaskInfo focusedActivityTaskWithWinMode;
                                DesktopTasksController desktopTasksController3 = desktopTasksController2;
                                int i5 = i4;
                                DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                ShellTaskOrganizer shellTaskOrganizer = desktopTasksController3.shellTaskOrganizer;
                                ArrayList runningTasks = shellTaskOrganizer.getRunningTasks(i5);
                                ArrayList arrayList = new ArrayList();
                                int size = runningTasks.size();
                                int i6 = 0;
                                while (i6 < size) {
                                    Object obj2 = runningTasks.get(i6);
                                    i6++;
                                    ActivityManager.RunningTaskInfo runningTaskInfo = (ActivityManager.RunningTaskInfo) obj2;
                                    if (runningTaskInfo.isFocused && (runningTaskInfo.getWindowingMode() == 1 || runningTaskInfo.getWindowingMode() == 6)) {
                                        if (runningTaskInfo.getActivityType() != 2) {
                                            arrayList.add(obj2);
                                        }
                                    }
                                }
                                int size2 = arrayList.size();
                                if (size2 == 0) {
                                    if (desktopModeTransitionSource2 == DesktopModeTransitionSource.KEYBOARD_SHORTCUT && desktopTasksController3.taskRepository.isAnyDeskActive(i5) && (focusedActivityTaskWithWinMode = shellTaskOrganizer.getFocusedActivityTaskWithWinMode(i5, 5)) != null) {
                                        desktopTasksController3.toggleDesktopTask(focusedActivityTaskWithWinMode, ToggleTaskSizeInteraction.Direction.RESTORE);
                                        return;
                                    }
                                    return;
                                }
                                DesktopModeCompatPolicy desktopModeCompatPolicy = desktopTasksController3.desktopModeCompatPolicy;
                                if (size2 != 1) {
                                    if (size2 != 2) {
                                        DesktopTasksController.logW("DesktopTasksController: Cannot enter desktop, expected less than 3 focused tasks but found %d", Integer.valueOf(arrayList.size()));
                                        return;
                                    }
                                    ActivityManager.RunningTaskInfo runningTaskInfo2 = (ActivityManager.RunningTaskInfo) arrayList.get(0);
                                    ActivityManager.RunningTaskInfo runningTaskInfo3 = (ActivityManager.RunningTaskInfo) arrayList.get(1);
                                    if (runningTaskInfo2.taskId == runningTaskInfo3.parentTaskId) {
                                        runningTaskInfo2 = runningTaskInfo3;
                                    }
                                    if (desktopModeTransitionSource2 == DesktopModeTransitionSource.KEYBOARD_SHORTCUT || desktopModeCompatPolicy.shouldDisableDesktopEntryPoints(runningTaskInfo2)) {
                                        return;
                                    }
                                    DesktopTasksController.moveTaskToDefaultDeskAndActivate$default(desktopTasksController3, runningTaskInfo2.taskId, desktopModeTransitionSource2, null, null, 26);
                                    return;
                                }
                                if (desktopModeCompatPolicy.shouldDisableDesktopEntryPoints((TaskInfo) CollectionsKt___CollectionsKt.single((List) arrayList))) {
                                    return;
                                }
                                if (desktopModeTransitionSource2 == DesktopModeTransitionSource.KEYBOARD_SHORTCUT) {
                                    ActivityManager.RunningTaskInfo runningTaskInfo4 = (ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.single((List) arrayList);
                                    if (!((DesktopStateImpl) desktopTasksController3.desktopState).isDesktopModeSupportedOnDisplay(i5)) {
                                        WindowContainerTransaction windowContainerTransaction = new WindowContainerTransaction();
                                        DesktopTasksController.logV$1("moveToFreeform taskId=%d displayId=%d", Integer.valueOf(runningTaskInfo4.taskId), Integer.valueOf(runningTaskInfo4.displayId));
                                        SplitScreenController splitScreenController = desktopTasksController3.splitScreenController;
                                        if (splitScreenController == null) {
                                            splitScreenController = null;
                                        }
                                        if (splitScreenController.isTaskInSplitScreen$1(runningTaskInfo4.taskId)) {
                                            SplitScreenController splitScreenController2 = desktopTasksController3.splitScreenController;
                                            if (splitScreenController2 == null) {
                                                splitScreenController2 = null;
                                            }
                                            splitScreenController2.moveSplitToFreeform(runningTaskInfo4.token, null, false);
                                        }
                                        windowContainerTransaction.setWindowingMode(runningTaskInfo4.token, 5);
                                        if (CoreRune.MT_APP_COMPAT_ASPECT_RATIO_POLICY && runningTaskInfo4.appCompatTaskInfo.isTopActivityLetterboxed()) {
                                            windowContainerTransaction.setDisplayIdForChangeTransition(runningTaskInfo4.getDisplayId(), "compat_to_freeform");
                                        }
                                        windowContainerTransaction.setChangeTransitMode(runningTaskInfo4.token, 1, "moveToFreeform");
                                        desktopTasksController3.desktopMixedTransitionHandler.startWindowingModeTransition(windowContainerTransaction, 5);
                                        return;
                                    }
                                    if (desktopTasksController3.taskRepository.isAnyDeskActive(i5) && runningTaskInfo4.isFreeform()) {
                                        desktopTasksController3.toggleDesktopTask(runningTaskInfo4, ToggleTaskSizeInteraction.Direction.RESTORE);
                                        return;
                                    }
                                }
                                DesktopTasksController.moveTaskToDefaultDeskAndActivate$default(desktopTasksController3, ((ActivityManager.RunningTaskInfo) CollectionsKt___CollectionsKt.single((List) arrayList)).taskId, desktopModeTransitionSource2, null, null, 26);
                            }
                        });
                    }

                    @Override // com.android.systemui.statusbar.CommandQueue.Callbacks
                    public final void moveFocusedTaskToFullscreen(final int i4) {
                        final DesktopModeTransitionSource desktopModeTransitionSource = DesktopModeTransitionSource.KEYBOARD_SHORTCUT;
                        DesktopTasksController.DesktopModeImpl desktopModeImpl = (DesktopTasksController.DesktopModeImpl) this.val$desktopMode;
                        desktopModeImpl.getClass();
                        DesktopTasksController.Companion companion = DesktopTasksController.Companion;
                        final DesktopTasksController desktopTasksController2 = DesktopTasksController.this;
                        desktopTasksController2.getClass();
                        DesktopTasksController.logV$1("moveFocusedTaskToFullscreen", new Object[0]);
                        desktopTasksController2.mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$DesktopModeImpl$moveFocusedTaskToFullscreen$1
                            @Override // java.lang.Runnable
                            public final void run() throws Resources.NotFoundException {
                                DesktopTasksController desktopTasksController3 = desktopTasksController2;
                                int i5 = i4;
                                DesktopModeTransitionSource desktopModeTransitionSource2 = desktopModeTransitionSource;
                                if (i5 != 0 && desktopTasksController3.taskRepository.isAnyDeskActive(i5)) {
                                    ActivityManager.RunningTaskInfo focusedActivityTaskWithWinMode = desktopTasksController3.shellTaskOrganizer.getFocusedActivityTaskWithWinMode(i5, 0);
                                    if (focusedActivityTaskWithWinMode != null) {
                                        desktopTasksController3.toggleDesktopTask(focusedActivityTaskWithWinMode, ToggleTaskSizeInteraction.Direction.MAXIMIZE);
                                        return;
                                    }
                                    return;
                                }
                                ActivityManager.RunningTaskInfo focusedFreeformTask = desktopTasksController3.getFocusedFreeformTask(i5);
                                if (focusedFreeformTask != null) {
                                    DesktopModeWindowDecorViewModel desktopModeWindowDecorViewModel = desktopTasksController3.snapEventHandler;
                                    if (desktopModeWindowDecorViewModel == null) {
                                        desktopModeWindowDecorViewModel = null;
                                    }
                                    desktopModeWindowDecorViewModel.removeTaskIfTiled(i5, focusedFreeformTask.taskId);
                                    desktopTasksController3.moveToFullscreenWithAnimation(focusedFreeformTask, focusedFreeformTask.positionInParent, desktopModeTransitionSource2, null);
                                }
                            }
                        });
                    }
                });
                break;
        }
    }
}
