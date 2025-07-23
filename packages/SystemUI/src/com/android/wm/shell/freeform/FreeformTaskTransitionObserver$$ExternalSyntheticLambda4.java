package com.android.wm.shell.freeform;

import android.app.ActivityManager;
import android.os.IBinder;
import android.window.DesktopExperienceFlags;
import android.window.DesktopModeFlags;
import android.window.TransitionInfo;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.desktopmode.DesktopDisplayEventHandler$$ExternalSyntheticOutline0;
import com.android.wm.shell.desktopmode.DesktopRepository;
import com.android.wm.shell.desktopmode.DesktopRepository$$ExternalSyntheticLambda4;
import com.android.wm.shell.desktopmode.DesktopTaskChangeListener;
import com.android.wm.shell.desktopmode.multidesks.DeskTransition;
import com.android.wm.shell.desktopmode.multidesks.DesksTransitionObserver;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopStateImpl;
import java.util.Set;
import java.util.function.Consumer;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
                        profile.desktopData.forAllDesks(new DesktopRepository$$ExternalSyntheticLambda4(taskInfo.taskId, profile));
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
                ActivityManager.RunningTaskInfo taskInfo2 = ((TransitionInfo.Change) obj2).getTaskInfo();
                DesktopTaskChangeListener desktopTaskChangeListener2 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener2.getClass();
                DesktopTaskChangeListener.logD("onTaskOpening for taskId=%d, displayId=%d", Integer.valueOf(taskInfo2.taskId), Integer.valueOf(taskInfo2.displayId));
                DesktopRepository profile2 = desktopTaskChangeListener2.desktopUserRepositories.getProfile(taskInfo2.userId);
                if (taskInfo2.getWindowingMode() != 5 && !desktopTaskChangeListener2.allowInDesk(taskInfo2) && profile2.isActiveTask(taskInfo2.taskId)) {
                    profile2.removeTask(taskInfo2.displayId, taskInfo2.taskId);
                    break;
                } else if ((taskInfo2.getWindowingMode() == 5 || desktopTaskChangeListener2.allowInDesk(taskInfo2)) && !profile2.isActiveTask(taskInfo2.taskId)) {
                    DesktopStateImpl.Companion companion = DesktopStateImpl.Companion;
                    int i2 = taskInfo2.displayId;
                    companion.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(i2)) {
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
                DesktopRepository profile3 = desktopTaskChangeListener3.desktopUserRepositories.getProfile(taskInfo3.userId);
                if (taskInfo3.getWindowingMode() != 5 && !desktopTaskChangeListener3.allowInDesk(taskInfo3) && profile3.isActiveTask(taskInfo3.taskId) && (displayIdForTask = profile3.getDisplayIdForTask(taskInfo3.taskId)) != null) {
                    profile3.removeTask(displayIdForTask.intValue(), taskInfo3.taskId);
                }
                if (taskInfo3.getWindowingMode() == 5 || desktopTaskChangeListener3.allowInDesk(taskInfo3)) {
                    DesktopStateImpl.Companion companion2 = DesktopStateImpl.Companion;
                    int i3 = taskInfo3.displayId;
                    companion2.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(i3)) {
                        profile3.addTask(taskInfo3.displayId, taskInfo3.taskId, taskInfo3.isVisible);
                        break;
                    }
                }
                break;
            case 3:
                ActivityManager.RunningTaskInfo taskInfo4 = ((TransitionInfo.Change) obj2).getTaskInfo();
                DesktopTaskChangeListener desktopTaskChangeListener4 = (DesktopTaskChangeListener) ((TaskChangeListener) obj);
                desktopTaskChangeListener4.getClass();
                DesktopTaskChangeListener.logD("onTaskChanging for taskId=%d, displayId=%d", Integer.valueOf(taskInfo4.taskId), Integer.valueOf(taskInfo4.displayId));
                DesktopRepository profile4 = desktopTaskChangeListener4.desktopUserRepositories.getProfile(taskInfo4.userId);
                if (profile4.getDefaultDeskId(taskInfo4.displayId) == null || profile4.getAllDeskIds().contains(Integer.valueOf(taskInfo4.taskId))) {
                    DesktopTaskChangeListener.logD("onTaskChanging skip for desk root task", new Object[0]);
                    break;
                } else if (taskInfo4.getWindowingMode() != 5 && !desktopTaskChangeListener4.allowInDesk(taskInfo4) && profile4.isActiveTask(taskInfo4.taskId)) {
                    profile4.removeTask(taskInfo4.displayId, taskInfo4.taskId);
                    break;
                } else if (taskInfo4.getWindowingMode() == 5 || desktopTaskChangeListener4.allowInDesk(taskInfo4)) {
                    DesktopStateImpl.Companion companion3 = DesktopStateImpl.Companion;
                    int i4 = taskInfo4.displayId;
                    companion3.getClass();
                    if (DesktopStateImpl.Companion.inDesktopWindowing(i4)) {
                        profile4.addTask(taskInfo4.displayId, taskInfo4.taskId, taskInfo4.isVisible);
                        break;
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
                            SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesksTransitionObserver", new Object[]{deskTransition});
                            ProtoLog.w(shellProtoLogGroup, "%s: Unexpected desk transition finished without being handled: %s", m.list.toArray(new Object[m.list.size()]));
                        }
                    }
                    break;
                }
                break;
        }
    }
}
