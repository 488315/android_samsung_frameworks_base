package android.hardware.location;

import android.hardware.location.IContextHubTransactionCallback;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes2.dex */
public interface IContextHubClient extends IInterface {
    void callbackFinished() throws RemoteException;

    void close() throws RemoteException;

    int getId() throws RemoteException;

    void reliableMessageCallbackFinished(int i, byte b) throws RemoteException;

    int sendMessageToNanoApp(NanoAppMessage nanoAppMessage) throws RemoteException;

    int sendReliableMessageToNanoApp(NanoAppMessage nanoAppMessage, IContextHubTransactionCallback iContextHubTransactionCallback) throws RemoteException;

    public static class Default implements IContextHubClient {
        @Override // android.hardware.location.IContextHubClient
        public int sendMessageToNanoApp(NanoAppMessage message) throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public void close() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int getId() throws RemoteException {
            return 0;
        }

        @Override // android.hardware.location.IContextHubClient
        public void callbackFinished() throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public void reliableMessageCallbackFinished(int messageSequenceNumber, byte errorCode) throws RemoteException {
        }

        @Override // android.hardware.location.IContextHubClient
        public int sendReliableMessageToNanoApp(NanoAppMessage message, IContextHubTransactionCallback transactionCallback) throws RemoteException {
            return 0;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IContextHubClient {
        public static final String DESCRIPTOR = "android.hardware.location.IContextHubClient";
        static final int TRANSACTION_callbackFinished = 4;
        static final int TRANSACTION_close = 2;
        static final int TRANSACTION_getId = 3;
        static final int TRANSACTION_reliableMessageCallbackFinished = 5;
        static final int TRANSACTION_sendMessageToNanoApp = 1;
        static final int TRANSACTION_sendReliableMessageToNanoApp = 6;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IContextHubClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IContextHubClient)) {
                return (IContextHubClient) iin;
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
        public String getTransactionName(int transactionCode) {
            return getDefaultTransactionName(transactionCode);
        }

        @Override // android.os.Binder
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            if (code >= 1 && code <= 16777215) {
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    NanoAppMessage _arg0 = (NanoAppMessage) data.readTypedObject(NanoAppMessage.CREATOR);
                    data.enforceNoDataAvail();
                    int _result = sendMessageToNanoApp(_arg0);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                case 2:
                    close();
                    reply.writeNoException();
                    return true;
                case 3:
                    int _result2 = getId();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 4:
                    callbackFinished();
                    reply.writeNoException();
                    return true;
                case 5:
                    int _arg02 = data.readInt();
                    byte _arg1 = data.readByte();
                    data.enforceNoDataAvail();
                    reliableMessageCallbackFinished(_arg02, _arg1);
                    reply.writeNoException();
                    return true;
                case 6:
                    NanoAppMessage _arg03 = (NanoAppMessage) data.readTypedObject(NanoAppMessage.CREATOR);
                    IContextHubTransactionCallback _arg12 = IContextHubTransactionCallback.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    int _result3 = sendReliableMessageToNanoApp(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IContextHubClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.hardware.location.IContextHubClient
            public int sendMessageToNanoApp(NanoAppMessage message) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(message, 0);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void close() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int getId() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void callbackFinished() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public void reliableMessageCallbackFinished(int messageSequenceNumber, byte errorCode) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(messageSequenceNumber);
                    _data.writeByte(errorCode);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.hardware.location.IContextHubClient
            public int sendReliableMessageToNanoApp(NanoAppMessage message, IContextHubTransactionCallback transactionCallback) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedObject(message, 0);
                    _data.writeStrongInterface(transactionCallback);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 5;
        }
    }
}
