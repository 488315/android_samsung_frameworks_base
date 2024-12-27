package android.hardware.authsecret;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IAuthSecret$Stub$Proxy implements IAuthSecret {
    public IBinder mRemote;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    @Override // android.hardware.authsecret.IAuthSecret
    public final void setPrimaryUserCredential(byte[] bArr) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        try {
            obtain.writeInterfaceToken(IAuthSecret.DESCRIPTOR);
            obtain.writeByteArray(bArr);
            if (this.mRemote.transact(1, obtain, null, 1)) {
            } else {
                throw new RemoteException("Method setPrimaryUserCredential is unimplemented.");
            }
        } finally {
            obtain.recycle();
        }
    }
}
