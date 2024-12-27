package android.security;

import android.os.RemoteException;

@FunctionalInterface
interface CheckedRemoteRequest<R> {
    R execute() throws RemoteException;
}
