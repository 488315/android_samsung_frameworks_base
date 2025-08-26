package com.android.wm.shell.desktopmode;

import android.app.ActivityManager;
import androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.freeform.TaskChangeListener;
import com.android.wm.shell.protolog.ShellProtoLogGroup;
import java.util.Optional;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.SpreadBuilder;

/* loaded from: classes3.dex */
public final class DesktopTaskChangeListener implements TaskChangeListener {
    public final Optional desktopTasksController;
    public final DesktopUserRepositories desktopUserRepositories;

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

    public DesktopTaskChangeListener(Optional<DesktopTasksController> optional, DesktopUserRepositories desktopUserRepositories) {
        this.desktopTasksController = optional;
        this.desktopUserRepositories = desktopUserRepositories;
    }

    public static void logD(String str, Object... objArr) {
        ShellProtoLogGroup shellProtoLogGroup = ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE;
        String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("%s: ", str);
        SpreadBuilder spreadBuilderM = DesktopDisplayEventHandler$$ExternalSyntheticOutline0.m(2, "DesktopTaskChangeListener", objArr);
        ProtoLog.d(shellProtoLogGroup, strM, spreadBuilderM.list.toArray(new Object[spreadBuilderM.list.size()]));
    }

    public final boolean allowInDesk(ActivityManager.RunningTaskInfo runningTaskInfo) {
        return runningTaskInfo.getWindowingMode() == 1 && this.desktopUserRepositories.getProfile(runningTaskInfo.userId).getAllDeskIds().contains(Integer.valueOf(runningTaskInfo.parentTaskId));
    }
}
