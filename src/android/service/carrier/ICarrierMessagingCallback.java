package android.service.carrier;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes3.dex */
public interface ICarrierMessagingCallback extends IInterface {

    public static class Default implements ICarrierMessagingCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onDownloadMmsComplete(int i) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onFilterComplete(int i) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMmsComplete(int i, byte[] bArr) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendMultipartSmsComplete(int i, int[] iArr) throws RemoteException {
        }

        @Override // android.service.carrier.ICarrierMessagingCallback
        public void onSendSmsComplete(int i, int i2) throws RemoteException {
        }
    }

    void onDownloadMmsComplete(int i) throws RemoteException;

    void onFilterComplete(int i) throws RemoteException;

    void onSendMmsComplete(int i, byte[] bArr) throws RemoteException;

    void onSendMultipartSmsComplete(int i, int[] iArr) throws RemoteException;

    void onSendSmsComplete(int i, int i2) throws RemoteException;

    public static abstract class Stub extends Binder implements ICarrierMessagingCallback {
        public static final String DESCRIPTOR = "android.service.carrier.ICarrierMessagingCallback";
        static final int TRANSACTION_onDownloadMmsComplete = 5;
        static final int TRANSACTION_onFilterComplete = 1;
        static final int TRANSACTION_onSendMmsComplete = 4;
        static final int TRANSACTION_onSendMultipartSmsComplete = 3;
        static final int TRANSACTION_onSendSmsComplete = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 4;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ICarrierMessagingCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICarrierMessagingCallback)) {
                return (ICarrierMessagingCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onFilterComplete";
            }
            if (i == 2) {
                return "onSendSmsComplete";
            }
            if (i == 3) {
                return "onSendMultipartSmsComplete";
            }
            if (i == 4) {
                return "onSendMmsComplete";
            }
            if (i != 5) {
                return null;
            }
            return "onDownloadMmsComplete";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onFilterComplete(readInt);
            } else if (i == 2) {
                int readInt2 = parcel.readInt();
                int readInt3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSendSmsComplete(readInt2, readInt3);
            } else if (i == 3) {
                int readInt4 = parcel.readInt();
                int[] createIntArray = parcel.createIntArray();
                parcel.enforceNoDataAvail();
                onSendMultipartSmsComplete(readInt4, createIntArray);
            } else if (i == 4) {
                int readInt5 = parcel.readInt();
                byte[] createByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                onSendMmsComplete(readInt5, createByteArray);
            } else if (i == 5) {
                int readInt6 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onDownloadMmsComplete(readInt6);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements ICarrierMessagingCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.service.carrier.ICarrierMessagingCallback
            public void onFilterComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingCallback
            public void onSendSmsComplete(int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingCallback
            public void onSendMultipartSmsComplete(int i, int[] iArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeIntArray(iArr);
                    this.mRemote.transact(3, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingCallback
            public void onSendMmsComplete(int i, byte[] bArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByteArray(bArr);
                    this.mRemote.transact(4, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.service.carrier.ICarrierMessagingCallback
            public void onDownloadMmsComplete(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(5, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
