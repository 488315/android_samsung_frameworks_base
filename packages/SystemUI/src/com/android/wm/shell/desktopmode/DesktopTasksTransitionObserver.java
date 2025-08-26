package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import android.window.WindowContainerToken;
import android.window.WindowContainerTransaction;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.ShellTaskOrganizer;
import com.android.wm.shell.back.BackAnimationController;
import com.android.wm.shell.desktopmode.DesktopMixedTransitionHandler;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopWallpaperActivity;
import com.android.wm.shell.desktopmode.desktopwallpaperactivity.DesktopWallpaperActivityTokenProvider;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.shared.desktopmode.DesktopState;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import com.android.wm.shell.sysui.ShellInit;
import com.android.wm.shell.transition.Transitions;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt___SequencesKt;

/* loaded from: classes3.dex */
public final class DesktopTasksTransitionObserver implements Transitions.TransitionObserver {
    public final BackAnimationController backAnimationController;
    public final DesktopMixedTransitionHandler desktopMixedTransitionHandler;
    public final DesktopUserRepositories desktopUserRepositories;
    public final DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider;
    public final ShellTaskOrganizer shellTaskOrganizer;
    public CloseWallpaperTransition transitionToCloseWallpaper;
    public final Transitions transitions;

    public final class CloseWallpaperTransition {
        public final int displayId;
        public final IBinder transition;

