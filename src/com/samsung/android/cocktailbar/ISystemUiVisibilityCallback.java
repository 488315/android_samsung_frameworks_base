package com.samsung.android.cocktailbar;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface ISystemUiVisibilityCallback extends IInterface {
    public static final String DESCRIPTOR = "com.samsung.android.cocktailbar.ISystemUiVisibilityCallback";

    public static class Default implements ISystemUiVisibilityCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
        public void onSystemUiVisibilityChanged(int i) throws RemoteException {
        }
    }

    void onSystemUiVisibilityChanged(int i) throws RemoteException;

    public static abstract class Stub extends Binder implements ISystemUiVisibilityCallback {
        static final int TRANSACTION_onSystemUiVisibilityChanged = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, ISystemUiVisibilityCallback.DESCRIPTOR);
        }

        public static ISystemUiVisibilityCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(ISystemUiVisibilityCallback.DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof ISystemUiVisibilityCallback)) {
                return (ISystemUiVisibilityCallback) iInterfaceQueryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onSystemUiVisibilityChanged";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(ISystemUiVisibilityCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(ISystemUiVisibilityCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int i3 = parcel.readInt();
                parcel.enforceNoDataAvail();
                onSystemUiVisibilityChanged(i3);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements ISystemUiVisibilityCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return ISystemUiVisibilityCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.cocktailbar.ISystemUiVisibilityCallback
            public void onSystemUiVisibilityChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(ISystemUiVisibilityCallback.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }
    }
}
