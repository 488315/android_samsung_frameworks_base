package android.hardware.location;

import android.hardware.location.IContextHubTransactionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubClient extends IInterface {

    public static class Default implements IContextHubClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.hardware.location.IContextHubClient
        public void callbackFinished() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public void close() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int getId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public void reliableMessageCallbackFinished(int i, byte b) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public int sendReliableMessageToNanoApp(NanoAppMessage nanoAppMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
            return 0;
        }
    }

    void callbackFinished() throws RemoteException;

    void close() throws RemoteException;

    int getId() throws RemoteException;

    void reliableMessageCallbackFinished(int i, byte b) throws RemoteException;

    int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException;

    int sendReliableMessageToNanoApp(NanoAppMessage nanoAppMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException;

    public static abstract class Stub extends Binder implements IContextHubClient {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubClient";
        static final int TRANSACTION_callbackFinished = 4;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_getId = 3;
        static final int TRANSACTION_reliableMessageCallbackFinished = 5;
        static final int TRANSACTION_sendMessageToNanoApp = 1;
        static final int TRANSACTION_sendReliableMessageToNanoApp = 6;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IContextHubClient)) {
                return (IContextHubClient) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "sendMessageToNanoApp";
                case 2:
                    return "close";
                case 3:
                    return "getId";
                case 4:
                    return "callbackFinished";
                case 5:
                    return "reliableMessageCallbackFinished";
                case 6:
                    return "sendReliableMessageToNanoApp";
                default:
                    return null;
            }
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
            switch (i) {
                case 1:
                    NanoAppMessage nanoAppMessage = (NanoAppMessage) parcel.readTypedObject(NanoAppMessage.CREATOR);
                    parcel.enforceNoDataAvail();
                    int sendMessageToNanoApp = sendMessageToNanoApp(nanoAppMessage);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendMessageToNanoApp);
                    return true;
                case 2:
                    close();
                    parcel2.writeNoException();
                    return true;
                case 3:
                    int id = getId();
                    parcel2.writeNoException();
                    parcel2.writeInt(id);
                    return true;
                case 4:
                    callbackFinished();
                    parcel2.writeNoException();
                    return true;
                case 5:
                    int readInt = parcel.readInt();
                    byte readByte = parcel.readByte();
                    parcel.enforceNoDataAvail();
                    reliableMessageCallbackFinished(readInt, readByte);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    NanoAppMessage nanoAppMessage2 = (NanoAppMessage) parcel.readTypedObject(NanoAppMessage.CREATOR);
                    IContextHubTransactionCallback asInterface = IContextHubTransactionCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int sendReliableMessageToNanoApp = sendReliableMessageToNanoApp(nanoAppMessage2, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeInt(sendReliableMessageToNanoApp);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements IContextHubClient {
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

            @Override // android.hardware.location.IContextHubClient
            public int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nanoAppMessage, 0);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void close() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int getId() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void callbackFinished() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void reliableMessageCallbackFinished(int i, byte b) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeByte(b);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int sendReliableMessageToNanoApp(NanoAppMessage nanoAppMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(nanoAppMessage, 0);
                    obtain.writeStrongInterface(iContextHubTransactionCallback);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
