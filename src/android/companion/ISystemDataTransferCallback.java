package android.companion;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface ISystemDataTransferCallback extends IInterface {
    public static final String DESCRIPTOR = "android.companion.ISystemDataTransferCallback";

    public static class Default implements ISystemDataTransferCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onError(String str) throws RemoteException {
        }

        @Override // android.companion.ISystemDataTransferCallback
        public void onResult() throws RemoteException {
        }
    }

    void onError(String str) throws RemoteException;

    void onResult() throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemDataTransferCallback {
        static final int TRANSACTION_onError = 2;
        static final int TRANSACTION_onResult = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, ISystemDataTransferCallback.DESCRIPTOR);
        }

        public static ISystemDataTransferCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ISystemDataTransferCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ISystemDataTransferCallback)) {
                return (ISystemDataTransferCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onResult";
            }
            if (i != 2) {
                return null;
            }
            return "onError";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISystemDataTransferCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemDataTransferCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                onResult();
            } else if (i == 2) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onError(readString);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ISystemDataTransferCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemDataTransferCallback.DESCRIPTOR;
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onResult() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISystemDataTransferCallback.DESCRIPTOR);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.companion.ISystemDataTransferCallback
            public void onError(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(ISystemDataTransferCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