        public CloseWallpaperTransition(IBinder iBinder, int i) {
            this.transition = iBinder;
            this.displayId = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof CloseWallpaperTransition)) {
                return false;
            }
            CloseWallpaperTransition closeWallpaperTransition = (CloseWallpaperTransition) obj;
            return Intrinsics.areEqual(this.transition, closeWallpaperTransition.transition) && this.displayId == closeWallpaperTransition.displayId;
        }

        public final int hashCode() {
            return Integer.hashCode(this.displayId) + (this.transition.hashCode() * 31);
        }

        public final String toString() {
            return "CloseWallpaperTransition(transition=" + this.transition + ", displayId=" + this.displayId + ")";
        }
    }

    public DesktopTasksTransitionObserver(DesktopUserRepositories desktopUserRepositories, Transitions transitions, ShellTaskOrganizer shellTaskOrganizer, DesktopMixedTransitionHandler desktopMixedTransitionHandler, BackAnimationController backAnimationController, DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider, DesktopState desktopState, ShellInit shellInit) {
        this.desktopUserRepositories = desktopUserRepositories;
        this.transitions = transitions;
        this.shellTaskOrganizer = shellTaskOrganizer;
        this.desktopMixedTransitionHandler = desktopMixedTransitionHandler;
        this.backAnimationController = backAnimationController;
        this.desktopWallpaperActivityTokenProvider = desktopWallpaperActivityTokenProvider;
        if (((DesktopStateImpl) desktopState).canEnterDesktopMode) {
            shellInit.addInitCallback(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTasksTransitionObserver.1
                @Override // java.lang.Runnable
                public final void run() {
                    DesktopTasksTransitionObserver desktopTasksTransitionObserver = DesktopTasksTransitionObserver.this;
                    desktopTasksTransitionObserver.getClass();
                    ProtoLog.d(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "DesktopTasksTransitionObserver: onInit", new Object[0]);
                    desktopTasksTransitionObserver.transitions.registerObserver(desktopTasksTransitionObserver);
                }
            }, this);
        }
        ActivityManager.getCurrentUser();
    }

    public final boolean allowInDesk(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.getWindowingMode() == 1 && this.desktopUserRepositories.getProfile(runningTaskInfo.userId).getAllDeskIds().contains(Integer.valueOf(runningTaskInfo.parentTaskId));
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionFinished(IBinder iBinder, boolean z) {
        CloseWallpaperTransition closeWallpaperTransition = this.transitionToCloseWallpaper;
        if (Intrinsics.areEqual(closeWallpaperTransition != null ? closeWallpaperTransition.transition : null, iBinder)) {
            WindowContainerToken token = this.desktopWallpaperActivityTokenProvider.getToken(closeWallpaperTransition.displayId);
            if (token != null) {
                boolean zIsTrue = DesktopModeFlags.ENABLE_DESKTOP_WALLPAPER_ACTIVITY_FOR_SYSTEM_USER.isTrue();
                Transitions transitions = this.transitions;
                if (zIsTrue) {
                    transitions.startTransition(4, new WindowContainerTransaction().reorder(token, false), null);
                } else {
                    transitions.startTransition(2, new WindowContainerTransaction().removeTask(token), null);
                }
            }
            this.transitionToCloseWallpaper = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:74:0x017e  */
    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onTransitionReady(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2) {
        Integer num;
        boolean z;
        boolean zIsTrue = DesktopModeFlags.INCLUDE_TOP_TRANSPARENT_FULLSCREEN_TASK_IN_DESKTOP_HEURISTIC.isTrue();
        boolean z2 = false;
        DesktopUserRepositories desktopUserRepositories = this.desktopUserRepositories;
        if (zIsTrue && DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_MODALS_POLICY.isTrue()) {
            for (TransitionInfo.Change change : transitionInfo.getChanges()) {
                ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
                if (taskInfo != null) {
                    DesktopRepository profile = desktopUserRepositories.getProfile(taskInfo.userId);
                    int i = taskInfo.displayId;
                    DesktopRepository.DesktopData desktopData = profile.desktopData;
                    Integer num2 = (Integer) SequencesKt___SequencesKt.firstOrNull(SequencesKt___SequencesKt.mapNotNull(desktopData.desksSequence(i), new DesktopRepository$$ExternalSyntheticLambda0(4)));
                    if (num2 == null) {
                        break;
                    }
                    int mode = change.getMode();
                    int i2 = taskInfo.taskId;
                    boolean z3 = i2 == num2.intValue() && TransitionUtil.isClosingMode(mode);
                    boolean z4 = i2 != num2.intValue() && TransitionUtil.isOpeningMode(mode);
                    if (z3 || z4) {
                        DesktopRepository.Desk activeDesk = desktopData.getActiveDesk(i);
                        profile.logD("Top transparent fullscreen task cleared for display: taskId=%d, displayId=%d", activeDesk != null ? activeDesk.topTransparentFullscreenTaskId : null, Integer.valueOf(i));
                        DesktopRepository.Desk activeDesk2 = desktopData.getActiveDesk(i);
                        if (activeDesk2 != null) {
                            activeDesk2.topTransparentFullscreenTaskId = null;
                        }
                    }
                }
            }
        }
        boolean zIsTrue2 = DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY.isTrue();
        DesktopWallpaperActivityTokenProvider desktopWallpaperActivityTokenProvider = this.desktopWallpaperActivityTokenProvider;
        if (zIsTrue2) {
            for (TransitionInfo.Change change2 : transitionInfo.getChanges()) {
                ActivityManager.RunningTaskInfo taskInfo2 = change2.getTaskInfo();
                if (taskInfo2 != null) {
                    DesktopWallpaperActivity.Companion.getClass();
                    if (DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo2)) {
                        int mode2 = change2.getMode();
                        if (mode2 == 1) {
                            WindowContainerToken windowContainerToken = taskInfo2.token;
                            int i3 = taskInfo2.displayId;
                            Object[] objArr = {Integer.valueOf(i3)};
                            desktopWallpaperActivityTokenProvider.getClass();
                            DesktopWallpaperActivityTokenProvider.logV("Setting desktop wallpaper activity token for display %s", objArr);
                            desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.set(i3, windowContainerToken);
                            this.shellTaskOrganizer.applyTransaction(new WindowContainerTransaction().setTaskTrimmableFromRecents(taskInfo2.token, false));
                        } else if (mode2 == 2) {
                            int i4 = taskInfo2.displayId;
                            Object[] objArr2 = {Integer.valueOf(i4)};
                            desktopWallpaperActivityTokenProvider.getClass();
                            DesktopWallpaperActivityTokenProvider.logV("Remove desktop wallpaper activity token for display %s", objArr2);
                            desktopWallpaperActivityTokenProvider.wallpaperActivityTokenByDisplayId.delete(i4);
                        }
                    }
                }
            }
        }
        if (DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue()) {
            int type = transitionInfo.getType();
            DesktopMixedTransitionHandler desktopMixedTransitionHandler = this.desktopMixedTransitionHandler;
            if (type == 4) {
                for (TransitionInfo.Change change3 : transitionInfo.getChanges()) {
                    ActivityManager.RunningTaskInfo taskInfo3 = change3.getTaskInfo();
                    if (taskInfo3 != null && taskInfo3.taskId != -1) {
                        DesktopRepository profile2 = desktopUserRepositories.getProfile(taskInfo3.userId);
                        if (profile2.isAnyDeskActive(taskInfo3.displayId) && change3.getMode() == 4 && (taskInfo3.getWindowingMode() == 5 || allowInDesk(taskInfo3))) {
                            if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue()) {
                                int i5 = taskInfo3.taskId;
                                DesktopRepository.Desk activeDesk3 = profile2.desktopData.getActiveDesk(taskInfo3.displayId);
                                if (activeDesk3 == null || activeDesk3.visibleTasks.size() != 1 || (num = (Integer) CollectionsKt___CollectionsKt.single(activeDesk3.visibleTasks)) == null || num.intValue() != i5) {
                                    z = false;
                                }
                                profile2.minimizeTask(taskInfo3.displayId, taskInfo3.taskId);
                                ((ArrayList) desktopMixedTransitionHandler.pendingMixedTransitions).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Minimize(iBinder, taskInfo3.taskId, z));
                            } else {
                                z = profile2.getVisibleTaskCount(taskInfo3.displayId) == 1 ? true : z2;
                                profile2.minimizeTask(taskInfo3.displayId, taskInfo3.taskId);
                                ((ArrayList) desktopMixedTransitionHandler.pendingMixedTransitions).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Minimize(iBinder, taskInfo3.taskId, z));
                            }
                        }
                    }
                    z2 = false;
                }
            } else if (transitionInfo.getType() == 2) {
                Integer numValueOf = null;
                boolean z5 = false;
                for (TransitionInfo.Change change4 : transitionInfo.getChanges()) {
                    ActivityManager.RunningTaskInfo taskInfo4 = change4.getTaskInfo();
                    if (taskInfo4 != null && taskInfo4.taskId != -1) {
                        if (TransitionUtil.isClosingMode(change4.getMode())) {
                            DesktopWallpaperActivity.Companion.getClass();
                            if (DesktopWallpaperActivity.Companion.isWallpaperTask(taskInfo4)) {
                                z5 = true;
                            }
                        }
                        if (change4.getMode() == 2 && numValueOf == null) {
                            DesktopRepository profile3 = desktopUserRepositories.getProfile(taskInfo4.userId);
                            if (profile3.isAnyDeskActive(taskInfo4.displayId) && (taskInfo4.getWindowingMode() == 5 || allowInDesk(taskInfo4))) {
                                int i6 = this.backAnimationController.mBackTransitionObserver.mFocusedTaskId;
                                int i7 = taskInfo4.taskId;
                                if (i6 == i7 && !profile3.isClosingTask(i7)) {
                                    profile3.minimizeTask(taskInfo4.displayId, taskInfo4.taskId);
                                    numValueOf = Integer.valueOf(taskInfo4.taskId);
                                }
                            }
                            numValueOf = null;
                        }
                    }
                }
                if (numValueOf != null) {
                    ((ArrayList) desktopMixedTransitionHandler.pendingMixedTransitions).add(new DesktopMixedTransitionHandler.PendingMixedTransition.Minimize(iBinder, numValueOf.intValue(), z5));
                }
            }
            if (TransitionUtil.isOpeningType(transitionInfo.getType()) || DesktopModeTransitionTypes.isExitDesktopModeTransition(transitionInfo.getType())) {
                Iterator it = transitionInfo.getChanges().iterator();
                while (it.hasNext()) {
                    ActivityManager.RunningTaskInfo taskInfo5 = ((TransitionInfo.Change) it.next()).getTaskInfo();
                    if (taskInfo5 != null && taskInfo5.taskId != -1) {
                        DesktopRepository profile4 = desktopUserRepositories.getProfile(taskInfo5.userId);
                        if (profile4.isActiveTask(taskInfo5.taskId) && taskInfo5.getWindowingMode() != 5 && !allowInDesk(taskInfo5)) {
                            profile4.removeTask(taskInfo5.displayId, taskInfo5.taskId);
                        }
                    }
                }
            }
        }
        for (TransitionInfo.Change change5 : transitionInfo.getChanges()) {
            ActivityManager.RunningTaskInfo taskInfo6 = change5.getTaskInfo();
            if (taskInfo6 != null && taskInfo6.taskId != -1 && !desktopUserRepositories.getProfile(taskInfo6.userId).isAnyDeskActive(taskInfo6.displayId) && change5.getMode() == 2 && (taskInfo6.getWindowingMode() == 5 || allowInDesk(taskInfo6))) {
                if (desktopWallpaperActivityTokenProvider.getToken(taskInfo6.displayId) != null) {
                    this.transitionToCloseWallpaper = new CloseWallpaperTransition(iBinder, taskInfo6.displayId);
                }
            }
        }
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionStarting(IBinder iBinder) {
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionObserver
    public final void onTransitionMerged(IBinder iBinder, IBinder iBinder2) {
    }
}
