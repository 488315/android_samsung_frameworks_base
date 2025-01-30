package com.android.wm.shell.dagger;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.wm.shell.common.HandlerExecutor;
import com.samsung.android.rune.CoreRune;
import javax.inject.Provider;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule_ProvideShellAnimationExecutorFactory implements Provider {
    public static HandlerExecutor provideShellAnimationExecutor() {
        final HandlerThread handlerThread = new HandlerThread("wmshell.anim", -10);
        handlerThread.start();
        if (CoreRune.SYSPERF_VI_BOOST) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.dagger.WMShellConcurrencyModule$2
                @Override // java.lang.Runnable
                public final void run() {
                    ICustomFrequencyManager asInterface;
                    IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                    if (service == null || (asInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                        return;
                    }
                    try {
                        asInterface.sendTid(Process.myPid(), handlerThread.getThreadId(), 4);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 10000L);
        }
        if (Build.IS_DEBUGGABLE) {
            handlerThread.getLooper().setTraceTag(32L);
            handlerThread.getLooper().setSlowLogThresholdMs(30L, 30L);
        }
        return new HandlerExecutor(Handler.createAsync(handlerThread.getLooper()));
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellAnimationExecutor();
    }
}
