package android.os;

import android.util.AndroidException;

/* loaded from: classes3.dex */
public class RemoteException extends AndroidException {
    public RemoteException() {
    }

    public RemoteException(String str) {
        super(str);
    }

    public RemoteException(String str, Throwable th, boolean z, boolean z2) {
        super(str, th, z, z2);
    }

    public RemoteException(Throwable th) {
        this(th.getMessage(), th, true, false);
    }

    public RuntimeException rethrowAsRuntimeException() {
        throw new RuntimeException(this);
    }

    public RuntimeException rethrowFromSystemServer() {
        if (this instanceof DeadObjectException) {
            throw new DeadSystemRuntimeException();
        }
        throw new RuntimeException(this);
    }
}
