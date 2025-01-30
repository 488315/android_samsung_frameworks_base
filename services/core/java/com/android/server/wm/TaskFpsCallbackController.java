package com.android.server.wm;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.window.ITaskFpsCallback;
import java.util.HashMap;

/* loaded from: classes3.dex */
final class TaskFpsCallbackController {
    public final Context mContext;
    public final HashMap mTaskFpsCallbacks = new HashMap();
    public final HashMap mDeathRecipients = new HashMap();

    private static native long nativeRegister(ITaskFpsCallback iTaskFpsCallback, int i);

    private static native void nativeUnregister(long j);

    public TaskFpsCallbackController(Context context) {
        this.mContext = context;
    }

    public void registerListener(int i, final ITaskFpsCallback iTaskFpsCallback) {
        if (iTaskFpsCallback == null) {
            return;
        }
        IBinder asBinder = iTaskFpsCallback.asBinder();
        if (this.mTaskFpsCallbacks.containsKey(asBinder)) {
            return;
        }
        this.mTaskFpsCallbacks.put(asBinder, Long.valueOf(nativeRegister(iTaskFpsCallback, i)));
        IBinder.DeathRecipient deathRecipient = new IBinder.DeathRecipient() { // from class: com.android.server.wm.TaskFpsCallbackController$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                TaskFpsCallbackController.this.lambda$registerListener$0(iTaskFpsCallback);
            }
        };
        try {
            asBinder.linkToDeath(deathRecipient, 0);
            this.mDeathRecipients.put(asBinder, deathRecipient);
        } catch (RemoteException unused) {
        }
    }

    /* renamed from: unregisterListener, reason: merged with bridge method [inline-methods] */
    public void lambda$registerListener$0(ITaskFpsCallback iTaskFpsCallback) {
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
