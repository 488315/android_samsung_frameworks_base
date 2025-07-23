package com.samsung.android.game;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IGameManagerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.game.IGameManagerCallback";

    public static class Default implements IGameManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.game.IGameManagerCallback
        public void onGameFocusIn(String str) throws RemoteException {
        }

        @Override // com.samsung.android.game.IGameManagerCallback
        public void onGameFocusOut(String str) throws RemoteException {
        }
    }

    void onGameFocusIn(String str) throws RemoteException;

    void onGameFocusOut(String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IGameManagerCallback {
        static final int TRANSACTION_onGameFocusIn = 1;
        static final int TRANSACTION_onGameFocusOut = 2;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 1;
        }

        public Stub() {
            attachInterface(this, IGameManagerCallback.DESCRIPTOR);
        }

        public static IGameManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IGameManagerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IGameManagerCallback)) {
                return (IGameManagerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i == 1) {
                return "onGameFocusIn";
            }
            if (i != 2) {
                return null;
            }
            return "onGameFocusOut";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IGameManagerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IGameManagerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onGameFocusIn(readString);
            } else if (i == 2) {
                String readString2 = parcel.readString();
                parcel.enforceNoDataAvail();
                onGameFocusOut(readString2);
            } else {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            return true;
        }

        private static class Proxy implements IGameManagerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IGameManagerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.game.IGameManagerCallback
            public void onGameFocusIn(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGameManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // com.samsung.android.game.IGameManagerCallback
            public void onGameFocusOut(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IGameManagerCallback.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(2, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
