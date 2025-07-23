package android.os.instrumentation;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IOffsetCallback extends IInterface {
    public static final String DESCRIPTOR = "android.os.instrumentation.IOffsetCallback";

    public static class Default implements IOffsetCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.os.instrumentation.IOffsetCallback
        public void onResult(ExecutableMethodFileOffsets executableMethodFileOffsets) throws RemoteException {
        }
    }

    void onResult(ExecutableMethodFileOffsets executableMethodFileOffsets) throws RemoteException;

    public static abstract class Stub extends Binder implements IOffsetCallback {
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IOffsetCallback.DESCRIPTOR);
        }

        public static IOffsetCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IOffsetCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IOffsetCallback)) {
                return (IOffsetCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IOffsetCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IOffsetCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                ExecutableMethodFileOffsets executableMethodFileOffsets = (ExecutableMethodFileOffsets) parcel.readTypedObject(ExecutableMethodFileOffsets.CREATOR);
                parcel.enforceNoDataAvail();
                onResult(executableMethodFileOffsets);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IOffsetCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IOffsetCallback.DESCRIPTOR;
            }

            @Override // android.os.instrumentation.IOffsetCallback
            public void onResult(ExecutableMethodFileOffsets executableMethodFileOffsets) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IOffsetCallback.DESCRIPTOR);
                    obtain.writeTypedObject(executableMethodFileOffsets, 0);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
