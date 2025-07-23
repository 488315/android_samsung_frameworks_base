package com.android.wm.shell.desktopmode.minimize;

import android.app.ActivityManager;
import android.os.IBinder;
import android.view.SurfaceControl;
import android.window.RemoteTransition;
import android.window.TransitionInfo;
import android.window.TransitionRequestInfo;
import android.window.WindowContainerTransaction;
import com.android.wm.shell.RootTaskDisplayAreaOrganizer;
import com.android.wm.shell.common.ShellExecutor;
import com.android.wm.shell.shared.TransitionUtil;
import com.android.wm.shell.transition.OneShotRemoteHandler;
import com.android.wm.shell.transition.Transitions;
import java.util.Iterator;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopWindowLimitRemoteHandler implements Transitions.TransitionHandler {
    public final OneShotRemoteHandler oneShotRemoteHandler;
    public final RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer;
    public final int taskIdToMinimize;
    public IBinder transition;

    public DesktopWindowLimitRemoteHandler(ShellExecutor shellExecutor, RootTaskDisplayAreaOrganizer rootTaskDisplayAreaOrganizer, RemoteTransition remoteTransition, int i) {
        this.rootTaskDisplayAreaOrganizer = rootTaskDisplayAreaOrganizer;
        this.taskIdToMinimize = i;
        this.oneShotRemoteHandler = new OneShotRemoteHandler(shellExecutor, remoteTransition);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final WindowContainerTransaction handleRequest(IBinder iBinder, TransitionRequestInfo transitionRequestInfo) {
        this.transition = iBinder;
        return this.oneShotRemoteHandler.handleRequest(iBinder, transitionRequestInfo);
    }

    @Override // com.android.wm.shell.transition.Transitions.TransitionHandler
    public final boolean startAnimation(IBinder iBinder, TransitionInfo transitionInfo, SurfaceControl.Transaction transaction, SurfaceControl.Transaction transaction2, Transitions.TransitionFinishCallback transitionFinishCallback) {
        Object obj;
        if (!Intrinsics.areEqual(iBinder, this.transition)) {
            return false;
        }
        Iterator it = transitionInfo.getChanges().iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            }
            obj = it.next();
            TransitionInfo.Change change = (TransitionInfo.Change) obj;
            ActivityManager.RunningTaskInfo taskInfo = change.getTaskInfo();
            if (taskInfo != null && taskInfo.taskId == this.taskIdToMinimize && change.getMode() == 4) {
                break;
            }
        }
        TransitionInfo.Change change2 = (TransitionInfo.Change) obj;
        if (change2 == null) {
            return false;
        }
        ActivityManager.RunningTaskInfo taskInfo2 = change2.getTaskInfo();
        if (taskInfo2 != null && taskInfo2.isFreeform() && TransitionUtil.isOpeningMode(transitionInfo.getType())) {
            this.rootTaskDisplayAreaOrganizer.reparentToDisplayArea(taskInfo2.displayId, transaction, change2.getLeash());
        }
        return this.oneShotRemoteHandler.startAnimation(iBinder, transitionInfo, transaction, transaction2, transitionFinishCallback);
    }
}
