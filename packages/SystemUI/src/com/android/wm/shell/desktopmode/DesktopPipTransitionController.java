package com.android.wm.shell.desktopmode;

import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopPipTransitionController {
    public final DesktopTasksController desktopTasksController;
    public final DesktopUserRepositories desktopUserRepositories;
    public final PipDesktopState pipDesktopState;

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

    public DesktopPipTransitionController(DesktopTasksController desktopTasksController, DesktopUserRepositories desktopUserRepositories, PipDesktopState pipDesktopState) {
        this.desktopTasksController = desktopTasksController;
        this.desktopUserRepositories = desktopUserRepositories;
        this.pipDesktopState = pipDesktopState;
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String concat = "%s: ".concat(str);
        SpreadBuilder m = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopPipTransitionController", objArr);
        ProtoLog.d(shellProtoLogGroup, concat, m.list.toArray(new Object[m.list.size()]));
    }
}
