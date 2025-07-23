package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.freeform.TaskChangeListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import com.android.wm.shell.shared.desktopmode.DesktopModeCompatPolicy;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTaskChangeListener implements TaskChangeListener {
    public final DesktopModeCompatPolicy desktopModeCompatPolicy;
    public final DesktopUserRepositories desktopUserRepositories;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DesktopTaskChangeListener(DesktopModeCompatPolicy desktopModeCompatPolicy, DesktopUserRepositories desktopUserRepositories) {
        this.desktopModeCompatPolicy = desktopModeCompatPolicy;
        this.desktopUserRepositories = desktopUserRepositories;
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTaskChangeListener", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }

    public final boolean allowInDesk(ActivityManager.RunningTaskInfo runningTaskInfo) {
        if (this.desktopModeCompatPolicy.isTopActivityExemptFromDesktopWindowing(runningTaskInfo)) {
            return this.desktopUserRepositories.getProfile(runningTaskInfo.userId).getAllDeskIds().contains(Integer.valueOf(runningTaskInfo.parentTaskId));
        }
        return false;
    }
}
