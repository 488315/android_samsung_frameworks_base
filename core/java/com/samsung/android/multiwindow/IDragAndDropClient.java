package com.samsung.android.multiwindow;

import android.graphics.Rect;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IDragAndDropClient extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.multiwindow.IDragAndDropClient";

    Rect getHiddenDropTargetArea() throws RemoteException;

    boolean getInitialDropTargetVisible() throws RemoteException;

    void onConnected(IBinder iBinder, int i) throws RemoteException;

    void onDisconnected() throws RemoteException;

    public static class Default implements IDragAndDropClient {
        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onConnected(IBinder serverProxy, int displayId) throws RemoteException {}

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public void onDisconnected() throws RemoteException {}

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public boolean getInitialDropTargetVisible() throws RemoteException {
            return false;
        }

        @Override // com.samsung.android.multiwindow.IDragAndDropClient
        public Rect getHiddenDropTargetArea() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public abstract static class Stub extends Binder implements IDragAndDropClient {
        static final int TRANSACTION_getHiddenDropTargetArea = 4;
        static final int TRANSACTION_getInitialDropTargetVisible = 3;
        static final int TRANSACTION_onConnected = 1;
        static final int TRANSACTION_onDisconnected = 2;

        public Stub() {
            attachInterface(this, IDragAndDropClient.DESCRIPTOR);
        }

        public static IDragAndDropClient asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IDragAndDropClient.DESCRIPTOR);
            if (iin != null && (iin instanceof IDragAndDropClient)) {
                return (IDragAndDropClient) iin;
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
                    return "onDisconnected";
                case 3:
                    return "getInitialDropTargetVisible";
                case 4:
                    return "getHiddenDropTargetArea";
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
                data.enforceInterface(IDragAndDropClient.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IDragAndDropClient.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    IBinder _arg0 = data.readStrongBinder();
                    int _arg1 = data.readInt();
                    data.enforceNoDataAvail();
                    onConnected(_arg0, _arg1);
                    reply.writeNoException();
                    return true;
                case 2:
                    onDisconnected();
                    reply.writeNoException();
                    return true;
                case 3:
                    boolean _result = getInitialDropTargetVisible();
                    reply.writeNoException();
                    reply.writeBoolean(_result);
                    return true;
                case 4:
                    Rect _result2 = getHiddenDropTargetArea();
                    reply.writeNoException();
                    reply.writeTypedObject(_result2, 1);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IDragAndDropClient {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IDragAndDropClient.DESCRIPTOR;
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onConnected(IBinder serverProxy, int displayId) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    _data.writeStrongBinder(serverProxy);
                    _data.writeInt(displayId);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public void onDisconnected() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public boolean getInitialDropTargetVisible() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    boolean _result = _reply.readBoolean();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.multiwindow.IDragAndDropClient
            public Rect getHiddenDropTargetArea() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(IDragAndDropClient.DESCRIPTOR);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    Rect _result = (Rect) _reply.readTypedObject(Rect.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
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
