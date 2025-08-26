package com.android.wm.shell.desktopmode;

import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.pip.PipDesktopState;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopPipTransitionController {
    public final DesktopTasksController desktopTasksController;
    public final DesktopUserRepositories desktopUserRepositories;
    public final PipDesktopState pipDesktopState;

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
        String strConcat = "%s: ".concat(str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopPipTransitionController", objArr);
        ProtoLog.d(shellProtoLogGroup, strConcat, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }
}
