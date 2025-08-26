package com.android.wm.shell.dagger;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.android.wm.shell.common.HandlerExecutor;
import com.samsung.android.rune.CoreRune;
import dagger.internal.Provider;

/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideSplashScreenExecutorFactory implements Provider {
    public static HandlerExecutor provideSplashScreenExecutor() {
        HandlerThread handlerThread = new HandlerThread("wmshell.splashscreen", -10);
        handlerThread.start();
        if (CoreRune.SYSPERF_VI_BOOST) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.dagger.WMShellConcurrencyModule.3
                public final /* synthetic */ HandlerThread val$shellSplashscreenThread;

                public AnonymousClass3(HandlerThread handlerThread2) {
                    handlerThread = handlerThread2;
                }

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
        return new HandlerExecutor(handlerThread2.getThreadHandler());
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideSplashScreenExecutor();
    }
}
