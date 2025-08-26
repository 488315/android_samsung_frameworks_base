package com.android.wm.shell.dagger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.rune.CoreRune;

/* loaded from: classes3.dex */
public abstract class WMShellConcurrencyModule {
    public static HandlerThread createShellMainThread() {
        final HandlerThread handlerThread = new HandlerThread("wmshell.main", -10);
        if (CoreRune.SYSPERF_VI_BOOST) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.dagger.WMShellConcurrencyModule.1
                @Override // java.lang.Runnable
                public final void run() {
                    ICustomFrequencyManager iCustomFrequencyManagerAsInterface;
                    IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                    if (service == null || (iCustomFrequencyManagerAsInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                        return;
                    }
                    try {
                        iCustomFrequencyManagerAsInterface.sendTid(Process.myPid(), handlerThread.getThreadId(), 4);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 10000L);
        }
        return handlerThread;
    }
}
