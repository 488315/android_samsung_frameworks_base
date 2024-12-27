package android.accessibilityservice;

import android.accessibilityservice.IBrailleDisplayConnection;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IBrailleDisplayController extends IInterface {
    public static final String DESCRIPTOR = "android.accessibilityservice.IBrailleDisplayController";

    void onConnected(IBrailleDisplayConnection iBrailleDisplayConnection, byte[] bArr) throws RemoteException;

    void onConnectionFailed(int i) throws RemoteException;

    void onDisconnected() throws RemoteException;

    void onInput(byte[] bArr) throws RemoteException;

    public static class Default implements IBrailleDisplayController {
        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnected(IBrailleDisplayConnection connection, byte[] hidDescriptor) throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onConnectionFailed(int error) throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onInput(byte[] input) throws RemoteException {
        }

        @Override // android.accessibilityservice.IBrailleDisplayController
        public void onDisconnected() throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IBrailleDisplayController {
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onConnectionFailed = 2;
        static final int TRANSACTION_onDisconnected = 4;
        static final int TRANSACTION_onInput = 3;

        public Stub() {
            attachInterface(this, IBrailleDisplayController.DESCRIPTOR);
        }

        public static IBrailleDisplayController asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IBrailleDisplayController.DESCRIPTOR);
            if (iin != null && (iin instanceof IBrailleDisplayController)) {
                return (IBrailleDisplayController) iin;
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
                    return "onConnected";
                case 2:
                    return "onConnectionFailed";
                case 3:
                    return "onInput";
                case 4:
                    return "onDisconnected";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(IBrailleDisplayController.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IBrailleDisplayController.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBrailleDisplayConnection _arg0 = IBrailleDisplayConnection.Stub.asInterface(data.readStrongBinder());
                    byte[] _arg1 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onConnected(_arg0, _arg1);
                    return true;
                case 2:
                    int _arg02 = data.readInt();
                    data.enforceNoDataAvail();
                    onConnectionFailed(_arg02);
                    return true;
                case 3:
                    byte[] _arg03 = data.createByteArray();
                    data.enforceNoDataAvail();
                    onInput(_arg03);
                    return true;
                case 4:
                    onDisconnected();
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IBrailleDisplayController {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IBrailleDisplayController.DESCRIPTOR;
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnected(IBrailleDisplayConnection connection, byte[] hidDescriptor) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    _data.writeStrongInterface(connection);
                    _data.writeByteArray(hidDescriptor);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onConnectionFailed(int error) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    _data.writeInt(error);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onInput(byte[] input) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    _data.writeByteArray(input);
                    this.mRemote.transact(3, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // android.accessibilityservice.IBrailleDisplayController
            public void onDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IBrailleDisplayController.DESCRIPTOR);
                    this.mRemote.transact(4, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 3;
        }
    }
}
