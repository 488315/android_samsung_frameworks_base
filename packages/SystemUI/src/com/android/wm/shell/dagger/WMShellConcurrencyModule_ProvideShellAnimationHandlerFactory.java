package com.android.wm.shell.dagger;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import com.samsung.android.rune.CoreRune;
import dagger.internal.Provider;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class WMShellConcurrencyModule_ProvideShellAnimationHandlerFactory implements Provider {
    public static Handler provideShellAnimationHandler() {
        HandlerThread handlerThread = new HandlerThread("wmshell.anim", -10);
        handlerThread.start();
        if (CoreRune.SYSPERF_VI_BOOST) {
            new Handler().postDelayed(new Runnable() { // from class: com.android.wm.shell.dagger.WMShellConcurrencyModule.2
                public final /* synthetic */ HandlerThread val$animThread;

                public AnonymousClass2(HandlerThread handlerThread2) {
                    r1 = handlerThread2;
                }

                @Override // java.lang.Runnable
                public final void run() {
                    ICustomFrequencyManager asInterface;
                    IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
                    if (service == null || (asInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
                        return;
                    }
                    try {
                        asInterface.sendTid(Process.myPid(), r1.getThreadId(), 4);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }, 10000L);
        }
        if (Build.IS_DEBUGGABLE) {
            handlerThread2.getLooper().setTraceTag(32L);
            handlerThread2.getLooper().setSlowLogThresholdMs(30L, 30L);
        }
        Handler createAsync = Handler.createAsync(handlerThread2.getLooper());
        createAsync.getClass();
        return createAsync;
    }

    @Override // javax.inject.Provider
    public final Object get() {
        return provideShellAnimationHandler();
    }
}
