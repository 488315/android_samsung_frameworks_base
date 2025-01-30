package com.android.server.location.listeners;

import android.app.PendingIntent;
import android.util.Log;
import com.android.internal.listeners.ListenerExecutor;
import com.android.internal.util.ConcurrentUtils;

/* loaded from: classes2.dex */
public abstract class PendingIntentListenerRegistration extends RemovableListenerRegistration implements PendingIntent.CancelListener {
    public abstract PendingIntent getPendingIntentFromKey(Object obj);

    public PendingIntentListenerRegistration(Object obj) {
        super(ConcurrentUtils.DIRECT_EXECUTOR, obj);
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration
    public void onRegister() {
        super.onRegister();
        if (getPendingIntentFromKey(getKey()).addCancelListener(ConcurrentUtils.DIRECT_EXECUTOR, this)) {
            return;
        }
        remove();
    }

    @Override // com.android.server.location.listeners.RemovableListenerRegistration, com.android.server.location.listeners.ListenerRegistration
    public void onUnregister() {
        getPendingIntentFromKey(getKey()).removeCancelListener(this);
        super.onUnregister();
    }

    @Override // com.android.server.location.listeners.ListenerRegistration
    public void onOperationFailure(ListenerExecutor.ListenerOperation listenerOperation, Exception exc) {
        if (exc instanceof PendingIntent.CanceledException) {
            Log.w(getTag(), "registration " + this + " removed", exc);
            remove();
            return;
        }
        super.onOperationFailure(listenerOperation, exc);
    }

    public void onCanceled(PendingIntent pendingIntent) {
        if (Log.isLoggable(getTag(), 3)) {
            Log.d(getTag(), "pending intent registration " + this + " canceled");
        }
        remove();
    }
}
