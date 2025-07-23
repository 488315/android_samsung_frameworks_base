package com.android.wm.shell.desktopmode;

import android.os.Parcel;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1 {
    public final /* synthetic */ DesktopTasksController.IDesktopModeImpl this$0;

    public DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1(DesktopTasksController.IDesktopModeImpl iDesktopModeImpl) {
        this.this$0 = iDesktopModeImpl;
    }

    public final void onTaskbarCornerRoundingUpdate(final boolean z) {
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onTaskbarCornerRoundingUpdate doesAnyTaskRequireTaskbarRounding=%s", new Object[]{Boolean.valueOf(z)});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$taskbarDesktopTaskListener$1$onTaskbarCornerRoundingUpdate$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                boolean z2 = z;
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    obtain.writeBoolean(z2);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        });
    }
}
