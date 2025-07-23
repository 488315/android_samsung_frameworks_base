package android.security.apc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface IConfirmationCallback extends IInterface {
    public static final String DESCRIPTOR = "android.security.apc.IConfirmationCallback";

    public static class Default implements IConfirmationCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.security.apc.IConfirmationCallback
        public void onCompleted(int i, byte[] bArr) throws RemoteException {
        }
    }

    void onCompleted(int i, byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IConfirmationCallback {
        static final int TRANSACTION_onCompleted = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public Stub() {
            attachInterface(this, IConfirmationCallback.DESCRIPTOR);
        }

        public static IConfirmationCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IConfirmationCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IConfirmationCallback)) {
                return (IConfirmationCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IConfirmationCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IConfirmationCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onCompleted(readInt, createByteArray);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IConfirmationCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IConfirmationCallback.DESCRIPTOR;
            }

            @Override // android.security.apc.IConfirmationCallback
            public void onCompleted(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IConfirmationCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
