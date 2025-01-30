package com.android.p038wm.shell.dagger;

import android.os.HandlerThread;
import android.os.IBinder;
import android.os.ICustomFrequencyManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class WMShellConcurrencyModule$1 implements Runnable {
    public final /* synthetic */ HandlerThread val$mainThread;

    public WMShellConcurrencyModule$1(HandlerThread handlerThread) {
        this.val$mainThread = handlerThread;
    }

    @Override // java.lang.Runnable
    public final void run() {
        ICustomFrequencyManager asInterface;
        IBinder service = ServiceManager.getService("CustomFrequencyManagerService");
        if (service == null || (asInterface = ICustomFrequencyManager.Stub.asInterface(service)) == null) {
            return;
        }
        try {
            asInterface.sendTid(Process.myPid(), this.val$mainThread.getThreadId(), 4);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
