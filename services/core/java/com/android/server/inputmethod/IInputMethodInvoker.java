package com.android.server.inputmethod;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.inputmethod.IInputMethod;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IInputMethodInvoker {
    public final IInputMethod mTarget;

    public IInputMethodInvoker(IInputMethod iInputMethod) {
        this.mTarget = iInputMethod;
    }

    public static void logRemoteException(RemoteException remoteException) {
        if (remoteException instanceof DeadObjectException) {
            return;
        }
        StringBuilder sb = new StringBuilder("IPC failed at IInputMethodInvoker#");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        sb.append(
                stackTrace.length <= 4 ? "<bottom of call stack>" : stackTrace[4].getMethodName());
        Slog.w("InputMethodManagerService", sb.toString(), remoteException);
    }
}
