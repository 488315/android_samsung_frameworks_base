package com.samsung.android.cover;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ICoverWindowStateListenerCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cover.ICoverWindowStateListenerCallback";

    public static class Default implements ICoverWindowStateListenerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cover.ICoverWindowStateListenerCallback
        public void onCoverAppCovered(boolean z) throws RemoteException {
        }
    }

    void onCoverAppCovered(boolean z) throws RemoteException;

    public static abstract class Stub extends Binder implements ICoverWindowStateListenerCallback {
        static final int TRANSACTION_onCoverAppCovered = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ICoverWindowStateListenerCallback.DESCRIPTOR);
        }

        public static ICoverWindowStateListenerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(ICoverWindowStateListenerCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof ICoverWindowStateListenerCallback)) {
                return (ICoverWindowStateListenerCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onCoverAppCovered";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ICoverWindowStateListenerCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ICoverWindowStateListenerCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                boolean readBoolean = parcel.readBoolean();
                parcel.enforceNoDataAvail();
                onCoverAppCovered(readBoolean);
                parcel2.writeNoException();
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ICoverWindowStateListenerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ICoverWindowStateListenerCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cover.ICoverWindowStateListenerCallback
            public void onCoverAppCovered(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(ICoverWindowStateListenerCallback.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }
    }
}
