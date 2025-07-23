package com.android.wm.shell.recents;

import android.app.ActivityTaskManager;
import android.os.RemoteException;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellCommandHandler;
import java.io.PrintWriter;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class RecentsShellCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public final RecentTasksController recentTasksController;

    public RecentsShellCommandHandler(RecentTasksController recentTasksController) {
        this.recentTasksController = recentTasksController;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) {
        if (!Intrinsics.areEqual(strArr[0], "clearAll")) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
            return false;
        }
        try {
            this.recentTasksController.getClass();
            ActivityTaskManager.getService().removeAllVisibleRecentTasks();
            return true;
        } catch (RemoteException e) {
            printWriter.println("Exception while removing visible recent tasks:");
            e.printStackTrace(printWriter);
            return false;
        }
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        printWriter.println("    clearAll");
        printWriter.println("      Clears all visible recent tasks.");
    }
}
