package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverStateListenerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverStateListenerCallback";

    String getListenerInfo() throws RemoteException;

    void onCoverAttachStateChanged(boolean z) throws RemoteException;

    void onCoverSwitchStateChanged(boolean z) throws RemoteException;

    public static class Default implements ICoverStateListenerCallback {
        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public void onCoverSwitchStateChanged(boolean switchState) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public void onCoverAttachStateChanged(boolean attached) throws RemoteException {
        }

        @Override // com.samsung.android.cover.ICoverStateListenerCallback
        public String getListenerInfo() throws RemoteException {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICoverStateListenerCallback {
        static final int TRANSACTION_getListenerInfo = 3;
        static final int TRANSACTION_onCoverAttachStateChanged = 2;
        static final int TRANSACTION_onCoverSwitchStateChanged = 1;

        public Stub() {
            attachInterface(this, ICoverStateListenerCallback.DESCRIPTOR);
        }

        public static ICoverStateListenerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(ICoverStateListenerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof ICoverStateListenerCallback)) {
                return (ICoverStateListenerCallback) iin;
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
                    return "onCoverSwitchStateChanged";
                case 2:
                    return "onCoverAttachStateChanged";
                case 3:
                    return "getListenerInfo";
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
                data.enforceInterface(ICoverStateListenerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(ICoverStateListenerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    boolean _arg0 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onCoverSwitchStateChanged(_arg0);
                    return true;
                case 2:
                    boolean _arg02 = data.readBoolean();
                    data.enforceNoDataAvail();
                    onCoverAttachStateChanged(_arg02);
                    return true;
                case 3:
                    String _result = getListenerInfo();
                    reply.writeNoException();
                    reply.writeString(_result);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements ICoverStateListenerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverStateListenerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public void onCoverSwitchStateChanged(boolean switchState) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    _data.writeBoolean(switchState);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public void onCoverAttachStateChanged(boolean attached) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    _data.writeBoolean(attached);
                    this.mRemote.transact(2, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.cover.ICoverStateListenerCallback
            public String getListenerInfo() throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(ICoverStateListenerCallback.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
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
