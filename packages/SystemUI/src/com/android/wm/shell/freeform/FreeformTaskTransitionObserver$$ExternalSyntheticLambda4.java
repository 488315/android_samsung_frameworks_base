package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.content.res.Resources;
import android.os.IBinder;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda5;
import com.android.wm.shell.desktopmode.DesktopTaskChangeListener;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.desktopmode.DesktopUserRepositories;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final /* synthetic */ class FreeformTaskTransitionObserver$$ExternalSyntheticLambda4 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;

    public /* synthetic */ FreeformTaskTransitionObserver$$ExternalSyntheticLambda4(Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        Integer displayIdForTask;
        Set<DeskTransition> set;
        int i = this.$r8$classId;
        Object obj2 = this.f$0;
        switch (i) {
            case 0:
                ActivityManager.RunningTaskInfo taskInfo = ((TransitionInfo.Change) obj2).getTaskInfo();
                DesktopTaskChangeListener desktopTaskChangeListener = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener.getClass();
                DesktopTaskChangeListener.logD("onTaskClosing for taskId=%d, displayId=%d", Integer.valueOf(taskInfo.taskId), Integer.valueOf(taskInfo.displayId));
                DesktopRepository profile = desktopTaskChangeListener.desktopUserRepositories.getProfile(taskInfo.userId);
                if (profile.isActiveTask(taskInfo.taskId)) {
                    if (!DesktopModeFlags.ENABLE_DESKTOP_WINDOWING_BACK_NAVIGATION.isTrue() || profile.isClosingTask(taskInfo.taskId)) {
                        profile.desktopData.forAllDesks(new DesktopRepository$$ExternalSyntheticLambda5(taskInfo.taskId, profile));
                        profile.removeTask(taskInfo.displayId, taskInfo.taskId);
                        break;
                    } else {
                        profile.updateTask(taskInfo.displayId, taskInfo.taskId, false);
                        profile.minimizeTask(taskInfo.displayId, taskInfo.taskId);
                        break;
                    }
                }
                break;
            case 1:
                final ActivityManager.RunningTaskInfo taskInfo2 = ((TransitionInfo.Change) obj2).getTaskInfo();
                final DesktopTaskChangeListener desktopTaskChangeListener2 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener2.getClass();
                DesktopTaskChangeListener.logD("onTaskOpening for taskId=%d, displayId=%d", Integer.valueOf(taskInfo2.taskId), Integer.valueOf(taskInfo2.displayId));
                DesktopRepository profile2 = desktopTaskChangeListener2.desktopUserRepositories.getProfile(taskInfo2.userId);
                if (taskInfo2.getWindowingMode() != 5 && !desktopTaskChangeListener2.allowInDesk(taskInfo2) && profile2.isActiveTask(taskInfo2.taskId)) {
                    profile2.removeTask(taskInfo2.displayId, taskInfo2.taskId);
                    break;
                } else {
                    DesktopStateImpl.Companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(0) && taskInfo2.getWindowingMode() == 1 && taskInfo2.requestFullscreenMode) {
                        ((DesktopTasksController) desktopTaskChangeListener2.desktopTasksController.get()).mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTaskChangeListener$onTaskOpening$1
                            @Override // java.lang.Runnable
                            public final void run() throws Resources.NotFoundException {
                                DesktopTasksController.exitDefaultDisplayDesktopWindowing$default((DesktopTasksController) desktopTaskChangeListener2.desktopTasksController.get(), taskInfo2, 2);
                            }
                        });
                        break;
                    } else if ((taskInfo2.getWindowingMode() == 5 || desktopTaskChangeListener2.allowInDesk(taskInfo2)) && !profile2.isActiveTask(taskInfo2.taskId) && DesktopStateImpl.Companion.inDesktopWindowing(taskInfo2.displayId)) {
                        profile2.addTask(taskInfo2.displayId, taskInfo2.taskId, taskInfo2.isVisible);
                        break;
                    }
                }
                break;
            case 2:
                ActivityManager.RunningTaskInfo taskInfo3 = ((TransitionInfo.Change) obj2).getTaskInfo();
                DesktopTaskChangeListener desktopTaskChangeListener3 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener3.getClass();
                DesktopTaskChangeListener.logD("onTaskMovingToFront for taskId=%d, displayId=%d", Integer.valueOf(taskInfo3.taskId), Integer.valueOf(taskInfo3.displayId));
                int i2 = taskInfo3.taskId;
                DesktopUserRepositories desktopUserRepositories = desktopTaskChangeListener3.desktopUserRepositories;
                Set repositoriesWithDeskId = desktopUserRepositories.getRepositoriesWithDeskId(i2);
                if (!repositoriesWithDeskId.isEmpty()) {
                    DesktopTaskChangeListener.logD("onTaskMovingToFront: skip for taskId=" + i2 + ", reason=desk, repo=" + repositoriesWithDeskId, new Object[0]);
                    break;
                } else {
                    DesktopRepository profile3 = desktopUserRepositories.getProfile(taskInfo3.userId);
                    if (taskInfo3.getWindowingMode() != 5 && !desktopTaskChangeListener3.allowInDesk(taskInfo3) && profile3.isActiveTask(taskInfo3.taskId) && (displayIdForTask = profile3.getDisplayIdForTask(taskInfo3.taskId)) != null) {
                        profile3.removeTask(displayIdForTask.intValue(), taskInfo3.taskId);
                    }
                    if (taskInfo3.getWindowingMode() == 5 || desktopTaskChangeListener3.allowInDesk(taskInfo3)) {
                        DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                        int i3 = taskInfo3.displayId;
                        companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
                            profile3.addTask(taskInfo3.displayId, taskInfo3.taskId, taskInfo3.isVisible);
                            break;
                        }
                    }
                }
                break;
            case 3:
                final ActivityManager.RunningTaskInfo taskInfo4 = ((TransitionInfo.Change) obj2).getTaskInfo();
                final DesktopTaskChangeListener desktopTaskChangeListener4 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener4.getClass();
                DesktopTaskChangeListener.logD("onTaskChanging for taskId=%d, displayId=%d", Integer.valueOf(taskInfo4.taskId), Integer.valueOf(taskInfo4.displayId));
                int i4 = taskInfo4.userId;
                DesktopUserRepositories desktopUserRepositories2 = desktopTaskChangeListener4.desktopUserRepositories;
                DesktopRepository profile4 = desktopUserRepositories2.getProfile(i4);
                int i5 = taskInfo4.taskId;
                if (profile4.getDefaultDeskId(taskInfo4.displayId) == null) {
                    DesktopTaskChangeListener.logD(ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i5, "onTaskChanging: skip for taskId=", ", reason=no_default_desk"), new Object[0]);
                    break;
                } else {
                    Set repositoriesWithDeskId2 = desktopUserRepositories2.getRepositoriesWithDeskId(taskInfo4.taskId);
                    if (!repositoriesWithDeskId2.isEmpty()) {
                        DesktopTaskChangeListener.logD("onTaskChanging: skip for taskId=" + i5 + ", reason=desk, repo=" + repositoriesWithDeskId2, new Object[0]);
                        break;
                    } else if (taskInfo4.getWindowingMode() != 5 && ((!desktopTaskChangeListener4.allowInDesk(taskInfo4) || taskInfo4.requestFullscreenMode) && profile4.isActiveTask(taskInfo4.taskId))) {
                        profile4.removeTask(taskInfo4.displayId, taskInfo4.taskId);
                        DesktopStateImpl.Companion.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(0) && taskInfo4.getWindowingMode() == 1) {
                            ((DesktopTasksController) desktopTaskChangeListener4.desktopTasksController.get()).mainExecutor.execute(new Runnable() { // from class: com.android.wm.shell.desktopmode.DesktopTaskChangeListener$onTaskChanging$1
                                @Override // java.lang.Runnable
                                public final void run() throws Resources.NotFoundException {
                                    DesktopTasksController.exitDefaultDisplayDesktopWindowing$default((DesktopTasksController) desktopTaskChangeListener4.desktopTasksController.get(), taskInfo4, 2);
                                }
                            });
                            break;
                        }
                    } else if (taskInfo4.getWindowingMode() == 5 || desktopTaskChangeListener4.allowInDesk(taskInfo4)) {
                        DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                        int i6 = taskInfo4.displayId;
                        companion2.getClass();
                        if (DesktopStateImpl.Companion.inDesktopWindowing(i6)) {
                            profile4.addTask(taskInfo4.displayId, taskInfo4.taskId, taskInfo4.isVisible);
                            break;
                        }
                    }
                }
                break;
            case 4:
                ActivityManager.RunningTaskInfo taskInfo5 = ((TransitionInfo.Change) obj2).getTaskInfo();
                DesktopTaskChangeListener desktopTaskChangeListener5 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener5.getClass();
                DesktopRepository profile5 = desktopTaskChangeListener5.desktopUserRepositories.getProfile(taskInfo5.userId);
                if (profile5.isActiveTask(taskInfo5.taskId)) {
                    DesktopTaskChangeListener.logD("onTaskMovingToBack for taskId=%d, displayId=%d", Integer.valueOf(taskInfo5.taskId), Integer.valueOf(taskInfo5.displayId));
                    profile5.updateTask(taskInfo5.displayId, taskInfo5.taskId, false);
                    break;
                }
                break;
            default:
                IBinder iBinder = (IBinder) obj2;
                DesksTransitionObserver desksTransitionObserver = (DesksTransitionObserver) obj;
                desksTransitionObserver.getClass();
                if (DesktopExperienceFlags.ENABLE_MULTIPLE_DESKTOPS_BACKEND.isTrue() && (set = (Set) desksTransitionObserver.deskTransitions.remove(iBinder)) != null) {
                    for (DeskTransition deskTransition : set) {
                        if (deskTransition instanceof DeskTransition.DeactivateDesk) {
                            desksTransitionObserver.handleDeactivateDeskTransition(null, (DeskTransition.DeactivateDesk) deskTransition);
                        } else {
                            ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
                            SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesksTransitionObserver", new Object[]{deskTransition});
                            ProtoLog.w(shellProtoLogGroup, "%s: Unexpected desk transition finished without being handled: %s", spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
                        }
                    }
                    break;
                }
                break;
        }
    }
}
