package com.samsung.android.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes6.dex */
public interface IFoldStarCallback extends IInterface {
    public static final int BOUNDS_COMPAT_NONE = 0;
    public static final String DESCRIPTOR = "com.samsung.android.core.IFoldStarCallback";
    public static final int DISPLAY_COMPAT_MODE = 1;

    public static class Default implements IFoldStarCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.samsung.android.core.IFoldStarCallback
        public void onBoundsCompatPackageAppeared(int i, String str) throws RemoteException {
        }
    }

    void onBoundsCompatPackageAppeared(int i, String str) throws RemoteException;

    public static abstract class Stub extends Binder implements IFoldStarCallback {
        static final int TRANSACTION_onBoundsCompatPackageAppeared = 1;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 0;
        }

        public Stub() {
            attachInterface(this, IFoldStarCallback.DESCRIPTOR);
        }

        public static IFoldStarCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(IFoldStarCallback.DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof IFoldStarCallback)) {
                return (IFoldStarCallback) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            if (i != 1) {
                return null;
            }
            return "onBoundsCompatPackageAppeared";
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IFoldStarCallback.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IFoldStarCallback.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                int readInt = parcel.readInt();
                String readString = parcel.readString();
                parcel.enforceNoDataAvail();
                onBoundsCompatPackageAppeared(readInt, readString);
                return true;
            }
            return super.onTransact(i, parcel, parcel2, i2);
        }

        private static class Proxy implements IFoldStarCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IFoldStarCallback.DESCRIPTOR;
            }

            @Override // com.samsung.android.core.IFoldStarCallback
            public void onBoundsCompatPackageAppeared(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(IFoldStarCallback.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(1, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }
        }
    }
}
