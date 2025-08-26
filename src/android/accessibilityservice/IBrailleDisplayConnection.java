package android.accessibilityservice;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBrailleDisplayConnection extends IInterface {
    public static final String DESCRIPTOR = "android.accessibilityservice.IBrailleDisplayConnection";

    public static class Default implements IBrailleDisplayConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void disconnect() throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void write(byte[] bArr) throws RemoteException {
        }
    }

    void disconnect() throws RemoteException;

    void write(byte[] bArr) throws RemoteException;

    public static abstract class Stub extends Binder implements IBrailleDisplayConnection {
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_write = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IBrailleDisplayConnection.DESCRIPTOR);
        }

        public static IBrailleDisplayConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IBrailleDisplayConnection.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof IBrailleDisplayConnection)) {
                return (IBrailleDisplayConnection) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return MediaMetrics.Value.DISCONNECT;
            }
            if (i != 2) {
                return null;
            }
            return "write";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IBrailleDisplayConnection.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IBrailleDisplayConnection.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                disconnect();
            } else if (i == 2) {
                byte[] bArrCreateByteArray = parcel.createByteArray();
                parcel.enforceNoDataAvail();
                write(bArrCreateByteArray);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IBrailleDisplayConnection {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBrailleDisplayConnection.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IBrailleDisplayConnection
            public void disconnect() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayConnection.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayConnection
            public void write(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(IBrailleDisplayConnection.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
