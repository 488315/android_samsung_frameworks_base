package com.android.systemui;

import android.os.DeadObjectException;
import android.os.Parcel;
import android.util.Log;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIcon$Stub;
import com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener$Stub$Proxy;
import com.android.systemui.statusbar.phone.StatusBarSignalPolicy;
import java.util.ArrayList;
import java.util.List;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.internal.MainDispatcherLoader;
import kotlinx.coroutines.scheduling.DefaultScheduler;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class TaskbarIndicatorController extends ITaskbarStatusIcon$Stub {
    public final String TAG = "TaskbarIndicatorController";
    public List mDesktopStatusBarIconCallback;
    public ITaskbarStatusIconListener$Stub$Proxy taskbarStatusIconListener;

    public final void requestStatusIcons() {
        DefaultScheduler defaultScheduler = Dispatchers.Default;
        BuildersKt.launch$default(CoroutineScopeKt.CoroutineScope(MainDispatcherLoader.dispatcher), null, null, new TaskbarIndicatorController$requestStatusIcons$1(this, null), 3);
    }

    public final void setDesktopStatusBarIconCallback(StatusBarSignalPolicy.DesktopCallback desktopCallback) {
        if (this.mDesktopStatusBarIconCallback == null) {
            this.mDesktopStatusBarIconCallback = new ArrayList();
        }
        if (desktopCallback != null) {
            List list = this.mDesktopStatusBarIconCallback;
            list.getClass();
            list.add(desktopCallback);
        }
    }

    public final void setWifiIcon(boolean z, int i, int i2) {
        try {
            ITaskbarStatusIconListener$Stub$Proxy iTaskbarStatusIconListener$Stub$Proxy = this.taskbarStatusIconListener;
            if (iTaskbarStatusIconListener$Stub$Proxy != null) {
                Parcel obtain = Parcel.obtain(iTaskbarStatusIconListener$Stub$Proxy.mRemote);
                try {
                    obtain.writeInterfaceToken("com.android.systemui.shared.launcher.dex.ITaskbarStatusIconListener");
                    obtain.writeBoolean(z);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    iTaskbarStatusIconListener$Stub$Proxy.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        } catch (DeadObjectException unused) {
            Log.e(this.TAG, "setWifiIcon taskbarStatusIconListener was dead, but non-null");
        }
    }
}
