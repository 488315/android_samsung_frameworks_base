package com.android.wm.shell.desktopmode;

import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* loaded from: classes3.dex */
public final class DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1 {
    public final /* synthetic */ DesktopTasksController.IDesktopModeImpl this$0;

    public DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1(DesktopTasksController.IDesktopModeImpl iDesktopModeImpl) {
        this.this$0 = iDesktopModeImpl;
    }

    public final void onDesktopDisabledFlagsChangedOnDefaultDisplay(final int i) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onDesktopDisabledFlagsChangedOnDefaultDisplay flags=0x%x", new Object[]{Integer.valueOf(i)});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$desktopDisabledFlagsListener$1$onDesktopDisabledFlagsChangedOnDefaultDisplay$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                ((IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj)).onDesktopDisabledFlagsChangedOnDefaultDisplay(i);
            }
        });
    }
}
