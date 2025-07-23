package com.android.internal.statusbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes4.dex */
public interface IAddTileResultCallback extends IInterface {
    public static final String DESCRIPTOR = "com.android.internal.statusbar.IAddTileResultCallback";

    public static class Default implements IAddTileResultCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.android.internal.statusbar.IAddTileResultCallback
        public void onTileRequest(int i) throws RemoteException {
        }
    }

    void onTileRequest(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements IAddTileResultCallback {
        static final int TRANSACTION_onTileRequest = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IAddTileResultCallback.DESCRIPTOR);
        }

        public static IAddTileResultCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IAddTileResultCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IAddTileResultCallback)) {
                return (IAddTileResultCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onTileRequest";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAddTileResultCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAddTileResultCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                parcel.enforceNoDataAvail();
                onTileRequest(readInt);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IAddTileResultCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAddTileResultCallback.DESCRIPTOR;
            }

            @Override // com.android.internal.statusbar.IAddTileResultCallback
            public void onTileRequest(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IAddTileResultCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
