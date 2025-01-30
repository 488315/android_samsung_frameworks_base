package android.security;

import android.p009os.RemoteException;

@FunctionalInterface
/* loaded from: classes3.dex */
interface CheckedRemoteRequest<R> {
    R execute() throws RemoteException;
}
