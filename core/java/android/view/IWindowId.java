package android.view;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IWindowId extends IInterface {
    boolean isFocused() throws RemoteException;

    void registerFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException;

    void unregisterFocusObserver(IWindowFocusObserver iWindowFocusObserver) throws RemoteException;

    public static class Default implements IWindowId {
        @Override // android.view.IWindowId
        public void registerFocusObserver(IWindowFocusObserver observer) throws RemoteException {}

        @Override // android.view.IWindowId
        public void unregisterFocusObserver(IWindowFocusObserver observer) throws RemoteException {}

        @Override // android.view.IWindowId
        public boolean isFocused() throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IWindowId {
        public static final String DESCRIPTOR = "android.view.IWindowId";
        static final int TRANSACTION_isFocused = 3;
        static final int TRANSACTION_registerFocusObserver = 1;
        static final int TRANSACTION_unregisterFocusObserver = 2;

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IWindowId asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin != null && (iin instanceof IWindowId)) {
                return (IWindowId) iin;
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
                    return "registerFocusObserver";
                case 2:
                    return "unregisterFocusObserver";
                case 3:
                    return "isFocused";
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
                data.enforceInterface(DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IWindowFocusObserver _arg0 =
                            IWindowFocusObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    registerFocusObserver(_arg0);
                    reply.writeNoException();
                    return true;
                case 2:
                    IWindowFocusObserver _arg02 =
                            IWindowFocusObserver.Stub.asInterface(data.readStrongBinder());
                    data.enforceNoDataAvail();
                    unregisterFocusObserver(_arg02);
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result = isFocused();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IWindowId {
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

            @Override // android.view.IWindowId
            public void registerFocusObserver(IWindowFocusObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowId
            public void unregisterFocusObserver(IWindowFocusObserver observer)
                    throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongInterface(observer);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // android.view.IWindowId
            public boolean isFocused() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 2;
        }
    }
}
