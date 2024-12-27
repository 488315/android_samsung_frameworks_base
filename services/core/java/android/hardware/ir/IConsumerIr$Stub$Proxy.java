package android.hardware.ir;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

public final class IConsumerIr$Stub$Proxy implements IConsumerIr {
    public IBinder mRemote;

    @Override // android.os.IInterface
    public final IBinder asBinder() {
        return this.mRemote;
    }

    public final ConsumerIrFreqRange[] getCarrierFreqs() {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IConsumerIr.DESCRIPTOR);
            if (!this.mRemote.transact(1, obtain, obtain2, 0)) {
                throw new RemoteException("Method getCarrierFreqs is unimplemented.");
            }
            obtain2.readException();
            return (ConsumerIrFreqRange[]) obtain2.createTypedArray(ConsumerIrFreqRange.CREATOR);
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }

    public final void transmit(int i, int[] iArr) {
        Parcel obtain = Parcel.obtain(this.mRemote);
        Parcel obtain2 = Parcel.obtain();
        try {
            obtain.writeInterfaceToken(IConsumerIr.DESCRIPTOR);
            obtain.writeInt(i);
            obtain.writeIntArray(iArr);
            if (!this.mRemote.transact(2, obtain, obtain2, 0)) {
                throw new RemoteException("Method transmit is unimplemented.");
            }
            obtain2.readException();
        } finally {
            obtain2.recycle();
            obtain.recycle();
        }
    }
}
