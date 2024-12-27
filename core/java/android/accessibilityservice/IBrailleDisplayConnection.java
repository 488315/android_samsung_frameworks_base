package android.accessibilityservice;

import android.media.MediaMetrics;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IBrailleDisplayConnection extends IInterface {
    public static final String DESCRIPTOR =
            "android.accessibilityservice.IBrailleDisplayConnection";

    void disconnect() throws RemoteException;

    void write(byte[] bArr) throws RemoteException;

    public static class Default implements IBrailleDisplayConnection {
        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void disconnect() throws RemoteException {}

        @Override // android.accessibilityservice.IBrailleDisplayConnection
        public void write(byte[] output) throws RemoteException {}

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IBrailleDisplayConnection {
        static final int TRANSACTION_disconnect = 1;
        static final int TRANSACTION_write = 2;

        public Stub() {
            attachInterface(this, IBrailleDisplayConnection.DESCRIPTOR);
        }

        public static IBrailleDisplayConnection asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBrailleDisplayConnection.DESCRIPTOR);
            if (iin != null && (iin instanceof IBrailleDisplayConnection)) {
                return (IBrailleDisplayConnection) iin;
            }
            return new Proxy(obj);
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        public static String getDefaultTransactionName(int transactionCode) {
            switch (transactionCode) {
                case 1:
                    return MediaMetrics.Value.DISCONNECT;
                case 2:
                    return "write";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IBrailleDisplayConnection.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBrailleDisplayConnection.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    disconnect();
                    return true;
                case 2:
                    byte[] _arg0 = data.createByteArray();
                    data.enforceNoDataAvail();
                    write(_arg0);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBrailleDisplayConnection {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
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
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayConnection.DESCRIPTOR);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayConnection
            public void write(byte[] output) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayConnection.DESCRIPTOR);
                    _data.writeByteArray(output);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }
    }
}
