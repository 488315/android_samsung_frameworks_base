package com.android.server.inputmethod;

import android.os.DeadObjectException;
import android.os.RemoteException;
import android.util.Slog;

import com.android.internal.inputmethod.IInputMethod;

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
