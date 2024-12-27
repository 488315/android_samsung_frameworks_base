package com.android.server.inputmethod;

import android.os.DeadObjectException;
import android.os.Handler;
import android.os.RemoteException;
import android.util.Slog;
import android.view.InputChannel;

import com.android.internal.inputmethod.IInputMethodClient;
import com.android.internal.inputmethod.InputBindResult;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class IInputMethodClientInvoker {
    public final Handler mHandler;
    public final boolean mIsProxy;
    public final IInputMethodClient mTarget;

    public IInputMethodClientInvoker(
            IInputMethodClient iInputMethodClient, boolean z, Handler handler) {
        this.mTarget = iInputMethodClient;
        this.mIsProxy = z;
        this.mHandler = handler;
    }

    public static void logRemoteException(RemoteException remoteException) {
        if (remoteException instanceof DeadObjectException) {
            return;
        }
        StringBuilder sb = new StringBuilder("IPC failed at IInputMethodClientInvoker#");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        sb.append(
                stackTrace.length <= 4 ? "<bottom of call stack>" : stackTrace[4].getMethodName());
        Slog.w("InputMethodManagerService", sb.toString(), remoteException);
    }

    public final void onBindAccessibilityServiceInternal(InputBindResult inputBindResult, int i) {
        InputChannel inputChannel;
        boolean z = this.mIsProxy;
        try {
            try {
                this.mTarget.onBindAccessibilityService(inputBindResult, i);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            } catch (RemoteException e) {
                logRemoteException(e);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            }
            inputChannel.dispose();
        } catch (Throwable th) {
            InputChannel inputChannel2 = inputBindResult.channel;
            if (inputChannel2 != null && z) {
                inputChannel2.dispose();
            }
            throw th;
        }
    }

    public final void onBindMethodInternal(InputBindResult inputBindResult) {
        InputChannel inputChannel;
        boolean z = this.mIsProxy;
        try {
            try {
                this.mTarget.onBindMethod(inputBindResult);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            } catch (RemoteException e) {
                logRemoteException(e);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            }
            inputChannel.dispose();
        } catch (Throwable th) {
            InputChannel inputChannel2 = inputBindResult.channel;
            if (inputChannel2 != null && z) {
                inputChannel2.dispose();
            }
            throw th;
        }
    }

    public final void onStartInputResultInternal(InputBindResult inputBindResult, int i) {
        InputChannel inputChannel;
        boolean z = this.mIsProxy;
        try {
            try {
                this.mTarget.onStartInputResult(inputBindResult, i);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            } catch (RemoteException e) {
                logRemoteException(e);
                inputChannel = inputBindResult.channel;
                if (inputChannel == null || !z) {
                    return;
                }
            }
            inputChannel.dispose();
        } catch (Throwable th) {
            InputChannel inputChannel2 = inputBindResult.channel;
            if (inputChannel2 != null && z) {
                inputChannel2.dispose();
            }
            throw th;
        }
    }

    public final void setActive(boolean z, boolean z2) {
        if (!this.mIsProxy) {
            this.mHandler.post(
                    new IInputMethodClientInvoker$$ExternalSyntheticLambda2(this, z, z2, 1));
        } else {
            try {
                this.mTarget.setActive(z, z2);
            } catch (RemoteException e) {
                logRemoteException(e);
            }
        }
    }

    public final void setImeTraceEnabled(boolean z) {
        if (!this.mIsProxy) {
            this.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda4(this, z, 3));
        } else {
            try {
                this.mTarget.setImeTraceEnabled(z);
            } catch (RemoteException e) {
                logRemoteException(e);
            }
        }
    }

    public final void setImeVisibility(boolean z) {
        if (!this.mIsProxy) {
            this.mHandler.post(new IInputMethodClientInvoker$$ExternalSyntheticLambda4(this, z, 0));
        } else {
            try {
                this.mTarget.setImeVisibility(z);
            } catch (RemoteException e) {
                logRemoteException(e);
            }
        }
    }
}
