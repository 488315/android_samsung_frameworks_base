package com.android.server.wm;

import android.os.IBinder;
import android.os.RemoteException;
import android.window.ITaskFpsCallback;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
final class TaskFpsCallbackController {
    public final HashMap mTaskFpsCallbacks = new HashMap();
    public final HashMap mDeathRecipients = new HashMap();

    private static native long nativeRegister(ITaskFpsCallback iTaskFpsCallback, int i);

    private static native void nativeUnregister(long j);

    public final void registerListener(int i, final ITaskFpsCallback iTaskFpsCallback) {
        if (iTaskFpsCallback == null) {
            return;
        }
        IBinder asBinder = iTaskFpsCallback.asBinder();
        if (this.mTaskFpsCallbacks.containsKey(asBinder)) {
            return;
        }
        this.mTaskFpsCallbacks.put(asBinder, Long.valueOf(nativeRegister(iTaskFpsCallback, i)));
        IBinder.DeathRecipient deathRecipient =
                new IBinder
                        .DeathRecipient() { // from class:
                                            // com.android.server.wm.TaskFpsCallbackController$$ExternalSyntheticLambda0
                    @Override // android.os.IBinder.DeathRecipient
                    public final void binderDied() {
                        TaskFpsCallbackController.this.unregisterListener(iTaskFpsCallback);
                    }
                };
        try {
            asBinder.linkToDeath(deathRecipient, 0);
            this.mDeathRecipients.put(asBinder, deathRecipient);
        } catch (RemoteException unused) {
        }
    }

    public final void unregisterListener(ITaskFpsCallback iTaskFpsCallback) {
        if (iTaskFpsCallback == null) {
            return;
        }
        IBinder asBinder = iTaskFpsCallback.asBinder();
        if (this.mTaskFpsCallbacks.containsKey(asBinder)) {
            asBinder.unlinkToDeath((IBinder.DeathRecipient) this.mDeathRecipients.get(asBinder), 0);
            this.mDeathRecipients.remove(asBinder);
            nativeUnregister(((Long) this.mTaskFpsCallbacks.get(asBinder)).longValue());
            this.mTaskFpsCallbacks.remove(asBinder);
        }
    }
}
