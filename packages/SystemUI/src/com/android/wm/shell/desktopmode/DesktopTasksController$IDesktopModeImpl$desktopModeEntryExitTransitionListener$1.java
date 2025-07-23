package com.android.wm.shell.desktopmode;

import android.os.Parcel;
import com.android.internal.protolog.ProtoLog;
import com.android.wm.shell.common.SingleInstanceRemoteListener;
import com.android.wm.shell.desktopmode.DesktopTasksController;
import com.android.wm.shell.protolog.ShellProtoLogGroup;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1 {
    public final /* synthetic */ DesktopTasksController.IDesktopModeImpl this$0;

    public DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1(DesktopTasksController.IDesktopModeImpl iDesktopModeImpl) {
        this.this$0 = iDesktopModeImpl;
    }

    public final void onEnterDesktopModeTransitionStarted() {
        final int i = 336;
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onEnterDesktopModeTransitionStarted transitionTime=%s", new Object[]{336});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1$onEnterDesktopModeTransitionStarted$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                int i2 = i;
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    obtain.writeInt(i2);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        });
    }

    public final void onExitDesktopModeTransitionStarted() {
        final int i = 336;
        ProtoLog.v(ShellProtoLogGroup.WM_SHELL_DESKTOP_MODE, "IDesktopModeImpl: onExitDesktopModeTransitionStarted transitionTime=%s", new Object[]{336});
        SingleInstanceRemoteListener singleInstanceRemoteListener = this.this$0.remoteListener;
        if (singleInstanceRemoteListener == null) {
            singleInstanceRemoteListener = null;
        }
        singleInstanceRemoteListener.call(new SingleInstanceRemoteListener.RemoteCall() { // from class: com.android.wm.shell.desktopmode.DesktopTasksController$IDesktopModeImpl$desktopModeEntryExitTransitionListener$1$onExitDesktopModeTransitionStarted$1
            @Override // com.android.wm.shell.common.SingleInstanceRemoteListener.RemoteCall
            public final void accept(Object obj) {
                int i2 = i;
                IDesktopTaskListener$Stub$Proxy iDesktopTaskListener$Stub$Proxy = (IDesktopTaskListener$Stub$Proxy) ((IDesktopTaskListener) obj);
                Parcel obtain = Parcel.obtain(iDesktopTaskListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.wm.shell.desktopmode.IDesktopTaskListener");
                    obtain.writeInt(i2);
                    iDesktopTaskListener$Stub$Proxy.mRemote.transact(6, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        });
    }
}
