package vendor.samsung.hardware.biometrics.fingerprint;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ISehFingerprint$Stub$Proxy implements ISehFingerprint {
    public IBinder mRemote;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    @Override // vendor.samsung.hardware.biometrics.fingerprint.ISehFingerprint
    public final SehResult sehRequest(int i, int i2, int i3, byte[] bArr) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(ISehFingerprint.DESCRIPTOR);
            obtain.writeInt(i);
            obtain.writeInt(i2);
            obtain.writeInt(i3);
            obtain.writeByteArray(bArr);
            if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                throw new RemoteException("Method sehRequest is unimplemented.");
            }
            obtain2.readException();
            return (SehResult) obtain2.readTypedObject(SehResult.CREATOR);
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
