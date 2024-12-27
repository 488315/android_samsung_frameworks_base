package com.samsung.android.game;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IGameManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.game.IGameManagerCallback";

    void onGameFocusIn(String str) throws RemoteException;

    void onGameFocusOut(String str) throws RemoteException;

    public static class Default implements IGameManagerCallback {
        @Override // com.samsung.android.game.IGameManagerCallback
        public void onGameFocusIn(String pkgName) throws RemoteException {
        }

        @Override // com.samsung.android.game.IGameManagerCallback
        public void onGameFocusOut(String pkgName) throws RemoteException {
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IGameManagerCallback {
        static final int TRANSACTION_onGameFocusIn = 1;
        static final int TRANSACTION_onGameFocusOut = 2;

        public Stub() {
            attachInterface(this, IGameManagerCallback.DESCRIPTOR);
        }

        public static IGameManagerCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(IGameManagerCallback.DESCRIPTOR);
            if (iin != null && (iin instanceof IGameManagerCallback)) {
                return (IGameManagerCallback) iin;
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
                    return "onGameFocusIn";
                case 2:
                    return "onGameFocusOut";
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
                data.enforceInterface(IGameManagerCallback.DESCRIPTOR);
            }
            if (code == 1598968902) {
                reply.writeString(IGameManagerCallback.DESCRIPTOR);
                return true;
            }
            switch (code) {
                case 1:
                    String _arg0 = data.readString();
                    data.enforceNoDataAvail();
                    onGameFocusIn(_arg0);
                    return true;
                case 2:
                    String _arg02 = data.readString();
                    data.enforceNoDataAvail();
                    onGameFocusOut(_arg02);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }

        private static class Proxy implements IGameManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameManagerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.game.IGameManagerCallback
            public void onGameFocusIn(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameManagerCallback.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(1, _data, null, 1);
                } finally {
                    _data.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerCallback
            public void onGameFocusOut(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain(asBinder());
                try {
                    _data.writeInterfaceToken(IGameManagerCallback.DESCRIPTOR);
                    _data.writeString(pkgName);
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
